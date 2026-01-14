package com.wifisentinel.app.replay

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.wifisentinel.core.detectors.AnalyzeContext
import com.wifisentinel.core.detectors.CaptivePortalDetector
import com.wifisentinel.core.detectors.Detector
import com.wifisentinel.core.detectors.DisconnectAnomalyDetector
import com.wifisentinel.core.detectors.DnsIntegrityDetector
import com.wifisentinel.core.detectors.EvilTwinDetector
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingActionResolver
import com.wifisentinel.core.detectors.GatewayAnomalyDetector
import com.wifisentinel.core.detectors.LookalikeSsidDetector
import com.wifisentinel.core.detectors.MeshNewBssidDetector
import com.wifisentinel.core.detectors.PinnedDnsDetector
import com.wifisentinel.core.detectors.UnusualBehaviorDetector
import com.wifisentinel.core.detectors.WeakSecurityDetector
import com.wifisentinel.core.net.CaptivePortalProbe
import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import com.wifisentinel.core.wifi.WifiScanner
import com.wifisentinel.core.detectors.Severity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReplayManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: NetworkRepository,
    private val wifiScanner: WifiScanner,
    private val dnsProbe: DnsProbe,
    private val portalProbe: CaptivePortalProbe,
    private val settingsRepository: SettingsRepository
) {
    suspend fun exportCurrent(maskSensitive: Boolean): Uri = withContext(Dispatchers.IO) {
        val snapshot = repository.latestSnapshotOnce()
            ?: throw IllegalStateException("no snapshot")
        val scanResults = runCatching { wifiScanner.scan() }.getOrDefault(emptyList())
        val scanWithCurrent = ensureCurrentNetworkScan(snapshot, scanResults)
        val dnsCheck = if (settingsRepository.settings.first().dnsCheckEnabled) {
            buildDnsCheck()
        } else {
            null
        }
        val portalCheck = if (snapshot.captivePortal) {
            portalProbe.check()
        } else {
            null
        }
        val payload = ReplayPayload(
            snapshot = snapshot,
            scanResults = scanWithCurrent,
            dnsCheck = dnsCheck,
            portalCheck = portalCheck
        )
        val json = ReplayPayloadCodec.buildPayloadJson(payload, maskSensitive)
        val reportDir = File(context.cacheDir, "replay").apply { mkdirs() }
        val reportFile = File(reportDir, "wifi_sentinel_replay_${System.currentTimeMillis()}.json")
        reportFile.writeText(json.toString(2), Charsets.UTF_8)
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            reportFile
        )
    }

    suspend fun runFromUri(uri: Uri): Boolean = withContext(Dispatchers.IO) {
        val json = context.contentResolver.openInputStream(uri)?.use { input ->
            input.bufferedReader(Charsets.UTF_8).readText()
        } ?: return@withContext false
        val root = JSONObject(json)
        if (root.has("snapshots")) {
            val reportPayload = parseReportPayload(root) ?: return@withContext false
            runReportPayload(reportPayload)
        } else {
            val payload = ReplayPayloadCodec.parsePayload(root)
            runPayload(payload)
        }
        true
    }

    private suspend fun runPayload(payload: ReplayPayload) {
        val now = System.currentTimeMillis()
        val snapshot = payload.snapshot.copy(
            id = UUID.randomUUID().toString(),
            timestampMs = now
        )
        repository.saveSnapshot(snapshot)
        val history = repository.recentSnapshots(snapshot.networkIdHint, 10)
        val trustedProfiles = if (payload.trustedProfiles.isNotEmpty()) {
            payload.trustedProfiles
        } else {
            repository.trustedProfilesOnce()
        }
        val trustedProfile = findTrustedProfile(snapshot, trustedProfiles)
        val category = trustedProfile?.category
        val scanResults = ensureCurrentNetworkScan(snapshot, payload.scanResults)
        val detectors = buildReplayDetectors(payload)
        val findings = detectors.flatMap { detector ->
            detector.analyze(
                AnalyzeContext(
                    current = snapshot,
                    scanResults = scanResults,
                    trustedProfile = trustedProfile,
                    trustedProfiles = trustedProfiles,
                    history = history,
                    category = category
                )
            )
        }
        val findingsWithKeys = findings.map { finding ->
            finding.copy(dedupKey = buildDedupKey(finding, snapshot))
        }
        if (findingsWithKeys.isNotEmpty()) {
            repository.saveFindings(findingsWithKeys, snapshot.timestampMs)
        }
    }

    private fun buildReplayDetectors(payload: ReplayPayload): List<Detector> {
        val replayDnsProbe = ReplayDnsProbe(payload.dnsCheck)
        val replayPortalProbe = ReplayCaptivePortalProbe(payload.portalCheck)
        return listOf(
            EvilTwinDetector(),
            CaptivePortalDetector(replayPortalProbe),
            WeakSecurityDetector(),
            DnsIntegrityDetector(replayDnsProbe, cacheTtlMs = 0L),
            PinnedDnsDetector(),
            GatewayAnomalyDetector(),
            DisconnectAnomalyDetector(),
            MeshNewBssidDetector(),
            UnusualBehaviorDetector(),
            LookalikeSsidDetector()
        )
    }

    private data class ReportPayload(
        val snapshots: List<ReportSnapshot>,
        val events: List<com.wifisentinel.core.storage.NetworkEvent>
    )

    private data class ReportSnapshot(
        val snapshot: NetworkSnapshot,
        val findings: List<Finding>
    )

    private fun parseReportPayload(root: JSONObject): ReportPayload? {
        val snapshotsJson = root.optJSONArray("snapshots") ?: return null
        val snapshots = mutableListOf<ReportSnapshot>()
        for (i in 0 until snapshotsJson.length()) {
            val snapshotJson = snapshotsJson.optJSONObject(i) ?: continue
            val snapshot = parseReportSnapshot(snapshotJson) ?: continue
            val findings = parseReportFindings(snapshotJson.optJSONArray("findings"))
            snapshots.add(ReportSnapshot(snapshot, findings))
        }
        val events = parseReportEvents(root.optJSONArray("events"))
        return ReportPayload(snapshots, events)
    }

    private fun parseReportSnapshot(json: JSONObject): NetworkSnapshot? {
        val security = parseSecurityType(json.optString("securityType"))
        val networkIdHint = json.optString("networkIdHint")
        if (networkIdHint.isBlank()) return null
        return NetworkSnapshot(
            id = json.optString("id").ifBlank { UUID.randomUUID().toString() },
            timestampMs = json.optLong("timestampMs", System.currentTimeMillis()),
            ssid = json.optNullableString("ssid"),
            bssid = json.optNullableString("bssid"),
            securityType = security,
            frequencyMhz = json.optNullableInt("frequencyMhz"),
            rssiDbm = json.optNullableInt("rssiDbm"),
            ipV4 = json.optNullableString("ipV4"),
            gatewayV4 = json.optNullableString("gatewayV4"),
            dnsServers = json.optJSONArray("dnsServers")?.toStringList().orEmpty(),
            captivePortal = json.optBoolean("captivePortal", false),
            networkIdHint = networkIdHint
        )
    }

    private fun parseReportFindings(array: org.json.JSONArray?): List<Finding> {
        if (array == null) return emptyList()
        val findings = mutableListOf<Finding>()
        for (i in 0 until array.length()) {
            val json = array.optJSONObject(i) ?: continue
            val evidenceJson = json.optJSONObject("evidence") ?: JSONObject()
            val evidence = mutableMapOf<String, String>()
            evidenceJson.keys().forEach { key ->
                val value = evidenceJson.optString(key, "")
                if (value.isNotBlank()) {
                    evidence[key] = value
                }
            }
            findings.add(
                Finding(
                    id = json.optString("id").ifBlank { UUID.randomUUID().toString() },
                    snapshotId = json.optString("snapshotId"),
                    detectorId = json.optString("detectorId"),
                    severity = parseSeverity(json.optString("severity")),
                    scoreDelta = json.optInt("scoreDelta", 0),
                    title = json.optString("title"),
                    explanation = json.optString("explanation"),
                    evidence = evidence,
                    actions = emptyList(),
                    dedupKey = ""
                )
            )
        }
        return findings
    }

    private fun parseReportEvents(array: org.json.JSONArray?): List<com.wifisentinel.core.storage.NetworkEvent> {
        if (array == null) return emptyList()
        val events = mutableListOf<com.wifisentinel.core.storage.NetworkEvent>()
        for (i in 0 until array.length()) {
            val json = array.optJSONObject(i) ?: continue
            events.add(
                com.wifisentinel.core.storage.NetworkEvent(
                    id = json.optString("id").ifBlank { UUID.randomUUID().toString() },
                    timestampMs = json.optLong("timestampMs", System.currentTimeMillis()),
                    title = json.optString("title"),
                    detail = json.optString("detail"),
                    severity = parseSeverity(json.optString("severity")),
                    snapshotId = json.optNullableString("snapshotId")
                )
            )
        }
        return events
    }

    private suspend fun runReportPayload(payload: ReportPayload) {
        if (payload.snapshots.isEmpty()) return
        val now = System.currentTimeMillis()
        val maxTimestamp = payload.snapshots.maxOf { it.snapshot.timestampMs }
        val offset = now - maxTimestamp
        val idMap = mutableMapOf<String, String>()
        val adjustedSnapshots = payload.snapshots.map { report ->
            val newId = UUID.randomUUID().toString()
            idMap[report.snapshot.id] = newId
            val newSnapshot = report.snapshot.copy(
                id = newId,
                timestampMs = report.snapshot.timestampMs + offset
            )
            ReportSnapshot(newSnapshot, report.findings)
        }
        adjustedSnapshots.forEach { report ->
            repository.saveSnapshot(report.snapshot)
            val mappedFindings = report.findings.map { finding ->
                val newFindingId = UUID.randomUUID().toString()
                val mapped = finding.copy(
                    id = newFindingId,
                    snapshotId = idMap[finding.snapshotId] ?: report.snapshot.id,
                    actions = FindingActionResolver.resolve(finding.detectorId, finding.severity)
                )
                mapped.copy(dedupKey = buildDedupKey(mapped, report.snapshot))
            }
            repository.saveFindings(mappedFindings, report.snapshot.timestampMs)
        }
        val mappedEvents = payload.events.map { event ->
            event.copy(
                id = UUID.randomUUID().toString(),
                timestampMs = event.timestampMs + offset,
                snapshotId = event.snapshotId?.let { idMap[it] }
            )
        }
        repository.saveEvents(mappedEvents)
        settingsRepository.setDemoModeEnabled(true)
    }

    private suspend fun buildDnsCheck(): DnsCheckPayload? {
        val domains = LinkedHashMap<String, DnsDomainPayload>()
        DNS_CONTROL_DOMAINS.forEach { domain ->
            val system = dnsProbe.resolveSystem(domain).filter { it.isNotBlank() }
            val doh = dnsProbe.resolveDoh(domain).filter { it.isNotBlank() }
            domains[domain] = DnsDomainPayload(system, doh)
        }
        return DnsCheckPayload(domains)
    }

    private fun findTrustedProfile(
        snapshot: NetworkSnapshot,
        profiles: List<TrustedNetworkProfile>
    ): TrustedNetworkProfile? {
        val ssid = snapshot.ssid
        val normalizedSsid = ssid?.trim()?.lowercase()
        return when {
            !ssid.isNullOrBlank() -> profiles.firstOrNull { it.ssid?.trim()?.lowercase() == normalizedSsid }
            snapshot.bssid != null -> profiles.firstOrNull { it.allowedBssids.contains(snapshot.bssid) }
            else -> null
        }
    }

    private fun buildDedupKey(finding: Finding, snapshot: NetworkSnapshot): String {
        val key = normalizedNetworkKey(snapshot)
        val evidenceHash = hashEvidence(finding.evidence)
        return "${finding.detectorId}|$key|$evidenceHash"
    }

    private fun normalizedNetworkKey(snapshot: NetworkSnapshot): String {
        val hint = snapshot.networkIdHint.trim().lowercase()
        val ssid = snapshot.ssid?.trim()?.lowercase()
        val bssid = snapshot.bssid?.trim()?.lowercase()
        return hint.ifBlank { ssid ?: bssid ?: "unknown" }
    }

    private fun hashEvidence(evidence: Map<String, String>): String {
        val normalized = evidence.toSortedMap().entries.joinToString("|") { entry ->
            "${entry.key}=${entry.value}"
        }
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(normalized.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun parseSecurityType(value: String): SecurityType {
        return runCatching { SecurityType.valueOf(value) }.getOrDefault(SecurityType.UNKNOWN)
    }

    private fun parseSeverity(value: String): Severity {
        return runCatching { Severity.valueOf(value) }.getOrDefault(Severity.INFO)
    }

    private fun JSONObject.optNullableString(key: String): String? {
        return if (isNull(key)) null else optString(key)
    }

    private fun JSONObject.optNullableInt(key: String): Int? {
        return if (isNull(key)) null else optInt(key)
    }

    private fun org.json.JSONArray.toStringList(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until length()) {
            val value = optString(i, "")
            if (value.isNotBlank()) list.add(value)
        }
        return list
    }

    private fun ensureCurrentNetworkScan(
        snapshot: NetworkSnapshot,
        scanResults: List<ScanNet>
    ): List<ScanNet> {
        val ssid = snapshot.ssid?.trim()
        val bssid = snapshot.bssid?.trim()
        if (ssid.isNullOrBlank() && bssid.isNullOrBlank()) return scanResults
        val hasMatch = scanResults.any { net ->
            (bssid != null && net.bssid?.equals(bssid, ignoreCase = true) == true) ||
                (!ssid.isNullOrBlank() && net.ssid?.equals(ssid, ignoreCase = true) == true)
        }
        if (hasMatch) return scanResults
        return scanResults + ScanNet(
            ssid = ssid,
            bssid = bssid,
            frequencyMhz = snapshot.frequencyMhz,
            rssiDbm = snapshot.rssiDbm,
            securityType = snapshot.securityType,
            channelWidth = null,
            wifiStandard = null
        )
    }

    private companion object {
        val DNS_CONTROL_DOMAINS = listOf("example.com", "example.org", "example.net")
    }
}

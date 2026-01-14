package com.wifisentinel.app.monitor

import android.content.Context
import com.wifisentinel.app.R
import com.wifisentinel.app.di.ApplicationScope
import com.wifisentinel.app.notifications.NotificationHelper
import com.wifisentinel.app.permissions.WifiPermissions
import com.wifisentinel.core.detectors.AnalyzeContext
import com.wifisentinel.core.detectors.Detector
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.storage.NetworkEvent
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.NetworkObserver
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.NetworkSnapshotProvider
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import com.wifisentinel.core.wifi.WifiScanner
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.JvmSuppressWildcards

@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkObserver: NetworkObserver,
    private val snapshotProvider: NetworkSnapshotProvider,
    private val wifiScanner: WifiScanner,
    private val repository: NetworkRepository,
    private val detectors: List<@JvmSuppressWildcards Detector>,
    private val notificationHelper: NotificationHelper,
    private val settingsRepository: SettingsRepository,
    @ApplicationScope private val appScope: CoroutineScope
) {
    private val started = AtomicBoolean(false)
    private val processingMutex = Mutex()
    private var lastScanMs: Long = 0L
    private var lastAlertMs: Long = 0L
    private val lastEventTimestamps = mutableMapOf<String, Long>()
    private val hasConnectedOnce = AtomicBoolean(false)

    fun start() {
        if (!started.compareAndSet(false, true)) return

        networkObserver.snapshots
            .onEach { snapshot -> processSnapshot(snapshot) }
            .launchIn(appScope)
    }

    fun refreshSnapshot(force: Boolean = false) {
        appScope.launch { runHealthCheck(force) }
    }

    suspend fun runHealthCheck(force: Boolean = false): Boolean {
        if (settingsRepository.settings.first().demoModeEnabled) return false
        val snapshot = snapshotProvider.currentSnapshot() ?: return false
        processSnapshot(snapshot, force)
        return true
    }

    private fun snapshotKey(snapshot: NetworkSnapshot): String {
        return listOf(
            snapshot.networkIdHint,
            snapshot.bssid ?: "",
            snapshot.securityType.name,
            snapshot.gatewayV4 ?: "",
            snapshot.captivePortal.toString()
        ).joinToString("|")
    }

    private suspend fun processSnapshot(snapshot: NetworkSnapshot, force: Boolean = false) {
        processingMutex.withLock {
            withContext(Dispatchers.IO) {
                if (settingsRepository.settings.first().demoModeEnabled) return@withContext
                val now = snapshot.timestampMs
                val previous = repository.latestSnapshotOnce()
                val sameKey = previous != null && snapshotKey(previous) == snapshotKey(snapshot)
                val isNewNetwork = previous == null || previous.networkIdHint != snapshot.networkIdHint
                val connected = isConnectedSnapshot(snapshot)
                val wasDisconnected = previous != null && !isConnectedSnapshot(previous) && connected
                val reconnect = previous != null &&
                    previous.networkIdHint == snapshot.networkIdHint &&
                    (wasDisconnected || now - previous.timestampMs > RECONNECT_SCAN_THRESHOLD_MS)
                val connectionEvent = connected && (
                    isNewNetwork || reconnect || shouldLogEvent(
                        "connect-scan|${snapshot.networkIdHint}",
                        now,
                        CONNECT_SCAN_THROTTLE_MS
                    )
                )
                if (!force && sameKey && !connectionEvent) {
                    val delta = now - (previous?.timestampMs ?: now)
                    if (delta < RECONNECT_SCAN_THRESHOLD_MS) {
                        return@withContext
                    }
                }

                val history = repository.recentSnapshots(snapshot.networkIdHint, 10)
                repository.saveSnapshot(snapshot)
                val forceScan = force || connectionEvent
                val readyForDetectors = connected && (hasConnectedOnce.get() || forceScan)
                val scanResults = if (readyForDetectors && canRecordEvents()) {
                    maybeScan(forceScan)
                } else {
                    emptyList()
                }
                val scanWithCurrent = if (readyForDetectors) {
                    ensureCurrentNetworkScan(snapshot, scanResults)
                } else {
                    scanResults
                }
                val trustedProfiles = if (readyForDetectors) repository.trustedProfilesOnce() else emptyList()
                val trustedProfile = if (readyForDetectors) {
                    findTrustedProfile(snapshot, trustedProfiles)
                } else {
                    null
                }
                val category = trustedProfile?.category

                val findings = if (readyForDetectors) {
                    detectors.flatMap { detector ->
                        detector.analyze(
                            AnalyzeContext(
                                current = snapshot,
                                scanResults = scanWithCurrent,
                                trustedProfile = trustedProfile,
                                trustedProfiles = trustedProfiles,
                                history = history,
                                category = category
                            )
                        )
                    }
                } else {
                    emptyList()
                }

                val findingsWithKeys = if (findings.isNotEmpty()) {
                    findings.map { finding ->
                        finding.copy(dedupKey = buildDedupKey(finding, snapshot))
                    }
                } else {
                    emptyList()
                }

                if (findingsWithKeys.isNotEmpty()) {
                    repository.saveFindings(findingsWithKeys, snapshot.timestampMs)
                }

                if (readyForDetectors) {
                    maybeLearnBssid(trustedProfile, snapshot, history)
                }

                if (canRecordEvents()) {
                    val findingsForAlerts = filterFindingsForCooldown(findingsWithKeys, snapshot.timestampMs)
                    val events = buildEvents(snapshot, previous, findingsForAlerts)
                    repository.saveEvents(events)
                    if (connectionEvent) {
                        notifyNetworkReport(snapshot, findingsForAlerts, force = true)
                    }
                    if (hasConnectedOnce.get()) {
                        notifyIfNeeded(snapshot, findingsForAlerts)
                    }
                }
            }
        }
    }

    private suspend fun filterFindingsForCooldown(
        findings: List<Finding>,
        now: Long
    ): List<Finding> {
        if (findings.isEmpty()) return emptyList()
        val result = ArrayList<Finding>(findings.size)
        for (finding in findings) {
            val lastTimestamp = repository.latestFindingTimestamp(finding.dedupKey)
            if (lastTimestamp == null || now - lastTimestamp >= FINDING_DEDUP_THROTTLE_MS) {
                result.add(finding)
            }
        }
        return result
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

    private suspend fun maybeLearnBssid(
        trustedProfile: TrustedNetworkProfile?,
        snapshot: NetworkSnapshot,
        history: List<NetworkSnapshot>
    ) {
        val profile = trustedProfile ?: return
        if (!profile.meshMode || !profile.bssidLearning) return
        val bssid = snapshot.bssid?.trim() ?: return
        if (bssid.isBlank() || profile.allowedBssids.contains(bssid)) return

        val cutoff = snapshot.timestampMs - ONE_DAY_MS
        val observed = (history + snapshot)
            .filter { it.timestampMs >= cutoff }
            .mapNotNull { it.bssid?.lowercase() }
            .toSet()
        val known = profile.allowedBssids.map { it.lowercase() }.toSet()
        val newOnes = observed.filter { it !in known }

        if (newOnes.size <= profile.maxNewBssidPerDay) {
            repository.upsertTrustedProfile(
                profile.copy(
                    allowedBssids = profile.allowedBssids + bssid,
                    lastSeenMs = snapshot.timestampMs
                )
            )
        }
    }

    private fun hashEvidence(evidence: Map<String, String>): String {
        val normalized = evidence.toSortedMap().entries.joinToString("|") { entry ->
            "${entry.key}=${entry.value}"
        }
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(normalized.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private suspend fun maybeScan(force: Boolean = false): List<ScanNet> {
        val now = System.currentTimeMillis()
        if (force && now - lastScanMs < FORCE_SCAN_THROTTLE_MS) return emptyList()
        if (!force && now - lastScanMs < 30_000L) return emptyList()
        lastScanMs = now
        return wifiScanner.scan()
    }

    private fun buildEvents(
        snapshot: NetworkSnapshot,
        previous: NetworkSnapshot?,
        findings: List<Finding>
    ): List<NetworkEvent> {
        val events = mutableListOf<NetworkEvent>()
        val now = snapshot.timestampMs
        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        if (!hasConnectedOnce.get()) {
            if (isConnectedSnapshot(snapshot)) {
                hasConnectedOnce.set(true)
                events.add(
                    NetworkEvent(
                        id = UUID.randomUUID().toString(),
                        timestampMs = now,
                        title = context.getString(R.string.event_connected_title),
                        detail = context.getString(R.string.event_connected_detail, ssidLabel),
                        severity = Severity.INFO,
                        snapshotId = snapshot.id
                    )
                )
            }
            return events
        }

        if (previous == null) {
            return emptyList()
        }

        if (previous.networkIdHint != snapshot.networkIdHint) {
            if (shouldLogEvent("connect|${snapshot.networkIdHint}", now, CONNECT_THROTTLE_MS)) {
                events.add(
                    NetworkEvent(
                        id = UUID.randomUUID().toString(),
                        timestampMs = now,
                        title = context.getString(R.string.event_connected_title),
                        detail = context.getString(R.string.event_connected_detail, ssidLabel),
                        severity = Severity.INFO,
                        snapshotId = snapshot.id
                    )
                )
            }
        } else {
            if (previous.bssid != null && snapshot.bssid != null && previous.bssid != snapshot.bssid) {
                if (shouldLogEvent("bssid|${snapshot.networkIdHint}|${snapshot.bssid}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_bssid_change_title),
                            detail = context.getString(R.string.event_bssid_change_detail, ssidLabel),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
            if (previous.securityType != snapshot.securityType) {
                if (shouldLogEvent("security|${snapshot.networkIdHint}|${snapshot.securityType.name}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_security_change_title),
                            detail = context.getString(
                                R.string.event_security_change_detail,
                                ssidLabel,
                                securityLabel(snapshot.securityType)
                            ),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
            if (!previous.captivePortal && snapshot.captivePortal) {
                if (shouldLogEvent("captive|${snapshot.networkIdHint}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_captive_portal_title),
                            detail = context.getString(R.string.event_captive_portal_detail, ssidLabel),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
        }

        findings.forEach { finding ->
            val key = "finding|${snapshot.networkIdHint}|${finding.detectorId}|${finding.severity.name}"
            if (shouldLogEvent(key, now, FINDING_THROTTLE_MS)) {
                events.add(
                    NetworkEvent(
                        id = UUID.randomUUID().toString(),
                        timestampMs = now,
                        title = FindingTextResolver.title(context, finding),
                        detail = friendlyFindingDetail(finding),
                        severity = finding.severity,
                        snapshotId = finding.snapshotId
                    )
                )
            }
        }

        return events
    }

    private suspend fun notifyIfNeeded(
        snapshot: NetworkSnapshot,
        findings: List<Finding>
    ) {
        val settings = settingsRepository.settings.first()
        if (!settings.notificationsEnabled) return
        if (!hasConnectedOnce.get()) return
        val severe = findings.filter { it.severity == Severity.HIGH || it.severity == Severity.CRITICAL }
        if (severe.isEmpty()) return
        val now = System.currentTimeMillis()
        if (now - lastAlertMs < ALERT_THROTTLE_MS) return
        lastAlertMs = now

        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        val first = severe.first()
        val extra = if (severe.size > 1) " +${severe.size - 1}" else ""
        val title = context.getString(R.string.notif_alert_title)
        val message = "$ssidLabel: ${FindingTextResolver.title(context, first)}$extra"
        notificationHelper.notifyRiskAlert(title, message)
    }

    private suspend fun notifyNetworkReport(
        snapshot: NetworkSnapshot,
        findings: List<Finding>,
        force: Boolean
    ) {
        val settings = settingsRepository.settings.first()
        if (!settings.notificationsEnabled) return
        val key = if (force) {
            "report-connect|${snapshot.networkIdHint}"
        } else {
            "report|${snapshot.networkIdHint}"
        }
        val throttleMs = if (force) RECONNECT_REPORT_THROTTLE_MS else REPORT_THROTTLE_MS
        if (!shouldLogEvent(key, snapshot.timestampMs, throttleMs)) return

        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        val safety = safetyLabel(findings)
        val separator = context.getString(R.string.value_separator)
        val message = buildString {
            append(ssidLabel)
            append(separator)
            append(securityLabel(snapshot.securityType))
            append(separator)
            append(signalLabel(snapshot.rssiDbm))
            append(separator)
            append(safety)
        }
        val notificationId = (snapshot.networkIdHint.hashCode() and 0x7fffffff) + 2000
        val showForget = findings.any { it.severity == Severity.HIGH || it.severity == Severity.CRITICAL }
        notificationHelper.notifyNetworkReport(
            notificationId,
            context.getString(R.string.notif_network_report_title),
            message,
            showForget,
            snapshot.ssid
        )
    }

    private fun canRecordEvents(): Boolean {
        return WifiPermissions.hasRequiredPermissions(context)
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

    private fun isConnectedSnapshot(snapshot: NetworkSnapshot): Boolean {
        return !snapshot.ssid.isNullOrBlank() || !snapshot.bssid.isNullOrBlank()
    }

    private fun shouldLogEvent(key: String, now: Long, throttleMs: Long): Boolean {
        val last = lastEventTimestamps[key]
        if (last != null && now - last < throttleMs) return false
        lastEventTimestamps[key] = now
        return true
    }

    private fun securityLabel(type: SecurityType): String {
        return when (type) {
            SecurityType.OPEN -> context.getString(R.string.security_open)
            SecurityType.WEP -> context.getString(R.string.security_wep)
            SecurityType.WPA2 -> context.getString(R.string.security_wpa2)
            SecurityType.WPA3 -> context.getString(R.string.security_wpa3)
            SecurityType.WPA2_WPA3 -> context.getString(R.string.security_wpa2_wpa3)
            SecurityType.UNKNOWN -> context.getString(R.string.security_unknown)
        }
    }

    private fun safetyLabel(findings: List<Finding>): String {
        return when {
            findings.any { it.severity == Severity.CRITICAL || it.severity == Severity.HIGH } ->
                context.getString(R.string.safety_risk_high)
            findings.any { it.severity == Severity.WARN } ->
                context.getString(R.string.safety_risk_warn)
            else -> context.getString(R.string.safety_risk_low)
        }
    }

    private fun signalLabel(rssiDbm: Int?): String {
        if (rssiDbm == null) return context.getString(R.string.signal_no_data)
        return when {
            rssiDbm >= -50 -> context.getString(R.string.signal_excellent)
            rssiDbm >= -60 -> context.getString(R.string.signal_good)
            rssiDbm >= -70 -> context.getString(R.string.signal_medium)
            rssiDbm >= -80 -> context.getString(R.string.signal_weak)
            else -> context.getString(R.string.signal_very_weak)
        }
    }

    private fun friendlyFindingDetail(finding: Finding): String {
        return when (finding.detectorId) {
            "captive_portal" -> context.getString(R.string.finding_detail_captive_portal)
            "weak_security" -> context.getString(R.string.finding_detail_weak_security)
            "dns_integrity" -> context.getString(R.string.finding_detail_dns_integrity)
            "gateway_anomaly" -> context.getString(R.string.finding_detail_gateway_anomaly)
            "disconnect_anomaly" -> context.getString(R.string.finding_detail_disconnect_anomaly)
            else -> FindingTextResolver.explanation(context, finding)
        }
    }

    private companion object {
        const val ALERT_THROTTLE_MS = 2 * 60 * 1000L
        const val EVENT_THROTTLE_MS = 10 * 60 * 1000L
        const val FINDING_THROTTLE_MS = 30 * 60 * 1000L
        const val FINDING_DEDUP_THROTTLE_MS = 10 * 60 * 1000L
        const val CONNECT_THROTTLE_MS = 2 * 60 * 1000L
        const val REPORT_THROTTLE_MS = 60 * 1000L
        const val CONNECT_SCAN_THROTTLE_MS = 5 * 1000L
        const val RECONNECT_SCAN_THRESHOLD_MS = 1_000L
        const val FORCE_SCAN_THROTTLE_MS = 10_000L
        const val RECONNECT_REPORT_THROTTLE_MS = 10 * 1000L
        const val ONE_DAY_MS = 24 * 60 * 60 * 1000L
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
}

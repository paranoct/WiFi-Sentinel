package com.wifisentinel.app.report

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.wifisentinel.app.R
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.risk.RiskEngine
import com.wifisentinel.core.risk.RiskSummary
import com.wifisentinel.core.risk.RiskTextResolver
import com.wifisentinel.core.storage.NetworkEvent
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.NetworkSnapshot
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportExporter @Inject constructor(
    private val repository: NetworkRepository,
    private val riskEngine: RiskEngine,
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun export(): Uri {
        return withContext(Dispatchers.IO) {
            val snapshots = repository.latestSnapshotsOnce(SNAPSHOT_LIMIT)
            if (snapshots.isEmpty()) {
                throw IllegalStateException(context.getString(R.string.report_export_no_data))
            }
            val findingsBySnapshot = snapshots.associate { snapshot ->
                snapshot.id to repository.findingsForSnapshotOnce(snapshot.id)
            }
            val events = repository.latestEventsOnce(EVENT_LIMIT)
            val riskSummary = buildRiskSummary(snapshots, findingsBySnapshot)
            val settings = settingsRepository.settings.first()

            val json = buildReportJson(
                snapshots = snapshots,
                findingsBySnapshot = findingsBySnapshot,
                events = events,
                riskSummary = riskSummary,
                maskSensitive = settings.maskSensitive,
                reportType = "full",
                networkIdHint = null
            )
            val reportDir = File(context.cacheDir, "reports").apply { mkdirs() }
            val reportFile = File(reportDir, "wifi_sentinel_report_${System.currentTimeMillis()}.json")
            reportFile.writeText(json.toString(2), Charsets.UTF_8)

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                reportFile
            )
        }
    }

    private suspend fun buildRiskSummary(
        snapshots: List<NetworkSnapshot>,
        findingsBySnapshot: Map<String, List<Finding>>
    ): RiskSummary {
        val latestSnapshot = snapshots.firstOrNull() ?: return RiskSummary.empty()
        val latestFindings = findingsBySnapshot[latestSnapshot.id].orEmpty()
        val trusted = repository.findTrustedProfile(latestSnapshot)
        return riskEngine.evaluate(latestFindings, trusted?.category)
    }

    private fun buildReportJson(
        snapshots: List<NetworkSnapshot>,
        findingsBySnapshot: Map<String, List<Finding>>,
        events: List<NetworkEvent>,
        riskSummary: RiskSummary,
        maskSensitive: Boolean,
        reportType: String?,
        networkIdHint: String?
    ): JSONObject {
        val root = JSONObject()
        root.put("version", 1)
        reportType?.let { root.put("reportType", it) }
        networkIdHint?.let { root.put("networkIdHint", it) }
        root.put("generatedAtMs", System.currentTimeMillis())
        root.put("device", buildDeviceJson())
        root.put("risk", buildRiskJson(riskSummary, maskSensitive))
        root.put("snapshots", buildSnapshotsJson(snapshots, findingsBySnapshot, maskSensitive))
        root.put("events", buildEventsJson(events, maskSensitive))
        return root
    }

    private fun buildDeviceJson(): JSONObject {
        val device = JSONObject()
        device.put("manufacturer", Build.MANUFACTURER)
        device.put("model", Build.MODEL)
        device.put("sdkInt", Build.VERSION.SDK_INT)
        device.put("versionRelease", Build.VERSION.RELEASE ?: "")
        device.put("locale", Locale.getDefault().toLanguageTag())
        return device
    }

    private fun buildRiskJson(summary: RiskSummary, maskSensitive: Boolean): JSONObject {
        val risk = JSONObject()
        risk.put("score", summary.score)
        risk.put("level", summary.level.name)
        risk.put("summary", RiskTextResolver.resolve(context, summary.summary, summary.summaryArgs))
        val actions = JSONArray()
        summary.actions.forEach { actions.put(RiskTextResolver.resolve(context, it)) }
        risk.put("actions", actions)
        val top = JSONArray()
        summary.topFindings.forEach { finding ->
            top.put(buildFindingJson(finding, maskSensitive = maskSensitive))
        }
        risk.put("topFindings", top)
        return risk
    }

    private fun buildSnapshotsJson(
        snapshots: List<NetworkSnapshot>,
        findingsBySnapshot: Map<String, List<Finding>>,
        maskSensitive: Boolean
    ): JSONArray {
        val array = JSONArray()
        snapshots.forEach { snapshot ->
            val json = JSONObject()
            json.put("id", snapshot.id)
            json.put("timestampMs", snapshot.timestampMs)
            json.putNullable("ssid", maskValue(snapshot.ssid, maskSensitive))
            json.putNullable("bssid", maskValue(snapshot.bssid, maskSensitive))
            json.put("securityType", snapshot.securityType.name)
            json.putNullable("frequencyMhz", snapshot.frequencyMhz)
            json.putNullable("rssiDbm", snapshot.rssiDbm)
            json.putNullable("ipV4", snapshot.ipV4)
            json.putNullable("gatewayV4", snapshot.gatewayV4)
            json.put("dnsServers", JSONArray(snapshot.dnsServers))
            json.put("captivePortal", snapshot.captivePortal)
            json.put("networkIdHint", snapshot.networkIdHint)
            val findingsArray = JSONArray()
            findingsBySnapshot[snapshot.id].orEmpty().forEach { finding ->
                findingsArray.put(buildFindingJson(finding, maskSensitive))
            }
            json.put("findings", findingsArray)
            array.put(json)
        }
        return array
    }

    private fun buildFindingJson(finding: Finding, maskSensitive: Boolean): JSONObject {
        val json = JSONObject()
        json.put("id", finding.id)
        json.put("snapshotId", finding.snapshotId)
        json.put("detectorId", finding.detectorId)
        json.put("severity", finding.severity.name)
        json.put("scoreDelta", finding.scoreDelta)
        json.put("title", FindingTextResolver.title(context, finding))
        json.put("explanation", FindingTextResolver.explanation(context, finding))
        val evidenceJson = JSONObject()
        finding.evidence.forEach { (key, value) ->
            val masked = if (maskSensitive && isSensitiveKey(key)) maskValue(value, true) else value
            evidenceJson.put(key, masked)
        }
        json.put("evidence", evidenceJson)
        return json
    }

    private fun buildEventsJson(events: List<NetworkEvent>, maskSensitive: Boolean): JSONArray {
        val array = JSONArray()
        events.forEach { event ->
            val json = JSONObject()
            json.put("id", event.id)
            json.put("timestampMs", event.timestampMs)
            json.put("title", maskSensitiveValue(event.title, maskSensitive))
            json.put("detail", maskSensitiveValue(event.detail, maskSensitive))
            json.put("severity", event.severity.name)
            json.putNullable("snapshotId", event.snapshotId)
            array.put(json)
        }
        return array
    }

    private fun JSONObject.putNullable(key: String, value: Any?) {
        if (value == null) {
            put(key, JSONObject.NULL)
        } else {
            put(key, value)
        }
    }

    suspend fun exportCurrentNetwork(maskSensitive: Boolean): Uri {
        return withContext(Dispatchers.IO) {
            val latest = repository.latestSnapshotOnce()
                ?: throw IllegalStateException(context.getString(R.string.report_export_no_data))
            val snapshots = if (latest.networkIdHint.isNullOrBlank()) {
                listOf(latest)
            } else {
                repository.recentSnapshots(latest.networkIdHint, SNAPSHOT_LIMIT).ifEmpty { listOf(latest) }
            }
            val findingsBySnapshot = snapshots.associate { snapshot ->
                snapshot.id to repository.findingsForSnapshotOnce(snapshot.id)
            }
            val snapshotIds = snapshots.map { it.id }.toSet()
            val events = repository.latestEventsOnce(EVENT_LIMIT)
                .filter { it.snapshotId != null && snapshotIds.contains(it.snapshotId) }
            val riskSummary = buildRiskSummary(snapshots, findingsBySnapshot)

            val json = buildReportJson(
                snapshots = snapshots,
                findingsBySnapshot = findingsBySnapshot,
                events = events,
                riskSummary = riskSummary,
                maskSensitive = maskSensitive,
                reportType = "network",
                networkIdHint = latest.networkIdHint
            )
            val reportDir = File(context.cacheDir, "reports").apply { mkdirs() }
            val reportFile = File(reportDir, "wifi_sentinel_network_${System.currentTimeMillis()}.json")
            reportFile.writeText(json.toString(2), Charsets.UTF_8)

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                reportFile
            )
        }
    }

    private fun maskValue(value: String?, enabled: Boolean): String? {
        if (!enabled || value.isNullOrBlank()) return value
        val trimmed = value.trim()
        if (trimmed.length <= 3) return "***"
        return trimmed.take(3) + "***"
    }

    private fun isSensitiveKey(key: String): Boolean {
        val normalized = key.lowercase(Locale.getDefault())
        return normalized.contains("ssid") || normalized.contains("bssid")
    }

    private fun maskSensitiveValue(value: String?, enabled: Boolean): String? {
        if (!enabled || value.isNullOrBlank()) return value
        return value.replace(Regex("(?i)(SSID|BSSID)\\s*[:=]\\s*[^\\s,;]+")) { match ->
            val parts = match.value.split(':', '=')
            if (parts.size < 2) match.value else "${parts[0]}: ${maskValue(parts[1].trim(), true)}"
        }
    }

    private companion object {
        const val SNAPSHOT_LIMIT = 20
        const val EVENT_LIMIT = 200
    }
}

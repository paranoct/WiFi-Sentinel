package com.wifisentinel.app.report

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.wifisentinel.app.R
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.risk.RiskEngine
import com.wifisentinel.core.risk.RiskLevel
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
import java.text.SimpleDateFormat
import java.util.Date
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

            val generatedAtMs = System.currentTimeMillis()
            val json = buildReportJson(
                generatedAtMs = generatedAtMs,
                snapshots = snapshots,
                findingsBySnapshot = findingsBySnapshot,
                events = events,
                riskSummary = riskSummary,
                maskSensitive = settings.maskSensitive,
                reportType = "full",
                networkIdHint = null
            )
            val reportDir = reportDirectory()
            val reportFile = File(reportDir, "wifi_sentinel_report_$generatedAtMs.json")
            reportFile.writeText(json.toString(2), Charsets.UTF_8)

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                reportFile
            )
        }
    }

    suspend fun exportCurrentNetwork(maskSensitive: Boolean): Uri {
        return withContext(Dispatchers.IO) {
            val data = loadNetworkData(networkIdHint = null)
            val generatedAtMs = System.currentTimeMillis()
            val json = buildReportJson(
                generatedAtMs = generatedAtMs,
                snapshots = data.snapshots,
                findingsBySnapshot = data.findingsBySnapshot,
                events = data.events,
                riskSummary = data.riskSummary,
                maskSensitive = maskSensitive,
                reportType = "network",
                networkIdHint = data.networkIdHint
            )
            val reportFile = File(reportDirectory(), "wifi_sentinel_network_$generatedAtMs.json")
            reportFile.writeText(json.toString(2), Charsets.UTF_8)

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                reportFile
            )
        }
    }

    suspend fun exportNetworkBundle(networkIdHint: String, maskSensitive: Boolean): List<Uri> {
        return withContext(Dispatchers.IO) {
            val data = loadNetworkData(networkIdHint)
            val generatedAtMs = System.currentTimeMillis()
            val json = buildReportJson(
                generatedAtMs = generatedAtMs,
                snapshots = data.snapshots,
                findingsBySnapshot = data.findingsBySnapshot,
                events = data.events,
                riskSummary = data.riskSummary,
                maskSensitive = maskSensitive,
                reportType = "timeline-network",
                networkIdHint = data.networkIdHint
            )
            val html = buildReportHtml(
                generatedAtMs = generatedAtMs,
                networkIdHint = data.networkIdHint,
                snapshots = data.snapshots,
                findingsBySnapshot = data.findingsBySnapshot,
                events = data.events,
                riskSummary = data.riskSummary,
                maskSensitive = maskSensitive
            )

            val safeNetwork = safeFilePart(data.networkIdHint)
            val reportDir = reportDirectory()
            val jsonFile = File(reportDir, "wifi_sentinel_${safeNetwork}_$generatedAtMs.json")
            val htmlFile = File(reportDir, "wifi_sentinel_${safeNetwork}_$generatedAtMs.html")
            jsonFile.writeText(json.toString(2), Charsets.UTF_8)
            htmlFile.writeText(html, Charsets.UTF_8)

            listOf(
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", jsonFile),
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", htmlFile)
            )
        }
    }

    private suspend fun loadNetworkData(networkIdHint: String?): NetworkExportData {
        val latest = repository.latestSnapshotOnce()
            ?: throw IllegalStateException(context.getString(R.string.report_export_no_data))

        val targetId = networkIdHint ?: latest.networkIdHint
        val snapshots = if (targetId.isNullOrBlank()) {
            listOf(latest)
        } else {
            repository.recentSnapshots(targetId, SNAPSHOT_LIMIT).ifEmpty {
                if (latest.networkIdHint == targetId) listOf(latest) else emptyList()
            }
        }
        if (snapshots.isEmpty()) {
            throw IllegalStateException(context.getString(R.string.report_export_no_data))
        }

        val findingsBySnapshot = snapshots.associate { snapshot ->
            snapshot.id to repository.findingsForSnapshotOnce(snapshot.id)
        }
        val snapshotIds = snapshots.map { it.id }.toSet()
        val events = repository.latestEventsOnce(EVENT_LIMIT)
            .filter { it.snapshotId != null && snapshotIds.contains(it.snapshotId) }
            .sortedByDescending { it.timestampMs }
        val riskSummary = buildRiskSummary(snapshots, findingsBySnapshot)

        return NetworkExportData(
            networkIdHint = targetId,
            snapshots = snapshots,
            findingsBySnapshot = findingsBySnapshot,
            events = events,
            riskSummary = riskSummary
        )
    }

    private fun reportDirectory(): File {
        return File(context.cacheDir, "reports").apply { mkdirs() }
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
        generatedAtMs: Long,
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
        root.put("generatedAtMs", generatedAtMs)
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

    private fun buildReportHtml(
        generatedAtMs: Long,
        networkIdHint: String,
        snapshots: List<NetworkSnapshot>,
        findingsBySnapshot: Map<String, List<Finding>>,
        events: List<NetworkEvent>,
        riskSummary: RiskSummary,
        maskSensitive: Boolean
    ): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val generatedAt = dateFormat.format(Date(generatedAtMs))
        val latest = snapshots.firstOrNull()
        val networkTitle = maskValue(latest?.ssid, maskSensitive)
            ?: latest?.ssid
            ?: context.getString(R.string.network_hidden)
        val summaryText = RiskTextResolver.resolve(context, riskSummary.summary, riskSummary.summaryArgs)
        val actions = if (riskSummary.actions.isEmpty()) {
            "<li>Явных срочных действий нет.</li>"
        } else {
            riskSummary.actions.joinToString(separator = "") { action ->
                "<li>${htmlEscape(RiskTextResolver.resolve(context, action))}</li>"
            }
        }

        val eventsHtml = if (events.isEmpty()) {
            "<li class=\"muted\">События по этой сети не зафиксированы.</li>"
        } else {
            events.joinToString(separator = "") { event ->
                val time = htmlEscape(dateFormat.format(Date(event.timestampMs)))
                val title = htmlEscape(maskSensitiveValue(event.title, maskSensitive) ?: "")
                val detail = htmlEscape(maskSensitiveValue(event.detail, maskSensitive) ?: "")
                """
                <li>
                  <div class="row between"><strong>$title</strong><span class="badge">$time</span></div>
                  <div class="muted">$detail</div>
                </li>
                """.trimIndent()
            }
        }

        val snapshotsHtml = snapshots.joinToString(separator = "") { snapshot ->
            val ssid = htmlEscape(maskValue(snapshot.ssid, maskSensitive) ?: snapshot.ssid ?: context.getString(R.string.network_hidden))
            val bssid = htmlEscape(maskValue(snapshot.bssid, maskSensitive) ?: snapshot.bssid ?: "-")
            val dns = if (snapshot.dnsServers.isEmpty()) "-" else snapshot.dnsServers.joinToString()
            val findings = findingsBySnapshot[snapshot.id].orEmpty()
            val findingsHtml = if (findings.isEmpty()) {
                "<div class=\"muted\">Угрозы не найдены.</div>"
            } else {
                findings.joinToString(separator = "") { finding ->
                    val title = htmlEscape(FindingTextResolver.title(context, finding))
                    val explanation = htmlEscape(FindingTextResolver.explanation(context, finding))
                    val evidence = if (finding.evidence.isEmpty()) {
                        ""
                    } else {
                        val items = finding.evidence.entries.joinToString(separator = "") { (key, value) ->
                            val shownValue = if (maskSensitive && isSensitiveKey(key)) {
                                maskValue(value, true) ?: value
                            } else {
                                value
                            }
                            "<li><span class=\"muted\">${htmlEscape(key)}:</span> ${htmlEscape(shownValue)}</li>"
                        }
                        "<ul>$items</ul>"
                    }
                    """
                    <article class="finding">
                      <div class="row between">
                        <strong>$title</strong>
                        <span class="badge">${severityLabel(finding.severity.name)}</span>
                      </div>
                      <p>$explanation</p>
                      $evidence
                    </article>
                    """.trimIndent()
                }
            }

            """
            <section class="card">
              <div class="row between"><h3>${htmlEscape(dateFormat.format(Date(snapshot.timestampMs)))}</h3><span class="badge">${htmlEscape(snapshot.securityType.name)}</span></div>
              <div class="grid">
                <div><span class="muted">SSID</span><div>$ssid</div></div>
                <div><span class="muted">BSSID</span><div>$bssid</div></div>
                <div><span class="muted">DNS</span><div>${htmlEscape(dns)}</div></div>
                <div><span class="muted">Сигнал</span><div>${snapshot.rssiDbm ?: "-"} dBm</div></div>
              </div>
              <h4>Найденные угрозы</h4>
              $findingsHtml
            </section>
            """.trimIndent()
        }

        return """
        <!doctype html>
        <html lang="ru">
        <head>
          <meta charset="utf-8" />
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <title>WiFi Sentinel - Отчёт по сети</title>
          <style>
            :root {
              --bg: #111214;
              --panel: #17191c;
              --text: #e8eaed;
              --muted: #9aa0a6;
              --border: #2a2d31;
            }
            body[data-theme='light'] {
              --bg: #f4f5f7;
              --panel: #ffffff;
              --text: #1a1c1f;
              --muted: #61656b;
              --border: #d9dde3;
            }
            * { box-sizing: border-box; }
            body {
              margin: 0;
              font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
              background: var(--bg);
              color: var(--text);
              line-height: 1.45;
            }
            .wrap {
              max-width: 980px;
              margin: 0 auto;
              padding: 24px 16px 40px;
            }
            .row {
              display: flex;
              align-items: center;
              gap: 10px;
              flex-wrap: wrap;
            }
            .between { justify-content: space-between; }
            .grid {
              display: grid;
              grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
              gap: 10px;
              margin-bottom: 12px;
            }
            .card {
              background: var(--panel);
              border: 1px solid var(--border);
              border-radius: 12px;
              padding: 14px;
              margin-bottom: 12px;
            }
            .badge {
              border: 1px solid var(--border);
              border-radius: 999px;
              padding: 2px 9px;
              font-size: 12px;
              color: var(--muted);
            }
            .muted { color: var(--muted); }
            h1, h2, h3, h4 { margin: 0 0 8px; }
            h4 { margin-top: 10px; }
            p { margin: 0 0 8px; }
            ul { margin: 8px 0 0; padding-left: 18px; }
            li { margin-bottom: 6px; }
            button {
              border: 1px solid var(--border);
              background: transparent;
              color: var(--text);
              border-radius: 8px;
              padding: 8px 12px;
              cursor: pointer;
            }
            .finding {
              border: 1px solid var(--border);
              border-radius: 10px;
              padding: 10px;
              margin-top: 8px;
            }
          </style>
        </head>
        <body data-theme="dark">
          <main class="wrap">
            <div class="row between" style="margin-bottom: 14px;">
              <div>
                <h1>Отчёт по сети Wi-Fi</h1>
                <div class="muted">Сеть: ${htmlEscape(networkTitle)} | ID: ${htmlEscape(networkIdHint)} | Сформирован: ${htmlEscape(generatedAt)}</div>
              </div>
              <button id="themeToggle" type="button">Светлая тема</button>
            </div>

            <section class="card">
              <div class="row between">
                <h2>Сводка риска</h2>
                <span class="badge">${riskSummary.score} / 100 - ${htmlEscape(riskLevelLabel(riskSummary.level))}</span>
              </div>
              <p>${htmlEscape(summaryText)}</p>
              <ul>
                $actions
              </ul>
            </section>

            <section class="card">
              <h2>События по сети</h2>
              <ul>
                $eventsHtml
              </ul>
            </section>

            <h2 style="margin: 6px 0 10px;">Снимки и находки</h2>
            $snapshotsHtml
          </main>

          <script>
            (function () {
              const button = document.getElementById('themeToggle');
              const body = document.body;
              button.addEventListener('click', function () {
                const dark = body.getAttribute('data-theme') !== 'light';
                body.setAttribute('data-theme', dark ? 'light' : 'dark');
                button.textContent = dark ? 'Тёмная тема' : 'Светлая тема';
              });
            })();
          </script>
        </body>
        </html>
        """.trimIndent()
    }

    private fun riskLevelLabel(level: RiskLevel): String {
        return when (level) {
            RiskLevel.LOW -> "низкий"
            RiskLevel.MEDIUM -> "средний"
            RiskLevel.HIGH -> "высокий"
            RiskLevel.CRITICAL -> "критический"
        }
    }

    private fun severityLabel(level: String): String {
        return when (level) {
            "INFO" -> "низкая"
            "WARN" -> "средняя"
            "HIGH" -> "высокая"
            "CRITICAL" -> "критическая"
            else -> level
        }
    }

    private fun htmlEscape(value: String): String {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;")
    }

    private fun JSONObject.putNullable(key: String, value: Any?) {
        if (value == null) {
            put(key, JSONObject.NULL)
        } else {
            put(key, value)
        }
    }

    private fun safeFilePart(value: String?): String {
        val normalized = value.orEmpty()
            .lowercase(Locale.ROOT)
            .replace(Regex("[^a-z0-9._-]+"), "_")
            .trim('_')
        return if (normalized.isBlank()) "network" else normalized
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

    private data class NetworkExportData(
        val networkIdHint: String,
        val snapshots: List<NetworkSnapshot>,
        val findingsBySnapshot: Map<String, List<Finding>>,
        val events: List<NetworkEvent>,
        val riskSummary: RiskSummary
    )

    private companion object {
        const val SNAPSHOT_LIMIT = 20
        const val EVENT_LIMIT = 200
    }
}

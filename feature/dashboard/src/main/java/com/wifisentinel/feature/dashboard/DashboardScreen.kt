package com.wifisentinel.feature.dashboard

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wifisentinel.core.detectors.EvidenceTextResolver
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingActionType
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.risk.RiskLevel
import com.wifisentinel.core.risk.RiskTextResolver
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.SecurityType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onOpenDetails: () -> Unit,
    onAddTrusted: () -> Unit,
    onScanNow: () -> Unit,
    onShareReport: () -> Unit,
    onExitDemo: () -> Unit,
    onLoadReplay: () -> Unit,
    permissionsMissing: Boolean,
    onRequestPermissions: () -> Unit
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    var recommendationsTab by remember { mutableStateOf(0) }
    val formatter = remember { SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault()) }
    var expandedFindingId by remember { mutableStateOf<String?>(null) }
    var showAutoJoinHelp by remember { mutableStateOf(false) }
    val riskLine = if (state.isScanning) {
        stringResource(R.string.dashboard_risk_scanning)
    } else {
        stringResource(
            R.string.dashboard_risk_value_format,
            state.riskSummary.score,
            riskLevelLabel(context, state.riskSummary.level)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.isDemoMode) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = stringResource(R.string.dashboard_demo_title), style = MaterialTheme.typography.titleMedium)
                        Text(text = stringResource(R.string.dashboard_demo_description))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = onExitDemo, modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.dashboard_demo_exit))
                            }
                            Button(onClick = onLoadReplay, modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.dashboard_demo_load_scan))
                            }
                        }
                    }
                }
            }
        }
        if (permissionsMissing) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = stringResource(R.string.dashboard_permissions_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.dashboard_permissions_body)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onRequestPermissions) {
                            Text(text = stringResource(R.string.dashboard_permissions_button))
                        }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.dashboard_current_network_title), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(R.string.dashboard_ssid_format, state.snapshot?.ssid ?: stringResource(R.string.value_unknown)))
                    Text(
                        text = stringResource(
                            R.string.dashboard_security_format,
                            state.snapshot?.securityType?.let { securityLabel(context, it) } ?: stringResource(R.string.value_unknown)
                        )
                    )
                    Text(text = stringResource(R.string.dashboard_signal_format, signalLabel(context, state.snapshot?.rssiDbm)))
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onOpenDetails) {
                        Text(text = stringResource(R.string.dashboard_show_details))
                    }
                    if (!permissionsMissing && state.snapshot?.ssid == null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.dashboard_ssid_missing))
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.label_security_nutrition), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.label_security_type
                        ) + ": " + securityLabelText(context, state.securityLabel.security)
                    )
                    Text(
                        text = stringResource(
                            R.string.label_portal
                        ) + ": " + portalLabelText(context, state.securityLabel.portal)
                    )
                    Text(
                        text = stringResource(
                            R.string.label_changes
                        ) + ": " + changesLabelText(context, state.securityLabel)
                    )
                    Text(
                        text = stringResource(
                            R.string.label_dns
                        ) + ": " + dnsLabelText(context, state.securityLabel.dns)
                    )
                    Text(
                        text = stringResource(
                            R.string.label_mesh
                        ) + ": " + meshLabelText(context, state.securityLabel.mesh)
                    )
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.dashboard_risk_title), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = riskLine)
                    if (!state.isScanning) {
                        Text(
                            text = RiskTextResolver.resolve(
                                context,
                                state.riskSummary.summary,
                                state.riskSummary.summaryArgs
                            )
                        )
                        if (state.isRiskCalculating) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = stringResource(R.string.dashboard_risk_calculating))
                        }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        TabRow(
                            selectedTabIndex = recommendationsTab,
                            modifier = Modifier.clip(MaterialTheme.shapes.medium),
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Tab(
                                selected = recommendationsTab == 0,
                                onClick = { recommendationsTab = 0 },
                                text = { Text(text = stringResource(R.string.dashboard_tab_risks)) }
                            )
                            Tab(
                                selected = recommendationsTab == 1,
                                onClick = { recommendationsTab = 1 },
                                text = { Text(text = stringResource(R.string.dashboard_tab_settings)) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    if (recommendationsTab == 0) {
                        if (state.riskSummary.actions.isEmpty()) {
                            Text(text = stringResource(R.string.dashboard_risk_actions_empty))
                        } else {
                            state.riskSummary.actions.forEach { action ->
                                Text(text = "- ${RiskTextResolver.resolve(context, action)}")
                            }
                        }
                    } else {
                        val rec = state.routerRecommendations
                        if (rec.sections.isEmpty() || state.lastScanTimeMs == null) {
                            Text(text = stringResource(R.string.dashboard_router_need_scan))
                        } else {
                            Text(
                                text = stringResource(
                                    R.string.dashboard_router_scan_count_format,
                                    rec.scanCount24,
                                    rec.scanCount5
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            if (rec.currentSections.isNotEmpty()) {
                                Text(text = stringResource(R.string.dashboard_router_current_settings), style = MaterialTheme.typography.titleSmall)
                                Spacer(modifier = Modifier.height(6.dp))
                                rec.currentSections.forEach { section ->
                                    Text(
                                        text = stringResource(
                                            section.titleResId,
                                            *section.titleArgs.toTypedArray()
                                        ),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    section.items.forEach { item ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = stringResource(item.labelResId))
                                            Text(text = item.value)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                            Text(text = stringResource(R.string.dashboard_router_recommended_settings), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(6.dp))
                            rec.sections.forEach { section ->
                                Text(
                                    text = stringResource(
                                        section.titleResId,
                                        *section.titleArgs.toTypedArray()
                                    ),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                section.items.forEach { item ->
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = stringResource(item.labelResId))
                                        Text(text = item.value)
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = onScanNow, modifier = Modifier.fillMaxWidth(), enabled = !state.isScanning) {
                    Text(text = stringResource(if (state.isScanning) R.string.dashboard_scan_button_scanning else R.string.dashboard_scan_button))
                }
                if (state.lastScanTimeMs != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = stringResource(R.string.dashboard_last_scan_format, formatter.format(Date(state.lastScanTimeMs))))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            val text = buildThreatsText(
                                context,
                                state.snapshot,
                                state.findings,
                                state.riskSummary,
                                state.maskSensitive
                            )
                            clipboard.setText(AnnotatedString(text))
                            Toast.makeText(context, context.getString(R.string.dashboard_copy_threats_toast), Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = state.findings.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.dashboard_copy_threats), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                    Button(onClick = onAddTrusted, modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.dashboard_add_trusted),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        item {
            Text(text = stringResource(R.string.dashboard_findings_title), style = MaterialTheme.typography.titleMedium)
        }

        if (state.findings.isEmpty()) {
            item {
                Text(text = stringResource(R.string.dashboard_findings_empty))
            }
        } else {
            items(state.findings, key = { it.id }) { finding ->
                val isExpanded = expandedFindingId == finding.id
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            expandedFindingId = if (isExpanded) null else finding.id
                        }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = FindingTextResolver.title(context, finding),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(text = severityLabel(context, finding.severity))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = FindingTextResolver.explanation(context, finding))
                        if (isExpanded && finding.actions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.dashboard_actions_title), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                finding.actions.forEach { action ->
                                    Button(
                                        onClick = {
                                            when (action) {
                                                FindingActionType.OPEN_WIFI_SETTINGS -> openWifiSettings(context)
                                                FindingActionType.OPEN_NETWORK_DETAILS -> onOpenDetails()
                                                FindingActionType.COPY_EVIDENCE -> {
                                                    val text = buildEvidenceText(context, finding, state.snapshot, state.maskSensitive)
                                                    clipboard.setText(AnnotatedString(text))
                                                    Toast.makeText(
                                                        context,
                                                        context.getString(R.string.dashboard_copy_evidence_toast),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                                FindingActionType.SHARE_REPORT -> onShareReport()
                                                FindingActionType.HOW_TO_DISABLE_AUTOJOIN -> showAutoJoinHelp = true
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = actionLabel(context, action),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAutoJoinHelp) {
        AlertDialog(
            onDismissRequest = { showAutoJoinHelp = false },
            title = { Text(text = stringResource(R.string.dashboard_autojoin_title)) },
            text = { Text(text = stringResource(R.string.dashboard_autojoin_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAutoJoinHelp = false
                        openWifiSettings(context)
                    }
                ) {
                    Text(text = stringResource(R.string.dashboard_autojoin_open_settings))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAutoJoinHelp = false }) {
                    Text(text = stringResource(R.string.dashboard_autojoin_close))
                }
            }
        )
    }
}

private fun severityLabel(context: Context, severity: Severity): String {
    return when (severity) {
        Severity.INFO -> context.getString(R.string.severity_info)
        Severity.WARN -> context.getString(R.string.severity_warn)
        Severity.HIGH -> context.getString(R.string.severity_high)
        Severity.CRITICAL -> context.getString(R.string.severity_critical)
    }
}

private fun riskLevelLabel(context: Context, level: RiskLevel): String {
    return when (level) {
        RiskLevel.LOW -> context.getString(R.string.risk_level_low)
        RiskLevel.MEDIUM -> context.getString(R.string.risk_level_medium)
        RiskLevel.HIGH -> context.getString(R.string.risk_level_high)
        RiskLevel.CRITICAL -> context.getString(R.string.risk_level_critical)
    }
}

private fun securityLabel(context: Context, type: SecurityType): String {
    return when (type) {
        SecurityType.OPEN -> context.getString(R.string.security_open)
        SecurityType.WEP -> context.getString(R.string.security_wep)
        SecurityType.WPA2 -> context.getString(R.string.security_wpa2)
        SecurityType.WPA3 -> context.getString(R.string.security_wpa3)
        SecurityType.WPA2_WPA3 -> context.getString(R.string.security_wpa2_wpa3)
        SecurityType.UNKNOWN -> context.getString(R.string.security_unknown)
    }
}

private fun signalLabel(context: Context, rssiDbm: Int?): String {
    if (rssiDbm == null) return context.getString(R.string.signal_no_data)
    val percent = ((rssiDbm + 100) * 2).coerceIn(0, 100)
    val quality = when {
        rssiDbm >= -50 -> context.getString(R.string.signal_excellent)
        rssiDbm >= -60 -> context.getString(R.string.signal_good)
        rssiDbm >= -70 -> context.getString(R.string.signal_medium)
        rssiDbm >= -80 -> context.getString(R.string.signal_weak)
        else -> context.getString(R.string.signal_very_weak)
    }
    return "$quality ($percent%)"
}

private fun actionLabel(context: Context, action: FindingActionType): String {
    return when (action) {
        FindingActionType.OPEN_WIFI_SETTINGS -> context.getString(R.string.action_wifi_settings)
        FindingActionType.OPEN_NETWORK_DETAILS -> context.getString(R.string.action_network_details)
        FindingActionType.COPY_EVIDENCE -> context.getString(R.string.action_copy_evidence)
        FindingActionType.SHARE_REPORT -> context.getString(R.string.action_share_report)
        FindingActionType.HOW_TO_DISABLE_AUTOJOIN -> context.getString(R.string.action_how_disable_autojoin)
    }
}

private fun securityLabelText(context: Context, value: SecurityLabelSecurity): String {
    return when (value) {
        SecurityLabelSecurity.WPA3 -> context.getString(R.string.security_wpa3)
        SecurityLabelSecurity.WPA2 -> context.getString(R.string.security_wpa2)
        SecurityLabelSecurity.WPA2_WPA3 -> context.getString(R.string.security_wpa2_wpa3)
        SecurityLabelSecurity.OPEN -> context.getString(R.string.security_open)
        SecurityLabelSecurity.WEP -> context.getString(R.string.security_wep)
        SecurityLabelSecurity.UNKNOWN -> context.getString(R.string.value_unknown)
    }
}

private fun portalLabelText(context: Context, value: SecurityLabelPortal): String {
    return when (value) {
        SecurityLabelPortal.PRESENT -> context.getString(R.string.value_yes)
        SecurityLabelPortal.ABSENT -> context.getString(R.string.value_no)
        SecurityLabelPortal.UNKNOWN -> context.getString(R.string.value_unknown)
    }
}

private fun dnsLabelText(context: Context, value: SecurityLabelDns): String {
    return when (value) {
        SecurityLabelDns.NORMAL -> context.getString(R.string.value_dns_normal)
        SecurityLabelDns.SUSPICIOUS -> context.getString(R.string.value_dns_suspicious)
        SecurityLabelDns.UNAVAILABLE -> context.getString(R.string.value_dns_unavailable)
    }
}

private fun meshLabelText(context: Context, value: SecurityLabelMesh?): String {
    return when (value) {
        SecurityLabelMesh.ENABLED -> context.getString(R.string.value_mesh_enabled)
        SecurityLabelMesh.DISABLED -> context.getString(R.string.value_mesh_disabled)
        null -> context.getString(R.string.value_not_available)
    }
}

private fun changesLabelText(context: Context, label: SecurityNutritionLabelState): String {
    if (!label.changesKnown) return context.getString(R.string.value_not_available)
    if (label.changes.isEmpty()) return context.getString(R.string.value_changes_none)
    val items = label.changes.joinToString(", ") { change -> changeLabelText(context, change) }
    return context.getString(R.string.value_changes_present_format, items)
}

private fun changeLabelText(context: Context, change: SecurityLabelChange): String {
    return when (change) {
        SecurityLabelChange.BSSID -> context.getString(R.string.change_bssid_label)
        SecurityLabelChange.DNS -> context.getString(R.string.change_dns_label)
        SecurityLabelChange.SECURITY -> context.getString(R.string.change_security_label)
    }
}

private fun maskValue(value: String?, enabled: Boolean): String? {
    if (!enabled || value.isNullOrBlank()) return value
    val trimmed = value.trim()
    if (trimmed.length <= 3) return "***"
    return trimmed.take(3) + "***"
}

private fun buildThreatsText(
    context: Context,
    snapshot: NetworkSnapshot?,
    findings: List<Finding>,
    riskSummary: com.wifisentinel.core.risk.RiskSummary,
    maskSensitive: Boolean
): String {
    val header = when {
        snapshot?.ssid != null -> context.getString(
            R.string.threats_header_with_ssid,
            maskValue(snapshot.ssid, maskSensitive) ?: snapshot.ssid
        )
        snapshot?.bssid != null -> context.getString(
            R.string.threats_header_with_bssid,
            maskValue(snapshot.bssid, maskSensitive) ?: snapshot.bssid
        )
        else -> context.getString(R.string.threats_header)
    }
    val riskLine = context.getString(
        R.string.risk_line_format,
        riskSummary.score,
        riskLevelLabel(context, riskSummary.level)
    )
    val lines = findings.mapIndexed { index, finding ->
        val line = StringBuilder()
        line.append("${index + 1}. ${FindingTextResolver.title(context, finding)}")
        val explanation = FindingTextResolver.explanation(context, finding)
        if (explanation.isNotBlank()) {
            line.append(" - $explanation")
        }
        line.toString()
    }
    return (listOf(header, riskLine) + lines).joinToString("\n")
}

private fun buildEvidenceText(
    context: Context,
    finding: Finding,
    snapshot: NetworkSnapshot?,
    maskSensitive: Boolean
): String {
    val formatter = SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault())
    val lines = mutableListOf<String>()
    lines.add(
        context.getString(
            R.string.evidence_title_format,
            FindingTextResolver.title(context, finding)
        )
    )
    lines.add(context.getString(R.string.evidence_severity_format, severityLabel(context, finding.severity)))
    snapshot?.let {
        val ssid = maskValue(it.ssid, maskSensitive) ?: it.ssid ?: context.getString(R.string.evidence_ssid_hidden)
        val bssid = maskValue(it.bssid, maskSensitive) ?: it.bssid ?: "-"
        val dns = if (it.dnsServers.isEmpty()) "-" else it.dnsServers.joinToString()
        lines.add(context.getString(R.string.evidence_ssid_format, ssid))
        lines.add(context.getString(R.string.evidence_bssid_format, bssid))
        lines.add(context.getString(R.string.evidence_security_format, securityLabel(context, it.securityType)))
        lines.add(context.getString(R.string.evidence_dns_format, dns))
        lines.add(context.getString(R.string.evidence_time_format, formatter.format(Date(it.timestampMs))))
    }
    if (finding.evidence.isNotEmpty()) {
        val evidenceText = finding.evidence.entries.joinToString { entry ->
            val masked = if (maskSensitive && (entry.key.lowercase().contains("ssid") || entry.key.lowercase().contains("bssid"))) {
                maskValue(entry.value, true) ?: entry.value
            } else {
                entry.value
            }
            val label = EvidenceTextResolver.label(context, entry.key)
            val value = EvidenceTextResolver.value(context, entry.key, masked)
            "$label: $value"
        }
        lines.add(context.getString(R.string.evidence_details_format, evidenceText))
    }
    return lines.joinToString("\n")
}

private fun openWifiSettings(context: Context) {
    val intent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

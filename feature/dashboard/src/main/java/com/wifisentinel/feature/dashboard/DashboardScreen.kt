package com.wifisentinel.feature.dashboard

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
    onToggleAutoJoinBlock: (Boolean) -> Unit,
    onScanNow: () -> Unit,
    onShareReport: () -> Unit,
    onExitDemo: () -> Unit,
    onLoadReplay: () -> Unit,
    permissionsMissing: Boolean,
    onRequestPermissions: () -> Unit
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val formatter = remember { SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault()) }
    val buttonShape = RoundedCornerShape(APP_BUTTON_RADIUS)
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
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = stringResource(R.string.dashboard_demo_title), style = MaterialTheme.typography.titleMedium)
                        Text(text = stringResource(R.string.dashboard_demo_description))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = onExitDemo,
                                modifier = Modifier.weight(1f),
                                shape = buttonShape
                            ) {
                                Text(text = stringResource(R.string.dashboard_demo_exit))
                            }
                            OutlinedButton(
                                onClick = onLoadReplay,
                                modifier = Modifier.weight(1f),
                                shape = buttonShape
                            ) {
                                Text(text = stringResource(R.string.dashboard_demo_load_scan))
                            }
                        }
                    }
                }
            }
        }
        if (permissionsMissing) {
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = stringResource(R.string.dashboard_permissions_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.dashboard_permissions_body)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(onClick = onRequestPermissions, shape = buttonShape) {
                            Text(text = stringResource(R.string.dashboard_permissions_button))
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
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
                    AutoJoinIndicator(
                        isBlocked = state.isCurrentNetworkAutoJoinBlocked,
                        isTrusted = state.isCurrentNetworkTrusted
                    )
                    if (!state.isScanning &&
                        (state.riskSummary.level == RiskLevel.HIGH || state.riskSummary.level == RiskLevel.CRITICAL)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        UnsafeNetworkIndicator()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onOpenDetails, shape = buttonShape) {
                        Text(text = stringResource(R.string.dashboard_show_details))
                    }
                    if (!permissionsMissing && state.snapshot?.ssid == null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.dashboard_ssid_missing))
                    }
                }
                if (state.snapshot != null && !state.isCurrentNetworkTrusted) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { onToggleAutoJoinBlock(!state.isCurrentNetworkAutoJoinBlocked) },
                            modifier = Modifier.fillMaxWidth(0.92f),
                            shape = buttonShape
                        ) {
                            Text(
                                text = if (state.isCurrentNetworkAutoJoinBlocked) {
                                    stringResource(R.string.dashboard_autojoin_allow_button)
                                } else {
                                    stringResource(R.string.dashboard_autojoin_block_button)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stringResource(R.string.dashboard_risk_title), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = riskLine,
                        style = MaterialTheme.typography.headlineSmall,
                        color = riskTextColor(state.riskSummary.level, state.isScanning)
                    )
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
                        if (state.riskSummary.actions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            state.riskSummary.actions.forEach { action ->
                                Text(text = "• ${RiskTextResolver.resolve(context, action)}")
                            }
                        } else if (state.findings.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = stringResource(R.string.dashboard_risk_open_findings_hint))
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onScanNow,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isScanning,
                    shape = buttonShape
                ) {
                    Text(text = stringResource(if (state.isScanning) R.string.dashboard_scan_button_scanning else R.string.dashboard_scan_button))
                }
                if (state.lastScanTimeMs != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = stringResource(R.string.dashboard_last_scan_format, formatter.format(Date(state.lastScanTimeMs))))
                }
                if (!state.isCurrentNetworkTrusted) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onAddTrusted,
                        modifier = Modifier.fillMaxWidth(),
                        shape = buttonShape
                    ) {
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
                ElevatedCard(
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
                        Text(
                            text = stringResource(
                                R.string.finding_threat_level_format,
                                findingThreatLevelLabel(context, finding.severity)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = FindingTextResolver.explanation(context, finding))
                        if (isExpanded && finding.actions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(R.string.dashboard_actions_title), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                finding.actions.forEach { action ->
                                    OutlinedButton(
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
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = buttonShape
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
                    },
                    shape = buttonShape
                ) {
                    Text(text = stringResource(R.string.dashboard_autojoin_open_settings))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAutoJoinHelp = false }, shape = buttonShape) {
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

private fun findingThreatLevelLabel(context: Context, severity: Severity): String {
    return when (severity) {
        Severity.INFO -> context.getString(R.string.finding_threat_level_low)
        Severity.WARN -> context.getString(R.string.finding_threat_level_medium)
        Severity.HIGH -> context.getString(R.string.finding_threat_level_high)
        Severity.CRITICAL -> context.getString(R.string.finding_threat_level_critical)
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

@Composable
private fun riskTextColor(level: RiskLevel, isScanning: Boolean): Color {
    if (isScanning) {
        return MaterialTheme.colorScheme.onSurfaceVariant
    }
    return when (level) {
        RiskLevel.LOW -> Color(0xFF2E7D32)
        RiskLevel.MEDIUM -> Color(0xFFAD6800)
        RiskLevel.HIGH -> Color(0xFFB23A48)
        RiskLevel.CRITICAL -> Color(0xFF8B1E3F)
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

private val APP_BUTTON_RADIUS: Dp = 8.dp

@Composable
private fun AutoJoinIndicator(isBlocked: Boolean, isTrusted: Boolean) {
    val containerColor = if (isBlocked) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    val contentColor = if (isBlocked) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }
    val dotColor = if (isBlocked) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.secondary
    }
    val title = when {
        isBlocked -> stringResource(R.string.dashboard_autojoin_indicator_blocked_title)
        isTrusted -> stringResource(R.string.dashboard_autojoin_indicator_allowed_title_trusted)
        else -> stringResource(R.string.dashboard_autojoin_indicator_allowed_title)
    }
    val detail = when {
        isBlocked -> stringResource(R.string.dashboard_autojoin_indicator_blocked_detail)
        isTrusted -> stringResource(R.string.dashboard_autojoin_indicator_allowed_detail_trusted)
        else -> stringResource(R.string.dashboard_autojoin_indicator_allowed_detail)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(APP_BUTTON_RADIUS)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = dotColor, shape = CircleShape)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = detail,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor
            )
        }
    }
}

@Composable
private fun UnsafeNetworkIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(APP_BUTTON_RADIUS)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = MaterialTheme.colorScheme.error, shape = CircleShape)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.dashboard_unsafe_network_title),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.dashboard_unsafe_network_detail),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

private fun maskValue(value: String?, enabled: Boolean): String? {
    if (!enabled || value.isNullOrBlank()) return value
    val trimmed = value.trim()
    if (trimmed.length <= 3) return "***"
    return trimmed.take(3) + "***"
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

package com.wifisentinel.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.wifisentinel.core.detectors.Severity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimelineScreen(
    state: TimelineUiState,
    onSearchQueryChange: (String) -> Unit,
    onToggleFilter: (TimelineFilter) -> Unit,
    onSelectNetwork: (String) -> Unit,
    onClearSelection: () -> Unit,
    onDownloadReport: (String) -> Unit,
    onClearHistory: (String) -> Unit
) {
    val formatter = remember { SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) }
    val selected = state.selectedNetwork
    val buttonShape = RoundedCornerShape(APP_BUTTON_RADIUS)
    var pendingHistoryClear by remember { mutableStateOf<TimelineHistoryClearRequest?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.timeline_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = stringResource(R.string.timeline_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text(text = stringResource(R.string.timeline_search_label)) },
                placeholder = { Text(text = stringResource(R.string.timeline_search_placeholder)) },
                singleLine = true
            )
        }
        item {
            Text(
                text = stringResource(R.string.timeline_filters_title),
                style = MaterialTheme.typography.titleSmall
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(TimelineFilter.entries.toList()) { filter ->
                    FilterChip(
                        selected = filter in state.activeFilters,
                        onClick = { onToggleFilter(filter) },
                        label = { Text(text = filterLabel(filter)) }
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.timeline_networks_title),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (state.networks.isEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.timeline_empty),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(state.networks, key = { it.networkIdHint }) { network ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSelectNetwork(network.networkIdHint) }
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = network.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = network.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = stringResource(
                                    R.string.timeline_network_card_sessions_format,
                                    network.sessionsCount
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = stringResource(
                                    R.string.timeline_network_card_findings_format,
                                    network.findingsCount
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = severityLabel(network.highestSeverity),
                            color = severityColor(network.highestSeverity),
                            style = MaterialTheme.typography.labelMedium
                        )
                        val tags = networkTags(network)
                        if (tags.isNotEmpty()) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                tags.forEach { tag ->
                                    FilterChip(
                                        selected = false,
                                        onClick = {},
                                        label = { Text(text = tag) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
        item {
            Text(
                text = stringResource(R.string.timeline_selected_title),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (selected == null) {
            item {
                Text(
                    text = stringResource(R.string.timeline_selected_empty),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = selected.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = selected.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onDownloadReport(selected.networkIdHint) },
                                modifier = Modifier.weight(1f),
                                shape = buttonShape
                            ) {
                                Text(text = stringResource(R.string.timeline_download_report))
                            }
                            OutlinedButton(
                                onClick = onClearSelection,
                                modifier = Modifier.weight(1f),
                                shape = buttonShape
                            ) {
                                Text(text = stringResource(R.string.timeline_back_to_list))
                            }
                        }
                        OutlinedButton(
                            onClick = {
                                pendingHistoryClear = TimelineHistoryClearRequest(
                                    networkIdHint = selected.networkIdHint,
                                    title = selected.title
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = buttonShape
                        ) {
                            Text(text = stringResource(R.string.timeline_clear_history))
                        }
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.timeline_sessions_title),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            items(selected.sessions, key = { it.id }) { session ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(
                                R.string.timeline_session_period_format,
                                formatter.format(Date(session.startMs)),
                                formatter.format(Date(session.endMs))
                            ),
                            style = MaterialTheme.typography.titleSmall
                        )

                        if (session.suspiciousChanges.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.timeline_session_changes_title),
                                style = MaterialTheme.typography.labelLarge
                            )
                            session.suspiciousChanges.forEach { line ->
                                Text(text = "• $line", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        if (session.eventNotes.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.timeline_session_events_title),
                                style = MaterialTheme.typography.labelLarge
                            )
                            session.eventNotes.forEach { note ->
                                Text(
                                    text = "• $note",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.timeline_no_events),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = stringResource(R.string.timeline_session_findings_title),
                            style = MaterialTheme.typography.labelLarge
                        )
                        if (session.findings.isEmpty()) {
                            Text(
                                text = stringResource(R.string.timeline_no_findings),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            session.findings.forEach { finding ->
                                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = finding.title,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Text(
                                            text = severityLabel(finding.severity),
                                            color = severityColor(finding.severity),
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                        Text(
                                            text = finding.explanation,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            text = stringResource(R.string.timeline_finding_why_title),
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                        Text(
                                            text = finding.whyImportant,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = stringResource(R.string.timeline_finding_action_title),
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                        Text(
                                            text = finding.whatToDo,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
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

    pendingHistoryClear?.let { request ->
        AlertDialog(
            onDismissRequest = { pendingHistoryClear = null },
            title = { Text(text = stringResource(R.string.timeline_clear_history_confirm_title)) },
            text = {
                Text(
                    text = stringResource(
                        R.string.timeline_clear_history_confirm_message,
                        request.title
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearHistory(request.networkIdHint)
                        pendingHistoryClear = null
                    },
                    shape = buttonShape
                ) {
                    Text(text = stringResource(R.string.timeline_clear_history_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { pendingHistoryClear = null },
                    shape = buttonShape
                ) {
                    Text(text = stringResource(R.string.timeline_clear_history_cancel))
                }
            }
        )
    }
}

@Composable
private fun severityLabel(severity: Severity): String {
    return when (severity) {
        Severity.INFO -> stringResource(R.string.timeline_severity_info)
        Severity.WARN -> stringResource(R.string.timeline_severity_warn)
        Severity.HIGH -> stringResource(R.string.timeline_severity_high)
        Severity.CRITICAL -> stringResource(R.string.timeline_severity_critical)
    }
}

@Composable
private fun severityColor(severity: Severity): Color {
    return when (severity) {
        Severity.INFO -> MaterialTheme.colorScheme.onSurfaceVariant
        Severity.WARN -> MaterialTheme.colorScheme.tertiary
        Severity.HIGH -> MaterialTheme.colorScheme.error
        Severity.CRITICAL -> MaterialTheme.colorScheme.error
    }
}

@Composable
private fun filterLabel(filter: TimelineFilter): String {
    return when (filter) {
        TimelineFilter.CRITICAL_ONLY -> stringResource(R.string.timeline_filter_critical)
        TimelineFilter.CURRENT_NETWORK -> stringResource(R.string.timeline_filter_current)
        TimelineFilter.SUSPICIOUS_CHANGES -> stringResource(R.string.timeline_filter_changes)
    }
}

@Composable
private fun networkTags(network: TimelineNetworkCard): List<String> {
    val tags = mutableListOf<String>()
    if (network.isCurrent) {
        tags += stringResource(R.string.timeline_filter_current)
    }
    if (network.hasSuspiciousChanges) {
        tags += stringResource(R.string.timeline_filter_changes)
    }
    return tags
}

private data class TimelineHistoryClearRequest(
    val networkIdHint: String,
    val title: String
)

private val APP_BUTTON_RADIUS: Dp = 8.dp

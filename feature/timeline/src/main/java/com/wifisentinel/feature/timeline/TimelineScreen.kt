package com.wifisentinel.feature.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.feature.timeline.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimelineScreen(state: TimelineUiState, onExportReport: () -> Unit) {
    val formatter = SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.timeline_title), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onExportReport, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.timeline_export_json))
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (state.events.isEmpty()) {
            Text(text = stringResource(R.string.timeline_empty))
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.events) { event ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = event.title, style = MaterialTheme.typography.titleSmall)
                            Text(text = "${severityLabel(event.severity)} - ${formatter.format(Date(event.timestampMs))}")
                            Text(text = event.detail)
                        }
                    }
                }
            }
        }
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

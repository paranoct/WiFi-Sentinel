package com.wifisentinel.feature.networkdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Severity

@Composable
fun NetworkDetailsScreen(
    state: NetworkDetailsUiState,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text(text = stringResource(R.string.network_details_back))
        }
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.network_details_title),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.snapshot == null) {
                    Text(text = stringResource(R.string.network_details_empty))
                } else {
                    val unavailable = stringResource(R.string.network_details_unavailable)
                    val frequency = state.snapshot.frequencyMhz?.toString() ?: "-"
                    val dnsValue = state.snapshot.dnsServers.joinToString().ifBlank { unavailable }
                    Text(
                        text = stringResource(
                            R.string.network_details_label_ssid,
                            state.snapshot.ssid ?: unavailable
                        )
                    )
                    Text(
                        text = stringResource(
                            R.string.network_details_label_bssid,
                            state.snapshot.bssid ?: unavailable
                        )
                    )
                    Text(text = stringResource(R.string.network_details_label_frequency, frequency))
                    Text(text = stringResource(R.string.network_details_label_dns, dnsValue))
                    Text(
                        text = stringResource(
                            R.string.network_details_label_gateway,
                            state.snapshot.gatewayV4 ?: "-"
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.network_details_findings_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (state.findings.isEmpty()) {
            Text(text = stringResource(R.string.network_details_findings_empty))
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.findings) { finding ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = FindingTextResolver.title(context, finding),
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(text = severityLabel(finding.severity))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = FindingTextResolver.explanation(context, finding))
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
        Severity.INFO -> stringResource(R.string.network_details_severity_info)
        Severity.WARN -> stringResource(R.string.network_details_severity_warn)
        Severity.HIGH -> stringResource(R.string.network_details_severity_high)
        Severity.CRITICAL -> stringResource(R.string.network_details_severity_critical)
    }
}

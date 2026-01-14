package com.wifisentinel.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ReplayScreen(
    state: ReplayUiState,
    onToggleMaskSensitive: (Boolean) -> Unit,
    onSaveCurrent: () -> Unit,
    onLoadFile: () -> Unit,
    onExitDemo: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.replay_title), style = MaterialTheme.typography.titleMedium)
        Text(text = stringResource(R.string.replay_subtitle), style = MaterialTheme.typography.bodySmall)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.replay_mask_toggle))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = state.maskSensitive,
                    onCheckedChange = onToggleMaskSensitive
                )
            }
        }

        if (state.demoModeEnabled) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(R.string.replay_demo_active), style = MaterialTheme.typography.titleSmall)
                    Text(text = stringResource(R.string.replay_demo_description), style = MaterialTheme.typography.bodySmall)
                    Button(
                        onClick = onExitDemo,
                        enabled = !state.isRunning,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.replay_demo_exit))
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onSaveCurrent,
                    enabled = !state.isRunning,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.replay_save_current))
                }
                Button(
                    onClick = onLoadFile,
                    enabled = !state.isRunning,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.replay_load_file))
                }
            }
        }

    }
}

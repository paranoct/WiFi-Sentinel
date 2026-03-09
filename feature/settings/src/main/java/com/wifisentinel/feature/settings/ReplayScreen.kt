package com.wifisentinel.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ReplayScreen(
    state: ReplayUiState,
    onToggleMaskSensitive: (Boolean) -> Unit,
    onLoadFile: () -> Unit,
    onExitDemo: () -> Unit
) {
    val sostoyanieProkrutki = rememberScrollState()
    val formaKnopki = RoundedCornerShape(APP_BUTTON_RADIUS)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(sostoyanieProkrutki)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.replay_title), style = MaterialTheme.typography.titleMedium)
        Text(text = stringResource(R.string.replay_subtitle), style = MaterialTheme.typography.bodySmall)

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
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
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = stringResource(R.string.replay_demo_active), style = MaterialTheme.typography.titleSmall)
                    Text(text = stringResource(R.string.replay_demo_description), style = MaterialTheme.typography.bodySmall)
                    OutlinedButton(
                        onClick = onExitDemo,
                        enabled = !state.isRunning,
                        modifier = Modifier.fillMaxWidth(),
                        shape = formaKnopki
                    ) {
                        Text(text = stringResource(R.string.replay_demo_exit))
                    }
                }
            }
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onLoadFile,
                    enabled = !state.isRunning,
                    modifier = Modifier.fillMaxWidth(),
                    shape = formaKnopki
                ) {
                    Text(text = stringResource(R.string.replay_load_file))
                }
            }
        }

    }
}

private val APP_BUTTON_RADIUS: Dp = 8.dp

package com.wifisentinel.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wifisentinel.core.storage.settings.ThemeMode

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onToggleNotifications: (Boolean) -> Unit,
    onToggleDnsCheck: (Boolean) -> Unit,
    onOpenReplay: () -> Unit,
    onThemeChange: (ThemeMode) -> Unit,
    onToggleMaskSensitive: (Boolean) -> Unit,
    onToggleAutoDisconnect: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val buttonShape = RoundedCornerShape(APP_BUTTON_RADIUS)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.settings_title), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_always_on_title))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = true,
                    onCheckedChange = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.settings_always_on_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_notifications_title))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = state.settings.notificationsEnabled,
                    onCheckedChange = onToggleNotifications
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.settings_notifications_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_auto_disconnect_title))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = state.settings.autoDisconnectEnabled,
                    onCheckedChange = onToggleAutoDisconnect
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.settings_auto_disconnect_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_mask_sensitive_title))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = state.settings.maskSensitive,
                    onCheckedChange = onToggleMaskSensitive
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.settings_mask_sensitive_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_dns_check_title))
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = state.settings.dnsCheckEnabled,
                    onCheckedChange = onToggleDnsCheck
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.settings_dns_check_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.settings_theme_title))
                Spacer(modifier = Modifier.height(8.dp))
                val themes = listOf(ThemeMode.LIGHT, ThemeMode.DARK)
                themes.forEach { mode ->
                    val selected = state.settings.themeMode == mode
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selected,
                                onClick = { onThemeChange(mode) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected,
                            onClick = { onThemeChange(mode) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = if (mode == ThemeMode.LIGHT) stringResource(R.string.settings_theme_light) else stringResource(R.string.settings_theme_dark))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = stringResource(R.string.replay_title))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onOpenReplay,
                    modifier = Modifier.fillMaxWidth(),
                    shape = buttonShape
                ) {
                    Text(text = stringResource(R.string.replay_title))
                }
            }
        }
    }
}

private val APP_BUTTON_RADIUS: Dp = 8.dp

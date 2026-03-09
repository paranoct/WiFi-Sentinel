package com.wifisentinel.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
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
    val sostoyanieProkrutki = rememberScrollState()
    val formaKnopki = RoundedCornerShape(APP_BUTTON_RADIUS)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(sostoyanieProkrutki)
            .padding(16.dp)
    ) {
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                StrokaPereklyuchatelya(
                    zagolovok = stringResource(R.string.settings_always_on_title),
                    opisanie = stringResource(R.string.settings_always_on_description),
                    vklyucheno = state.settings.alwaysOnEnabled,
                    priPereklyuchenii = null
                )
                RazdelitelNastroek()
                StrokaPereklyuchatelya(
                    zagolovok = stringResource(R.string.settings_notifications_title),
                    opisanie = stringResource(R.string.settings_notifications_description),
                    vklyucheno = state.settings.notificationsEnabled,
                    priPereklyuchenii = onToggleNotifications
                )
                RazdelitelNastroek()
                StrokaPereklyuchatelya(
                    zagolovok = stringResource(R.string.settings_auto_disconnect_title),
                    opisanie = stringResource(R.string.settings_auto_disconnect_description),
                    vklyucheno = state.settings.autoDisconnectEnabled,
                    priPereklyuchenii = onToggleAutoDisconnect
                )
                RazdelitelNastroek()
                StrokaPereklyuchatelya(
                    zagolovok = stringResource(R.string.settings_mask_sensitive_title),
                    opisanie = stringResource(R.string.settings_mask_sensitive_description),
                    vklyucheno = state.settings.maskSensitive,
                    priPereklyuchenii = onToggleMaskSensitive
                )
                RazdelitelNastroek()
                StrokaPereklyuchatelya(
                    zagolovok = stringResource(R.string.settings_dns_check_title),
                    opisanie = stringResource(R.string.settings_dns_check_description),
                    vklyucheno = state.settings.dnsCheckEnabled,
                    priPereklyuchenii = onToggleDnsCheck
                )
                RazdelitelNastroek()
                BlokTemy(
                    tekushayaTema = state.settings.themeMode,
                    priSmeneTemy = onThemeChange
                )
                RazdelitelNastroek()
                Column {
                    Text(text = stringResource(R.string.replay_title))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = onOpenReplay,
                        modifier = Modifier.fillMaxWidth(),
                        shape = formaKnopki
                    ) {
                        Text(text = stringResource(R.string.replay_title))
                    }
                }
            }
        }
    }
}

@Composable
private fun StrokaPereklyuchatelya(
    zagolovok: String,
    opisanie: String,
    vklyucheno: Boolean,
    priPereklyuchenii: ((Boolean) -> Unit)?
) {
    val dostupno = priPereklyuchenii != null

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = zagolovok)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = opisanie, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = vklyucheno,
            onCheckedChange = priPereklyuchenii,
            enabled = dostupno
        )
    }
}

@Composable
private fun BlokTemy(
    tekushayaTema: ThemeMode,
    priSmeneTemy: (ThemeMode) -> Unit
) {
    Column {
        Text(text = stringResource(R.string.settings_theme_title))
        Spacer(modifier = Modifier.height(8.dp))
        val spisokTem = listOf(ThemeMode.LIGHT, ThemeMode.DARK)
        spisokTem.forEach { tema ->
            val temaVybrana = tekushayaTema == tema
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = temaVybrana,
                        onClick = { priSmeneTemy(tema) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = temaVybrana,
                    onClick = { priSmeneTemy(tema) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (tema == ThemeMode.LIGHT) {
                        stringResource(R.string.settings_theme_light)
                    } else {
                        stringResource(R.string.settings_theme_dark)
                    }
                )
            }
        }
    }
}

@Composable
private fun RazdelitelNastroek() {
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(16.dp))
}

private val APP_BUTTON_RADIUS: Dp = 8.dp

package com.wifisentinel.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wifisentinel.app.ui.AppRoot
import com.wifisentinel.app.ui.theme.WiFiSentinelTheme
import com.wifisentinel.core.storage.settings.AppSettings
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.storage.settings.ThemeMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var repoNastroek: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nastroiki = repoNastroek.settings.collectAsStateWithLifecycle(
                initialValue = AppSettings(
                    dohProviderId = "google",
                    dnsCheckEnabled = true,
                    notificationsEnabled = true,
                    themeMode = ThemeMode.DARK,
                    alwaysOnEnabled = true,
                    maskSensitive = true,
                    demoModeEnabled = false,
                    autoDisconnectEnabled = true,
                    blockedAutoJoinNetworkHints = emptySet(),
                    manualAutoJoinBlockedNetworkHints = emptySet()
                )
            ).value
            WiFiSentinelTheme(darkTheme = nastroiki.themeMode == ThemeMode.DARK) {
                AppRoot()
            }
        }
    }
}

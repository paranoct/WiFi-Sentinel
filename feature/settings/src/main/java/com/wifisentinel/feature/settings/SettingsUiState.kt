package com.wifisentinel.feature.settings

import com.wifisentinel.core.storage.settings.AppSettings
import com.wifisentinel.core.storage.settings.ThemeMode

data class SettingsUiState(
    val settings: AppSettings = AppSettings(
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
)

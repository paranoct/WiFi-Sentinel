package com.wifisentinel.core.storage.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "wifi_sentinel_settings"
private const val DEFAULT_DOH_PROVIDER = "google"

val Context.settingsDataStore by preferencesDataStore(name = DATASTORE_NAME)

object SettingsKeys {
    val dohProvider = stringPreferencesKey("doh_provider")
    val dnsCheckEnabled = booleanPreferencesKey("dns_check_enabled")
    val notificationsEnabled = booleanPreferencesKey("notifications_enabled")
    val themeMode = stringPreferencesKey("theme_mode")
    val alwaysOnEnabled = booleanPreferencesKey("always_on_enabled")
    val maskSensitive = booleanPreferencesKey("mask_sensitive")
    val demoModeEnabled = booleanPreferencesKey("demo_mode_enabled")
    val autoDisconnectEnabled = booleanPreferencesKey("auto_disconnect_enabled")
    val blockedAutoJoinNetworkHints = stringSetPreferencesKey("blocked_autojoin_network_hints")
    val manualAutoJoinBlockedNetworkHints = stringSetPreferencesKey("manual_autojoin_blocked_network_hints")
}

enum class ThemeMode {
    LIGHT,
    DARK
}

data class AppSettings(
    val dohProviderId: String,
    val dnsCheckEnabled: Boolean,
    val notificationsEnabled: Boolean,
    val themeMode: ThemeMode,
    val alwaysOnEnabled: Boolean,
    val maskSensitive: Boolean,
    val demoModeEnabled: Boolean,
    val autoDisconnectEnabled: Boolean,
    val blockedAutoJoinNetworkHints: Set<String>,
    val manualAutoJoinBlockedNetworkHints: Set<String>
)

class SettingsRepository(private val context: Context) {
    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            // Provider selection is fixed to Google for simpler UX.
            dohProviderId = DEFAULT_DOH_PROVIDER,
            dnsCheckEnabled = prefs[SettingsKeys.dnsCheckEnabled] ?: true,
            notificationsEnabled = prefs[SettingsKeys.notificationsEnabled] ?: true,
            themeMode = parseThemeMode(prefs[SettingsKeys.themeMode]),
            // Background protection is always enabled.
            alwaysOnEnabled = true,
            maskSensitive = prefs[SettingsKeys.maskSensitive] ?: true,
            demoModeEnabled = prefs[SettingsKeys.demoModeEnabled] ?: false,
            autoDisconnectEnabled = prefs[SettingsKeys.autoDisconnectEnabled] ?: true,
            blockedAutoJoinNetworkHints = prefs[SettingsKeys.blockedAutoJoinNetworkHints] ?: emptySet(),
            manualAutoJoinBlockedNetworkHints = prefs[SettingsKeys.manualAutoJoinBlockedNetworkHints] ?: emptySet()
        )
    }

    suspend fun setDohProvider(@Suppress("UNUSED_PARAMETER") id: String) {
        context.settingsDataStore.edit { it[SettingsKeys.dohProvider] = DEFAULT_DOH_PROVIDER }
    }

    suspend fun setDnsCheckEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.dnsCheckEnabled] = enabled }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.notificationsEnabled] = enabled }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { it[SettingsKeys.themeMode] = mode.name }
    }

    suspend fun setAlwaysOnEnabled(@Suppress("UNUSED_PARAMETER") enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.alwaysOnEnabled] = true }
    }

    suspend fun setMaskSensitive(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.maskSensitive] = enabled }
    }

    suspend fun setDemoModeEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.demoModeEnabled] = enabled }
    }

    suspend fun setAutoDisconnectEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.autoDisconnectEnabled] = enabled }
    }

    suspend fun blockAutoJoinForNetwork(networkIdHint: String, manual: Boolean = false) {
        if (networkIdHint.isBlank()) return
        context.settingsDataStore.edit { prefs ->
            val current = prefs[SettingsKeys.blockedAutoJoinNetworkHints] ?: emptySet()
            prefs[SettingsKeys.blockedAutoJoinNetworkHints] = current + networkIdHint
            if (manual) {
                val currentManual = prefs[SettingsKeys.manualAutoJoinBlockedNetworkHints] ?: emptySet()
                prefs[SettingsKeys.manualAutoJoinBlockedNetworkHints] = currentManual + networkIdHint
            }
        }
    }

    suspend fun unblockAutoJoinForNetwork(networkIdHint: String, clearManual: Boolean = true) {
        if (networkIdHint.isBlank()) return
        context.settingsDataStore.edit { prefs ->
            val current = prefs[SettingsKeys.blockedAutoJoinNetworkHints] ?: emptySet()
            prefs[SettingsKeys.blockedAutoJoinNetworkHints] = current - networkIdHint
            if (clearManual) {
                val currentManual = prefs[SettingsKeys.manualAutoJoinBlockedNetworkHints] ?: emptySet()
                prefs[SettingsKeys.manualAutoJoinBlockedNetworkHints] = currentManual - networkIdHint
            }
        }
    }

    private fun parseThemeMode(value: String?): ThemeMode {
        return value?.let { mode -> ThemeMode.values().firstOrNull { it.name == mode } } ?: ThemeMode.DARK
    }
}

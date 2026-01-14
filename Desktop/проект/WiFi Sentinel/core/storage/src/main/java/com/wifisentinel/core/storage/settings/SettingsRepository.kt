package com.wifisentinel.core.storage.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "wifi_sentinel_settings"

val Context.settingsDataStore by preferencesDataStore(name = DATASTORE_NAME)

object SettingsKeys {
    val dohProvider = stringPreferencesKey("doh_provider")
    val dnsCheckEnabled = booleanPreferencesKey("dns_check_enabled")
    val notificationsEnabled = booleanPreferencesKey("notifications_enabled")
    val scanIntervalHours = intPreferencesKey("scan_interval_hours")
    val themeMode = stringPreferencesKey("theme_mode")
    val alwaysOnEnabled = booleanPreferencesKey("always_on_enabled")
    val maskSensitive = booleanPreferencesKey("mask_sensitive")
    val demoModeEnabled = booleanPreferencesKey("demo_mode_enabled")
}

enum class ThemeMode {
    LIGHT,
    DARK
}

data class AppSettings(
    val dohProviderId: String,
    val dnsCheckEnabled: Boolean,
    val notificationsEnabled: Boolean,
    val scanIntervalHours: Int,
    val themeMode: ThemeMode,
    val alwaysOnEnabled: Boolean,
    val maskSensitive: Boolean,
    val demoModeEnabled: Boolean
)

class SettingsRepository(private val context: Context) {
    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            dohProviderId = prefs[SettingsKeys.dohProvider] ?: "google",
            dnsCheckEnabled = prefs[SettingsKeys.dnsCheckEnabled] ?: true,
            notificationsEnabled = prefs[SettingsKeys.notificationsEnabled] ?: true,
            scanIntervalHours = prefs[SettingsKeys.scanIntervalHours] ?: 6,
            themeMode = parseThemeMode(prefs[SettingsKeys.themeMode]),
            alwaysOnEnabled = prefs[SettingsKeys.alwaysOnEnabled] ?: true,
            maskSensitive = prefs[SettingsKeys.maskSensitive] ?: true,
            demoModeEnabled = prefs[SettingsKeys.demoModeEnabled] ?: false
        )
    }

    suspend fun setDohProvider(id: String) {
        context.settingsDataStore.edit { it[SettingsKeys.dohProvider] = id }
    }

    suspend fun setDnsCheckEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.dnsCheckEnabled] = enabled }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.notificationsEnabled] = enabled }
    }

    suspend fun setScanIntervalHours(hours: Int) {
        context.settingsDataStore.edit { it[SettingsKeys.scanIntervalHours] = hours }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { it[SettingsKeys.themeMode] = mode.name }
    }

    suspend fun setAlwaysOnEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.alwaysOnEnabled] = enabled }
    }

    suspend fun setMaskSensitive(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.maskSensitive] = enabled }
    }

    suspend fun setDemoModeEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.demoModeEnabled] = enabled }
    }

    private fun parseThemeMode(value: String?): ThemeMode {
        return value?.let { mode -> ThemeMode.values().firstOrNull { it.name == mode } } ?: ThemeMode.DARK
    }
}

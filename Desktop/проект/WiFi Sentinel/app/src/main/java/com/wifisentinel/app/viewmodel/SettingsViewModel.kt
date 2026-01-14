package com.wifisentinel.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.core.net.DohProviders
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.storage.settings.ThemeMode
import com.wifisentinel.feature.settings.SettingsUiState
import com.wifisentinel.app.work.BackgroundScanScheduler
import com.wifisentinel.app.service.AlwaysOnServiceController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val backgroundScanScheduler: BackgroundScanScheduler,
    private val alwaysOnServiceController: AlwaysOnServiceController
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = settingsRepository.settings
        .map { settings ->
            SettingsUiState(settings = settings, providers = DohProviders.defaults)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())

    fun setDohProvider(id: String) {
        viewModelScope.launch { settingsRepository.setDohProvider(id) }
    }

    fun setDnsCheckEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDnsCheckEnabled(enabled) }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setNotificationsEnabled(enabled) }
    }

    fun setScanInterval(hours: Int) {
        viewModelScope.launch {
            settingsRepository.setScanIntervalHours(hours)
            backgroundScanScheduler.schedule(hours)
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
    }

    fun setAlwaysOnEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setAlwaysOnEnabled(enabled)
            if (enabled) {
                alwaysOnServiceController.start()
            } else {
                alwaysOnServiceController.stop()
            }
        }
    }

    fun setMaskSensitive(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setMaskSensitive(enabled) }
    }
}

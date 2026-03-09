package com.wifisentinel.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.storage.settings.ThemeMode
import com.wifisentinel.feature.settings.SettingsUiState
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
    private val repoNastroek: SettingsRepository,
    private val kontrollerAlwaysOnServisa: AlwaysOnServiceController
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = repoNastroek.settings
        .map { nastroiki ->
            SettingsUiState(settings = nastroiki)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())

    fun setDnsCheckEnabled(enabled: Boolean) {
        viewModelScope.launch { repoNastroek.setDnsCheckEnabled(enabled) }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { repoNastroek.setNotificationsEnabled(enabled) }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { repoNastroek.setThemeMode(mode) }
    }

    fun setAlwaysOnEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repoNastroek.setAlwaysOnEnabled(enabled)
            if (enabled) {
                kontrollerAlwaysOnServisa.start()
            } else {
                kontrollerAlwaysOnServisa.stop()
            }
        }
    }

    fun setMaskSensitive(enabled: Boolean) {
        viewModelScope.launch { repoNastroek.setMaskSensitive(enabled) }
    }

    fun setAutoDisconnectEnabled(enabled: Boolean) {
        viewModelScope.launch { repoNastroek.setAutoDisconnectEnabled(enabled) }
    }
}

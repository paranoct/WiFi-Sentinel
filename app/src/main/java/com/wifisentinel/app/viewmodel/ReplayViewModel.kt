package com.wifisentinel.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.app.R
import com.wifisentinel.app.replay.ReplayManager
import com.wifisentinel.app.report.ReportExporter
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.feature.settings.ReplayUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplayViewModel @Inject constructor(
    private val replayManager: ReplayManager,
    private val settingsRepository: SettingsRepository,
    private val reportExporter: ReportExporter
) : ViewModel() {
    private val running = MutableStateFlow(false)

    val uiState: StateFlow<ReplayUiState> = combine(
        settingsRepository.settings,
        running
    ) { settings, isRunning ->
        ReplayUiState(
            maskSensitive = settings.maskSensitive,
            isRunning = isRunning,
            demoModeEnabled = settings.demoModeEnabled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ReplayUiState())

    private val _events = MutableSharedFlow<ReplayEvent>()
    val events = _events.asSharedFlow()

    fun setMaskSensitive(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setMaskSensitive(enabled) }
    }

    fun exportCurrent() {
        viewModelScope.launch {
            running.value = true
            try {
                val uri = reportExporter.exportCurrentNetwork(uiState.value.maskSensitive)
                _events.emit(ReplayEvent.Share(uri))
            } catch (_: Exception) {
                _events.emit(ReplayEvent.Error(R.string.replay_export_error))
            } finally {
                running.value = false
            }
        }
    }

    fun importFromUri(uri: Uri) {
        viewModelScope.launch {
            running.value = true
            try {
                val ok = replayManager.runFromUri(uri)
                if (ok) {
                    _events.emit(ReplayEvent.Message(R.string.replay_import_success))
                } else {
                    _events.emit(ReplayEvent.Error(R.string.replay_import_error))
                }
            } finally {
                running.value = false
            }
        }
    }

    fun exitDemoMode() {
        viewModelScope.launch {
            settingsRepository.setDemoModeEnabled(false)
            _events.emit(ReplayEvent.Message(R.string.replay_demo_exit))
        }
    }

    sealed interface ReplayEvent {
        data class Share(val uri: Uri) : ReplayEvent
        data class Message(val messageResId: Int) : ReplayEvent
        data class Error(val messageResId: Int) : ReplayEvent
    }
}

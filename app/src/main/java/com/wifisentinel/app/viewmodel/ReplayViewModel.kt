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
    private val menedzherReplay: ReplayManager,
    private val repoNastroek: SettingsRepository,
    private val eksporterOtcheta: ReportExporter
) : ViewModel() {
    private val idetObrabotka = MutableStateFlow(false)

    val uiState: StateFlow<ReplayUiState> = combine(
        repoNastroek.settings,
        idetObrabotka
    ) { nastroiki, seychasObrabatyvaetsya ->
        ReplayUiState(
            maskSensitive = nastroiki.maskSensitive,
            isRunning = seychasObrabatyvaetsya,
            demoModeEnabled = nastroiki.demoModeEnabled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ReplayUiState())

    private val _sobytiya = MutableSharedFlow<ReplayEvent>()
    val events = _sobytiya.asSharedFlow()

    fun setMaskSensitive(enabled: Boolean) {
        viewModelScope.launch { repoNastroek.setMaskSensitive(enabled) }
    }

    fun exportCurrent() {
        viewModelScope.launch {
            idetObrabotka.value = true
            try {
                val uriFayla = eksporterOtcheta.exportCurrentNetwork(uiState.value.maskSensitive)
                _sobytiya.emit(ReplayEvent.Share(uriFayla))
            } catch (_: Exception) {
                _sobytiya.emit(ReplayEvent.Error(R.string.replay_export_error))
            } finally {
                idetObrabotka.value = false
            }
        }
    }

    fun importFromUri(uri: Uri) {
        viewModelScope.launch {
            idetObrabotka.value = true
            try {
                val uspeshno = menedzherReplay.runFromUri(uri)
                if (uspeshno) {
                    _sobytiya.emit(ReplayEvent.Message(R.string.replay_import_success))
                } else {
                    _sobytiya.emit(ReplayEvent.Error(R.string.replay_import_error))
                }
            } finally {
                idetObrabotka.value = false
            }
        }
    }

    fun exitDemoMode() {
        viewModelScope.launch {
            repoNastroek.setDemoModeEnabled(false)
            _sobytiya.emit(ReplayEvent.Message(R.string.replay_demo_exit))
        }
    }

    sealed interface ReplayEvent {
        data class Share(val uri: Uri) : ReplayEvent
        data class Message(val messageResId: Int) : ReplayEvent
        data class Error(val messageResId: Int) : ReplayEvent
    }
}

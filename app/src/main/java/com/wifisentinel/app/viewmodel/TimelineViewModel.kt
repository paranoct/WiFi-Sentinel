package com.wifisentinel.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.app.report.ReportExporter
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.feature.timeline.TimelineEvent
import com.wifisentinel.feature.timeline.TimelineUiState
import com.wifisentinel.feature.timeline.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.net.Uri

@HiltViewModel
class TimelineViewModel @Inject constructor(
    repository: NetworkRepository,
    private val reportExporter: ReportExporter
) : ViewModel() {
    val uiState: StateFlow<TimelineUiState> = repository.latestEvents()
        .map { events ->
            val timelineEvents = events.map { event ->
                TimelineEvent(
                    id = event.id,
                    timestampMs = event.timestampMs,
                    title = event.title,
                    severity = event.severity,
                    detail = event.detail
                )
            }
            TimelineUiState(timelineEvents)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TimelineUiState())

    private val _reportEvents = MutableSharedFlow<ReportEvent>()
    val reportEvents = _reportEvents.asSharedFlow()

    fun exportReport() {
        viewModelScope.launch {
            try {
                val uri = reportExporter.export()
                _reportEvents.emit(ReportEvent.Share(uri))
            } catch (e: Exception) {
                _reportEvents.emit(ReportEvent.Error(R.string.timeline_report_error))
            }
        }
    }

    sealed interface ReportEvent {
        data class Share(val uri: Uri) : ReportEvent
        data class Error(val messageResId: Int) : ReportEvent
    }
}

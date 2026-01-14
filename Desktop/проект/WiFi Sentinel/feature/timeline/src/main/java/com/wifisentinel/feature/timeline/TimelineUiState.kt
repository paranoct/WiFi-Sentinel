package com.wifisentinel.feature.timeline

import com.wifisentinel.core.detectors.Severity


data class TimelineEvent(
    val id: String,
    val timestampMs: Long,
    val title: String,
    val severity: Severity,
    val detail: String
)

data class TimelineUiState(
    val events: List<TimelineEvent> = emptyList()
)

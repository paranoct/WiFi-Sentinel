package com.wifisentinel.feature.timeline

import com.wifisentinel.core.detectors.Severity

enum class TimelineFilter {
    CRITICAL_ONLY,
    CURRENT_NETWORK,
    SUSPICIOUS_CHANGES
}

data class TimelineNetworkCard(
    val networkIdHint: String,
    val title: String,
    val subtitle: String,
    val lastSeenMs: Long,
    val sessionsCount: Int,
    val findingsCount: Int,
    val highestSeverity: Severity,
    val isCurrent: Boolean,
    val hasNewFindings: Boolean,
    val hasSuspiciousChanges: Boolean
)

data class TimelineSessionCard(
    val id: String,
    val startMs: Long,
    val endMs: Long,
    val findings: List<TimelineFindingCard>,
    val eventNotes: List<String>,
    val suspiciousChanges: List<String>
)

data class TimelineFindingCard(
    val id: String,
    val timestampMs: Long,
    val title: String,
    val severity: Severity,
    val explanation: String,
    val whyImportant: String,
    val whatToDo: String
)

data class TimelineNetworkDetails(
    val networkIdHint: String,
    val title: String,
    val subtitle: String,
    val sessions: List<TimelineSessionCard>
)

data class TimelineUiState(
    val searchQuery: String = "",
    val activeFilters: Set<TimelineFilter> = emptySet(),
    val networks: List<TimelineNetworkCard> = emptyList(),
    val selectedNetwork: TimelineNetworkDetails? = null
)

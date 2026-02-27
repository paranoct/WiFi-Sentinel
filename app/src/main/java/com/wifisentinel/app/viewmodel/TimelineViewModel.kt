package com.wifisentinel.app.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.app.R
import com.wifisentinel.app.report.ReportExporter
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.storage.NetworkEvent
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.feature.timeline.TimelineFilter
import com.wifisentinel.feature.timeline.TimelineFindingCard
import com.wifisentinel.feature.timeline.TimelineNetworkCard
import com.wifisentinel.feature.timeline.TimelineNetworkDetails
import com.wifisentinel.feature.timeline.TimelineSessionCard
import com.wifisentinel.feature.timeline.TimelineUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: NetworkRepository,
    private val reportExporter: ReportExporter,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val activeFilters = MutableStateFlow<Set<TimelineFilter>>(emptySet())
    private val selectedNetworkIdHint = MutableStateFlow<String?>(null)

    private val _reportEvents = MutableSharedFlow<ReportEvent>()
    val reportEvents = _reportEvents.asSharedFlow()

    private val timelineData = combine(
        repository.latestSnapshots(limit = SNAPSHOT_LIMIT),
        repository.latestFindings(limit = FINDINGS_LIMIT),
        repository.latestEvents(limit = EVENT_LIMIT),
        repository.latestSnapshot()
    ) { snapshots, findings, events, latestSnapshot ->
        TimelineData(
            networkSources = buildNetworkSources(
                snapshots = snapshots,
                findings = findings,
                events = events,
                currentNetworkIdHint = latestSnapshot?.networkIdHint
            )
        )
    }

    val uiState: StateFlow<TimelineUiState> = combine(
        timelineData,
        searchQuery,
        activeFilters,
        selectedNetworkIdHint
    ) { data, query, filters, selectedIdHint ->
        val sources = data.networkSources

        val filteredCards = applyFilters(
            sources = sources,
            query = query,
            filters = filters
        )

        val selectedSource = selectedIdHint?.let { selected ->
            sources.firstOrNull { it.networkIdHint == selected }
        }
        val selectedDetails = selectedSource?.toDetails()

        TimelineUiState(
            searchQuery = query,
            activeFilters = filters,
            networks = filteredCards.map { it.toCard() },
            selectedNetwork = selectedDetails
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TimelineUiState())

    fun onSearchQueryChange(value: String) {
        searchQuery.value = value
    }

    fun toggleFilter(filter: TimelineFilter) {
        activeFilters.update { current ->
            if (filter in current) current - filter else current + filter
        }
    }

    fun selectNetwork(networkIdHint: String) {
        selectedNetworkIdHint.value = networkIdHint
    }

    fun clearSelection() {
        selectedNetworkIdHint.value = null
    }

    fun clearNetworkHistory(networkIdHint: String? = null) {
        val selected = networkIdHint ?: selectedNetworkIdHint.value ?: return
        viewModelScope.launch {
            val cleared = runCatching { repository.clearHistoryForNetwork(selected) }.isSuccess
            if (cleared && selectedNetworkIdHint.value == selected) {
                selectedNetworkIdHint.value = null
            }
        }
    }

    fun exportNetworkReport(networkIdHint: String? = null) {
        val selected = networkIdHint ?: selectedNetworkIdHint.value ?: return
        if (networkIdHint != null) {
            selectedNetworkIdHint.value = networkIdHint
        }
        viewModelScope.launch {
            try {
                val maskSensitive = settingsRepository.settings.first().maskSensitive
                val uris = reportExporter.exportNetworkBundle(
                    networkIdHint = selected,
                    maskSensitive = maskSensitive
                )
                _reportEvents.emit(ReportEvent.Share(uris))
            } catch (_: Exception) {
                _reportEvents.emit(ReportEvent.Error(R.string.report_export_error))
            }
        }
    }

    sealed interface ReportEvent {
        data class Share(val uris: List<Uri>) : ReportEvent
        data class Error(val messageResId: Int) : ReportEvent
    }

    private fun buildNetworkSources(
        snapshots: List<NetworkSnapshot>,
        findings: List<Finding>,
        events: List<NetworkEvent>,
        currentNetworkIdHint: String?
    ): List<NetworkSource> {
        if (snapshots.isEmpty()) return emptyList()
        val visibleSnapshots = snapshots.filter { !it.ssid.isNullOrBlank() }
        if (visibleSnapshots.isEmpty()) return emptyList()
        val snapshotById = visibleSnapshots.associateBy { it.id }
        val findingsBySnapshot = findings.groupBy { it.snapshotId }
        val eventsBySnapshot = events
            .filter { !it.snapshotId.isNullOrBlank() }
            .groupBy { it.snapshotId.orEmpty() }

        return visibleSnapshots
            .groupBy { it.networkIdHint }
            .values
            .map { networkSnapshots ->
                val sortedAsc = networkSnapshots.sortedBy { it.timestampMs }
                val sortedDesc = networkSnapshots.sortedByDescending { it.timestampMs }
                val latest = sortedDesc.first()
                val sessions = splitIntoSessions(sortedAsc)
                val sessionCards = sessions.map { sessionSnapshots ->
                    val sessionFindings = sessionSnapshots
                        .flatMap { findingsBySnapshot[it.id].orEmpty() }
                        .map { finding ->
                            finding.toTimelineFinding(
                                snapshotTimestamp = snapshotById[finding.snapshotId]?.timestampMs
                                    ?: sessionSnapshots.last().timestampMs
                            )
                        }
                        .sortedWith(compareByDescending<TimelineFindingCard> { severityRank(it.severity) }
                            .thenByDescending { it.timestampMs })

                    val sessionEvents = sessionSnapshots
                        .flatMap { snapshot -> eventsBySnapshot[snapshot.id].orEmpty() }
                        .sortedByDescending { it.timestampMs }
                        .filterNot(::isConnectionEvent)
                        .map { event ->
                            val detail = normalizeLegacyText(event.detail).ifBlank { normalizeLegacyText(event.title) }
                            "${formatTime(event.timestampMs)} - $detail"
                        }
                        .distinct()
                        .take(MAX_EVENT_NOTES_PER_SESSION)

                    TimelineSessionCard(
                        id = "${latest.networkIdHint}|${sessionSnapshots.first().timestampMs}|${sessionSnapshots.last().timestampMs}",
                        startMs = sessionSnapshots.first().timestampMs,
                        endMs = sessionSnapshots.last().timestampMs,
                        findings = sessionFindings,
                        eventNotes = sessionEvents,
                        suspiciousChanges = describeSuspiciousChanges(sessionSnapshots)
                    )
                }.sortedByDescending { it.endMs }

                val allSessionFindings = sessionCards.flatMap { it.findings }
                val highestSeverity = allSessionFindings.maxOfOrNull { it.severity } ?: Severity.INFO
                val hasNew = allSessionFindings.any { it.timestampMs >= System.currentTimeMillis() - NEW_FINDINGS_WINDOW_MS }
                val hasSuspiciousChanges = sessionCards.any { it.suspiciousChanges.isNotEmpty() } || hasNew
                val title = networkTitle(latest)
                val subtitle = context.getString(
                    R.string.timeline_network_subtitle_format,
                    securityLabel(latest.securityType.name),
                    formatTime(latest.timestampMs)
                )
                NetworkSource(
                    networkIdHint = latest.networkIdHint,
                    title = title,
                    subtitle = subtitle,
                    lastSeenMs = latest.timestampMs,
                    sessions = sessionCards,
                    findingsCount = allSessionFindings.size,
                    highestSeverity = highestSeverity,
                    hasNewFindings = hasNew,
                    hasSuspiciousChanges = hasSuspiciousChanges,
                    isCurrent = currentNetworkIdHint != null && currentNetworkIdHint == latest.networkIdHint,
                    searchIndex = buildSearchIndex(
                        title = title,
                        subtitle = subtitle,
                        sessions = sessionCards
                    )
                )
            }
            .sortedByDescending { it.lastSeenMs }
    }

    private fun applyFilters(
        sources: List<NetworkSource>,
        query: String,
        filters: Set<TimelineFilter>
    ): List<NetworkSource> {
        val normalizedQuery = query.trim().lowercase()
        return sources.filter { source ->
            val queryPass = normalizedQuery.isBlank() || source.searchIndex.contains(normalizedQuery)
            val criticalPass = TimelineFilter.CRITICAL_ONLY !in filters ||
                source.highestSeverity >= Severity.HIGH
            val currentPass = TimelineFilter.CURRENT_NETWORK !in filters || source.isCurrent
            val suspiciousPass = TimelineFilter.SUSPICIOUS_CHANGES !in filters || source.hasSuspiciousChanges
            queryPass && criticalPass && currentPass && suspiciousPass
        }
    }

    private fun buildSearchIndex(
        title: String,
        subtitle: String,
        sessions: List<TimelineSessionCard>
    ): String {
        val parts = mutableListOf<String>()
        parts += title
        parts += subtitle
        sessions.forEach { session ->
            parts += session.eventNotes
            parts += session.suspiciousChanges
            session.findings.forEach { finding ->
                parts += finding.title
                parts += finding.explanation
                parts += finding.whyImportant
                parts += finding.whatToDo
            }
        }
        return parts.joinToString("\n").lowercase()
    }

    private fun splitIntoSessions(snapshotsAsc: List<NetworkSnapshot>): List<List<NetworkSnapshot>> {
        if (snapshotsAsc.isEmpty()) return emptyList()
        val sessions = mutableListOf<MutableList<NetworkSnapshot>>()
        var currentSession = mutableListOf<NetworkSnapshot>()
        snapshotsAsc.forEach { snapshot ->
            val previous = currentSession.lastOrNull()
            if (previous == null) {
                currentSession.add(snapshot)
                return@forEach
            }
            val gap = snapshot.timestampMs - previous.timestampMs
            if (gap > SESSION_GAP_MS) {
                sessions.add(currentSession)
                currentSession = mutableListOf(snapshot)
            } else {
                currentSession.add(snapshot)
            }
        }
        if (currentSession.isNotEmpty()) {
            sessions.add(currentSession)
        }
        return sessions
    }

    private fun describeSuspiciousChanges(snapshots: List<NetworkSnapshot>): List<String> {
        val changes = mutableListOf<String>()
        val bssids = snapshots.mapNotNull { it.bssid?.lowercase() }.distinct()
        val securityTypes = snapshots.map { it.securityType }.distinct()
        val dnsVariants = snapshots
            .map { it.dnsServers.sorted().joinToString(",") }
            .filter { it.isNotBlank() }
            .distinct()
        if (bssids.size > 1) {
            changes.add(context.getString(R.string.timeline_change_bssid))
        }
        if (securityTypes.size > 1) {
            changes.add(context.getString(R.string.timeline_change_security))
        }
        if (dnsVariants.size > 1) {
            changes.add(context.getString(R.string.timeline_change_dns))
        }
        return changes
    }

    private fun Finding.toTimelineFinding(snapshotTimestamp: Long): TimelineFindingCard {
        val titleText = normalizeLegacyText(FindingTextResolver.resolve(context, title))
        val explanationText = normalizeLegacyText(FindingTextResolver.resolve(context, explanation))
        val guidance = buildGuidance(detectorId, severity)
        return TimelineFindingCard(
            id = id,
            timestampMs = snapshotTimestamp,
            title = titleText,
            severity = severity,
            explanation = explanationText,
            whyImportant = guidance.first,
            whatToDo = guidance.second
        )
    }

    private fun buildGuidance(detectorId: String, severity: Severity): Pair<String, String> {
        return when (detectorId) {
            "evil_twin", "lookalike_ssid", "mac_spoof" -> Pair(
                first = context.getString(R.string.timeline_guidance_spoof_why),
                second = context.getString(R.string.timeline_guidance_spoof_action)
            )
            "dns_integrity", "pinned_dns" -> Pair(
                first = context.getString(R.string.timeline_guidance_dns_why),
                second = context.getString(R.string.timeline_guidance_dns_action)
            )
            "captive_portal" -> Pair(
                first = context.getString(R.string.timeline_guidance_portal_why),
                second = context.getString(R.string.timeline_guidance_portal_action)
            )
            "weak_security" -> Pair(
                first = context.getString(R.string.timeline_guidance_security_why),
                second = context.getString(R.string.timeline_guidance_security_action)
            )
            else -> {
                if (severity >= Severity.HIGH) {
                    Pair(
                        first = context.getString(R.string.timeline_guidance_high_why),
                        second = context.getString(R.string.timeline_guidance_high_action)
                    )
                } else {
                    Pair(
                        first = context.getString(R.string.timeline_guidance_warn_why),
                        second = context.getString(R.string.timeline_guidance_warn_action)
                    )
                }
            }
        }
    }

    private fun networkTitle(snapshot: NetworkSnapshot): String {
        return snapshot.ssid?.takeIf { it.isNotBlank() } ?: context.getString(R.string.network_hidden)
    }

    private fun isConnectionEvent(event: NetworkEvent): Boolean {
        val normalizedTitle = normalizeLegacyText(event.title).trim()
        return normalizedTitle == context.getString(R.string.event_connected_title)
    }

    private fun securityLabel(raw: String): String {
        return when (raw) {
            "WPA3" -> "WPA3"
            "WPA2_WPA3" -> "WPA2/WPA3"
            "WPA2" -> "WPA2"
            "WEP" -> "WEP"
            "OPEN" -> context.getString(R.string.security_open)
            else -> context.getString(R.string.security_unknown)
        }
    }

    private fun normalizeLegacyText(text: String): String {
        if (text.isBlank() || !looksLikeMojibake(text)) return text
        val candidates = buildList {
            add(text)
            decodeFrom(text, CP1251)?.let { add(it) }
            decodeFrom(text, LATIN1)?.let { add(it) }
        }
        return candidates.maxByOrNull { readabilityScore(it) } ?: text
    }

    private fun decodeFrom(text: String, sourceCharset: Charset): String? {
        val converted = runCatching {
            String(text.toByteArray(sourceCharset), Charsets.UTF_8)
        }.getOrNull() ?: return null
        return if (converted.contains(REPLACEMENT_CHAR)) null else converted
    }

    private fun readabilityScore(text: String): Int {
        val cyrillic = text.count { it in '\u0400'..'\u04FF' }
        val mojibakePenalty = MOJIBAKE_REGEX.findAll(text).count() * 3
        val replacementPenalty = text.count { it == REPLACEMENT_CHAR } * 5
        return cyrillic - mojibakePenalty - replacementPenalty
    }

    private fun looksLikeMojibake(text: String): Boolean {
        return text.contains("вЂ") || MOJIBAKE_REGEX.findAll(text).count() >= 2
    }

    private fun formatTime(timestampMs: Long): String {
        return DATE_TIME_FORMAT.format(Date(timestampMs))
    }

    private fun severityRank(severity: Severity): Int {
        return when (severity) {
            Severity.CRITICAL -> 4
            Severity.HIGH -> 3
            Severity.WARN -> 2
            Severity.INFO -> 1
        }
    }

    private data class NetworkSource(
        val networkIdHint: String,
        val title: String,
        val subtitle: String,
        val lastSeenMs: Long,
        val sessions: List<TimelineSessionCard>,
        val findingsCount: Int,
        val highestSeverity: Severity,
        val hasNewFindings: Boolean,
        val hasSuspiciousChanges: Boolean,
        val isCurrent: Boolean,
        val searchIndex: String
    ) {
        fun toCard(): TimelineNetworkCard {
            return TimelineNetworkCard(
                networkIdHint = networkIdHint,
                title = title,
                subtitle = subtitle,
                lastSeenMs = lastSeenMs,
                sessionsCount = sessions.size,
                findingsCount = findingsCount,
                highestSeverity = highestSeverity,
                isCurrent = isCurrent,
                hasNewFindings = hasNewFindings,
                hasSuspiciousChanges = hasSuspiciousChanges
            )
        }

        fun toDetails(): TimelineNetworkDetails {
            return TimelineNetworkDetails(
                networkIdHint = networkIdHint,
                title = title,
                subtitle = subtitle,
                sessions = sessions
            )
        }
    }

    private data class TimelineData(
        val networkSources: List<NetworkSource>
    )

    private companion object {
        const val SNAPSHOT_LIMIT = 240
        const val FINDINGS_LIMIT = 1_200
        const val EVENT_LIMIT = 600
        const val SESSION_GAP_MS = 20 * 60 * 1000L
        const val NEW_FINDINGS_WINDOW_MS = 24 * 60 * 60 * 1000L
        const val MAX_EVENT_NOTES_PER_SESSION = 6
        val DATE_TIME_FORMAT = SimpleDateFormat("HH:mm - dd MMM", Locale.getDefault())
        val CP1251: Charset = Charset.forName("windows-1251")
        val LATIN1: Charset = Charsets.ISO_8859_1
        val MOJIBAKE_REGEX = Regex("(Р.|С.|В«|В»)")
        const val REPLACEMENT_CHAR = '\uFFFD'
    }
}

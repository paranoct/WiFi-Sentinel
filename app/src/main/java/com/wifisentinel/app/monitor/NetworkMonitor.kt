package com.wifisentinel.app.monitor

import android.content.Context
import com.wifisentinel.app.R
import com.wifisentinel.app.di.ApplicationScope
import com.wifisentinel.app.notifications.NotificationHelper
import com.wifisentinel.app.permissions.WifiPermissions
import com.wifisentinel.app.security.AutoDisconnectController
import com.wifisentinel.core.detectors.AnalyzeContext
import com.wifisentinel.core.detectors.Detector
import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.FindingTextResolver
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.storage.NetworkEvent
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.NetworkObservation
import com.wifisentinel.core.wifi.NetworkObserver
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.NetworkSnapshotProvider
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import com.wifisentinel.core.wifi.WifiScanner
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.JvmSuppressWildcards

@Singleton
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkObserver: NetworkObserver,
    private val snapshotProvider: NetworkSnapshotProvider,
    private val wifiScanner: WifiScanner,
    private val repository: NetworkRepository,
    private val detectors: List<@JvmSuppressWildcards Detector>,
    private val notificationHelper: NotificationHelper,
    private val autoDisconnectController: AutoDisconnectController,
    private val settingsRepository: SettingsRepository,
    @ApplicationScope private val appScope: CoroutineScope
) {
    private val started = AtomicBoolean(false)
    private val processingMutex = Mutex()
    private var lastScanMs: Long = 0L
    private var lastAlertMs: Long = 0L
    private val lastEventTimestamps = mutableMapOf<String, Long>()
    private var activeConnectionSignature: String? = null
    private var hasInitialObservation = false

    fun start() {
        if (!started.compareAndSet(false, true)) return

        networkObserver.observations
            .onEach { observation ->
                if (!hasInitialObservation) {
                    when (observation) {
                        is NetworkObservation.Connected -> handleInitialConnected(observation.snapshot)
                        NetworkObservation.Disconnected -> {
                            hasInitialObservation = true
                            handleDisconnected()
                        }
                    }
                    return@onEach
                }
                when (observation) {
                    is NetworkObservation.Connected -> {
                        handleConnected(
                            snapshot = observation.snapshot,
                            connectionEventHint = observation.isConnectionEvent
                        )
                    }
                    NetworkObservation.Disconnected -> handleDisconnected()
                }
            }
            .launchIn(appScope)
    }

    fun refreshSnapshot(force: Boolean = false, allowNotifications: Boolean = true) {
        appScope.launch {
            runHealthCheck(
                force = force,
                allowNotifications = allowNotifications
            )
        }
    }

    suspend fun setCurrentNetworkAutoJoinBlocked(blocked: Boolean): ManualAutoJoinUpdateResult {
        if (settingsRepository.settings.first().demoModeEnabled) {
            return ManualAutoJoinUpdateResult.NoTrackableNetwork
        }
        val snapshot = snapshotProvider.currentSnapshot()
            ?: repository.latestSnapshotOnce()
            ?: return ManualAutoJoinUpdateResult.NoTrackableNetwork
        if (!hasTrackableSsid(snapshot) || snapshot.networkIdHint.isBlank()) {
            return ManualAutoJoinUpdateResult.NoTrackableNetwork
        }

        return processingMutex.withLock {
            withContext(Dispatchers.IO) {
                if (blocked) {
                    settingsRepository.blockAutoJoinForNetwork(snapshot.networkIdHint, manual = true)
                    val result = autoDisconnectController.disconnectAndRestrictAutoJoin(snapshot.ssid)
                    val notificationsEnabled = settingsRepository.settings.first().notificationsEnabled
                    if (notificationsEnabled) {
                        if (result.disconnected) {
                            notificationHelper.notifyAutoJoinBlocked(
                                ssid = snapshot.ssid,
                                systemAutoJoinDisabled = result.systemAutoJoinDisabled
                            )
                        } else {
                            notificationHelper.notifyAutoDisconnectManual(snapshot.ssid)
                        }
                    }
                    if (result.disconnected && canRecordEvents()) {
                        repository.saveEvents(listOf(buildAutoJoinBlockedEvent(snapshot)))
                    }
                    ManualAutoJoinUpdateResult.BlockApplied(
                        ssid = snapshot.ssid,
                        disconnected = result.disconnected,
                        systemAutoJoinDisabled = result.systemAutoJoinDisabled
                    )
                } else {
                    settingsRepository.unblockAutoJoinForNetwork(snapshot.networkIdHint, clearManual = true)
                    if (settingsRepository.settings.first().notificationsEnabled) {
                        notificationHelper.notifyAutoJoinAllowed(snapshot.ssid)
                    }
                    ManualAutoJoinUpdateResult.UnblockApplied(ssid = snapshot.ssid)
                }
            }
        }
    }

    suspend fun runHealthCheck(force: Boolean = false, allowNotifications: Boolean = true): Boolean {
        if (settingsRepository.settings.first().demoModeEnabled) return false
        val snapshot = snapshotProvider.currentSnapshot() ?: return false
        if (!hasTrackableSsid(snapshot)) return false
        processSnapshot(
            snapshot = snapshot,
            force = force,
            triggerScan = force,
            connectionEvent = false,
            allowNotifications = allowNotifications
        )
        return true
    }

    private suspend fun handleConnected(
        snapshot: NetworkSnapshot,
        connectionEventHint: Boolean
    ) {
        if (!hasTrackableSsid(snapshot)) {
            handleDisconnected()
            return
        }
        val signature = connectionSignature(snapshot)
        val isNewConnection = connectionEventHint || activeConnectionSignature != signature
        activeConnectionSignature = signature

        processSnapshot(
            snapshot = snapshot,
            force = isNewConnection,
            triggerScan = isNewConnection,
            connectionEvent = isNewConnection,
            allowNotifications = true
        )
    }

    private fun handleDisconnected() {
        activeConnectionSignature = null
    }

    private suspend fun handleInitialConnected(snapshot: NetworkSnapshot) {
        hasInitialObservation = true
        if (!hasTrackableSsid(snapshot)) {
            handleDisconnected()
            return
        }
        val signature = connectionSignature(snapshot)
        activeConnectionSignature = signature

        processSnapshot(
            snapshot = snapshot,
            force = false,
            triggerScan = true,
            connectionEvent = true,
            allowNotifications = false
        )
    }

    private fun connectionSignature(snapshot: NetworkSnapshot): String {
        val bssid = snapshot.bssid?.trim()?.lowercase().orEmpty()
        return "${snapshot.networkIdHint}|$bssid"
    }

    private fun snapshotKey(snapshot: NetworkSnapshot): String {
        return listOf(
            snapshot.networkIdHint,
            snapshot.bssid ?: "",
            snapshot.securityType.name,
            snapshot.gatewayV4 ?: "",
            snapshot.captivePortal.toString(),
            snapshot.dnsServers.sorted().joinToString(",")
        ).joinToString("|")
    }

    private suspend fun processSnapshot(
        snapshot: NetworkSnapshot,
        force: Boolean,
        triggerScan: Boolean,
        connectionEvent: Boolean,
        allowNotifications: Boolean
    ) {
        processingMutex.withLock {
            withContext(Dispatchers.IO) {
                if (settingsRepository.settings.first().demoModeEnabled) return@withContext
                if (!hasTrackableSsid(snapshot)) return@withContext
                val previous = repository.latestSnapshotOnce()
                val sameKey = previous != null && snapshotKey(previous) == snapshotKey(snapshot)
                if (!force && sameKey && !connectionEvent) return@withContext

                val history = repository.recentSnapshots(snapshot.networkIdHint, 10)
                repository.saveSnapshot(snapshot)

                if (enforceAutoJoinGuard(snapshot, allowNotifications)) return@withContext

                val connected = isConnectedSnapshot(snapshot)
                val readyForDetectors = connected
                val scanResults = if (readyForDetectors && canRecordEvents() && triggerScan) {
                    maybeScan(
                        connectionEvent = connectionEvent,
                        manualTrigger = force && !connectionEvent
                    )
                } else {
                    emptyList()
                }
                val scanWithCurrent = if (readyForDetectors) {
                    ensureCurrentNetworkScan(snapshot, scanResults)
                } else {
                    scanResults
                }
                val trustedProfiles = if (readyForDetectors) repository.trustedProfilesOnce() else emptyList()
                val trustedProfile = if (readyForDetectors) {
                    findTrustedProfile(snapshot, trustedProfiles)
                } else {
                    null
                }
                val category = trustedProfile?.category

                val findings = if (readyForDetectors) {
                    detectors.flatMap { detector ->
                        detector.analyze(
                            AnalyzeContext(
                                current = snapshot,
                                scanResults = scanWithCurrent,
                                trustedProfile = trustedProfile,
                                trustedProfiles = trustedProfiles,
                                history = history,
                                category = category
                            )
                        )
                    }
                } else {
                    emptyList()
                }

                val findingsWithKeys = if (findings.isNotEmpty()) {
                    findings.map { finding ->
                        finding.copy(dedupKey = buildDedupKey(finding, snapshot))
                    }
                } else {
                    emptyList()
                }

                val findingsForStorage = filterFindingsForCooldown(findingsWithKeys, snapshot.timestampMs)
                val findingsForAlerts = if (canRecordEvents()) findingsForStorage else emptyList()
                val findingsDisplayOnly = buildDisplayOnlyFindings(
                    currentFindings = findingsWithKeys,
                    canonicalFindings = findingsForStorage
                )
                val findingsToPersist = findingsForStorage + findingsDisplayOnly

                if (findingsToPersist.isNotEmpty()) {
                    repository.saveFindings(findingsToPersist, snapshot.timestampMs)
                }

                if (readyForDetectors) {
                    maybeLearnBssid(trustedProfile, snapshot, history)
                }

                if (canRecordEvents()) {
                    val autoDisconnected = maybeAutoDisconnect(
                        snapshot = snapshot,
                        trustedProfile = trustedProfile,
                        findings = findingsWithKeys,
                        allowNotifications = allowNotifications
                    )
                    val events = buildEvents(
                        snapshot = snapshot,
                        previous = previous,
                        findings = findingsForAlerts
                    ).toMutableList()
                    if (autoDisconnected) {
                        events.add(buildAutoDisconnectEvent(snapshot))
                    }
                    repository.saveEvents(events)

                    if (allowNotifications && connectionEvent) {
                        notifyNetworkReport(snapshot, findingsWithKeys)
                    }
                    if (allowNotifications) {
                        notifyIfNeeded(snapshot, findingsForAlerts)
                    }
                }
            }
        }
    }

    private suspend fun filterFindingsForCooldown(
        findings: List<Finding>,
        now: Long
    ): List<Finding> {
        if (findings.isEmpty()) return emptyList()
        val result = ArrayList<Finding>(findings.size)
        for (finding in findings) {
            val lastTimestamp = repository.latestFindingTimestamp(finding.dedupKey)
            if (lastTimestamp == null || now - lastTimestamp >= FINDING_DEDUP_THROTTLE_MS) {
                result.add(finding)
            }
        }
        return result
    }

    private fun buildDisplayOnlyFindings(
        currentFindings: List<Finding>,
        canonicalFindings: List<Finding>
    ): List<Finding> {
        if (currentFindings.isEmpty()) return emptyList()
        if (canonicalFindings.size == currentFindings.size) return emptyList()

        val canonicalKeys = canonicalFindings.map { it.dedupKey }.toSet()
        return currentFindings
            .filter { finding -> finding.dedupKey !in canonicalKeys }
            .map { finding -> finding.copy(dedupKey = "") }
    }

    private fun buildDedupKey(finding: Finding, snapshot: NetworkSnapshot): String {
        val key = normalizedNetworkKey(snapshot)
        val evidenceHash = hashEvidence(finding.evidence)
        return "${finding.detectorId}|$key|$evidenceHash"
    }

    private fun normalizedNetworkKey(snapshot: NetworkSnapshot): String {
        val hint = snapshot.networkIdHint.trim().lowercase()
        val ssid = snapshot.ssid?.trim()?.lowercase()
        val bssid = snapshot.bssid?.trim()?.lowercase()
        return hint.ifBlank { ssid ?: bssid ?: "unknown" }
    }

    private suspend fun maybeLearnBssid(
        trustedProfile: TrustedNetworkProfile?,
        snapshot: NetworkSnapshot,
        history: List<NetworkSnapshot>
    ) {
        val profile = trustedProfile ?: return
        if (!profile.meshMode || !profile.bssidLearning) return
        val bssid = snapshot.bssid?.trim() ?: return
        if (bssid.isBlank() || profile.allowedBssids.contains(bssid)) return

        val cutoff = snapshot.timestampMs - ONE_DAY_MS
        val observed = (history + snapshot)
            .filter { it.timestampMs >= cutoff }
            .mapNotNull { it.bssid?.lowercase() }
            .toSet()
        val known = profile.allowedBssids.map { it.lowercase() }.toSet()
        val newOnes = observed.filter { it !in known }

        if (newOnes.size <= profile.maxNewBssidPerDay) {
            repository.upsertTrustedProfile(
                profile.copy(
                    allowedBssids = profile.allowedBssids + bssid,
                    lastSeenMs = snapshot.timestampMs
                )
            )
        }
    }

    private suspend fun maybeAutoDisconnect(
        snapshot: NetworkSnapshot,
        trustedProfile: TrustedNetworkProfile?,
        findings: List<Finding>,
        allowNotifications: Boolean
    ): Boolean {
        if (!isConnectedSnapshot(snapshot)) return false
        if (trustedProfile != null) return false
        val settings = settingsRepository.settings.first()
        if (!settings.autoDisconnectEnabled) return false

        val shouldDisconnect = shouldAutoDisconnect(findings)
        if (!shouldDisconnect) return false
        if (!shouldLogEvent("auto-disconnect|${snapshot.networkIdHint}", snapshot.timestampMs, AUTO_DISCONNECT_THROTTLE_MS)) {
            return false
        }

        settingsRepository.blockAutoJoinForNetwork(snapshot.networkIdHint)
        val result = autoDisconnectController.disconnectAndRestrictAutoJoin(snapshot.ssid)
        if (settings.notificationsEnabled && allowNotifications) {
            if (result.disconnected) {
                notificationHelper.notifyAutoDisconnect(
                    ssid = snapshot.ssid,
                    autoJoinBlockedByApp = true,
                    systemAutoJoinDisabled = result.systemAutoJoinDisabled
                )
            } else {
                notificationHelper.notifyAutoDisconnectManual(snapshot.ssid)
            }
        }
        return result.disconnected
    }

    private fun buildAutoDisconnectEvent(snapshot: NetworkSnapshot): NetworkEvent {
        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        return NetworkEvent(
            id = UUID.randomUUID().toString(),
            timestampMs = snapshot.timestampMs,
            title = context.getString(R.string.event_auto_disconnect_title),
            detail = context.getString(R.string.event_auto_disconnect_detail, ssidLabel),
            severity = Severity.HIGH,
            snapshotId = snapshot.id
        )
    }

    private fun hashEvidence(evidence: Map<String, String>): String {
        val normalized = evidence.toSortedMap().entries.joinToString("|") { entry ->
            "${entry.key}=${entry.value}"
        }
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(normalized.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private suspend fun maybeScan(
        connectionEvent: Boolean,
        manualTrigger: Boolean
    ): List<ScanNet> {
        val now = System.currentTimeMillis()
        val minIntervalMs = when {
            connectionEvent -> CONNECTION_SCAN_THROTTLE_MS
            manualTrigger -> MANUAL_SCAN_THROTTLE_MS
            else -> PASSIVE_SCAN_THROTTLE_MS
        }
        if (now - lastScanMs < minIntervalMs) return emptyList()
        lastScanMs = now
        return wifiScanner.scan()
    }

    private fun buildEvents(
        snapshot: NetworkSnapshot,
        previous: NetworkSnapshot?,
        findings: List<Finding>
    ): List<NetworkEvent> {
        val events = mutableListOf<NetworkEvent>()
        val now = snapshot.timestampMs
        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)

        if (previous != null && previous.networkIdHint == snapshot.networkIdHint) {
            if (previous.bssid != null && snapshot.bssid != null && previous.bssid != snapshot.bssid) {
                if (shouldLogEvent("bssid|${snapshot.networkIdHint}|${snapshot.bssid}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_bssid_change_title),
                            detail = context.getString(R.string.event_bssid_change_detail, ssidLabel),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
            if (previous.securityType != snapshot.securityType) {
                if (shouldLogEvent("security|${snapshot.networkIdHint}|${snapshot.securityType.name}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_security_change_title),
                            detail = context.getString(
                                R.string.event_security_change_detail,
                                ssidLabel,
                                securityLabel(snapshot.securityType)
                            ),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
            if (!previous.captivePortal && snapshot.captivePortal) {
                if (shouldLogEvent("captive|${snapshot.networkIdHint}", now, EVENT_THROTTLE_MS)) {
                    events.add(
                        NetworkEvent(
                            id = UUID.randomUUID().toString(),
                            timestampMs = now,
                            title = context.getString(R.string.event_captive_portal_title),
                            detail = context.getString(R.string.event_captive_portal_detail, ssidLabel),
                            severity = Severity.WARN,
                            snapshotId = snapshot.id
                        )
                    )
                }
            }
        }

        findings.forEach { finding ->
            val findingKey = finding.dedupKey.ifBlank { "${finding.detectorId}|${finding.severity.name}" }
            val key = "finding|${snapshot.networkIdHint}|$findingKey"
            if (shouldLogEvent(key, now, FINDING_THROTTLE_MS)) {
                events.add(
                    NetworkEvent(
                        id = UUID.randomUUID().toString(),
                        timestampMs = now,
                        title = FindingTextResolver.title(context, finding),
                        detail = friendlyFindingDetail(finding),
                        severity = finding.severity,
                        snapshotId = finding.snapshotId
                    )
                )
            }
        }

        return events
    }

    private suspend fun notifyIfNeeded(
        snapshot: NetworkSnapshot,
        findings: List<Finding>
    ) {
        val settings = settingsRepository.settings.first()
        if (!settings.notificationsEnabled) return
        val severe = findings.filter { it.severity == Severity.HIGH || it.severity == Severity.CRITICAL }
        if (severe.isEmpty()) return
        val now = System.currentTimeMillis()
        if (now - lastAlertMs < ALERT_THROTTLE_MS) return
        lastAlertMs = now

        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        val first = severe.first()
        val extra = if (severe.size > 1) " +${severe.size - 1}" else ""
        val title = context.getString(R.string.notif_alert_title)
        val message = "$ssidLabel: ${FindingTextResolver.title(context, first)}$extra"
        notificationHelper.notifyRiskAlert(title, message)
    }

    private suspend fun notifyNetworkReport(
        snapshot: NetworkSnapshot,
        findings: List<Finding>
    ) {
        val settings = settingsRepository.settings.first()
        if (!settings.notificationsEnabled) return
        val key = "report-connect|${snapshot.networkIdHint}"
        if (!shouldLogEvent(key, snapshot.timestampMs, RECONNECT_REPORT_THROTTLE_MS)) return

        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        val safety = safetyLabel(findings)
        val separator = context.getString(R.string.value_separator)
        val message = buildString {
            append(ssidLabel)
            append(separator)
            append(securityLabel(snapshot.securityType))
            append(separator)
            append(signalLabel(snapshot.rssiDbm))
            append(separator)
            append(safety)
        }
        val notificationId = (snapshot.networkIdHint.hashCode() and 0x7fffffff) + 2000
        val showForget = findings.any { it.severity == Severity.HIGH || it.severity == Severity.CRITICAL }
        notificationHelper.notifyNetworkReport(
            notificationId,
            context.getString(R.string.notif_network_report_title),
            message,
            showForget,
            snapshot.ssid
        )
    }

    private fun canRecordEvents(): Boolean {
        return WifiPermissions.hasRequiredPermissions(context)
    }

    private fun findTrustedProfile(
        snapshot: NetworkSnapshot,
        profiles: List<TrustedNetworkProfile>
    ): TrustedNetworkProfile? {
        val ssid = snapshot.ssid
        val normalizedSsid = ssid?.trim()?.lowercase()
        return when {
            !ssid.isNullOrBlank() -> profiles.firstOrNull { it.ssid?.trim()?.lowercase() == normalizedSsid }
            snapshot.bssid != null -> profiles.firstOrNull { it.allowedBssids.contains(snapshot.bssid) }
            else -> null
        }
    }

    private fun isConnectedSnapshot(snapshot: NetworkSnapshot): Boolean {
        return hasTrackableSsid(snapshot)
    }

    private fun hasTrackableSsid(snapshot: NetworkSnapshot): Boolean {
        return !snapshot.ssid.isNullOrBlank()
    }

    private fun shouldLogEvent(key: String, now: Long, throttleMs: Long): Boolean {
        val last = lastEventTimestamps[key]
        if (last != null && now - last < throttleMs) return false
        lastEventTimestamps[key] = now
        return true
    }

    private fun securityLabel(type: SecurityType): String {
        return when (type) {
            SecurityType.OPEN -> context.getString(R.string.security_open)
            SecurityType.WEP -> context.getString(R.string.security_wep)
            SecurityType.WPA2 -> context.getString(R.string.security_wpa2)
            SecurityType.WPA3 -> context.getString(R.string.security_wpa3)
            SecurityType.WPA2_WPA3 -> context.getString(R.string.security_wpa2_wpa3)
            SecurityType.UNKNOWN -> context.getString(R.string.security_unknown)
        }
    }

    private fun safetyLabel(findings: List<Finding>): String {
        return when {
            findings.any { it.severity == Severity.CRITICAL || it.severity == Severity.HIGH } ->
                context.getString(R.string.safety_risk_high)
            findings.any { it.severity == Severity.WARN } ->
                context.getString(R.string.safety_risk_warn)
            else -> context.getString(R.string.safety_risk_low)
        }
    }

    private fun signalLabel(rssiDbm: Int?): String {
        if (rssiDbm == null) return context.getString(R.string.signal_no_data)
        return when {
            rssiDbm >= -50 -> context.getString(R.string.signal_excellent)
            rssiDbm >= -60 -> context.getString(R.string.signal_good)
            rssiDbm >= -70 -> context.getString(R.string.signal_medium)
            rssiDbm >= -80 -> context.getString(R.string.signal_weak)
            else -> context.getString(R.string.signal_very_weak)
        }
    }

    private fun friendlyFindingDetail(finding: Finding): String {
        return when (finding.detectorId) {
            "captive_portal" -> context.getString(R.string.finding_detail_captive_portal)
            "weak_security" -> context.getString(R.string.finding_detail_weak_security)
            "dns_integrity" -> context.getString(R.string.finding_detail_dns_integrity)
            "gateway_anomaly" -> context.getString(R.string.finding_detail_gateway_anomaly)
            "disconnect_anomaly" -> context.getString(R.string.finding_detail_disconnect_anomaly)
            else -> FindingTextResolver.explanation(context, finding)
        }
    }

    private fun shouldAutoDisconnect(findings: List<Finding>): Boolean {
        if (findings.any { it.severity == Severity.CRITICAL }) return true
        val highFindings = findings.filter { it.severity == Severity.HIGH }
        if (highFindings.isEmpty()) return false
        val spoofingHigh = highFindings.any { it.detectorId in AUTO_DISCONNECT_HIGH_IMMEDIATE_IDS }
        if (spoofingHigh) return true
        val distinctHighDetectors = highFindings.map { it.detectorId }.distinct().size
        return distinctHighDetectors >= AUTO_DISCONNECT_MULTI_HIGH_THRESHOLD
    }

    private suspend fun enforceAutoJoinGuard(
        snapshot: NetworkSnapshot,
        allowNotifications: Boolean
    ): Boolean {
        val settings = settingsRepository.settings.first()
        if (!settings.blockedAutoJoinNetworkHints.contains(snapshot.networkIdHint)) return false

        val trustedNow = findTrustedProfile(snapshot, repository.trustedProfilesOnce()) != null
        if (trustedNow) {
            settingsRepository.unblockAutoJoinForNetwork(snapshot.networkIdHint)
            return false
        }

        val result = autoDisconnectController.disconnectAndRestrictAutoJoin(snapshot.ssid)
        val shouldRecordEvent = shouldLogEvent(
            key = "autojoin-guard|${snapshot.networkIdHint}",
            now = snapshot.timestampMs,
            throttleMs = AUTOJOIN_GUARD_THROTTLE_MS
        )
        if (shouldRecordEvent) {
            repository.saveEvents(listOf(buildAutoJoinBlockedEvent(snapshot)))
            if (settings.notificationsEnabled && allowNotifications) {
                if (result.disconnected) {
                    notificationHelper.notifyAutoJoinBlocked(snapshot.ssid)
                } else {
                    notificationHelper.notifyAutoDisconnectManual(snapshot.ssid)
                }
            }
        }
        return result.disconnected
    }

    private fun buildAutoJoinBlockedEvent(snapshot: NetworkSnapshot): NetworkEvent {
        val ssidLabel = snapshot.ssid ?: context.getString(R.string.network_hidden)
        return NetworkEvent(
            id = UUID.randomUUID().toString(),
            timestampMs = snapshot.timestampMs,
            title = context.getString(R.string.event_autojoin_blocked_title),
            detail = context.getString(R.string.event_autojoin_blocked_detail, ssidLabel),
            severity = Severity.WARN,
            snapshotId = snapshot.id
        )
    }

    private fun ensureCurrentNetworkScan(
        snapshot: NetworkSnapshot,
        scanResults: List<ScanNet>
    ): List<ScanNet> {
        val ssid = snapshot.ssid?.trim()
        val bssid = snapshot.bssid?.trim()
        if (ssid.isNullOrBlank() && bssid.isNullOrBlank()) return scanResults

        val hasMatch = scanResults.any { net ->
            (bssid != null && net.bssid?.equals(bssid, ignoreCase = true) == true) ||
                (!ssid.isNullOrBlank() && net.ssid?.equals(ssid, ignoreCase = true) == true)
        }
        if (hasMatch) return scanResults

        return scanResults + ScanNet(
            ssid = ssid,
            bssid = bssid,
            frequencyMhz = snapshot.frequencyMhz,
            rssiDbm = snapshot.rssiDbm,
            securityType = snapshot.securityType,
            channelWidth = null,
            wifiStandard = null
        )
    }

    private companion object {
        const val ONE_DAY_MS = 24 * 60 * 60 * 1000L
        const val ALERT_THROTTLE_MS = 2 * 60 * 1000L
        const val EVENT_THROTTLE_MS = 10 * 60 * 1000L
        const val FINDING_THROTTLE_MS = ONE_DAY_MS
        const val FINDING_DEDUP_THROTTLE_MS = ONE_DAY_MS
        const val CONNECTION_SCAN_THROTTLE_MS = 1_000L
        const val MANUAL_SCAN_THROTTLE_MS = 1_000L
        const val PASSIVE_SCAN_THROTTLE_MS = 30_000L
        const val RECONNECT_REPORT_THROTTLE_MS = 1_000L
        const val AUTO_DISCONNECT_THROTTLE_MS = 2 * 60 * 1000L
        const val AUTOJOIN_GUARD_THROTTLE_MS = 2 * 60 * 1000L
        const val AUTO_DISCONNECT_MULTI_HIGH_THRESHOLD = 2
        val AUTO_DISCONNECT_HIGH_IMMEDIATE_IDS = setOf("evil_twin", "mac_spoof")
    }
}

sealed interface ManualAutoJoinUpdateResult {
    data object NoTrackableNetwork : ManualAutoJoinUpdateResult
    data class BlockApplied(
        val ssid: String?,
        val disconnected: Boolean,
        val systemAutoJoinDisabled: Boolean
    ) : ManualAutoJoinUpdateResult

    data class UnblockApplied(val ssid: String?) : ManualAutoJoinUpdateResult
}

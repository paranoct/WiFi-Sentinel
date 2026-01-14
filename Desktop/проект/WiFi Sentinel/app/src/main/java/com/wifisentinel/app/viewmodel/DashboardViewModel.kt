package com.wifisentinel.app.viewmodel

import android.content.Context
import android.net.Uri
import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.app.R
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.report.ReportExporter
import com.wifisentinel.app.trusted.TrustedNetworkManager
import com.wifisentinel.core.risk.RiskEngine
import com.wifisentinel.core.risk.RiskSummary
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.Band
import com.wifisentinel.core.wifi.BandMapper
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.WifiScanner
import com.wifisentinel.feature.dashboard.DashboardUiState
import com.wifisentinel.feature.dashboard.RouterRecommendations
import com.wifisentinel.feature.dashboard.RouterSettingItem
import com.wifisentinel.feature.dashboard.RouterSettingsSection
import com.wifisentinel.feature.dashboard.SecurityLabelChange
import com.wifisentinel.feature.dashboard.SecurityLabelDns
import com.wifisentinel.feature.dashboard.SecurityLabelMesh
import com.wifisentinel.feature.dashboard.SecurityLabelPortal
import com.wifisentinel.feature.dashboard.SecurityLabelSecurity
import com.wifisentinel.feature.dashboard.SecurityNutritionLabelState
import com.wifisentinel.feature.dashboard.R as DashboardR
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: NetworkRepository,
    private val riskEngine: RiskEngine,
    private val trustedNetworkManager: TrustedNetworkManager,
    private val networkMonitor: NetworkMonitor,
    private val reportExporter: ReportExporter,
    private val wifiScanner: WifiScanner,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private var lastRiskSummary: RiskSummary = RiskSummary.empty()
    private var lastSnapshotKey: String? = null
    private var lastAutoScanNetworkId: String? = null
    private var lastAutoScanMs: Long = 0L

    private val baseStateFlow = repository.latestSnapshot()
        .onEach { snapshot ->
            if (snapshot != null) {
                maybeAutoScan(snapshot)
            }
        }
        .flatMapLatest { snapshot ->
            if (snapshot == null) {
                lastRiskSummary = RiskSummary.empty()
                lastSnapshotKey = null
                flowOf(DashboardUiState())
            } else {
                val key = snapshot.networkIdHint
                if (key != lastSnapshotKey) {
                    lastRiskSummary = RiskSummary.empty()
                    lastSnapshotKey = key
                }
                flow {
                    emit(
                        DashboardUiState(
                            snapshot = snapshot,
                            riskSummary = lastRiskSummary,
                            findings = emptyList(),
                            isRiskCalculating = true,
                            securityLabel = SecurityNutritionLabelState()
                        )
                    )
                    val trusted = repository.findTrustedProfile(snapshot)
                    val category = trusted?.category
                    val findingsFlow = repository.findingsForSnapshot(snapshot.id)
                    emitAll(
                        findingsFlow.map { findings ->
                            val risk = riskEngine.evaluate(findings, category)
                            lastRiskSummary = risk
                            val previous = repository.recentSnapshots(snapshot.networkIdHint, 2).getOrNull(1)
                            val labelState = buildSecurityLabel(snapshot, previous, findings, trusted)
                            DashboardUiState(
                                snapshot = snapshot,
                                riskSummary = risk,
                                findings = findings,
                                isRiskCalculating = false,
                                securityLabel = labelState
                            )
                        }
                    )
                }
            }
        }

    private data class ScanState(
        val inProgress: Boolean = false,
        val lastScanMs: Long? = null,
        val recommendations: RouterRecommendations = RouterRecommendations()
    )

    private val scanState = MutableStateFlow(ScanState())

    val uiState: StateFlow<DashboardUiState> = combine(
        baseStateFlow,
        scanState,
        settingsRepository.settings
    ) { base, scan, settings ->
        val dnsLabel = when {
            !settings.dnsCheckEnabled -> SecurityLabelDns.UNAVAILABLE
            base.securityLabel.dns != SecurityLabelDns.UNAVAILABLE -> base.securityLabel.dns
            base.snapshot?.dnsServers?.isNotEmpty() == true -> SecurityLabelDns.NORMAL
            else -> SecurityLabelDns.UNAVAILABLE
        }
        base.copy(
            isScanning = scan.inProgress,
            lastScanTimeMs = scan.lastScanMs,
            routerRecommendations = scan.recommendations,
            maskSensitive = settings.maskSensitive,
            securityLabel = base.securityLabel.copy(dns = dnsLabel),
            isDemoMode = settings.demoModeEnabled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())

    private val _reportEvents = MutableSharedFlow<ReportEvent>()
    val reportEvents = _reportEvents.asSharedFlow()

    fun exportReport() {
        viewModelScope.launch {
            try {
                val uri = reportExporter.export()
                _reportEvents.emit(ReportEvent.Share(uri))
            } catch (e: Exception) {
                _reportEvents.emit(
                    ReportEvent.Error(R.string.report_export_error)
                )
            }
        }
    }

    fun addCurrentToTrusted() {
        viewModelScope.launch {
            trustedNetworkManager.addOrUpdateCurrent(NetworkCategory.HOME, meshMode = false)
            networkMonitor.refreshSnapshot(force = true)
        }
    }

    fun refreshSnapshot() {
        networkMonitor.refreshSnapshot()
    }

    fun scanNow() {
        viewModelScope.launch {
            if (settingsRepository.settings.first().demoModeEnabled) return@launch
            val snapshot = repository.latestSnapshotOnce()
            performScan(snapshot, refreshSnapshot = true)
        }
    }

    fun exitDemoMode() {
        viewModelScope.launch {
            settingsRepository.setDemoModeEnabled(false)
        }
    }

    sealed interface ReportEvent {
        data class Share(val uri: Uri) : ReportEvent
        data class Error(val messageResId: Int) : ReportEvent
    }

    private fun buildRecommendations(
        scanResults: List<ScanNet>,
        snapshot: com.wifisentinel.core.wifi.NetworkSnapshot?
    ): RouterRecommendations {
        val byBand = scanResults.groupBy { BandMapper.fromFrequencyMhz(it.frequencyMhz) }
        val band24 = byBand[Band.BAND_2G4].orEmpty()
        val band5 = byBand[Band.BAND_5G].orEmpty()

        val currentNet = pickCurrentNetwork(scanResults, snapshot)
        val currentSections = buildCurrentSections(currentNet)

        val channel24 = choose24Channel(band24)
        val channel5 = choose5Channel(band5)
        val congestion24 = channel24?.let { channelInterferenceScore(it, band24, 4) } ?: 0
        val congestion5 = channel5?.let { channelInterferenceScore(it, band5, 4) } ?: 0
        val width24 = if (band24.size >= 6 || congestion24 >= 240) 20 else 40
        val width5 = if (band5.size >= 10 || congestion5 >= 320) 40 else 80
        val channel24Value = channel24?.let {
            context.getString(DashboardR.string.router_value_auto_channel, it)
        } ?: context.getString(DashboardR.string.router_value_no_data)
        val channel5Value = channel5?.let {
            context.getString(DashboardR.string.router_value_auto_channel, it)
        } ?: context.getString(DashboardR.string.router_value_no_data)

        val sections = listOf(
            RouterSettingsSection(
                titleResId = DashboardR.string.router_section_24_title,
                items = listOf(
                    RouterSettingItem(DashboardR.string.router_setting_channel, channel24Value),
                    RouterSettingItem(
                        DashboardR.string.router_setting_mode,
                        context.getString(DashboardR.string.router_value_mode_24)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_bandwidth,
                        context.getString(DashboardR.string.router_value_bandwidth_mhz, width24)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_guard_interval_11n,
                        context.getString(DashboardR.string.router_value_short)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_hidden_ssid,
                        context.getString(DashboardR.string.router_value_off)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_wmm,
                        context.getString(DashboardR.string.router_value_on)
                    )
                )
            ),
            RouterSettingsSection(
                titleResId = DashboardR.string.router_section_5_title,
                items = listOf(
                    RouterSettingItem(DashboardR.string.router_setting_channel, channel5Value),
                    RouterSettingItem(
                        DashboardR.string.router_setting_mode,
                        context.getString(DashboardR.string.router_value_mode_5)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_bandwidth,
                        context.getString(DashboardR.string.router_value_bandwidth_mhz, width5)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_guard_interval_11ax,
                        context.getString(DashboardR.string.router_value_short)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_hidden_ssid,
                        context.getString(DashboardR.string.router_value_off)
                    ),
                    RouterSettingItem(
                        DashboardR.string.router_setting_wmm,
                        context.getString(DashboardR.string.router_value_on)
                    )
                )
            )
        )

        return RouterRecommendations(
            sections = sections,
            currentSections = currentSections,
            scanCount24 = band24.size,
            scanCount5 = band5.size
        )
    }

    private fun choose24Channel(nets: List<ScanNet>): Int? {
        if (nets.isEmpty()) return null
        val candidates = listOf(1, 6, 11)
        return candidates.minByOrNull { candidate ->
            channelInterferenceScore(candidate, nets, 4)
        }
    }

    private fun choose5Channel(nets: List<ScanNet>): Int? {
        if (nets.isEmpty()) return null
        val candidates = listOf(36, 40, 44, 48, 149, 153, 157, 161, 165)
        return candidates.minByOrNull { candidate ->
            channelInterferenceScore(candidate, nets, 4)
        }
    }

    private fun frequencyToChannel(frequencyMhz: Int?): Int? {
        if (frequencyMhz == null) return null
        return when {
            frequencyMhz == 2484 -> 14
            frequencyMhz in 2412..2472 -> (frequencyMhz - 2407) / 5
            frequencyMhz in 5000..5899 -> (frequencyMhz - 5000) / 5
            frequencyMhz in 5925..7125 -> (frequencyMhz - 5950) / 5
            else -> null
        }
    }

    private fun rssiWeight(rssiDbm: Int?): Int {
        return if (rssiDbm == null) 50 else (100 + rssiDbm).coerceIn(0, 100)
    }

    private fun channelInterferenceScore(
        candidate: Int,
        nets: List<ScanNet>,
        maxDistance: Int
    ): Int {
        return nets.sumOf { net ->
            val channel = frequencyToChannel(net.frequencyMhz) ?: return@sumOf 0
            val distance = abs(channel - candidate)
            if (distance > maxDistance) return@sumOf 0
            val overlap = max(0, maxDistance - distance + 1)
            rssiWeight(net.rssiDbm) * overlap
        }
    }

    private fun pickCurrentNetwork(
        scanResults: List<ScanNet>,
        snapshot: com.wifisentinel.core.wifi.NetworkSnapshot?
    ): ScanNet? {
        if (snapshot == null) return null
        snapshot.bssid?.let { bssid ->
            scanResults.firstOrNull { it.bssid.equals(bssid, ignoreCase = true) }?.let { return it }
        }
        val ssid = snapshot.ssid
        if (!ssid.isNullOrBlank()) {
            val matches = scanResults.filter { it.ssid.equals(ssid, ignoreCase = true) }
            if (matches.isNotEmpty()) {
                return matches.maxByOrNull { it.rssiDbm ?: -100 }
            }
        }
        val fallback = if (!ssid.isNullOrBlank() || !snapshot.bssid.isNullOrBlank()) {
            ScanNet(
                ssid = ssid?.trim(),
                bssid = snapshot.bssid?.trim(),
                frequencyMhz = snapshot.frequencyMhz,
                rssiDbm = snapshot.rssiDbm,
                securityType = snapshot.securityType,
                channelWidth = null,
                wifiStandard = null
            )
        } else {
            null
        }
        return fallback ?: scanResults.maxByOrNull { it.rssiDbm ?: -100 }
    }

    private fun buildCurrentSections(currentNet: ScanNet?): List<RouterSettingsSection> {
        if (currentNet == null) return emptyList()
        val freq = currentNet.frequencyMhz
        val bandLabel = when {
            freq == null -> context.getString(DashboardR.string.router_value_unknown)
            freq < 3000 -> context.getString(DashboardR.string.router_band_2g4)
            freq < 6000 -> context.getString(DashboardR.string.router_band_5g)
            else -> context.getString(DashboardR.string.router_band_6g)
        }
        val channel = frequencyToChannel(freq)?.toString() ?: context.getString(DashboardR.string.router_value_unknown)
        val width = channelWidthLabel(currentNet.channelWidth)
        val standard = wifiStandardLabel(currentNet.wifiStandard)
        val hidden = if (currentNet.ssid.isNullOrBlank()) {
            context.getString(DashboardR.string.router_value_on)
        } else {
            context.getString(DashboardR.string.router_value_off)
        }

        return listOf(
            RouterSettingsSection(
                titleResId = DashboardR.string.router_section_current_title,
                titleArgs = listOf(bandLabel),
                items = listOf(
                    RouterSettingItem(DashboardR.string.router_setting_channel, channel),
                    RouterSettingItem(DashboardR.string.router_setting_mode, standard),
                    RouterSettingItem(DashboardR.string.router_setting_bandwidth, width),
                    RouterSettingItem(
                        DashboardR.string.router_setting_guard_interval,
                        context.getString(DashboardR.string.router_value_unknown)
                    ),
                    RouterSettingItem(DashboardR.string.router_setting_hidden_ssid, hidden),
                    RouterSettingItem(
                        DashboardR.string.router_setting_wmm,
                        context.getString(DashboardR.string.router_value_unknown)
                    )
                )
            )
        )
    }

    private fun wifiStandardLabel(standard: Int?): String {
        return when (standard) {
            null -> context.getString(DashboardR.string.router_value_unknown)
            ScanResult.WIFI_STANDARD_LEGACY -> context.getString(DashboardR.string.router_value_standard_legacy)
            ScanResult.WIFI_STANDARD_11AC -> context.getString(DashboardR.string.router_value_standard_11ac)
            ScanResult.WIFI_STANDARD_11AX -> context.getString(DashboardR.string.router_value_standard_11ax)
            ScanResult.WIFI_STANDARD_11N -> context.getString(DashboardR.string.router_value_standard_11n)
            ScanResult.WIFI_STANDARD_11AD -> context.getString(DashboardR.string.router_value_standard_11ad)
            ScanResult.WIFI_STANDARD_11BE -> context.getString(DashboardR.string.router_value_standard_11be)
            else -> context.getString(DashboardR.string.router_value_unknown)
        }
    }

    private fun channelWidthLabel(channelWidth: Int?): String {
        return when (channelWidth) {
            null -> context.getString(DashboardR.string.router_value_unknown)
            ScanResult.CHANNEL_WIDTH_20MHZ -> context.getString(DashboardR.string.router_value_bandwidth_20)
            ScanResult.CHANNEL_WIDTH_40MHZ -> context.getString(DashboardR.string.router_value_bandwidth_40)
            ScanResult.CHANNEL_WIDTH_80MHZ -> context.getString(DashboardR.string.router_value_bandwidth_80)
            ScanResult.CHANNEL_WIDTH_80MHZ_PLUS_MHZ -> context.getString(DashboardR.string.router_value_bandwidth_80p80)
            ScanResult.CHANNEL_WIDTH_160MHZ -> context.getString(DashboardR.string.router_value_bandwidth_160)
            else -> context.getString(DashboardR.string.router_value_unknown)
        }
    }

    private fun maybeAutoScan(snapshot: com.wifisentinel.core.wifi.NetworkSnapshot) {
        if (scanState.value.inProgress) return
        val connected = !snapshot.ssid.isNullOrBlank() || !snapshot.bssid.isNullOrBlank()
        if (!connected) return
        val now = System.currentTimeMillis()
        val networkId = snapshot.networkIdHint
        val networkChanged = networkId != lastAutoScanNetworkId
        if (!networkChanged && now - lastAutoScanMs < AUTO_SCAN_THROTTLE_MS) return
        lastAutoScanNetworkId = networkId
        lastAutoScanMs = now
        viewModelScope.launch {
            if (settingsRepository.settings.first().demoModeEnabled) return@launch
            performScan(snapshot, refreshSnapshot = false)
        }
    }

    private suspend fun performScan(
        snapshot: com.wifisentinel.core.wifi.NetworkSnapshot?,
        refreshSnapshot: Boolean
    ) {
        if (scanState.value.inProgress) return
        if (settingsRepository.settings.first().demoModeEnabled) return
        scanState.update { it.copy(inProgress = true) }
        try {
            val scanResults = wifiScanner.scan()
            val recommendations = buildRecommendations(scanResults, snapshot)
            scanState.value = ScanState(
                inProgress = false,
                lastScanMs = System.currentTimeMillis(),
                recommendations = recommendations
            )
        } catch (_: Exception) {
            scanState.update { it.copy(inProgress = false) }
        } finally {
            if (refreshSnapshot) {
                networkMonitor.refreshSnapshot(force = true)
            }
        }
    }

    private companion object {
        const val AUTO_SCAN_THROTTLE_MS = 5_000L
    }

    private fun buildSecurityLabel(
        snapshot: com.wifisentinel.core.wifi.NetworkSnapshot,
        previous: com.wifisentinel.core.wifi.NetworkSnapshot?,
        findings: List<com.wifisentinel.core.detectors.Finding>,
        trustedProfile: com.wifisentinel.core.wifi.TrustedNetworkProfile?
    ): SecurityNutritionLabelState {
        val security = when (snapshot.securityType) {
            com.wifisentinel.core.wifi.SecurityType.WPA3 -> SecurityLabelSecurity.WPA3
            com.wifisentinel.core.wifi.SecurityType.WPA2 -> SecurityLabelSecurity.WPA2
            com.wifisentinel.core.wifi.SecurityType.WPA2_WPA3 -> SecurityLabelSecurity.WPA2_WPA3
            com.wifisentinel.core.wifi.SecurityType.OPEN -> SecurityLabelSecurity.OPEN
            com.wifisentinel.core.wifi.SecurityType.WEP -> SecurityLabelSecurity.WEP
            com.wifisentinel.core.wifi.SecurityType.UNKNOWN -> SecurityLabelSecurity.UNKNOWN
        }
        val portal = if (snapshot.captivePortal) SecurityLabelPortal.PRESENT else SecurityLabelPortal.ABSENT

        val changesKnown = previous != null && previous.networkIdHint == snapshot.networkIdHint
        val changes = if (changesKnown) {
            buildSet {
                if (!previous?.bssid.isNullOrBlank() && previous?.bssid != snapshot.bssid) {
                    add(SecurityLabelChange.BSSID)
                }
                if (previous?.securityType != snapshot.securityType) {
                    add(SecurityLabelChange.SECURITY)
                }
                if (previous?.dnsServers?.toSet() != snapshot.dnsServers.toSet()) {
                    add(SecurityLabelChange.DNS)
                }
            }
        } else {
            emptySet()
        }

        val dnsStatus = when {
            findings.any { it.detectorId == "dns_integrity" && it.severity >= com.wifisentinel.core.detectors.Severity.WARN } ->
                SecurityLabelDns.SUSPICIOUS
            findings.any { it.detectorId == "dns_integrity" } ->
                SecurityLabelDns.NORMAL
            else -> SecurityLabelDns.UNAVAILABLE
        }

        val mesh = trustedProfile?.let { profile ->
            if (profile.meshMode) SecurityLabelMesh.ENABLED else SecurityLabelMesh.DISABLED
        }

        return SecurityNutritionLabelState(
            security = security,
            portal = portal,
            changes = changes,
            changesKnown = changesKnown,
            dns = dnsStatus,
            mesh = mesh
        )
    }
}

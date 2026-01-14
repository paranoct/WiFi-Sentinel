package com.wifisentinel.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.trusted.TrustedNetworkManager
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.WifiScanner
import com.wifisentinel.feature.trusted.AddSource
import com.wifisentinel.feature.trusted.TrustedAddCandidate
import com.wifisentinel.feature.trusted.TrustedUiState
import com.wifisentinel.feature.trusted.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class TrustedNetworksViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val trustedNetworkManager: TrustedNetworkManager,
    private val networkMonitor: NetworkMonitor,
    private val wifiScanner: WifiScanner
) : ViewModel() {
    private val selectedCategory = MutableStateFlow(NetworkCategory.HOME)
    private val scanResults = MutableStateFlow<List<ScanNet>>(emptyList())
    private val pendingAdd = MutableStateFlow<TrustedAddCandidate?>(null)
    private val isScanning = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<TrustedUiState> = selectedCategory
        .flatMapLatest { category ->
            combine(
                repository.trustedProfiles(category),
                scanResults,
                pendingAdd,
                isScanning,
                errorMessage
            ) { profiles, scans, pending, scanning, error ->
                TrustedUiState(
                    selectedCategory = category,
                    profiles = profiles,
                    scanResults = scans,
                    pendingAdd = pending,
                    isScanning = scanning,
                errorMessageResId = error
            )
        }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TrustedUiState())

    fun onTabSelected(category: NetworkCategory) {
        selectedCategory.value = category
    }

    fun requestAddCurrent() {
        viewModelScope.launch {
            val snapshot = repository.latestSnapshotOnce()
            if (snapshot == null) {
                errorMessage.value = R.string.trusted_error_no_current_network
                return@launch
            }
            errorMessage.value = null
            pendingAdd.value = TrustedAddCandidate(
                source = AddSource.CURRENT,
                ssid = snapshot.ssid,
                bssid = snapshot.bssid,
                securityType = snapshot.securityType,
                frequencyMhz = snapshot.frequencyMhz,
                rssiDbm = snapshot.rssiDbm
            )
        }
    }

    fun requestAddFromScan() {
        viewModelScope.launch {
            isScanning.value = true
            errorMessage.value = null
            scanResults.value = wifiScanner.scan()
            isScanning.value = false
        }
    }

    fun selectScanCandidate(net: ScanNet) {
        pendingAdd.value = TrustedAddCandidate(
            source = AddSource.SCAN,
            ssid = net.ssid,
            bssid = net.bssid,
            securityType = net.securityType,
            frequencyMhz = net.frequencyMhz,
            rssiDbm = net.rssiDbm
        )
        scanResults.value = emptyList()
    }

    fun dismissSheets() {
        pendingAdd.value = null
        scanResults.value = emptyList()
        errorMessage.value = null
    }

    fun confirmAdd(candidate: TrustedAddCandidate, category: NetworkCategory, meshMode: Boolean) {
        viewModelScope.launch {
            val success = when (candidate.source) {
                AddSource.CURRENT -> trustedNetworkManager.addOrUpdateCurrent(category, meshMode)
                AddSource.SCAN -> {
                    val scanNet = ScanNet(
                        ssid = candidate.ssid,
                        bssid = candidate.bssid,
                        frequencyMhz = candidate.frequencyMhz,
                        rssiDbm = candidate.rssiDbm,
                        securityType = candidate.securityType
                    )
                    trustedNetworkManager.addFromScan(scanNet, category, meshMode)
                }
            }
            if (!success) {
                errorMessage.value = R.string.trusted_error_add_failed
            } else {
                errorMessage.value = null
            }
            pendingAdd.value = null
            scanResults.value = emptyList()
            networkMonitor.refreshSnapshot(force = true)
        }
    }

    fun moveProfile(profileId: String, category: NetworkCategory) {
        viewModelScope.launch {
            repository.moveTrustedProfile(profileId, category)
        }
    }

    fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            repository.deleteTrustedProfile(profileId)
        }
    }

    fun acceptFingerprint(profileId: String) {
        viewModelScope.launch {
            trustedNetworkManager.acceptCurrentFingerprint(profileId)
            networkMonitor.refreshSnapshot(force = true)
        }
    }

    fun updateProfile(profile: com.wifisentinel.core.wifi.TrustedNetworkProfile) {
        viewModelScope.launch {
            repository.upsertTrustedProfile(profile)
        }
    }
}

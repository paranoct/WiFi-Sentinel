package com.wifisentinel.feature.trusted

import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile

data class TrustedUiState(
    val selectedCategory: NetworkCategory = NetworkCategory.HOME,
    val profiles: List<TrustedNetworkProfile> = emptyList(),
    val scanResults: List<ScanNet> = emptyList(),
    val pendingAdd: TrustedAddCandidate? = null,
    val isScanning: Boolean = false,
    val errorMessageResId: Int? = null
)

data class TrustedAddCandidate(
    val source: AddSource,
    val ssid: String?,
    val bssid: String?,
    val securityType: SecurityType,
    val frequencyMhz: Int?,
    val rssiDbm: Int?
)

enum class AddSource {
    CURRENT,
    SCAN
}

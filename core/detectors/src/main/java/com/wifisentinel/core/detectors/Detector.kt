package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.TrustedNetworkProfile

interface Detector {
    val id: String
    suspend fun analyze(ctx: AnalyzeContext): List<Finding>
}

data class AnalyzeContext(
    val current: NetworkSnapshot,
    val scanResults: List<ScanNet>,
    val trustedProfile: TrustedNetworkProfile?,
    val trustedProfiles: List<TrustedNetworkProfile>,
    val history: List<NetworkSnapshot>,
    val category: NetworkCategory?
)

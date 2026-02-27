package com.wifisentinel.core.wifi

data class TrustedNetworkProfile(
    val id: String,
    val displayName: String,
    val ssid: String?,
    val category: NetworkCategory,
    val meshMode: Boolean,
    val allowedBssids: Set<String>,
    val expectedSecurity: Set<SecurityType>,
    val expectedFreqBands: Set<Band>,
    val pinnedDns: List<String>,
    val createdAtMs: Long,
    val lastSeenMs: Long,
    val maxNewBssidPerDay: Int = 3,
    val bssidLearning: Boolean = false
)

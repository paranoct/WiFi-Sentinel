package com.wifisentinel.core.wifi

data class ScanNet(
    val ssid: String?,
    val bssid: String?,
    val frequencyMhz: Int?,
    val rssiDbm: Int?,
    val securityType: SecurityType,
    val channelWidth: Int? = null,
    val wifiStandard: Int? = null
)

package com.wifisentinel.core.wifi

data class NetworkSnapshot(
    val id: String,
    val timestampMs: Long,
    val ssid: String?,
    val bssid: String?,
    val securityType: SecurityType,
    val frequencyMhz: Int?,
    val rssiDbm: Int?,
    val ipV4: String?,
    val gatewayV4: String?,
    val dnsServers: List<String>,
    val captivePortal: Boolean,
    val networkIdHint: String
)

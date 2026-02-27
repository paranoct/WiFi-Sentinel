package com.wifisentinel.app.replay

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.TrustedNetworkProfile

data class ReplayPayload(
    val snapshot: NetworkSnapshot,
    val scanResults: List<ScanNet>,
    val dnsCheck: DnsCheckPayload? = null,
    val portalCheck: CaptivePortalCheck? = null,
    val trustedProfiles: List<TrustedNetworkProfile> = emptyList()
)

data class DnsCheckPayload(
    val domains: Map<String, DnsDomainPayload>
)

data class DnsDomainPayload(
    val systemAnswers: List<String>,
    val dohAnswers: List<String>
)

package com.wifisentinel.app.replay

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.net.CaptivePortalProbe
import com.wifisentinel.core.net.DnsProbe

class ReplayDnsProbe(
    private val dnsCheck: DnsCheckPayload?
) : DnsProbe {
    override suspend fun resolveSystem(domain: String): List<String> {
        return dnsCheck?.domains?.get(domain)?.systemAnswers.orEmpty()
    }

    override suspend fun resolveDoh(domain: String): List<String> {
        return dnsCheck?.domains?.get(domain)?.dohAnswers.orEmpty()
    }
}

class ReplayCaptivePortalProbe(
    private val portalCheck: CaptivePortalCheck?
) : CaptivePortalProbe {
    override suspend fun check(): CaptivePortalCheck? {
        return portalCheck
    }
}

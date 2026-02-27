package com.wifisentinel.core.net

data class CaptivePortalCheck(
    val redirectDomains: List<String>,
    val usedHttp: Boolean,
    val hasPunycode: Boolean
)

interface CaptivePortalProbe {
    suspend fun check(): CaptivePortalCheck?
}

package com.wifisentinel.core.wifi

enum class SecurityType {
    OPEN,
    WEP,
    WPA2,
    WPA3,
    WPA2_WPA3,
    UNKNOWN
}

object SecurityTypeParser {
    fun fromCapabilities(capabilities: String?): SecurityType {
        if (capabilities.isNullOrBlank()) return SecurityType.UNKNOWN
        val caps = capabilities.uppercase()
        if (caps.contains("WEP")) return SecurityType.WEP

        val hasWpa3 = caps.contains("WPA3") || caps.contains("SAE") || caps.contains("OWE")
        val hasWpa2 = caps.contains("WPA2") || caps.contains("PSK") || caps.contains("EAP")

        return when {
            hasWpa3 && hasWpa2 -> SecurityType.WPA2_WPA3
            hasWpa3 -> SecurityType.WPA3
            hasWpa2 -> SecurityType.WPA2
            caps.contains("ESS") -> SecurityType.OPEN
            else -> SecurityType.UNKNOWN
        }
    }

    fun fromWifiInfoSecurityType(securityType: Int): SecurityType {
        return when (securityType) {
            0 -> SecurityType.OPEN
            1 -> SecurityType.WEP
            2 -> SecurityType.WPA2
            3 -> SecurityType.WPA3
            4 -> SecurityType.WPA2
            5 -> SecurityType.WPA3
            else -> SecurityType.UNKNOWN
        }
    }
}

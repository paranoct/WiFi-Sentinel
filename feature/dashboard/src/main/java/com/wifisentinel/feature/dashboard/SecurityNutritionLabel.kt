package com.wifisentinel.feature.dashboard

enum class SecurityLabelSecurity {
    WPA3,
    WPA2,
    WPA2_WPA3,
    OPEN,
    WEP,
    UNKNOWN
}

enum class SecurityLabelPortal {
    PRESENT,
    ABSENT,
    UNKNOWN
}

enum class SecurityLabelDns {
    NORMAL,
    SUSPICIOUS,
    UNAVAILABLE
}

enum class SecurityLabelChange {
    BSSID,
    DNS,
    SECURITY
}

enum class SecurityLabelMesh {
    ENABLED,
    DISABLED
}

data class SecurityNutritionLabelState(
    val security: SecurityLabelSecurity = SecurityLabelSecurity.UNKNOWN,
    val portal: SecurityLabelPortal = SecurityLabelPortal.UNKNOWN,
    val changes: Set<SecurityLabelChange> = emptySet(),
    val changesKnown: Boolean = false,
    val dns: SecurityLabelDns = SecurityLabelDns.UNAVAILABLE,
    val mesh: SecurityLabelMesh? = null
)

package com.wifisentinel.app.ui

object AppRoutes {
    const val Dashboard = "dashboard"
    const val NetworkDetails = "network_details"
    const val Trusted = "trusted"
    const val TrustedOpenAddCurrentArg = "open_add_current"
    const val TrustedRoutePattern = "$Trusted?$TrustedOpenAddCurrentArg={$TrustedOpenAddCurrentArg}"
    fun trustedRoute(openAddCurrent: Boolean = false): String {
        return if (openAddCurrent) {
            "$Trusted?$TrustedOpenAddCurrentArg=true"
        } else {
            Trusted
        }
    }
    const val Timeline = "timeline"
    const val Settings = "settings"
    const val Replay = "replay"
}

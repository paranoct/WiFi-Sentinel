package com.wifisentinel.core.detectors

object FindingActionResolver {
    fun resolve(detectorId: String, severity: Severity): List<FindingActionType> {
        val actions = mutableListOf<FindingActionType>()
        if (severity == Severity.HIGH || severity == Severity.CRITICAL) {
            actions.add(FindingActionType.OPEN_WIFI_SETTINGS)
            actions.add(FindingActionType.COPY_EVIDENCE)
            actions.add(FindingActionType.OPEN_NETWORK_DETAILS)
        }
        when (detectorId) {
            "evil_twin" -> actions.add(FindingActionType.HOW_TO_DISABLE_AUTOJOIN)
            "weak_security", "dns_integrity" -> actions.add(FindingActionType.SHARE_REPORT)
            "unusual_behavior" -> actions.add(FindingActionType.OPEN_NETWORK_DETAILS)
            "mesh_new_bssid" -> actions.add(FindingActionType.OPEN_NETWORK_DETAILS)
        }
        return actions.distinct()
    }
}

package com.wifisentinel.core.detectors

import android.content.Context

object FindingTextResolver {
    fun title(context: Context, finding: Finding): String {
        return resolve(context, finding.title)
    }

    fun explanation(context: Context, finding: Finding): String {
        return resolve(context, finding.explanation)
    }

    fun resolve(context: Context, key: String): String {
        val resId = when (key) {
            FindingTextKeys.WEAK_SECURITY_TITLE -> R.string.finding_weak_security_title
            FindingTextKeys.WEAK_SECURITY_BODY -> R.string.finding_weak_security_body
            FindingTextKeys.UNKNOWN_SECURITY_TITLE -> R.string.finding_unknown_security_title
            FindingTextKeys.UNKNOWN_SECURITY_BODY -> R.string.finding_unknown_security_body
            FindingTextKeys.DNS_SUSPICIOUS_TITLE -> R.string.finding_dns_suspicious_title
            FindingTextKeys.DNS_SUSPICIOUS_BODY -> R.string.finding_dns_suspicious_body
            FindingTextKeys.CAPTIVE_PORTAL_TITLE -> R.string.finding_captive_portal_title
            FindingTextKeys.CAPTIVE_PORTAL_BODY -> R.string.finding_captive_portal_body
            FindingTextKeys.CAPTIVE_PORTAL_SUSPICIOUS_TITLE -> R.string.finding_captive_portal_suspicious_title
            FindingTextKeys.CAPTIVE_PORTAL_SUSPICIOUS_BODY -> R.string.finding_captive_portal_suspicious_body
            FindingTextKeys.DISCONNECT_ANOMALY_TITLE -> R.string.finding_disconnect_anomaly_title
            FindingTextKeys.DISCONNECT_ANOMALY_BODY -> R.string.finding_disconnect_anomaly_body
            FindingTextKeys.GATEWAY_ANOMALY_TITLE -> R.string.finding_gateway_anomaly_title
            FindingTextKeys.GATEWAY_ANOMALY_BODY -> R.string.finding_gateway_anomaly_body
            FindingTextKeys.PINNED_DNS_TITLE -> R.string.finding_pinned_dns_title
            FindingTextKeys.PINNED_DNS_BODY -> R.string.finding_pinned_dns_body
            FindingTextKeys.EVIL_TWIN_BSSID_TITLE -> R.string.finding_evil_twin_bssid_title
            FindingTextKeys.EVIL_TWIN_BSSID_BODY -> R.string.finding_evil_twin_bssid_body
            FindingTextKeys.EVIL_TWIN_SECURITY_TITLE -> R.string.finding_evil_twin_security_title
            FindingTextKeys.EVIL_TWIN_SECURITY_BODY -> R.string.finding_evil_twin_security_body
            FindingTextKeys.EVIL_TWIN_BAND_TITLE -> R.string.finding_evil_twin_band_title
            FindingTextKeys.EVIL_TWIN_BAND_BODY -> R.string.finding_evil_twin_band_body
            FindingTextKeys.TRUSTED_LIKE_TITLE -> R.string.finding_trusted_like_title
            FindingTextKeys.TRUSTED_LIKE_BODY -> R.string.finding_trusted_like_body
            FindingTextKeys.SIMILAR_NETWORKS_TITLE -> R.string.finding_similar_networks_title
            FindingTextKeys.SIMILAR_NETWORKS_BODY -> R.string.finding_similar_networks_body
            FindingTextKeys.MESH_TOO_MANY_BSSID_TITLE -> R.string.finding_mesh_too_many_bssid_title
            FindingTextKeys.MESH_TOO_MANY_BSSID_BODY -> R.string.finding_mesh_too_many_bssid_body
            FindingTextKeys.UNUSUAL_BEHAVIOR_TITLE -> R.string.finding_unusual_behavior_title
            FindingTextKeys.UNUSUAL_BEHAVIOR_BODY -> R.string.finding_unusual_behavior_body
            FindingTextKeys.LOOKALIKE_SSID_TITLE -> R.string.finding_lookalike_ssid_title
            FindingTextKeys.LOOKALIKE_SSID_BODY -> R.string.finding_lookalike_ssid_body
            else -> 0
        }
        return if (resId == 0) key else context.getString(resId)
    }
}

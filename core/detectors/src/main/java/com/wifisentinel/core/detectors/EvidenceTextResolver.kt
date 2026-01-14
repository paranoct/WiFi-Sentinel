package com.wifisentinel.core.detectors

import android.content.Context

object EvidenceTextResolver {
    fun label(context: Context, key: String): String {
        val resId = when (key) {
            EvidenceKeys.SECURITY_TYPE -> R.string.evidence_security_type
            EvidenceKeys.SYSTEM_DNS -> R.string.evidence_system_dns
            EvidenceKeys.EMPTY_SYSTEM_DOMAINS -> R.string.evidence_empty_system_domains
            EvidenceKeys.PRIVATE_ONLY_DOMAINS -> R.string.evidence_private_only_domains
            EvidenceKeys.MISMATCH_DOMAINS -> R.string.evidence_mismatch_domains
            EvidenceKeys.CAPTIVE_PORTAL -> R.string.evidence_captive_portal
            EvidenceKeys.PORTAL_REDIRECTS -> R.string.evidence_portal_redirects
            EvidenceKeys.PORTAL_HTTP -> R.string.evidence_portal_http
            EvidenceKeys.PORTAL_PUNYCODE -> R.string.evidence_portal_punycode
            EvidenceKeys.CURRENT_BSSID -> R.string.evidence_current_bssid
            EvidenceKeys.OBSERVED_BSSIDS -> R.string.evidence_observed_bssids
            EvidenceKeys.ALLOWED_BSSIDS -> R.string.evidence_allowed_bssids
            EvidenceKeys.EXPECTED_SECURITY -> R.string.evidence_expected_security
            EvidenceKeys.CURRENT_SECURITY -> R.string.evidence_current_security
            EvidenceKeys.EXPECTED_BAND -> R.string.evidence_expected_band
            EvidenceKeys.CURRENT_BAND -> R.string.evidence_current_band
            EvidenceKeys.SIMILAR_TRUSTED -> R.string.evidence_similar_trusted
            EvidenceKeys.SIMILAR_SSIDS -> R.string.evidence_similar_ssids
            EvidenceKeys.SEEN_SSID -> R.string.evidence_seen_ssid
            EvidenceKeys.BASE_SSID -> R.string.evidence_base_ssid
            EvidenceKeys.TRUSTED_SSID -> R.string.evidence_trusted_ssid
            EvidenceKeys.EXPECTED_DNS -> R.string.evidence_expected_dns
            EvidenceKeys.CURRENT_DNS -> R.string.evidence_current_dns
            EvidenceKeys.RECONNECTS -> R.string.evidence_reconnects
            EvidenceKeys.GATEWAY_IPS -> R.string.evidence_gateway_ips
            EvidenceKeys.NEW_BSSID_COUNT -> R.string.evidence_new_bssid_count
            EvidenceKeys.MAX_NEW_BSSID -> R.string.evidence_max_new_bssid
            EvidenceKeys.BASELINE_BSSID_COUNT -> R.string.evidence_baseline_bssid_count
            EvidenceKeys.CURRENT_BSSID_COUNT -> R.string.evidence_current_bssid_count
            EvidenceKeys.BASELINE_DNS_CHANGES -> R.string.evidence_baseline_dns_changes
            EvidenceKeys.BASELINE_MAIN_BAND -> R.string.evidence_baseline_main_band
            EvidenceKeys.CURRENT_BAND_SIMPLE -> R.string.evidence_current_band_simple
            EvidenceKeys.LOOKALIKE_TARGET -> R.string.evidence_lookalike_target
            EvidenceKeys.LOOKALIKE_FOUND -> R.string.evidence_lookalike_found
            EvidenceKeys.LOOKALIKE_DIFF -> R.string.evidence_lookalike_diff
            else -> 0
        }
        return if (resId == 0) key else context.getString(resId)
    }

    fun value(context: Context, key: String, rawValue: String): String {
        return when (key) {
            EvidenceKeys.CAPTIVE_PORTAL,
            EvidenceKeys.PORTAL_HTTP,
            EvidenceKeys.PORTAL_PUNYCODE -> booleanValue(context, rawValue)
            EvidenceKeys.LOOKALIKE_DIFF -> resolveLookalikeDiff(context, rawValue)
            else -> rawValue
        }
    }

    private fun booleanValue(context: Context, rawValue: String): String {
        return when (rawValue.lowercase()) {
            "true" -> context.getString(R.string.evidence_value_yes)
            "false" -> context.getString(R.string.evidence_value_no)
            else -> rawValue
        }
    }

    private fun resolveLookalikeDiff(context: Context, rawValue: String): String {
        val tokens = rawValue.split(",").map { it.trim() }.filter { it.isNotBlank() }
        if (tokens.isEmpty()) return context.getString(R.string.evidence_diff_unknown)
        val labels = tokens.map { token ->
            when (token) {
                "confusable" -> context.getString(R.string.evidence_diff_confusable)
                "spaces" -> context.getString(R.string.evidence_diff_spaces)
                "punycode" -> context.getString(R.string.evidence_diff_punycode)
                "unknown" -> context.getString(R.string.evidence_diff_unknown)
                else -> token
            }
        }
        return labels.joinToString(", ")
    }
}

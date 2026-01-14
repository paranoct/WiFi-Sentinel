package com.wifisentinel.core.risk

import android.content.Context

object RiskTextResolver {
    fun resolve(context: Context, key: String, args: List<String> = emptyList()): String {
        val resId = when (key) {
            RiskTextKeys.SUMMARY_EMPTY -> R.string.risk_summary_empty
            RiskTextKeys.SUMMARY_FOUND -> R.string.risk_summary_found
            RiskTextKeys.ACTION_NO_PASSWORDS -> R.string.risk_action_no_passwords
            RiskTextKeys.ACTION_CHECK_SSID -> R.string.risk_action_check_ssid
            RiskTextKeys.ACTION_USE_SECURE -> R.string.risk_action_use_secure
            RiskTextKeys.ACTION_DISCONNECT_HIGH -> R.string.risk_action_disconnect_high
            else -> 0
        }
        if (resId == 0) return key
        return if (args.isEmpty()) context.getString(resId) else context.getString(resId, *args.toTypedArray())
    }
}

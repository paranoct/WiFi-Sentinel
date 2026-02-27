package com.wifisentinel.core.wifi

import java.security.MessageDigest

object NetworkId {
    fun buildNetworkIdHint(ssid: String?, bssid: String?): String {
        val source = when {
            !ssid.isNullOrBlank() -> ssid
            !bssid.isNullOrBlank() -> bssid
            else -> "unknown"
        }
        return sha256(source)
    }

    private fun sha256(value: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(value.toByteArray(Charsets.UTF_8))
        return bytes.joinToString(separator = "") { byte -> "%02x".format(byte) }
    }
}

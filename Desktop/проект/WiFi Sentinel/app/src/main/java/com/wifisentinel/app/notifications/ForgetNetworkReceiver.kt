package com.wifisentinel.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.wifisentinel.app.R

class ForgetNetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_FORGET) return
        val ssid = intent.getStringExtra(EXTRA_SSID)
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val forgot = tryForgetNetwork(wifiManager, ssid)
        if (forgot) {
            Toast.makeText(
                context,
                context.getString(R.string.toast_network_forgot),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val settingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(settingsIntent)
            Toast.makeText(
                context,
                context.getString(R.string.toast_forget_open_settings),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @Suppress("DEPRECATION")
    private fun tryForgetNetwork(wifiManager: WifiManager, ssid: String?): Boolean {
        if (ssid.isNullOrBlank()) return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return false
        }
        return try {
            val target = wifiManager.configuredNetworks
                ?.firstOrNull { normalizeSsid(it.SSID) == ssid }
                ?: return false
            wifiManager.disableNetwork(target.networkId)
            wifiManager.removeNetwork(target.networkId)
            wifiManager.saveConfiguration()
        } catch (_: SecurityException) {
            false
        }
    }

    private fun normalizeSsid(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        return raw.trim('"')
    }

    companion object {
        const val ACTION_FORGET = "com.wifisentinel.action.FORGET_NETWORK"
        const val EXTRA_SSID = "extra_ssid"
    }
}

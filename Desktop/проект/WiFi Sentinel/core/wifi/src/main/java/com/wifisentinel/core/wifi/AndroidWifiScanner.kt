package com.wifisentinel.core.wifi

import android.annotation.SuppressLint
import android.net.wifi.WifiManager
import android.os.Build

class AndroidWifiScanner(
    private val wifiManager: WifiManager
) : WifiScanner {

    @SuppressLint("MissingPermission")
    override suspend fun scan(): List<ScanNet> {
        try {
            wifiManager.startScan()
        } catch (_: SecurityException) {
            return emptyList()
        }
        val results = wifiManager.scanResults
        return results.map { result ->
            ScanNet(
                ssid = result.SSID.takeIf { it.isNotBlank() },
                bssid = result.BSSID.takeIf { it.isNotBlank() },
                frequencyMhz = result.frequency,
                rssiDbm = result.level,
                securityType = SecurityTypeParser.fromCapabilities(result.capabilities),
                channelWidth = result.channelWidth,
                wifiStandard = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    result.wifiStandard
                } else {
                    null
                }
            )
        }
    }
}

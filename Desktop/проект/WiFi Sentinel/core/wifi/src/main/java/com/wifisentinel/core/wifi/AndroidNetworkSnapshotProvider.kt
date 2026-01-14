package com.wifisentinel.core.wifi

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import java.net.Inet4Address
import java.util.UUID

class AndroidNetworkSnapshotProvider(
    private val connectivityManager: ConnectivityManager,
    private val wifiManager: WifiManager
) : NetworkSnapshotProvider {

    @SuppressLint("MissingPermission")
    override suspend fun currentSnapshot(): NetworkSnapshot? {
        val network = try {
            connectivityManager.activeNetwork
        } catch (_: SecurityException) {
            null
        } ?: return null
        val capabilities = try {
            connectivityManager.getNetworkCapabilities(network)
        } catch (_: SecurityException) {
            null
        } ?: return null
        if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return null

        val transportInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            capabilities.transportInfo as? WifiInfo
        } else {
            null
        }
        val connectionInfo = try {
            wifiManager.connectionInfo
        } catch (_: SecurityException) {
            null
        }
        val ssid = normalizeSsid(transportInfo?.ssid) ?: normalizeSsid(connectionInfo?.ssid)
        val bssid = listOf(transportInfo?.bssid, connectionInfo?.bssid)
            .firstOrNull { !it.isNullOrBlank() && it != "02:00:00:00:00:00" }
        val wifiInfo = transportInfo ?: connectionInfo
        val linkProperties = try {
            connectivityManager.getLinkProperties(network)
        } catch (_: SecurityException) {
            null
        }

        return NetworkSnapshot(
            id = UUID.randomUUID().toString(),
            timestampMs = System.currentTimeMillis(),
            ssid = ssid,
            bssid = bssid,
            securityType = resolveSecurityType(wifiInfo),
            frequencyMhz = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) wifiInfo?.frequency else null,
            rssiDbm = wifiInfo?.rssi,
            ipV4 = wifiInfo?.let { ipv4FromInt(it.ipAddress) } ?: ipv4FromLinkProperties(linkProperties),
            gatewayV4 = gatewayFrom(linkProperties),
            dnsServers = linkProperties?.dnsServers?.map { it.hostAddress ?: "" }?.filter { it.isNotBlank() }
                ?: emptyList(),
            captivePortal = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL),
            networkIdHint = NetworkId.buildNetworkIdHint(ssid, bssid)
        )
    }

    private fun normalizeSsid(rawSsid: String?): String? {
        if (rawSsid.isNullOrBlank()) return null
        if (rawSsid == WifiManager.UNKNOWN_SSID) return null
        return rawSsid.trim('"')
    }

    private fun resolveSecurityType(wifiInfo: WifiInfo?): SecurityType {
        val info = wifiInfo ?: return SecurityType.UNKNOWN
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            SecurityTypeParser.fromWifiInfoSecurityType(info.currentSecurityType)
        } else {
            SecurityType.UNKNOWN
        }
    }

    private fun ipv4FromInt(ip: Int): String? {
        if (ip == 0) return null
        return listOf(
            ip and 0xff,
            ip shr 8 and 0xff,
            ip shr 16 and 0xff,
            ip shr 24 and 0xff
        ).joinToString(".")
    }

    private fun gatewayFrom(linkProperties: LinkProperties?): String? {
        val route = linkProperties?.routes?.firstOrNull { it.isDefaultRoute }
        val gateway = route?.gateway ?: return null
        return if (gateway is Inet4Address) gateway.hostAddress else null
    }

    private fun ipv4FromLinkProperties(linkProperties: LinkProperties?): String? {
        val address = linkProperties?.linkAddresses
            ?.mapNotNull { it.address as? Inet4Address }
            ?.firstOrNull()
        return address?.hostAddress
    }
}

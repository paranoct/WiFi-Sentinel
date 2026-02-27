package com.wifisentinel.app.replay

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.wifi.Band
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ReplayPayloadCodecTest {
    @Test
    fun buildPayloadJson_masksSensitiveValues() {
        val payload = samplePayload()

        val json = ReplayPayloadCodec.buildPayloadJson(payload, maskSensitive = true)

        val snapshotJson = json.getJSONObject("snapshot")
        assertEquals("MyW***", snapshotJson.getString("ssid"))
        assertEquals("AA:***", snapshotJson.getString("bssid"))

        val scanJson = json.getJSONArray("scanResults").getJSONObject(0)
        assertEquals("MyW***", scanJson.getString("ssid"))
        assertEquals("AA:***", scanJson.getString("bssid"))

        val trustedJson = json.getJSONArray("trustedProfiles").getJSONObject(0)
        assertEquals("MyW***", trustedJson.getString("ssid"))
        val allowedBssids = trustedJson.getJSONArray("allowedBssids")
        assertEquals("AA:***", allowedBssids.getString(0))
    }

    @Test
    fun parsePayload_roundTripsContent() {
        val payload = samplePayload()

        val json = ReplayPayloadCodec.buildPayloadJson(payload, maskSensitive = false)
        val parsed = ReplayPayloadCodec.parsePayload(json)

        assertEquals(payload.snapshot.ssid, parsed.snapshot.ssid)
        assertEquals(payload.snapshot.bssid, parsed.snapshot.bssid)
        assertEquals(payload.scanResults.size, parsed.scanResults.size)
        assertEquals(payload.scanResults.first().ssid, parsed.scanResults.first().ssid)
        assertEquals(payload.scanResults.first().bssid, parsed.scanResults.first().bssid)

        val parsedDns = parsed.dnsCheck
        assertNotNull(parsedDns)
        assertEquals(payload.dnsCheck?.domains?.size, parsedDns.domains.size)

        val parsedPortal = parsed.portalCheck
        assertNotNull(parsedPortal)
        assertEquals(payload.portalCheck?.usedHttp, parsedPortal.usedHttp)

        val parsedProfile = parsed.trustedProfiles.first()
        val expectedProfile = payload.trustedProfiles.first()
        assertEquals(expectedProfile.ssid, parsedProfile.ssid)
        assertEquals(expectedProfile.category, parsedProfile.category)
        assertEquals(expectedProfile.meshMode, parsedProfile.meshMode)
    }

    private fun samplePayload(): ReplayPayload {
        val snapshot = NetworkSnapshot(
            id = "snapshot-1",
            timestampMs = 1000L,
            ssid = "MyWifi",
            bssid = "AA:BB:CC:DD:EE:FF",
            securityType = SecurityType.WPA2,
            frequencyMhz = 2412,
            rssiDbm = -42,
            ipV4 = "192.168.1.2",
            gatewayV4 = "192.168.1.1",
            dnsServers = listOf("1.1.1.1"),
            captivePortal = false,
            networkIdHint = "hash"
        )
        val scan = listOf(
            ScanNet(
                ssid = "MyWifi",
                bssid = "AA:BB:CC:DD:EE:FF",
                frequencyMhz = 2412,
                rssiDbm = -42,
                securityType = SecurityType.WPA2,
                channelWidth = 20,
                wifiStandard = 4
            )
        )
        val dnsCheck = DnsCheckPayload(
            domains = mapOf(
                "example.com" to DnsDomainPayload(
                    systemAnswers = listOf("93.184.216.34"),
                    dohAnswers = listOf("93.184.216.34")
                )
            )
        )
        val portalCheck = CaptivePortalCheck(
            redirectDomains = listOf("login.example"),
            usedHttp = false,
            hasPunycode = false
        )
        val profile = TrustedNetworkProfile(
            id = "profile-1",
            displayName = "MyWifi",
            ssid = "MyWifi",
            category = NetworkCategory.HOME,
            meshMode = true,
            allowedBssids = setOf("AA:BB:CC:DD:EE:FF"),
            expectedSecurity = setOf(SecurityType.WPA2),
            expectedFreqBands = setOf(Band.BAND_2G4),
            pinnedDns = listOf("1.1.1.1"),
            createdAtMs = 500L,
            lastSeenMs = 900L,
            maxNewBssidPerDay = 2,
            bssidLearning = true
        )
        return ReplayPayload(
            snapshot = snapshot,
            scanResults = scan,
            dnsCheck = dnsCheck,
            portalCheck = portalCheck,
            trustedProfiles = listOf(profile)
        )
    }
}

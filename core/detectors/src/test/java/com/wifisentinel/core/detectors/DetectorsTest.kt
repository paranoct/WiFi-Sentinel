package com.wifisentinel.core.detectors

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.net.CaptivePortalProbe
import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DetectorsTest {
    @Test
    fun meshNewBssidDetectorTriggersOnLimit() = runTest {
        val detector = MeshNewBssidDetector()
        val trusted = trustedProfile(
            meshMode = true,
            allowedBssids = setOf("aa:bb:cc:dd:ee:01"),
            maxNewBssidPerDay = 1
        )
        val history = listOf(
            snapshot(bssid = "aa:bb:cc:dd:ee:02", timestampMs = 1_000L),
            snapshot(bssid = "aa:bb:cc:dd:ee:03", timestampMs = 2_000L)
        )
        val current = snapshot(bssid = "aa:bb:cc:dd:ee:04", timestampMs = 3_000L)
        val ctx = analyzeContext(current, trusted, history)

        val findings = detector.analyze(ctx)

        assertTrue(findings.isNotEmpty())
        assertEquals(Severity.HIGH, findings.first().severity)
    }

    @Test
    fun unusualBehaviorDetectorDetectsBssidSpike() = runTest {
        val detector = UnusualBehaviorDetector()
        val history = (1..5).map { idx ->
            snapshot(
                bssid = "aa:bb:cc:dd:ee:01",
                dnsServers = listOf("1.1.1.1"),
                timestampMs = idx * 1_000L
            )
        }
        val current = snapshot(
            bssid = "aa:bb:cc:dd:ee:99",
            dnsServers = listOf("1.1.1.1"),
            timestampMs = 10_000L
        )
        val ctx = analyzeContext(current, trustedProfile(), history)

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.detectorId == "unusual_behavior" })
    }

    @Test
    fun dnsIntegrityDetectorFlagsMismatch() = runTest {
        val probe = object : DnsProbe {
            override suspend fun resolveSystem(domain: String): List<String> = emptyList()
            override suspend fun resolveDoh(domain: String): List<String> = listOf("93.184.216.34")
        }
        val detector = DnsIntegrityDetector(
            dnsProbe = probe,
            enabledProvider = { true },
            cacheTtlMs = 0L
        )
        val current = snapshot(dnsServers = listOf("192.168.0.1"))
        val ctx = analyzeContext(current, trustedProfile(), emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.isNotEmpty())
        assertEquals("dns_integrity", findings.first().detectorId)
    }

    @Test
    fun captivePortalDetectorAddsSuspiciousFinding() = runTest {
        val probe = CaptivePortalProbe {
            CaptivePortalCheck(
                redirectDomains = listOf("login.example.com", "very-long-suspicious-domain-example.com"),
                usedHttp = true,
                hasPunycode = false
            )
        }
        val detector = CaptivePortalDetector(probe)
        val current = snapshot(captivePortal = true)
        val ctx = analyzeContext(current, trustedProfile(), emptyList())

        val findings = detector.analyze(ctx)

        assertEquals(2, findings.size)
        assertTrue(findings.any { it.severity == Severity.HIGH })
    }

    @Test
    fun lookalikeDetectorFindsConfusableSsid() = runTest {
        val detector = LookalikeSsidDetector()
        val trusted = trustedProfile(
            ssid = "HomeWiFi",
            category = NetworkCategory.PUBLIC,
            expectedSecurity = setOf(SecurityType.WPA3)
        )
        val scan = ScanNet(
            ssid = "\u041domeWiFi",
            bssid = "aa:bb:cc:dd:ee:10",
            frequencyMhz = 2412,
            rssiDbm = -40,
            securityType = SecurityType.OPEN
        )
        val current = snapshot(ssid = "Other", bssid = "aa:bb:cc:dd:ee:20")
        val ctx = analyzeContext(current, trusted, emptyList(), scanResults = listOf(scan))

        val findings = detector.analyze(ctx)

        val finding = findings.firstOrNull()
        assertNotNull(finding)
        assertEquals(Severity.WARN, finding.severity)
        assertTrue(finding.evidence[EvidenceKeys.LOOKALIKE_DIFF]?.contains("confusable") == true)
    }

    private fun analyzeContext(
        current: NetworkSnapshot,
        trustedProfile: TrustedNetworkProfile,
        history: List<NetworkSnapshot>,
        scanResults: List<ScanNet> = emptyList()
    ): AnalyzeContext {
        return AnalyzeContext(
            current = current,
            scanResults = scanResults,
            trustedProfile = trustedProfile,
            trustedProfiles = listOf(trustedProfile),
            history = history,
            category = trustedProfile.category
        )
    }

    private fun snapshot(
        ssid: String? = "TestNet",
        bssid: String? = "aa:bb:cc:dd:ee:01",
        securityType: SecurityType = SecurityType.WPA2,
        frequencyMhz: Int? = 2412,
        rssiDbm: Int? = -50,
        dnsServers: List<String> = listOf("1.1.1.1"),
        captivePortal: Boolean = false,
        timestampMs: Long = 0L
    ): NetworkSnapshot {
        return NetworkSnapshot(
            id = UUID.randomUUID().toString(),
            timestampMs = timestampMs,
            ssid = ssid,
            bssid = bssid,
            securityType = securityType,
            frequencyMhz = frequencyMhz,
            rssiDbm = rssiDbm,
            ipV4 = "192.168.0.2",
            gatewayV4 = "192.168.0.1",
            dnsServers = dnsServers,
            captivePortal = captivePortal,
            networkIdHint = "test-net"
        )
    }

    private fun trustedProfile(
        ssid: String? = "TestNet",
        category: NetworkCategory = NetworkCategory.HOME,
        meshMode: Boolean = false,
        allowedBssids: Set<String> = setOf("aa:bb:cc:dd:ee:01"),
        expectedSecurity: Set<SecurityType> = setOf(SecurityType.WPA2),
        maxNewBssidPerDay: Int = 3
    ): TrustedNetworkProfile {
        return TrustedNetworkProfile(
            id = UUID.randomUUID().toString(),
            displayName = ssid ?: "Trusted",
            ssid = ssid,
            category = category,
            meshMode = meshMode,
            allowedBssids = allowedBssids,
            expectedSecurity = expectedSecurity,
            expectedFreqBands = emptySet(),
            pinnedDns = emptyList(),
            createdAtMs = 0L,
            lastSeenMs = 0L,
            maxNewBssidPerDay = maxNewBssidPerDay,
            bssidLearning = false
        )
    }
}

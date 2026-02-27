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
    fun dnsIntegrityDetectorDoesNotFlagBenignCdnDifferences() = runTest {
        val system = mapOf(
            "example.com" to listOf("93.184.216.34"),
            "example.org" to listOf("93.184.216.35"),
            "example.net" to listOf("93.184.216.36")
        )
        val doh = mapOf(
            "example.com" to listOf("93.184.216.40"),
            "example.org" to listOf("93.184.216.41"),
            "example.net" to listOf("93.184.216.42")
        )
        val probe = object : DnsProbe {
            override suspend fun resolveSystem(domain: String): List<String> = system[domain].orEmpty()
            override suspend fun resolveDoh(domain: String): List<String> = doh[domain].orEmpty()
        }
        val detector = DnsIntegrityDetector(
            dnsProbe = probe,
            enabledProvider = { true },
            cacheTtlMs = 0L
        )
        val ctx = analyzeContext(snapshot(dnsServers = listOf("192.168.0.1")), trustedProfile(), emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.isEmpty())
    }

    @Test
    fun dnsIntegrityDetectorFlagsInterceptPattern() = runTest {
        val probe = object : DnsProbe {
            override suspend fun resolveSystem(domain: String): List<String> = listOf("203.0.113.10")
            override suspend fun resolveDoh(domain: String): List<String> = when (domain) {
                "example.com" -> listOf("93.184.216.34")
                "example.org" -> listOf("93.184.216.35")
                else -> listOf("93.184.216.36")
            }
        }
        val detector = DnsIntegrityDetector(
            dnsProbe = probe,
            enabledProvider = { true },
            cacheTtlMs = 0L
        )
        val ctx = analyzeContext(snapshot(dnsServers = listOf("192.168.0.1")), trustedProfile(), emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.title == FindingTextKeys.DNS_INTERCEPT_TITLE })
    }

    @Test
    fun captivePortalDetectorAddsSuspiciousFinding() = runTest {
        val probe = object : CaptivePortalProbe {
            override suspend fun check(): CaptivePortalCheck {
                return CaptivePortalCheck(
                    redirectDomains = listOf("login.example.com", "very-long-suspicious-domain-example.com"),
                    usedHttp = true,
                    hasPunycode = false
                )
            }
        }
        val detector = CaptivePortalDetector(probe)
        val current = snapshot(captivePortal = true)
        val ctx = analyzeContext(current, trustedProfile(), emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.size >= 2)
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

    @Test
    fun lookalikeDetectorFindsInvisibleCharacters() = runTest {
        val detector = LookalikeSsidDetector()
        val trusted = trustedProfile(ssid = "HomeWiFi", category = NetworkCategory.HOME)
        val scan = ScanNet(
            ssid = "Home\u200BWiFi",
            bssid = "aa:bb:cc:dd:ee:11",
            frequencyMhz = 2412,
            rssiDbm = -35,
            securityType = SecurityType.WPA2
        )
        val ctx = analyzeContext(snapshot(ssid = "OtherNet"), trusted, emptyList(), scanResults = listOf(scan))

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.title == FindingTextKeys.LOOKALIKE_INVISIBLE_TITLE })
    }

    @Test
    fun macSpoofDetectorFlagsLocallyAdministeredBit() = runTest {
        val detector = MacSpoofDetector()
        val trusted = trustedProfile(
            ssid = "HomeNet",
            category = NetworkCategory.HOME,
            allowedBssids = setOf("aa:bb:cc:dd:ee:01")
        )
        val current = snapshot(
            ssid = "HomeNet",
            bssid = "02:11:22:33:44:55"
        )
        val ctx = analyzeContext(current, trusted, emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.detectorId == "mac_spoof" })
        assertTrue(findings.any { it.evidence.containsKey(EvidenceKeys.MAC_LOCAL_ADMIN_BIT) })
    }

    @Test
    fun macSpoofDetectorFlagsUnexpectedOuiForTrusted() = runTest {
        val detector = MacSpoofDetector()
        val trusted = trustedProfile(
            ssid = "OfficeNet",
            category = NetworkCategory.HOME,
            allowedBssids = setOf("aa:bb:cc:dd:ee:01")
        )
        val current = snapshot(
            ssid = "OfficeNet",
            bssid = "cc:dd:ee:10:20:30"
        )
        val ctx = analyzeContext(current, trusted, emptyList())

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.evidence.containsKey(EvidenceKeys.CURRENT_BSSID_OUI) })
    }

    @Test
    fun macSpoofDetectorFlagsOuiChurn() = runTest {
        val detector = MacSpoofDetector()
        val trusted = trustedProfile(
            ssid = "OfficeNet",
            category = NetworkCategory.HOME,
            allowedBssids = setOf("aa:bb:cc:00:00:01")
        )
        val history = listOf(
            snapshot(ssid = "OfficeNet", bssid = "aa:bb:cc:11:11:11", timestampMs = 1_000L),
            snapshot(ssid = "OfficeNet", bssid = "dd:ee:ff:22:22:22", timestampMs = 2_000L),
            snapshot(ssid = "OfficeNet", bssid = "11:22:33:33:33:33", timestampMs = 3_000L)
        )
        val ctx = analyzeContext(
            current = snapshot(ssid = "OfficeNet", bssid = "44:55:66:44:44:44", timestampMs = 4_000L),
            trustedProfile = trusted,
            history = history
        )

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.title == FindingTextKeys.MAC_SPOOF_OUI_CHURN_TITLE })
    }

    @Test
    fun evilTwinDetectorFlagsNearbyDowngradeClone() = runTest {
        val detector = EvilTwinDetector()
        val trusted = trustedProfile(
            ssid = "HomeWiFi",
            category = NetworkCategory.HOME,
            expectedSecurity = setOf(SecurityType.WPA3)
        )
        val scans = listOf(
            ScanNet(
                ssid = "HomeWiFi",
                bssid = "aa:bb:cc:dd:ee:91",
                frequencyMhz = 2412,
                rssiDbm = -40,
                securityType = SecurityType.OPEN
            )
        )
        val ctx = analyzeContext(
            current = snapshot(ssid = "HomeWiFi", bssid = "aa:bb:cc:dd:ee:01"),
            trustedProfile = trusted,
            history = emptyList(),
            scanResults = scans
        )

        val findings = detector.analyze(ctx)

        assertTrue(findings.any { it.title == FindingTextKeys.EVIL_TWIN_DOWNGRADE_NEARBY_TITLE })
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

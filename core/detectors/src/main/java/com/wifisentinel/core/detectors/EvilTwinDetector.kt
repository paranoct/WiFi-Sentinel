package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.BandMapper
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import java.util.UUID
import kotlin.math.roundToInt

class EvilTwinDetector : Detector {
    override val id: String = "evil_twin"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val findings = mutableListOf<Finding>()
        val trusted = ctx.trustedProfile
        val category = ctx.category ?: NetworkCategory.PUBLIC

        if (trusted != null) {
            findings += analyzeTrustedMismatch(ctx, trusted, category)
        }

        findings += analyzeTrustedLike(ctx, category)

        return findings.distinctBy { finding ->
            val evidenceHash = finding.evidence.toSortedMap().entries.joinToString("|") { "${it.key}:${it.value}" }
            "${finding.detectorId}|${finding.title}|$evidenceHash"
        }
    }

    private fun analyzeTrustedMismatch(
        ctx: AnalyzeContext,
        trusted: TrustedNetworkProfile,
        category: NetworkCategory
    ): List<Finding> {
        val current = ctx.current
        val findings = mutableListOf<Finding>()
        val allowedBssidsNormalized = trusted.allowedBssids.map { it.lowercase() }.toSet()
        val currentBssidNormalized = current.bssid?.lowercase()

        current.bssid?.let { bssid ->
            if (allowedBssidsNormalized.isNotEmpty() && currentBssidNormalized !in allowedBssidsNormalized) {
                val bssidSeverity = when (category) {
                    NetworkCategory.HOME -> if (trusted.meshMode) Severity.WARN else Severity.HIGH
                    NetworkCategory.WORK -> if (trusted.meshMode) Severity.WARN else Severity.HIGH
                    NetworkCategory.PUBLIC -> Severity.INFO
                }
                findings.add(
                    buildFinding(
                        snapshotId = current.id,
                        severity = bssidSeverity,
                        score = severityScore(bssidSeverity, high = 58, warn = 24, info = 8),
                        title = FindingTextKeys.EVIL_TWIN_BSSID_TITLE,
                        body = FindingTextKeys.EVIL_TWIN_BSSID_BODY,
                        evidence = mapOf(
                            EvidenceKeys.ALLOWED_BSSIDS to trusted.allowedBssids.joinToString(),
                            EvidenceKeys.CURRENT_BSSID to bssid
                        )
                    )
                )
            }
        }

        if (trusted.expectedSecurity.isNotEmpty() && !trusted.expectedSecurity.contains(current.securityType)) {
            findings.add(
                buildFinding(
                    snapshotId = current.id,
                    severity = Severity.CRITICAL,
                    score = 84,
                    title = FindingTextKeys.EVIL_TWIN_SECURITY_TITLE,
                    body = FindingTextKeys.EVIL_TWIN_SECURITY_BODY,
                    evidence = mapOf(
                        EvidenceKeys.EXPECTED_SECURITY to trusted.expectedSecurity.joinToString(),
                        EvidenceKeys.CURRENT_SECURITY to current.securityType.name
                    )
                )
            )
        }

        if (trusted.expectedFreqBands.isNotEmpty()) {
            val band = BandMapper.fromFrequencyMhz(current.frequencyMhz)
            if (band != null && !trusted.expectedFreqBands.contains(band)) {
                findings.add(
                    buildFinding(
                        snapshotId = current.id,
                        severity = Severity.WARN,
                        score = 24,
                        title = FindingTextKeys.EVIL_TWIN_BAND_TITLE,
                        body = FindingTextKeys.EVIL_TWIN_BAND_BODY,
                        evidence = mapOf(
                            EvidenceKeys.EXPECTED_BAND to trusted.expectedFreqBands.joinToString(),
                            EvidenceKeys.CURRENT_BAND to band.name
                        )
                    )
                )
            }
        }

        val trustedSsid = trusted.ssid?.trim()
        val sameSsidBssids = if (trustedSsid.isNullOrBlank()) {
            emptySet()
        } else {
            ctx.scanResults
                .asSequence()
                .filter { net ->
                    val ssid = net.ssid ?: return@filter false
                    normalizeSsidValue(ssid) == normalizeSsidValue(trustedSsid)
                }
                .mapNotNull { it.bssid?.lowercase() }
                .toSet()
        }
        val floodThreshold = if (trusted.meshMode) 6 else 4
        if (sameSsidBssids.size >= floodThreshold) {
            val severity = when (category) {
                NetworkCategory.HOME, NetworkCategory.WORK -> Severity.HIGH
                NetworkCategory.PUBLIC -> Severity.WARN
            }
            findings.add(
                buildFinding(
                    snapshotId = current.id,
                    severity = severity,
                    score = severityScore(severity, high = 46, warn = 28, info = 10),
                    title = FindingTextKeys.EVIL_TWIN_BSSID_FLOOD_TITLE,
                    body = FindingTextKeys.EVIL_TWIN_BSSID_FLOOD_BODY,
                    evidence = mapOf(
                        EvidenceKeys.TRUSTED_SSID to (trusted.ssid ?: trusted.displayName),
                        EvidenceKeys.SAME_SSID_BSSID_COUNT to sameSsidBssids.size.toString(),
                        EvidenceKeys.OBSERVED_BSSIDS to sameSsidBssids.joinToString()
                    )
                )
            )
        }

        val currentRssi = current.rssiDbm
        if (
            currentBssidNormalized != null &&
            currentBssidNormalized !in allowedBssidsNormalized &&
            currentRssi != null
        ) {
            val baselineRssi = ctx.history
                .filter { it.networkIdHint == current.networkIdHint }
                .filter { history -> history.bssid?.lowercase() in allowedBssidsNormalized }
                .mapNotNull { it.rssiDbm }
                .takeIf { it.isNotEmpty() }
                ?.average()
                ?.roundToInt()
            if (baselineRssi != null && currentRssi >= -55 && currentRssi - baselineRssi >= 15) {
                val severity = when (category) {
                    NetworkCategory.HOME, NetworkCategory.WORK -> Severity.HIGH
                    NetworkCategory.PUBLIC -> Severity.WARN
                }
                findings.add(
                    buildFinding(
                        snapshotId = current.id,
                        severity = severity,
                        score = severityScore(severity, high = 52, warn = 30, info = 10),
                        title = FindingTextKeys.EVIL_TWIN_SIGNAL_TRAP_TITLE,
                        body = FindingTextKeys.EVIL_TWIN_SIGNAL_TRAP_BODY,
                        evidence = mapOf(
                            EvidenceKeys.CURRENT_BSSID to current.bssid.orEmpty(),
                            EvidenceKeys.CURRENT_RSSI to "$currentRssi dBm",
                            EvidenceKeys.BASELINE_RSSI to "$baselineRssi dBm"
                        )
                    )
                )
            }
        }

        val expectedMaxSecurity = trusted.expectedSecurity.maxOfOrNull { securityRank(it) }
        if (expectedMaxSecurity != null && !trustedSsid.isNullOrBlank()) {
            val downgradeNet = ctx.scanResults.firstOrNull { net ->
                val ssid = net.ssid ?: return@firstOrNull false
                normalizeSsidValue(ssid) == normalizeSsidValue(trustedSsid) &&
                    securityRank(net.securityType) + 1 <= expectedMaxSecurity
            }
            if (downgradeNet != null) {
                val severity = when (category) {
                    NetworkCategory.HOME, NetworkCategory.WORK -> Severity.HIGH
                    NetworkCategory.PUBLIC -> Severity.WARN
                }
                findings.add(
                    buildFinding(
                        snapshotId = current.id,
                        severity = severity,
                        score = severityScore(severity, high = 50, warn = 28, info = 10),
                        title = FindingTextKeys.EVIL_TWIN_DOWNGRADE_NEARBY_TITLE,
                        body = FindingTextKeys.EVIL_TWIN_DOWNGRADE_NEARBY_BODY,
                        evidence = mapOf(
                            EvidenceKeys.TRUSTED_SSID to trustedSsid,
                            EvidenceKeys.EXPECTED_SECURITY to trusted.expectedSecurity.joinToString(),
                            EvidenceKeys.DOWNGRADED_SECURITY_FOUND to downgradeNet.securityType.name
                        )
                    )
                )
            }
        }

        return findings
    }

    private fun analyzeTrustedLike(
        ctx: AnalyzeContext,
        category: NetworkCategory
    ): List<Finding> {
        val current = ctx.current
        val currentSsid = current.ssid?.trim() ?: return emptyList()
        if (currentSsid.isBlank()) return emptyList()

        val trustedProfiles = ctx.trustedProfiles.filter { !it.ssid.isNullOrBlank() }
        if (trustedProfiles.isEmpty()) return emptyList()

        val currentBase = normalizeSsidBase(currentSsid)
        if (currentBase.isBlank()) return emptyList()
        val currentNormalized = normalizeSsidValue(currentSsid)
        val trustedSsidSet = trustedProfiles
            .mapNotNull { it.ssid }
            .map { normalizeSsidValue(it) }
            .toSet()

        val findings = mutableListOf<Finding>()
        val matchingTrustedSsids = trustedProfiles
            .mapNotNull { it.ssid }
            .filter { normalizeSsidBase(it) == currentBase }
            .distinct()

        if (matchingTrustedSsids.isNotEmpty() && ctx.trustedProfile == null && currentNormalized !in trustedSsidSet) {
            val baseCategory = resolveCategory(matchingTrustedSsids, trustedProfiles)
            val severity = when (baseCategory) {
                NetworkCategory.HOME -> Severity.HIGH
                NetworkCategory.WORK -> Severity.WARN
                NetworkCategory.PUBLIC -> Severity.INFO
            }
            findings.add(
                buildFinding(
                    snapshotId = current.id,
                    severity = severity,
                    score = severityScore(severity, high = 56, warn = 25, info = 8),
                    title = FindingTextKeys.TRUSTED_LIKE_TITLE,
                    body = FindingTextKeys.TRUSTED_LIKE_BODY,
                    evidence = mapOf(
                        EvidenceKeys.SIMILAR_TRUSTED to matchingTrustedSsids.joinToString(),
                        EvidenceKeys.SEEN_SSID to currentSsid,
                        EvidenceKeys.BASE_SSID to currentBase
                    )
                )
            )
        }

        val similarSsids = ctx.scanResults
            .mapNotNull { it.ssid }
            .filter { normalizeSsidBase(it) == currentBase }
            .filter { normalizeSsidValue(it) !in trustedSsidSet }
            .filter { normalizeSsidValue(it) != currentNormalized }
            .distinct()
        if (similarSsids.isNotEmpty()) {
            val severity = when (category) {
                NetworkCategory.HOME, NetworkCategory.WORK -> Severity.WARN
                NetworkCategory.PUBLIC -> Severity.INFO
            }
            findings.add(
                buildFinding(
                    snapshotId = current.id,
                    severity = severity,
                    score = severityScore(severity, high = 24, warn = 16, info = 7),
                    title = FindingTextKeys.SIMILAR_NETWORKS_TITLE,
                    body = FindingTextKeys.SIMILAR_NETWORKS_BODY,
                    evidence = mapOf(
                        EvidenceKeys.TRUSTED_SSID to (ctx.trustedProfile?.ssid ?: currentSsid),
                        EvidenceKeys.SIMILAR_SSIDS to similarSsids.joinToString(),
                        EvidenceKeys.BASE_SSID to currentBase
                    )
                )
            )
        }

        return findings
    }

    private fun buildFinding(
        snapshotId: String,
        severity: Severity,
        score: Int,
        title: String,
        body: String,
        evidence: Map<String, String>
    ): Finding {
        return Finding(
            id = UUID.randomUUID().toString(),
            snapshotId = snapshotId,
            detectorId = id,
            severity = severity,
            scoreDelta = score,
            title = title,
            explanation = body,
            evidence = evidence,
            actions = FindingActionResolver.resolve(id, severity)
        )
    }

    private fun severityScore(severity: Severity, high: Int, warn: Int, info: Int): Int {
        return when (severity) {
            Severity.CRITICAL -> high.coerceAtLeast(70)
            Severity.HIGH -> high
            Severity.WARN -> warn
            Severity.INFO -> info
        }
    }

    private fun securityRank(type: SecurityType): Int {
        return when (type) {
            SecurityType.OPEN -> 0
            SecurityType.WEP -> 1
            SecurityType.WPA2 -> 2
            SecurityType.WPA3 -> 3
            SecurityType.WPA2_WPA3 -> 3
            SecurityType.UNKNOWN -> 0
        }
    }

    private fun resolveCategory(
        matchingTrustedSsids: List<String>,
        profiles: List<TrustedNetworkProfile>
    ): NetworkCategory {
        val normalized = matchingTrustedSsids.map { normalizeSsidValue(it) }.toSet()
        val categories = profiles
            .filter { it.ssid?.let { ssid -> normalizeSsidValue(ssid) in normalized } ?: false }
            .map { it.category }
        return when {
            categories.contains(NetworkCategory.HOME) -> NetworkCategory.HOME
            categories.contains(NetworkCategory.WORK) -> NetworkCategory.WORK
            categories.contains(NetworkCategory.PUBLIC) -> NetworkCategory.PUBLIC
            else -> NetworkCategory.PUBLIC
        }
    }

    private fun normalizeSsidBase(ssid: String): String {
        var base = ssid.trim()
        var changed: Boolean
        do {
            val stripped = base.replace(BAND_SUFFIX_REGEX, "").trim().trimEnd('_', '-', ' ')
            changed = stripped != base
            base = stripped
        } while (changed)
        return base.lowercase()
    }

    private fun normalizeSsidValue(ssid: String): String {
        return ssid.trim().lowercase()
    }

    private companion object {
        val BAND_SUFFIX_REGEX = Regex("""[\s_-]*(2\.4|2|5|6)\s*(ghz|g)$""", RegexOption.IGNORE_CASE)
    }
}

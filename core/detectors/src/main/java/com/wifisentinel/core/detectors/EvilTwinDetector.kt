package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.BandMapper
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import java.util.UUID

class EvilTwinDetector : Detector {
    override val id: String = "evil_twin"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val current = ctx.current
        val findings = mutableListOf<Finding>()
        val trusted = ctx.trustedProfile
        val category = ctx.category ?: NetworkCategory.PUBLIC

        if (trusted != null) {
            current.bssid?.let { bssid ->
                if (trusted.allowedBssids.isNotEmpty() && !trusted.allowedBssids.contains(bssid)) {
                    val bssidSeverity = when (category) {
                        NetworkCategory.HOME -> if (trusted.meshMode) Severity.WARN else Severity.HIGH
                        NetworkCategory.WORK -> if (trusted.meshMode) Severity.WARN else Severity.HIGH
                        NetworkCategory.PUBLIC -> Severity.INFO
                    }
                    val bssidScore = when (bssidSeverity) {
                        Severity.HIGH -> 60
                        Severity.WARN -> 25
                        else -> 5
                    }
                    findings.add(
                        Finding(
                            id = UUID.randomUUID().toString(),
                            snapshotId = current.id,
                            detectorId = id,
                            severity = bssidSeverity,
                            scoreDelta = bssidScore,
                            title = FindingTextKeys.EVIL_TWIN_BSSID_TITLE,
                            explanation = FindingTextKeys.EVIL_TWIN_BSSID_BODY,
                            evidence = mapOf(
                                EvidenceKeys.ALLOWED_BSSIDS to trusted.allowedBssids.joinToString(),
                                EvidenceKeys.CURRENT_BSSID to bssid
                            ),
                            actions = FindingActionResolver.resolve(id, bssidSeverity)
                        )
                    )
                }
            }

            if (trusted.expectedSecurity.isNotEmpty() && !trusted.expectedSecurity.contains(current.securityType)) {
                findings.add(
                    Finding(
                        id = UUID.randomUUID().toString(),
                        snapshotId = current.id,
                        detectorId = id,
                        severity = Severity.CRITICAL,
                        scoreDelta = 80,
                        title = FindingTextKeys.EVIL_TWIN_SECURITY_TITLE,
                        explanation = FindingTextKeys.EVIL_TWIN_SECURITY_BODY,
                        evidence = mapOf(
                            EvidenceKeys.EXPECTED_SECURITY to trusted.expectedSecurity.joinToString(),
                            EvidenceKeys.CURRENT_SECURITY to current.securityType.name
                        ),
                        actions = FindingActionResolver.resolve(id, Severity.CRITICAL)
                    )
                )
            }

            if (trusted.expectedFreqBands.isNotEmpty()) {
                val band = BandMapper.fromFrequencyMhz(current.frequencyMhz)
                if (band != null && !trusted.expectedFreqBands.contains(band)) {
                    findings.add(
                        Finding(
                            id = UUID.randomUUID().toString(),
                            snapshotId = current.id,
                            detectorId = id,
                            severity = Severity.WARN,
                            scoreDelta = 20,
                            title = FindingTextKeys.EVIL_TWIN_BAND_TITLE,
                            explanation = FindingTextKeys.EVIL_TWIN_BAND_BODY,
                            evidence = mapOf(
                                EvidenceKeys.EXPECTED_BAND to trusted.expectedFreqBands.joinToString(),
                                EvidenceKeys.CURRENT_BAND to band.name
                            ),
                            actions = FindingActionResolver.resolve(id, Severity.WARN)
                        )
                    )
                }
            }
        }

        val currentSsid = current.ssid?.trim()
        if (!currentSsid.isNullOrBlank()) {
            val trustedProfiles = ctx.trustedProfiles.filter { !it.ssid.isNullOrBlank() }
            val currentBase = normalizeSsidBase(currentSsid)
            val trustedSsidSet = trustedProfiles
                .mapNotNull { it.ssid }
                .map { normalizeSsidValue(it) }
                .toSet()
            val currentNormalized = normalizeSsidValue(currentSsid)
            if (trustedProfiles.isNotEmpty() && currentBase.isNotBlank()) {
                val matchingTrustedSsids = trustedProfiles
                    .mapNotNull { it.ssid }
                    .filter { normalizeSsidBase(it) == currentBase }
                    .distinct()

                if (matchingTrustedSsids.isNotEmpty()) {
                    if (trusted == null) {
                        val baseCategory = resolveCategory(matchingTrustedSsids, trustedProfiles)
                        if (currentNormalized !in trustedSsidSet) {
                            val severity = when (baseCategory) {
                                NetworkCategory.HOME -> Severity.HIGH
                                NetworkCategory.WORK -> Severity.WARN
                                NetworkCategory.PUBLIC -> Severity.INFO
                            }
                            val score = when (severity) {
                                Severity.HIGH -> 60
                                Severity.WARN -> 25
                                else -> 5
                            }
                            findings.add(
                                Finding(
                                    id = UUID.randomUUID().toString(),
                                    snapshotId = current.id,
                                    detectorId = id,
                            severity = severity,
                            scoreDelta = score,
                            title = FindingTextKeys.TRUSTED_LIKE_TITLE,
                            explanation = FindingTextKeys.TRUSTED_LIKE_BODY,
                            evidence = mapOf(
                                EvidenceKeys.SIMILAR_TRUSTED to matchingTrustedSsids.joinToString(),
                                EvidenceKeys.SEEN_SSID to currentSsid,
                                EvidenceKeys.BASE_SSID to currentBase
                            ),
                            actions = FindingActionResolver.resolve(id, severity)
                        )
                    )
                }
            } else {
                val similarSsids = ctx.scanResults
                    .mapNotNull { it.ssid }
                    .filter { normalizeSsidBase(it) == currentBase }
                    .filter { normalizeSsidValue(it) !in trustedSsidSet }
                    .filter { normalizeSsidValue(it) != currentNormalized }
                    .distinct()

                        if (similarSsids.isNotEmpty()) {
                            val severity = when (category) {
                                NetworkCategory.HOME -> Severity.WARN
                                NetworkCategory.WORK -> Severity.WARN
                                NetworkCategory.PUBLIC -> Severity.INFO
                            }
                            val score = when (severity) {
                                Severity.WARN -> 15
                                else -> 5
                            }
                            findings.add(
                                Finding(
                                    id = UUID.randomUUID().toString(),
                                    snapshotId = current.id,
                                    detectorId = id,
                            severity = severity,
                            scoreDelta = score,
                            title = FindingTextKeys.SIMILAR_NETWORKS_TITLE,
                            explanation = FindingTextKeys.SIMILAR_NETWORKS_BODY,
                            evidence = mapOf(
                                EvidenceKeys.TRUSTED_SSID to (trusted.ssid ?: currentSsid),
                                EvidenceKeys.SIMILAR_SSIDS to similarSsids.joinToString(),
                                EvidenceKeys.BASE_SSID to currentBase
                            ),
                            actions = FindingActionResolver.resolve(id, severity)
                        )
                    )
                }
            }
        }
            }
        }

        return findings
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

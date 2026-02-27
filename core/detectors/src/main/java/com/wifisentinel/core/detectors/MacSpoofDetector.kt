package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.NetworkCategory
import java.util.UUID

class MacSpoofDetector : Detector {
    override val id: String = "mac_spoof"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val currentMac = normalizeMac(ctx.current.bssid) ?: return emptyList()
        val findings = mutableListOf<Finding>()
        val firstByte = currentMac.substring(0, 2).toIntOrNull(16) ?: return emptyList()
        val isMulticast = (firstByte and 0x01) != 0
        val isLocallyAdministered = (firstByte and 0x02) != 0
        val category = ctx.category ?: NetworkCategory.PUBLIC

        if (isMulticast) {
            findings.add(
                buildFinding(
                    ctx = ctx,
                    severity = Severity.CRITICAL,
                    score = 90,
                    title = FindingTextKeys.MAC_SPOOF_INVALID_TITLE,
                    body = FindingTextKeys.MAC_SPOOF_INVALID_BODY,
                    evidence = mapOf(
                        EvidenceKeys.CURRENT_BSSID to currentMac,
                        EvidenceKeys.MAC_MULTICAST_BIT to "true"
                    )
                )
            )
        }

        if (isLocallyAdministered) {
            val severity = when (category) {
                NetworkCategory.HOME -> Severity.HIGH
                NetworkCategory.WORK -> Severity.WARN
                NetworkCategory.PUBLIC -> Severity.WARN
            }
            val score = when (severity) {
                Severity.HIGH -> 35
                Severity.WARN -> 18
                else -> 10
            }
            findings.add(
                buildFinding(
                    ctx = ctx,
                    severity = severity,
                    score = score,
                    title = FindingTextKeys.MAC_SPOOF_LOCAL_TITLE,
                    body = FindingTextKeys.MAC_SPOOF_LOCAL_BODY,
                    evidence = mapOf(
                        EvidenceKeys.CURRENT_BSSID to currentMac,
                        EvidenceKeys.MAC_LOCAL_ADMIN_BIT to "true"
                    )
                )
            )
        }

        val trustedProfile = ctx.trustedProfile
        if (trustedProfile != null) {
            val expectedOuis = trustedProfile.allowedBssids
                .mapNotNull { normalizeMac(it) }
                .map { it.take(8) }
                .toSet()
            val currentOui = currentMac.take(8)
            if (expectedOuis.isNotEmpty() && currentOui !in expectedOuis) {
                val severity = when (category) {
                    NetworkCategory.HOME -> Severity.HIGH
                    NetworkCategory.WORK -> Severity.WARN
                    NetworkCategory.PUBLIC -> Severity.INFO
                }
                val score = when (severity) {
                    Severity.HIGH -> 40
                    Severity.WARN -> 20
                    else -> 8
                }
                findings.add(
                    buildFinding(
                        ctx = ctx,
                        severity = severity,
                        score = score,
                        title = FindingTextKeys.MAC_SPOOF_OUI_TITLE,
                        body = FindingTextKeys.MAC_SPOOF_OUI_BODY,
                        evidence = mapOf(
                            EvidenceKeys.CURRENT_BSSID to currentMac,
                            EvidenceKeys.CURRENT_BSSID_OUI to currentOui,
                            EvidenceKeys.EXPECTED_BSSID_OUIS to expectedOuis.joinToString()
                        )
                    )
                )
            }

            val recentSnapshots = ctx.history
                .filter { it.networkIdHint == ctx.current.networkIdHint }
                .takeLast(8)
            val recentOuis = (recentSnapshots.mapNotNull { normalizeMac(it.bssid)?.take(8) } + currentMac.take(8))
                .distinct()
            if (recentOuis.size >= OUI_CHURN_THRESHOLD) {
                val severity = when (category) {
                    NetworkCategory.HOME -> Severity.HIGH
                    NetworkCategory.WORK -> Severity.HIGH
                    NetworkCategory.PUBLIC -> Severity.WARN
                }
                val score = when (severity) {
                    Severity.HIGH -> 36
                    Severity.WARN -> 22
                    Severity.CRITICAL -> 60
                    Severity.INFO -> 8
                }
                findings.add(
                    buildFinding(
                        ctx = ctx,
                        severity = severity,
                        score = score,
                        title = FindingTextKeys.MAC_SPOOF_OUI_CHURN_TITLE,
                        body = FindingTextKeys.MAC_SPOOF_OUI_CHURN_BODY,
                        evidence = mapOf(
                            EvidenceKeys.CURRENT_BSSID to currentMac,
                            EvidenceKeys.OUI_CHANGES to recentOuis.size.toString(),
                            EvidenceKeys.OBSERVED_OUIS to recentOuis.joinToString()
                        )
                    )
                )
            }
        }

        return findings.distinctBy { finding ->
            val evidenceHash = finding.evidence.toSortedMap().entries.joinToString("|") { "${it.key}:${it.value}" }
            "${finding.detectorId}|${finding.title}|$evidenceHash"
        }
    }

    private fun buildFinding(
        ctx: AnalyzeContext,
        severity: Severity,
        score: Int,
        title: String,
        body: String,
        evidence: Map<String, String>
    ): Finding {
        return Finding(
            id = UUID.randomUUID().toString(),
            snapshotId = ctx.current.id,
            detectorId = id,
            severity = severity,
            scoreDelta = score,
            title = title,
            explanation = body,
            evidence = evidence,
            actions = FindingActionResolver.resolve(id, severity)
        )
    }

    private fun normalizeMac(raw: String?): String? {
        if (raw.isNullOrBlank()) return null
        val hex = raw.filter { it.isLetterOrDigit() }.uppercase()
        if (hex.length != 12) return null
        if (hex.any { it !in '0'..'9' && it !in 'A'..'F' }) return null
        return hex.chunked(2).joinToString(":")
    }

    private companion object {
        const val OUI_CHURN_THRESHOLD = 3
    }
}

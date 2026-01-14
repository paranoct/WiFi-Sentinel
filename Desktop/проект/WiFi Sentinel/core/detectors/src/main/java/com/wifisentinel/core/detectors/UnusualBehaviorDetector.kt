package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.BandMapper
import java.util.UUID

class UnusualBehaviorDetector : Detector {
    override val id: String = "unusual_behavior"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val history = ctx.history.filter { it.networkIdHint == ctx.current.networkIdHint && it.id != ctx.current.id }
        if (history.size < MIN_HISTORY) return emptyList()

        val findings = mutableListOf<Finding>()
        val current = ctx.current

        // Baseline BSSID diversity
        val baselineBssids = history.mapNotNull { it.bssid?.lowercase() }.toSet()
        val currentBssidSet = (history.mapNotNull { it.bssid?.lowercase() } + listOfNotNull(current.bssid?.lowercase())).toSet()
        if (baselineBssids.isNotEmpty()) {
            val baselineCount = baselineBssids.size
            val currentCount = currentBssidSet.size
            if (baselineCount <= 3 && currentCount - baselineCount >= 3) {
                findings.add(
                    buildFinding(
                        severity = Severity.WARN,
                        score = 25,
                        title = FindingTextKeys.UNUSUAL_BEHAVIOR_TITLE,
                        explanation = FindingTextKeys.UNUSUAL_BEHAVIOR_BODY,
                        evidence = mapOf(
                            EvidenceKeys.BASELINE_BSSID_COUNT to baselineCount.toString(),
                            EvidenceKeys.CURRENT_BSSID_COUNT to currentCount.toString()
                        ),
                        snapshotId = current.id
                    )
                )
            }
        }

        // DNS change rate
        val dnsChangeCount = countDnsChanges(history)
        val lastDns = history.lastOrNull()?.dnsServers?.toSet()
        if (dnsChangeCount <= 1 && lastDns != null && lastDns.isNotEmpty()) {
            val currentDns = current.dnsServers.toSet()
            if (currentDns.isNotEmpty() && currentDns != lastDns) {
                findings.add(
                    buildFinding(
                        severity = Severity.WARN,
                        score = 20,
                        title = FindingTextKeys.UNUSUAL_BEHAVIOR_TITLE,
                        explanation = FindingTextKeys.UNUSUAL_BEHAVIOR_BODY,
                        evidence = mapOf(
                            EvidenceKeys.BASELINE_DNS_CHANGES to dnsChangeCount.toString(),
                            EvidenceKeys.EXPECTED_DNS to lastDns.joinToString(),
                            EvidenceKeys.CURRENT_DNS to currentDns.joinToString()
                        ),
                        snapshotId = current.id
                    )
                )
            }
        }

        // Captive portal unusual for history
        val portalBaseline = history.count { it.captivePortal }
        if (portalBaseline == 0 && current.captivePortal) {
            findings.add(
                buildFinding(
                    severity = Severity.WARN,
                    score = 20,
                    title = FindingTextKeys.UNUSUAL_BEHAVIOR_TITLE,
                    explanation = FindingTextKeys.UNUSUAL_BEHAVIOR_BODY,
                    evidence = mapOf(
                        EvidenceKeys.CAPTIVE_PORTAL to "true"
                    ),
                    snapshotId = current.id
                )
            )
        }

        // Band unusual
        val mainBand = mainBand(history)
        val currentBand = BandMapper.fromFrequencyMhz(current.frequencyMhz)
        if (mainBand != null && currentBand != null && mainBand != currentBand) {
            findings.add(
                    buildFinding(
                        severity = Severity.INFO,
                        score = 10,
                        title = FindingTextKeys.UNUSUAL_BEHAVIOR_TITLE,
                        explanation = FindingTextKeys.UNUSUAL_BEHAVIOR_BODY,
                        evidence = mapOf(
                            EvidenceKeys.BASELINE_MAIN_BAND to mainBand.name,
                            EvidenceKeys.CURRENT_BAND_SIMPLE to currentBand.name
                        ),
                        snapshotId = current.id
                    )
                )
        }

        return findings
    }

    private fun buildFinding(
        severity: Severity,
        score: Int,
        title: String,
        explanation: String,
        evidence: Map<String, String>,
        snapshotId: String
    ): Finding {
        return Finding(
            id = UUID.randomUUID().toString(),
            snapshotId = snapshotId,
            detectorId = id,
            severity = severity,
            scoreDelta = score,
            title = title,
            explanation = explanation,
            evidence = evidence,
            actions = FindingActionResolver.resolve(id, severity),
            dedupKey = "$id|${evidence.hashCode()}"
        )
    }

    private fun countDnsChanges(history: List<com.wifisentinel.core.wifi.NetworkSnapshot>): Int {
        var changes = 0
        var last: Set<String>? = null
        history.sortedBy { it.timestampMs }.forEach { snap ->
            val current = snap.dnsServers.toSet()
            if (last != null && last != current) {
                changes++
            }
            last = current
        }
        return changes
    }

    private fun mainBand(history: List<com.wifisentinel.core.wifi.NetworkSnapshot>): com.wifisentinel.core.wifi.Band? {
        return history.mapNotNull { BandMapper.fromFrequencyMhz(it.frequencyMhz) }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
    }

    private companion object {
        const val MIN_HISTORY = 5
    }
}

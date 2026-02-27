package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.NetworkCategory
import java.util.UUID

class PinnedDnsDetector : Detector {
    override val id: String = "pinned_dns"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val trusted = ctx.trustedProfile ?: return emptyList()
        val pinned = trusted.pinnedDns
        if (pinned.isEmpty()) return emptyList()
        val current = ctx.current.dnsServers
        if (current.isEmpty()) return emptyList()

        val pinnedSet = pinned.toSet()
        val currentSet = current.toSet()
        if (pinnedSet == currentSet) return emptyList()

        val category = ctx.category ?: NetworkCategory.PUBLIC
        val severity = when (category) {
            NetworkCategory.HOME -> Severity.HIGH
            NetworkCategory.WORK -> Severity.WARN
            NetworkCategory.PUBLIC -> Severity.INFO
        }
        val score = when (severity) {
            Severity.HIGH -> 40
            Severity.WARN -> 20
            else -> 5
        }

        return listOf(
            Finding(
                id = UUID.randomUUID().toString(),
                snapshotId = ctx.current.id,
                detectorId = id,
                severity = severity,
                scoreDelta = score,
                title = FindingTextKeys.PINNED_DNS_TITLE,
                explanation = FindingTextKeys.PINNED_DNS_BODY,
                actions = FindingActionResolver.resolve(id, severity),
                evidence = mapOf(
                    EvidenceKeys.EXPECTED_DNS to pinned.joinToString(),
                    EvidenceKeys.CURRENT_DNS to current.joinToString()
                )
            )
        )
    }
}

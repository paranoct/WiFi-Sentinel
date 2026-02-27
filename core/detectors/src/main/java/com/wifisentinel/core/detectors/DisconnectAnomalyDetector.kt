package com.wifisentinel.core.detectors

import java.util.UUID

class DisconnectAnomalyDetector : Detector {
    override val id: String = "disconnect_anomaly"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val recent = ctx.history.filter { ctx.current.timestampMs - it.timestampMs <= 10 * 60 * 1000L }
        if (recent.size < 8) return emptyList()

        val bssidSeries = recent.mapNotNull { it.bssid }
        if (bssidSeries.size < 2) return emptyList()

        val distinctBssids = bssidSeries.distinct()
        if (distinctBssids.size < 2) return emptyList()

        val transitions = bssidSeries.zipWithNext().count { (prev, next) -> prev != next }
        if (transitions < 2) return emptyList()

        return listOf(
            Finding(
                id = UUID.randomUUID().toString(),
                snapshotId = ctx.current.id,
                detectorId = id,
                severity = Severity.WARN,
                scoreDelta = 15,
                title = FindingTextKeys.DISCONNECT_ANOMALY_TITLE,
                explanation = FindingTextKeys.DISCONNECT_ANOMALY_BODY,
                actions = FindingActionResolver.resolve(id, Severity.WARN),
                evidence = mapOf(
                    EvidenceKeys.RECONNECTS to transitions.toString(),
                    EvidenceKeys.OBSERVED_BSSIDS to distinctBssids.joinToString()
                )
            )
        )
    }
}

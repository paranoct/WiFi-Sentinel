package com.wifisentinel.core.detectors

import java.util.UUID

class GatewayAnomalyDetector : Detector {
    override val id: String = "gateway_anomaly"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val currentGateway = ctx.current.gatewayV4 ?: return emptyList()
        val recent = ctx.history.take(5)
        val distinctGateways = recent.mapNotNull { it.gatewayV4 }.distinct()

        if (distinctGateways.size < 2) return emptyList()

        val timeSpanMs = recent.maxOfOrNull { it.timestampMs }?.minus(recent.minOfOrNull { it.timestampMs } ?: 0)
            ?: 0
        if (timeSpanMs > 60 * 60 * 1000L) return emptyList()

        return listOf(
            Finding(
                id = UUID.randomUUID().toString(),
                snapshotId = ctx.current.id,
                detectorId = id,
                severity = Severity.WARN,
                scoreDelta = 20,
                title = FindingTextKeys.GATEWAY_ANOMALY_TITLE,
                explanation = FindingTextKeys.GATEWAY_ANOMALY_BODY,
                actions = FindingActionResolver.resolve(id, Severity.WARN),
                evidence = mapOf(
                    EvidenceKeys.GATEWAY_IPS to distinctGateways.joinToString()
                )
            )
        )
    }
}

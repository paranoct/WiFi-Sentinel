package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.NetworkCategory
import java.util.UUID
import kotlin.math.max

class MeshNewBssidDetector : Detector {
    override val id: String = "mesh_new_bssid"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        val trusted = ctx.trustedProfile ?: return emptyList()
        if (!trusted.meshMode) return emptyList()

        val cutoff = ctx.current.timestampMs - ONE_DAY_MS
        val history = ctx.history
            .filter { it.networkIdHint == ctx.current.networkIdHint && it.timestampMs >= cutoff }
        val known = trusted.allowedBssids.map { it.lowercase() }.toSet()
        val observed = (history.mapNotNull { it.bssid } + listOfNotNull(ctx.current.bssid))
            .map { it.lowercase() }
            .toSet()
        val newOnes = observed.filter { it !in known }
        val limit = trusted.maxNewBssidPerDay

        if (newOnes.size > limit && newOnes.isNotEmpty()) {
            val severity = Severity.HIGH
            val score = 40 + max(0, newOnes.size - limit) * 5
            val actions = FindingActionResolver.resolve(id, severity)
            return listOf(
                Finding(
                    id = UUID.randomUUID().toString(),
                    snapshotId = ctx.current.id,
                    detectorId = id,
                    severity = severity,
                    scoreDelta = score,
                    title = FindingTextKeys.MESH_TOO_MANY_BSSID_TITLE,
                    explanation = FindingTextKeys.MESH_TOO_MANY_BSSID_BODY,
                    evidence = mapOf(
                        EvidenceKeys.NEW_BSSID_COUNT to newOnes.size.toString(),
                        EvidenceKeys.MAX_NEW_BSSID to limit.toString(),
                        EvidenceKeys.OBSERVED_BSSIDS to newOnes.joinToString()
                    ),
                    actions = actions
                )
            )
        }

        return emptyList()
    }

    private companion object {
        const val ONE_DAY_MS = 24 * 60 * 60 * 1000L
    }
}

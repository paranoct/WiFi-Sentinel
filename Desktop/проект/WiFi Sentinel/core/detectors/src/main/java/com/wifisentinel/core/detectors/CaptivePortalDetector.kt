package com.wifisentinel.core.detectors

import com.wifisentinel.core.net.CaptivePortalProbe
import java.util.UUID

class CaptivePortalDetector(
    private val portalProbe: CaptivePortalProbe? = null
) : Detector {
    override val id: String = "captive_portal"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        if (!ctx.current.captivePortal) return emptyList()

        val findings = mutableListOf(
            Finding(
                id = UUID.randomUUID().toString(),
                snapshotId = ctx.current.id,
                detectorId = id,
                severity = Severity.WARN,
                scoreDelta = 25,
                title = FindingTextKeys.CAPTIVE_PORTAL_TITLE,
                explanation = FindingTextKeys.CAPTIVE_PORTAL_BODY,
                actions = FindingActionResolver.resolve(id, Severity.WARN),
                evidence = mapOf(
                    EvidenceKeys.CAPTIVE_PORTAL to "true"
                )
            )
        )

        val probeResult = portalProbe?.check()
        if (probeResult != null) {
            val suspicious = probeResult.usedHttp ||
                probeResult.hasPunycode ||
                probeResult.redirectDomains.any { domain ->
                    domain.length >= 30 || domain.count { it == '-' } >= 3
                }

            if (suspicious) {
                findings.add(
                    Finding(
                        id = UUID.randomUUID().toString(),
                        snapshotId = ctx.current.id,
                        detectorId = id,
                        severity = Severity.HIGH,
                        scoreDelta = 40,
                        title = FindingTextKeys.CAPTIVE_PORTAL_SUSPICIOUS_TITLE,
                        explanation = FindingTextKeys.CAPTIVE_PORTAL_SUSPICIOUS_BODY,
                        actions = FindingActionResolver.resolve(id, Severity.HIGH),
                        evidence = buildMap {
                            put(EvidenceKeys.PORTAL_REDIRECTS, probeResult.redirectDomains.joinToString(" -> "))
                            if (probeResult.usedHttp) {
                                put(EvidenceKeys.PORTAL_HTTP, "true")
                            }
                            if (probeResult.hasPunycode) {
                                put(EvidenceKeys.PORTAL_PUNYCODE, "true")
                            }
                        }
                    )
                )
            }
        }

        return findings
    }
}

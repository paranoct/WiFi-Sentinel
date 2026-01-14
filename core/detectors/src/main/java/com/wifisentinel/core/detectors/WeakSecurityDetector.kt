package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.SecurityType
import java.util.UUID

class WeakSecurityDetector : Detector {
    override val id: String = "weak_security"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        return when (ctx.current.securityType) {
            SecurityType.OPEN, SecurityType.WEP -> listOf(
                Finding(
                    id = UUID.randomUUID().toString(),
                    snapshotId = ctx.current.id,
                    detectorId = id,
                    severity = Severity.CRITICAL,
                    scoreDelta = 80,
                    title = FindingTextKeys.WEAK_SECURITY_TITLE,
                    explanation = FindingTextKeys.WEAK_SECURITY_BODY,
                    actions = FindingActionResolver.resolve(id, Severity.CRITICAL),
                    evidence = mapOf(
                        EvidenceKeys.SECURITY_TYPE to ctx.current.securityType.name
                    )
                )
            )
            SecurityType.UNKNOWN -> listOf(
                Finding(
                    id = UUID.randomUUID().toString(),
                    snapshotId = ctx.current.id,
                    detectorId = id,
                    severity = Severity.WARN,
                    scoreDelta = 10,
                    title = FindingTextKeys.UNKNOWN_SECURITY_TITLE,
                    explanation = FindingTextKeys.UNKNOWN_SECURITY_BODY,
                    actions = FindingActionResolver.resolve(id, Severity.WARN),
                    evidence = mapOf(
                        EvidenceKeys.SECURITY_TYPE to ctx.current.securityType.name
                    )
                )
            )
            else -> emptyList()
        }
    }
}

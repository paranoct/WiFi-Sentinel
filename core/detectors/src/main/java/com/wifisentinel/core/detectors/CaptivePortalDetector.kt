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
            val phishingHints = detectPhishingHints(probeResult.redirectDomains)

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

            if (phishingHints.isNotEmpty()) {
                val severity = if (probeResult.usedHttp || probeResult.hasPunycode) {
                    Severity.CRITICAL
                } else {
                    Severity.HIGH
                }
                findings.add(
                    Finding(
                        id = UUID.randomUUID().toString(),
                        snapshotId = ctx.current.id,
                        detectorId = id,
                        severity = severity,
                        scoreDelta = if (severity == Severity.CRITICAL) 70 else 48,
                        title = FindingTextKeys.CAPTIVE_PORTAL_PHISHING_TITLE,
                        explanation = FindingTextKeys.CAPTIVE_PORTAL_PHISHING_BODY,
                        actions = FindingActionResolver.resolve(id, severity),
                        evidence = buildMap {
                            put(EvidenceKeys.PORTAL_REDIRECTS, probeResult.redirectDomains.joinToString(" -> "))
                            put(EvidenceKeys.PORTAL_PHISHING_HINTS, phishingHints.joinToString("; "))
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

    private fun detectPhishingHints(domains: List<String>): List<String> {
        if (domains.isEmpty()) return emptyList()
        return domains.mapNotNull { raw ->
            val domain = raw.lowercase()
            val hasCredentialWords = PHISHING_KEYWORDS.count { domain.contains(it) } >= 2
            val hasAggressivePattern = domain.contains("verify") && domain.contains("account")
            val hasSuspiciousHyphenation = domain.count { it == '-' } >= 4
            when {
                hasAggressivePattern -> "похоже на поддельную проверку аккаунта ($raw)"
                hasCredentialWords -> "много слов про вход/подтверждение ($raw)"
                hasSuspiciousHyphenation -> "чрезмерно сложный домен входа ($raw)"
                else -> null
            }
        }.distinct()
    }

    private companion object {
        val PHISHING_KEYWORDS = listOf(
            "login",
            "signin",
            "verify",
            "secure",
            "account",
            "auth",
            "update",
            "wallet",
            "payment",
            "sso"
        )
    }
}

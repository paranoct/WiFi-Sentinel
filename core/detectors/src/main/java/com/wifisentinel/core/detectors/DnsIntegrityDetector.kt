package com.wifisentinel.core.detectors

import com.wifisentinel.core.net.DnsProbe
import kotlinx.coroutines.delay
import java.util.UUID

class DnsIntegrityDetector(
    private val dnsProbe: DnsProbe,
    private val controlDomains: List<String> = listOf("example.com", "example.org", "example.net"),
    private val enabledProvider: suspend () -> Boolean = { true },
    private val cacheTtlMs: Long = 5 * 60 * 1000L
) : Detector {
    override val id: String = "dns_integrity"
    private val cache = mutableMapOf<String, CacheEntry>()

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        if (!enabledProvider()) return emptyList()
        if (ctx.current.dnsServers.isEmpty()) return emptyList()

        val networkKey = ctx.current.networkIdHint.ifBlank {
            ctx.current.ssid?.trim()?.lowercase() ?: ctx.current.bssid?.trim()?.lowercase() ?: "unknown"
        }
        val now = ctx.current.timestampMs
        val cached = cache[networkKey]
        if (cached != null && now - cached.timestampMs < cacheTtlMs) {
            return cached.finding?.let { listOf(buildFinding(ctx, it)) } ?: emptyList()
        }

        val first = probeDomains()
        delay(200)
        val second = probeDomains()

        val emptySystemDomains = mutableListOf<String>()
        val privateOnlyDomains = mutableListOf<String>()
        val interceptCandidates = linkedMapOf<String, MutableList<String>>()

        controlDomains.forEach { domain ->
            val firstResult = first[domain] ?: DomainCheck()
            val secondResult = second[domain] ?: DomainCheck()

            val stableSystem = firstResult.systemAnswers == secondResult.systemAnswers
            val stableDoh = firstResult.dohAnswers == secondResult.dohAnswers
            if (!stableSystem || !stableDoh) return@forEach

            val systemAnswers = firstResult.systemAnswers
            val dohAnswers = firstResult.dohAnswers

            if (systemAnswers.isEmpty() && dohAnswers.isNotEmpty()) {
                emptySystemDomains.add(domain)
                return@forEach
            }

            val systemPrivateOnly = systemAnswers.isNotEmpty() && systemAnswers.all { isPrivateIp(it) }
            val dohHasPublic = dohAnswers.any { isPublicIp(it) }
            if (systemPrivateOnly && dohHasPublic) {
                privateOnlyDomains.add(domain)
                return@forEach
            }

            val hasIntersection = systemAnswers.any { dohAnswers.contains(it) }
            if (!hasIntersection && systemAnswers.size == 1 && dohAnswers.isNotEmpty()) {
                val interceptIp = systemAnswers.first()
                interceptCandidates.getOrPut(interceptIp) { mutableListOf() }.add(domain)
            }
        }

        val confirmedIntercept = interceptCandidates.entries
            .filter { (_, domains) -> domains.size >= DNS_INTERCEPT_DOMAIN_THRESHOLD }
            .maxByOrNull { (_, domains) -> domains.size }
        val interceptDomains = confirmedIntercept?.value.orEmpty()
        val interceptIp = confirmedIntercept?.key

        val dnsHijackSuspicious = interceptDomains.size >= DNS_INTERCEPT_DOMAIN_THRESHOLD &&
            interceptIp != null &&
            !isPrivateIp(interceptIp)
        val suspicious = emptySystemDomains.size >= DOMAIN_MISMATCH_THRESHOLD ||
            privateOnlyDomains.size >= DOMAIN_MISMATCH_THRESHOLD ||
            dnsHijackSuspicious

        val finding = if (!suspicious) {
            null
        } else if (dnsHijackSuspicious) {
            CachedFinding(
                severity = Severity.HIGH,
                score = 42,
                title = FindingTextKeys.DNS_INTERCEPT_TITLE,
                explanation = FindingTextKeys.DNS_INTERCEPT_BODY,
                evidence = buildMap {
                    put(EvidenceKeys.SYSTEM_DNS, ctx.current.dnsServers.joinToString())
                    put(EvidenceKeys.DNS_INTERCEPT_DOMAINS, interceptDomains.joinToString())
                    put(EvidenceKeys.DNS_INTERCEPT_IP, interceptIp.orEmpty())
                }
            )
        } else {
            val severity = if (emptySystemDomains.size >= DOMAIN_MISMATCH_THRESHOLD) Severity.HIGH else Severity.WARN
            CachedFinding(
                severity = severity,
                score = if (severity == Severity.HIGH) 28 else 14,
                title = FindingTextKeys.DNS_SUSPICIOUS_TITLE,
                explanation = FindingTextKeys.DNS_SUSPICIOUS_BODY,
                evidence = buildMap {
                    put(EvidenceKeys.SYSTEM_DNS, ctx.current.dnsServers.joinToString())
                    if (emptySystemDomains.isNotEmpty()) {
                        put(EvidenceKeys.EMPTY_SYSTEM_DOMAINS, emptySystemDomains.joinToString())
                    }
                    if (privateOnlyDomains.isNotEmpty()) {
                        put(EvidenceKeys.PRIVATE_ONLY_DOMAINS, privateOnlyDomains.joinToString())
                    }
                }
            )
        }

        cache[networkKey] = CacheEntry(
            timestampMs = now,
            finding = finding
        )

        return finding?.let { listOf(buildFinding(ctx, it)) } ?: emptyList()
    }

    private suspend fun probeDomains(): Map<String, DomainCheck> {
        val results = LinkedHashMap<String, DomainCheck>()
        for (domain in controlDomains) {
            val systemAnswers = dnsProbe.resolveSystem(domain).normalizeAnswers()
            val dohAnswers = dnsProbe.resolveDoh(domain).normalizeAnswers()
            results[domain] = DomainCheck(
                systemAnswers = systemAnswers,
                dohAnswers = dohAnswers
            )
        }
        return results
    }

    private fun List<String>.normalizeAnswers(): List<String> {
        return asSequence()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()
            .toList()
    }

    private fun buildFinding(ctx: AnalyzeContext, finding: CachedFinding): Finding {
        return Finding(
            id = UUID.randomUUID().toString(),
            snapshotId = ctx.current.id,
            detectorId = id,
            severity = finding.severity,
            scoreDelta = finding.score,
            title = finding.title,
            explanation = finding.explanation,
            actions = FindingActionResolver.resolve(id, finding.severity),
            evidence = finding.evidence
        )
    }

    private fun isPrivateIp(ip: String): Boolean {
        val parts = ip.split(".")
        if (parts.size != 4) return false
        val octets = parts.mapNotNull { it.toIntOrNull() }
        if (octets.size != 4) return false
        val (a, b) = octets
        return when {
            a == 10 -> true
            a == 127 -> true
            a == 0 -> true
            a == 192 && b == 168 -> true
            a == 172 && b in 16..31 -> true
            a == 169 && b == 254 -> true
            else -> false
        }
    }

    private fun isPublicIp(ip: String): Boolean = !isPrivateIp(ip)

    private data class CacheEntry(
        val timestampMs: Long,
        val finding: CachedFinding?
    )

    private data class CachedFinding(
        val severity: Severity,
        val score: Int,
        val title: String,
        val explanation: String,
        val evidence: Map<String, String>
    )

    private data class DomainCheck(
        val systemAnswers: List<String> = emptyList(),
        val dohAnswers: List<String> = emptyList()
    )

    private companion object {
        const val DOMAIN_MISMATCH_THRESHOLD = 3
        const val DNS_INTERCEPT_DOMAIN_THRESHOLD = 3
    }
}

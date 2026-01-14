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
            return if (cached.suspicious) listOf(buildFinding(ctx, cached)) else emptyList()
        }

        val first = probeDomains()
        delay(200)
        val second = probeDomains()

        val emptySystemDomains = mutableListOf<String>()
        val privateOnlyDomains = mutableListOf<String>()
        val mismatchDomains = mutableListOf<String>()

        controlDomains.forEach { domain ->
            val firstResult = first[domain] ?: DomainCheck()
            val secondResult = second[domain] ?: DomainCheck()
            val stable = firstResult.suspicion != Suspicion.NONE &&
                firstResult.suspicion == secondResult.suspicion
            if (!stable) return@forEach

            when (firstResult.suspicion) {
                Suspicion.EMPTY_SYSTEM -> emptySystemDomains.add(domain)
                Suspicion.PRIVATE_ONLY -> privateOnlyDomains.add(domain)
                Suspicion.MISMATCH -> mismatchDomains.add(domain)
                Suspicion.NONE -> Unit
            }
        }

        val suspiciousCount = emptySystemDomains.size + privateOnlyDomains.size + mismatchDomains.size
        val suspicious = suspiciousCount >= 2
        val entry = CacheEntry(
            timestampMs = now,
            suspicious = suspicious,
            emptySystemDomains = emptySystemDomains,
            privateOnlyDomains = privateOnlyDomains,
            mismatchDomains = mismatchDomains
        )
        cache[networkKey] = entry
        return if (suspicious) listOf(buildFinding(ctx, entry)) else emptyList()
    }

    private suspend fun probeDomains(): Map<String, DomainCheck> {
        val results = LinkedHashMap<String, DomainCheck>()
        for (domain in controlDomains) {
            val systemAnswers = dnsProbe.resolveSystem(domain).filter { it.isNotBlank() }
            val dohAnswers = dnsProbe.resolveDoh(domain).filter { it.isNotBlank() }
            results[domain] = DomainCheck(
                suspicion = evaluateSuspicion(systemAnswers, dohAnswers)
            )
        }
        return results
    }

    private fun evaluateSuspicion(systemAnswers: List<String>, dohAnswers: List<String>): Suspicion {
        if (systemAnswers.isEmpty() && dohAnswers.isNotEmpty()) {
            return Suspicion.EMPTY_SYSTEM
        }
        val systemPrivateOnly = systemAnswers.isNotEmpty() && systemAnswers.all { isPrivateIp(it) }
        val dohHasPublic = dohAnswers.any { !isPrivateIp(it) }
        if (systemPrivateOnly && dohHasPublic) {
            return Suspicion.PRIVATE_ONLY
        }
        val intersects = systemAnswers.isNotEmpty() && dohAnswers.isNotEmpty() &&
            systemAnswers.any { dohAnswers.contains(it) }
        if (!intersects && systemAnswers.isNotEmpty() && dohAnswers.isNotEmpty()) {
            return Suspicion.MISMATCH
        }
        return Suspicion.NONE
    }

    private fun buildFinding(ctx: AnalyzeContext, entry: CacheEntry): Finding {
        val severity = if (entry.emptySystemDomains.size >= 2) Severity.HIGH else Severity.WARN
        val score = if (severity == Severity.HIGH) 30 else 20
        return Finding(
            id = UUID.randomUUID().toString(),
            snapshotId = ctx.current.id,
            detectorId = id,
            severity = severity,
            scoreDelta = score,
            title = FindingTextKeys.DNS_SUSPICIOUS_TITLE,
            explanation = FindingTextKeys.DNS_SUSPICIOUS_BODY,
            actions = FindingActionResolver.resolve(id, severity),
            evidence = buildMap {
                put(EvidenceKeys.SYSTEM_DNS, ctx.current.dnsServers.joinToString())
                if (entry.emptySystemDomains.isNotEmpty()) {
                    put(EvidenceKeys.EMPTY_SYSTEM_DOMAINS, entry.emptySystemDomains.joinToString())
                }
                if (entry.privateOnlyDomains.isNotEmpty()) {
                    put(EvidenceKeys.PRIVATE_ONLY_DOMAINS, entry.privateOnlyDomains.joinToString())
                }
                if (entry.mismatchDomains.isNotEmpty()) {
                    put(EvidenceKeys.MISMATCH_DOMAINS, entry.mismatchDomains.joinToString())
                }
            }
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

    private data class CacheEntry(
        val timestampMs: Long,
        val suspicious: Boolean,
        val emptySystemDomains: List<String> = emptyList(),
        val privateOnlyDomains: List<String> = emptyList(),
        val mismatchDomains: List<String> = emptyList()
    )

    private data class DomainCheck(
        val suspicion: Suspicion = Suspicion.NONE
    )

    private enum class Suspicion {
        NONE,
        EMPTY_SYSTEM,
        PRIVATE_ONLY,
        MISMATCH
    }
}

package com.wifisentinel.core.detectors

import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import java.text.Normalizer
import java.util.UUID
import kotlin.math.min

class LookalikeSsidDetector : Detector {
    override val id: String = "lookalike_ssid"

    override suspend fun analyze(ctx: AnalyzeContext): List<Finding> {
        if (ctx.trustedProfiles.isEmpty()) return emptyList()
        val trustedNormalized = ctx.trustedProfiles.mapNotNull { profile ->
            profile.ssid?.let { normalize(it) }?.let { norm -> norm to profile }
        }
        if (trustedNormalized.isEmpty()) return emptyList()

        val findings = mutableListOf<Finding>()
        ctx.scanResults.forEach { scan ->
            val rawSsid = scan.ssid ?: return@forEach
            val normalized = normalize(rawSsid)
            trustedNormalized.forEach { (trustedNorm, profile) ->
                if (trustedNorm == normalized) return@forEach
                val score = similarity(trustedNorm, normalized)
                if (score < SIMILARITY_THRESHOLD) return@forEach

                val baseSeverity = when (profile.category) {
                    NetworkCategory.HOME -> Severity.HIGH
                    NetworkCategory.WORK -> Severity.WARN
                    NetworkCategory.PUBLIC -> Severity.INFO
                }
                val downgraded = isDowngraded(scan.securityType, profile.expectedSecurity)
                val severity = if (downgraded) {
                    when (baseSeverity) {
                        Severity.INFO -> Severity.WARN
                        Severity.WARN -> Severity.HIGH
                        else -> Severity.HIGH
                    }
                } else {
                    baseSeverity
                }

                val evidence = mapOf(
                    EvidenceKeys.LOOKALIKE_TARGET to (profile.ssid ?: profile.displayName),
                    EvidenceKeys.LOOKALIKE_FOUND to rawSsid,
                    EvidenceKeys.LOOKALIKE_DIFF to diffTokens(rawSsid).joinToString(",")
                )
                findings.add(
                    Finding(
                        id = UUID.randomUUID().toString(),
                        snapshotId = ctx.current.id,
                        detectorId = id,
                        severity = severity,
                        scoreDelta = if (severity == Severity.HIGH) 40 else 20,
                        title = FindingTextKeys.LOOKALIKE_SSID_TITLE,
                        explanation = FindingTextKeys.LOOKALIKE_SSID_BODY,
                        evidence = evidence,
                        actions = FindingActionResolver.resolve(id, severity),
                        dedupKey = "$id|${profile.id}|$normalized"
                    )
                )
            }
        }
        return findings.distinctBy { it.dedupKey }
    }

    private fun normalize(input: String): String {
        val trimmed = input.trim().lowercase()
        val nkfc = Normalizer.normalize(trimmed, Normalizer.Form.NFKC)
        val collapsedSpaces = nkfc.replace(Regex("\\s+"), " ")
        return collapsedSpaces.map { confusableMap[it] ?: it }.joinToString("")
    }

    private fun similarity(a: String, b: String): Double {
        val maxLen = min(a.length, b.length).coerceAtLeast(1)
        val distance = levenshtein(a, b)
        return 1.0 - distance.toDouble() / maxLen.toDouble()
    }

    private fun diffTokens(rawSsid: String): List<String> {
        val tokens = mutableListOf<String>()
        val trimmed = rawSsid.trim()
        if (rawSsid.contains("xn--", ignoreCase = true)) {
            tokens.add(DIFF_PUNYCODE)
        }
        if (rawSsid != trimmed || rawSsid.contains(Regex("\\s{2,}"))) {
            tokens.add(DIFF_SPACES)
        }
        if (rawSsid.any { confusableMap.containsKey(it) }) {
            tokens.add(DIFF_CONFUSABLE)
        }
        if (tokens.isEmpty()) {
            tokens.add(DIFF_UNKNOWN)
        }
        return tokens
    }

    private fun isDowngraded(
        observed: SecurityType,
        expected: Set<SecurityType>
    ): Boolean {
        if (expected.isEmpty()) return false
        val expectedRank = expected.maxOf { securityRank(it) }
        return securityRank(observed) < expectedRank
    }

    private fun securityRank(type: SecurityType): Int {
        return when (type) {
            SecurityType.OPEN -> 0
            SecurityType.WEP -> 1
            SecurityType.WPA2 -> 2
            SecurityType.WPA3 -> 3
            SecurityType.WPA2_WPA3 -> 3
            SecurityType.UNKNOWN -> 0
        }
    }

    private fun levenshtein(lhs: String, rhs: String): Int {
        if (lhs == rhs) return 0
        if (lhs.isEmpty()) return rhs.length
        if (rhs.isEmpty()) return lhs.length
        val lhsLen = lhs.length
        val rhsLen = rhs.length
        val cost = IntArray(lhsLen + 1) { it }
        val newCost = IntArray(lhsLen + 1)
        for (j in 1..rhsLen) {
            newCost[0] = j
            for (i in 1..lhsLen) {
                val match = if (lhs[i - 1] == rhs[j - 1]) 0 else 1
                val replace = cost[i - 1] + match
                val insert = cost[i] + 1
                val delete = newCost[i - 1] + 1
                newCost[i] = minOf(replace, insert, delete)
            }
            for (i in 0..lhsLen) cost[i] = newCost[i]
        }
        return cost[lhsLen]
    }

    private companion object {
        const val SIMILARITY_THRESHOLD = 0.7
        const val DIFF_CONFUSABLE = "confusable"
        const val DIFF_SPACES = "spaces"
        const val DIFF_PUNYCODE = "punycode"
        const val DIFF_UNKNOWN = "unknown"
        private val confusableMap = mapOf(
            '\u0410' to 'a', // Cyrillic A
            '\u0430' to 'a',
            '\u041E' to 'o', // Cyrillic O
            '\u043E' to 'o',
            '\u0421' to 'c', // Cyrillic C
            '\u0441' to 'c',
            '\u0420' to 'p', // Cyrillic P
            '\u0440' to 'p',
            '\u041D' to 'h', // Cyrillic H
            '\u043D' to 'h',
            '\u041A' to 'k', // Cyrillic K
            '\u043A' to 'k',
            '\u041C' to 'm', // Cyrillic M
            '\u043C' to 'm',
            '\u0422' to 't', // Cyrillic T
            '\u0442' to 't',
            '\u0425' to 'x', // Cyrillic X
            '\u0445' to 'x',
            '\u0406' to 'i',
            '\u0456' to 'i'
        )
    }
}

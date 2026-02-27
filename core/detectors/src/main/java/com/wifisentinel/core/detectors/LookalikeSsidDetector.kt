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
        ctx.scanResults.forEach scanLoop@ { scan ->
            val rawSsid = scan.ssid ?: return@scanLoop
            val normalized = normalize(rawSsid)
            trustedNormalized.forEach trustedLoop@ { (trustedNorm, profile) ->
                val trustedRaw = profile.ssid ?: profile.displayName
                val sameNormalized = trustedNorm == normalized
                val sameRaw = trustedRaw.trim().equals(rawSsid.trim(), ignoreCase = true)
                if (sameNormalized && sameRaw) return@trustedLoop

                val score = if (sameNormalized) 1.0 else similarity(trustedNorm, normalized)
                if (score < SIMILARITY_THRESHOLD) return@trustedLoop

                val baseSeverity = when (profile.category) {
                    NetworkCategory.HOME -> Severity.HIGH
                    NetworkCategory.WORK -> Severity.WARN
                    NetworkCategory.PUBLIC -> Severity.INFO
                }
                val downgraded = isDowngraded(scan.securityType, profile.expectedSecurity)
                val diffTokens = diffTokens(rawSsid, trustedRaw)
                val hasInvisible = DIFF_INVISIBLE in diffTokens
                val hasNumeric = DIFF_NUMERIC in diffTokens
                val severity = adjustSeverity(baseSeverity, downgraded, hasInvisible, hasNumeric)
                val (title, body) = when {
                    hasInvisible -> FindingTextKeys.LOOKALIKE_INVISIBLE_TITLE to FindingTextKeys.LOOKALIKE_INVISIBLE_BODY
                    hasNumeric -> FindingTextKeys.LOOKALIKE_NUMERIC_TITLE to FindingTextKeys.LOOKALIKE_NUMERIC_BODY
                    else -> FindingTextKeys.LOOKALIKE_SSID_TITLE to FindingTextKeys.LOOKALIKE_SSID_BODY
                }

                val evidence = mapOf(
                    EvidenceKeys.LOOKALIKE_TARGET to (profile.ssid ?: profile.displayName),
                    EvidenceKeys.LOOKALIKE_FOUND to rawSsid,
                    EvidenceKeys.LOOKALIKE_DIFF to diffTokens.joinToString(",")
                )
                findings.add(
                    Finding(
                        id = UUID.randomUUID().toString(),
                        snapshotId = ctx.current.id,
                        detectorId = id,
                        severity = severity,
                        scoreDelta = scoreForSeverity(severity, hasInvisible, hasNumeric),
                        title = title,
                        explanation = body,
                        evidence = evidence,
                        actions = FindingActionResolver.resolve(id, severity),
                        dedupKey = "$id|${profile.id}|$normalized|$title"
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

    private fun diffTokens(rawSsid: String, trustedSsid: String): List<String> {
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
        if (INVISIBLE_CHARS_REGEX.containsMatchIn(rawSsid)) {
            tokens.add(DIFF_INVISIBLE)
        }
        if (hasNumericSubstitution(rawSsid, trustedSsid)) {
            tokens.add(DIFF_NUMERIC)
        }
        if (tokens.isEmpty()) {
            tokens.add(DIFF_UNKNOWN)
        }
        return tokens
    }

    private fun adjustSeverity(
        baseSeverity: Severity,
        downgraded: Boolean,
        hasInvisible: Boolean,
        hasNumeric: Boolean
    ): Severity {
        var severity = baseSeverity
        if (downgraded) {
            severity = escalate(severity)
        }
        if (hasInvisible) {
            severity = escalate(severity)
        } else if (hasNumeric && severity == Severity.INFO) {
            severity = Severity.WARN
        }
        return severity
    }

    private fun escalate(severity: Severity): Severity {
        return when (severity) {
            Severity.INFO -> Severity.WARN
            Severity.WARN -> Severity.HIGH
            Severity.HIGH -> Severity.HIGH
            Severity.CRITICAL -> Severity.CRITICAL
        }
    }

    private fun scoreForSeverity(severity: Severity, hasInvisible: Boolean, hasNumeric: Boolean): Int {
        val base = when (severity) {
            Severity.CRITICAL -> 75
            Severity.HIGH -> 42
            Severity.WARN -> 24
            Severity.INFO -> 10
        }
        val bonus = when {
            hasInvisible -> 8
            hasNumeric -> 5
            else -> 0
        }
        return base + bonus
    }

    private fun hasNumericSubstitution(rawSsid: String, trustedSsid: String): Boolean {
        val raw = Normalizer.normalize(rawSsid.trim().lowercase(), Normalizer.Form.NFKC)
        val trusted = Normalizer.normalize(trustedSsid.trim().lowercase(), Normalizer.Form.NFKC)
        if (raw.length != trusted.length || raw.isBlank()) return false
        var substitutions = 0
        for (idx in raw.indices) {
            val candidate = raw[idx]
            val target = trusted[idx]
            val mapped = numericLookalikeMap[candidate]
            if (mapped != null && mapped == target) {
                substitutions++
            }
        }
        return substitutions > 0
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
        const val DIFF_INVISIBLE = "invisible"
        const val DIFF_NUMERIC = "numeric"
        const val DIFF_UNKNOWN = "unknown"
        val INVISIBLE_CHARS_REGEX = Regex("[\\u200B\\u200C\\u200D\\u2060\\u00AD\\uFEFF]")
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
        private val numericLookalikeMap = mapOf(
            '0' to 'o',
            '1' to 'l',
            '3' to 'e',
            '4' to 'a',
            '5' to 's',
            '7' to 't',
            '8' to 'b'
        )
    }
}

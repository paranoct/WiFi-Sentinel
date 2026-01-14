package com.wifisentinel.core.risk

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.wifi.NetworkCategory
import kotlin.math.roundToInt

class RiskEngine {
    fun evaluate(findings: List<Finding>, category: NetworkCategory? = null): RiskSummary {
        if (findings.isEmpty()) return RiskSummary.empty()

        val adjusted = findings.map { adjustFinding(it, category) }
        val score = adjusted.sumOf { it.scoreDelta }.coerceIn(0, 100)
        val level = when (score) {
            in 0..19 -> RiskLevel.LOW
            in 20..49 -> RiskLevel.MEDIUM
            in 50..79 -> RiskLevel.HIGH
            else -> RiskLevel.CRITICAL
        }

        val topFindings = adjusted.sortedByDescending { it.scoreDelta }.take(3)
        val actions = buildActions(findings, level)

        return RiskSummary(
            score = score,
            level = level,
            summary = RiskTextKeys.SUMMARY_FOUND,
            summaryArgs = listOf(topFindings.size.toString()),
            topFindings = topFindings,
            actions = actions
        )
    }

    private fun adjustFinding(finding: Finding, category: NetworkCategory?): Finding {
        if (category == null) return finding
        val adjustedScore = when (finding.detectorId) {
            "evil_twin" -> when (category) {
                NetworkCategory.HOME -> finding.scoreDelta
                NetworkCategory.WORK -> (finding.scoreDelta * 0.7).roundToInt()
                NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.4).roundToInt()
            }
            "mesh_new_bssid" -> when (category) {
                NetworkCategory.HOME -> finding.scoreDelta
                NetworkCategory.WORK -> (finding.scoreDelta * 0.8).roundToInt()
                NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.5).roundToInt()
            }
            "unusual_behavior" -> when (category) {
                NetworkCategory.HOME -> finding.scoreDelta
                NetworkCategory.WORK -> (finding.scoreDelta * 0.8).roundToInt()
                NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.6).roundToInt()
            }
            "captive_portal" -> when (category) {
                NetworkCategory.HOME -> (finding.scoreDelta * 1.3).roundToInt()
                NetworkCategory.WORK -> finding.scoreDelta
                NetworkCategory.PUBLIC -> (finding.scoreDelta * 1.3).roundToInt()
            }
            else -> finding.scoreDelta
        }.coerceIn(0, 100)
        return if (adjustedScore == finding.scoreDelta) finding else finding.copy(scoreDelta = adjustedScore)
    }

    private fun buildActions(findings: List<Finding>, level: RiskLevel): List<String> {
        val actions = mutableListOf<String>()
        if (findings.any { it.severity == Severity.CRITICAL }) {
            actions.add(RiskTextKeys.ACTION_NO_PASSWORDS)
        }
        if (findings.any { it.detectorId == "evil_twin" }) {
            actions.add(RiskTextKeys.ACTION_CHECK_SSID)
        }
        if (findings.any { it.detectorId == "weak_security" }) {
            actions.add(RiskTextKeys.ACTION_USE_SECURE)
        }
        if (level == RiskLevel.HIGH || level == RiskLevel.CRITICAL) {
            actions.add(RiskTextKeys.ACTION_DISCONNECT_HIGH)
        }
        return actions.distinct()
    }
}

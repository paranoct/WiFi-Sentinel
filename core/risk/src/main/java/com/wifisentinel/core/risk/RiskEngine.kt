package com.wifisentinel.core.risk

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.detectors.Severity
import com.wifisentinel.core.wifi.NetworkCategory
import kotlin.math.max
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
        val categoryAdjusted = if (category == null) {
            finding.scoreDelta
        } else {
            when (finding.detectorId) {
                "evil_twin" -> when (category) {
                    NetworkCategory.HOME -> finding.scoreDelta
                    NetworkCategory.WORK -> (finding.scoreDelta * 0.8).roundToInt()
                    NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.6).roundToInt()
                }
                "mesh_new_bssid" -> when (category) {
                    NetworkCategory.HOME -> finding.scoreDelta
                    NetworkCategory.WORK -> (finding.scoreDelta * 0.85).roundToInt()
                    NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.65).roundToInt()
                }
                "unusual_behavior" -> when (category) {
                    NetworkCategory.HOME -> finding.scoreDelta
                    NetworkCategory.WORK -> (finding.scoreDelta * 0.85).roundToInt()
                    NetworkCategory.PUBLIC -> (finding.scoreDelta * 0.7).roundToInt()
                }
                "captive_portal" -> when (category) {
                    NetworkCategory.HOME -> (finding.scoreDelta * 1.2).roundToInt()
                    NetworkCategory.WORK -> finding.scoreDelta
                    NetworkCategory.PUBLIC -> (finding.scoreDelta * 1.25).roundToInt()
                }
                else -> finding.scoreDelta
            }
        }

        val detectorTuned = when (finding.detectorId) {
            "mac_spoof" -> (categoryAdjusted * 1.15).roundToInt()
            "evil_twin" -> (categoryAdjusted * 1.1).roundToInt()
            "lookalike_ssid" -> (categoryAdjusted * 1.1).roundToInt()
            "dns_integrity" -> (categoryAdjusted * 1.05).roundToInt()
            "disconnect_anomaly" -> (categoryAdjusted * 0.9).roundToInt()
            else -> categoryAdjusted
        }

        val severityFloor = when (finding.severity) {
            Severity.CRITICAL -> 35
            Severity.HIGH -> 22
            Severity.WARN -> 10
            Severity.INFO -> 3
        }
        val adjustedScore = max(detectorTuned, severityFloor).coerceIn(0, 100)
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
        if (findings.any { it.detectorId == "mac_spoof" }) {
            actions.add(RiskTextKeys.ACTION_CHECK_SSID)
        }
        if (findings.any { it.detectorId == "lookalike_ssid" }) {
            actions.add(RiskTextKeys.ACTION_CHECK_SSID)
        }
        if (findings.any { it.detectorId == "weak_security" }) {
            actions.add(RiskTextKeys.ACTION_USE_SECURE)
        }
        if (findings.any { it.detectorId == "dns_integrity" }) {
            actions.add(RiskTextKeys.ACTION_USE_SECURE)
        }
        if (findings.any { it.detectorId == "captive_portal" && it.severity >= Severity.HIGH }) {
            actions.add(RiskTextKeys.ACTION_NO_PASSWORDS)
        }
        if (level == RiskLevel.HIGH || level == RiskLevel.CRITICAL) {
            actions.add(RiskTextKeys.ACTION_DISCONNECT_HIGH)
        }
        return actions.distinct()
    }
}

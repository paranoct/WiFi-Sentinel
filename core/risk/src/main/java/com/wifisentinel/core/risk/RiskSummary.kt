package com.wifisentinel.core.risk

import com.wifisentinel.core.detectors.Finding

data class RiskSummary(
    val score: Int,
    val level: RiskLevel,
    val summary: String,
    val summaryArgs: List<String> = emptyList(),
    val topFindings: List<Finding>,
    val actions: List<String>
) {
    companion object {
        fun empty(): RiskSummary = RiskSummary(
            score = 0,
            level = RiskLevel.LOW,
            summary = RiskTextKeys.SUMMARY_EMPTY,
            topFindings = emptyList(),
            actions = emptyList()
        )
    }
}

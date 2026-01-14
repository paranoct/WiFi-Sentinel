package com.wifisentinel.feature.dashboard

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.risk.RiskSummary
import com.wifisentinel.core.wifi.NetworkSnapshot


data class DashboardUiState(
    val snapshot: NetworkSnapshot? = null,
    val riskSummary: RiskSummary = RiskSummary.empty(),
    val findings: List<Finding> = emptyList(),
    val isScanning: Boolean = false,
    val isRiskCalculating: Boolean = false,
    val lastScanTimeMs: Long? = null,
    val routerRecommendations: RouterRecommendations = RouterRecommendations(),
    val securityLabel: SecurityNutritionLabelState = SecurityNutritionLabelState(),
    val maskSensitive: Boolean = true,
    val isDemoMode: Boolean = false
)

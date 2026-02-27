package com.wifisentinel.feature.settings

data class ReplayUiState(
    val maskSensitive: Boolean = true,
    val isRunning: Boolean = false,
    val demoModeEnabled: Boolean = false
)

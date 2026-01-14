package com.wifisentinel.feature.networkdetails

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.wifi.NetworkSnapshot


data class NetworkDetailsUiState(
    val snapshot: NetworkSnapshot? = null,
    val findings: List<Finding> = emptyList()
)

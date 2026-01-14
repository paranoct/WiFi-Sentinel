package com.wifisentinel.core.detectors

data class Finding(
    val id: String,
    val snapshotId: String,
    val detectorId: String,
    val severity: Severity,
    val scoreDelta: Int,
    val title: String,
    val explanation: String,
    val evidence: Map<String, String>,
    val actions: List<FindingActionType> = emptyList(),
    val dedupKey: String = ""
)

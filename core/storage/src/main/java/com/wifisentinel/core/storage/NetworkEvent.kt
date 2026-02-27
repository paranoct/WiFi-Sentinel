package com.wifisentinel.core.storage

import com.wifisentinel.core.detectors.Severity


data class NetworkEvent(
    val id: String,
    val timestampMs: Long,
    val title: String,
    val detail: String,
    val severity: Severity,
    val snapshotId: String?
)

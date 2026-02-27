package com.wifisentinel.core.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wifisentinel.core.detectors.Severity

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val timestampMs: Long,
    val title: String,
    val detail: String,
    val severity: Severity,
    val snapshotId: String?
)

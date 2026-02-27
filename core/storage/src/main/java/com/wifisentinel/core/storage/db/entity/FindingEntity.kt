package com.wifisentinel.core.storage.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wifisentinel.core.detectors.FindingActionType
import com.wifisentinel.core.detectors.Severity

@Entity(tableName = "findings", indices = [Index("dedupKey")])
data class FindingEntity(
    @PrimaryKey val id: String,
    val snapshotId: String,
    val timestampMs: Long,
    val detectorId: String,
    val severity: Severity,
    val scoreDelta: Int,
    val title: String,
    val explanation: String,
    val evidence: Map<String, String>,
    val actions: List<FindingActionType>,
    val dedupKey: String
)

package com.wifisentinel.core.storage.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wifisentinel.core.wifi.Band
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.SecurityType

@Entity(tableName = "trusted_networks", indices = [Index("category")])
data class TrustedNetworkProfileEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val ssid: String?,
    val category: NetworkCategory,
    val meshMode: Boolean,
    val allowedBssids: Set<String>,
    val expectedSecurity: Set<SecurityType>,
    val expectedFreqBands: Set<Band>,
    val pinnedDns: List<String>,
    val createdAtMs: Long,
    val lastSeenMs: Long,
    val maxNewBssidPerDay: Int,
    val bssidLearning: Boolean
)

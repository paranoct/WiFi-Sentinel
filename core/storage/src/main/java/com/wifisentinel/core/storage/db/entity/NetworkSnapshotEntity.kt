package com.wifisentinel.core.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wifisentinel.core.wifi.SecurityType

@Entity(tableName = "network_snapshots")
data class NetworkSnapshotEntity(
    @PrimaryKey val id: String,
    val timestampMs: Long,
    val ssid: String?,
    val bssid: String?,
    val securityType: SecurityType,
    val frequencyMhz: Int?,
    val rssiDbm: Int?,
    val ipV4: String?,
    val gatewayV4: String?,
    val dnsServers: List<String>,
    val captivePortal: Boolean,
    val networkIdHint: String
)

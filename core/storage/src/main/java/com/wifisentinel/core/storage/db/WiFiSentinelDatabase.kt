package com.wifisentinel.core.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wifisentinel.core.storage.db.dao.EventDao
import com.wifisentinel.core.storage.db.dao.FindingDao
import com.wifisentinel.core.storage.db.dao.SnapshotDao
import com.wifisentinel.core.storage.db.dao.TrustedNetworkDao
import com.wifisentinel.core.storage.db.entity.EventEntity
import com.wifisentinel.core.storage.db.entity.FindingEntity
import com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity
import com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity

@Database(
    entities = [
        NetworkSnapshotEntity::class,
        FindingEntity::class,
        TrustedNetworkProfileEntity::class,
        EventEntity::class
    ],
    version = 4
)
@TypeConverters(Converters::class)
abstract class WiFiSentinelDatabase : RoomDatabase() {
    abstract fun snapshotDao(): SnapshotDao
    abstract fun findingDao(): FindingDao
    abstract fun trustedNetworkDao(): TrustedNetworkDao
    abstract fun eventDao(): EventDao
}

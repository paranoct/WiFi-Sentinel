package com.wifisentinel.core.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SnapshotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: NetworkSnapshotEntity)

    @Query("SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT 1")
    fun latest(): Flow<NetworkSnapshotEntity?>

    @Query("SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT 1")
    suspend fun latestOnce(): NetworkSnapshotEntity?

    @Query("SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT :limit")
    suspend fun latestList(limit: Int): List<NetworkSnapshotEntity>

    @Query("SELECT * FROM network_snapshots WHERE networkIdHint = :networkIdHint ORDER BY timestampMs DESC LIMIT :limit")
    suspend fun recentByNetworkId(networkIdHint: String, limit: Int): List<NetworkSnapshotEntity>
}

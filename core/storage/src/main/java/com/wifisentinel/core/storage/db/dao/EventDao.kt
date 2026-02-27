package com.wifisentinel.core.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wifisentinel.core.storage.db.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<EventEntity>)

    @Query("SELECT * FROM events ORDER BY timestampMs DESC LIMIT :limit")
    fun latest(limit: Int): Flow<List<EventEntity>>

    @Query("SELECT * FROM events ORDER BY timestampMs DESC LIMIT :limit")
    suspend fun latestOnce(limit: Int): List<EventEntity>

    @Query(
        """
        DELETE FROM events
        WHERE snapshotId IN (
            SELECT id FROM network_snapshots WHERE networkIdHint = :networkIdHint
        )
        """
    )
    suspend fun deleteByNetworkId(networkIdHint: String): Int
}

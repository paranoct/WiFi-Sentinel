package com.wifisentinel.core.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wifisentinel.core.storage.db.entity.FindingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FindingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<FindingEntity>)

    @Query("SELECT * FROM findings WHERE snapshotId = :snapshotId ORDER BY timestampMs DESC")
    fun forSnapshot(snapshotId: String): Flow<List<FindingEntity>>

    @Query("SELECT * FROM findings WHERE snapshotId = :snapshotId ORDER BY timestampMs DESC")
    suspend fun forSnapshotOnce(snapshotId: String): List<FindingEntity>

    @Query("SELECT * FROM findings ORDER BY timestampMs DESC LIMIT :limit")
    fun latest(limit: Int): Flow<List<FindingEntity>>

    @Query("SELECT * FROM findings ORDER BY timestampMs DESC LIMIT :limit")
    suspend fun latestOnce(limit: Int): List<FindingEntity>

    @Query("SELECT timestampMs FROM findings WHERE dedupKey = :dedupKey ORDER BY timestampMs DESC LIMIT 1")
    suspend fun latestTimestampForDedupKey(dedupKey: String): Long?
}

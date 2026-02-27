package com.wifisentinel.core.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity
import com.wifisentinel.core.wifi.NetworkCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TrustedNetworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: TrustedNetworkProfileEntity)

    @Query("SELECT * FROM trusted_networks ORDER BY displayName ASC")
    fun all(): Flow<List<TrustedNetworkProfileEntity>>

    @Query("SELECT * FROM trusted_networks ORDER BY displayName ASC")
    suspend fun allOnce(): List<TrustedNetworkProfileEntity>

    @Query("SELECT * FROM trusted_networks WHERE category = :category ORDER BY displayName ASC")
    fun byCategory(category: NetworkCategory): Flow<List<TrustedNetworkProfileEntity>>

    @Query("SELECT * FROM trusted_networks WHERE category = :category ORDER BY displayName ASC")
    suspend fun byCategoryOnce(category: NetworkCategory): List<TrustedNetworkProfileEntity>

    @Query("UPDATE trusted_networks SET category = :category WHERE id = :profileId")
    suspend fun moveProfile(profileId: String, category: NetworkCategory)

    @Query("DELETE FROM trusted_networks WHERE id = :profileId")
    suspend fun deleteById(profileId: String)
}

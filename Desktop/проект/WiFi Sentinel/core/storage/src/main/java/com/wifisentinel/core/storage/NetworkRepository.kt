package com.wifisentinel.core.storage

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.storage.db.dao.EventDao
import com.wifisentinel.core.storage.db.dao.FindingDao
import com.wifisentinel.core.storage.db.dao.SnapshotDao
import com.wifisentinel.core.storage.db.dao.TrustedNetworkDao
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface NetworkRepository {
    fun latestSnapshot(): Flow<NetworkSnapshot?>
    suspend fun latestSnapshotOnce(): NetworkSnapshot?
    fun latestFindings(limit: Int = 50): Flow<List<Finding>>
    fun findingsForSnapshot(snapshotId: String): Flow<List<Finding>>
    fun latestEvents(limit: Int = 100): Flow<List<NetworkEvent>>
    fun trustedProfiles(): Flow<List<TrustedNetworkProfile>>
    fun trustedProfiles(category: NetworkCategory): Flow<List<TrustedNetworkProfile>>
    suspend fun trustedProfilesOnce(): List<TrustedNetworkProfile>
    suspend fun trustedProfilesOnce(category: NetworkCategory): List<TrustedNetworkProfile>
    suspend fun findTrustedProfile(snapshot: NetworkSnapshot): TrustedNetworkProfile?
    suspend fun latestSnapshotsOnce(limit: Int = 50): List<NetworkSnapshot>
    suspend fun latestFindingsOnce(limit: Int = 200): List<Finding>
    suspend fun latestEventsOnce(limit: Int = 200): List<NetworkEvent>
    suspend fun findingsForSnapshotOnce(snapshotId: String): List<Finding>
    suspend fun latestFindingTimestamp(dedupKey: String): Long?

    suspend fun saveSnapshot(snapshot: NetworkSnapshot)
    suspend fun saveFindings(findings: List<Finding>, timestampMs: Long)
    suspend fun saveEvents(events: List<NetworkEvent>)
    suspend fun upsertTrustedProfile(profile: TrustedNetworkProfile)
    suspend fun moveTrustedProfile(profileId: String, category: NetworkCategory)
    suspend fun deleteTrustedProfile(profileId: String)
    suspend fun recentSnapshots(networkIdHint: String, limit: Int = 20): List<NetworkSnapshot>
}

class DefaultNetworkRepository(
    private val snapshotDao: SnapshotDao,
    private val findingDao: FindingDao,
    private val trustedNetworkDao: TrustedNetworkDao,
    private val eventDao: EventDao
) : NetworkRepository {
    override fun latestSnapshot(): Flow<NetworkSnapshot?> = snapshotDao.latest().map { it?.toDomain() }

    override suspend fun latestSnapshotOnce(): NetworkSnapshot? {
        return snapshotDao.latestOnce()?.toDomain()
    }

    override fun latestFindings(limit: Int): Flow<List<Finding>> =
        findingDao.latest(limit).map { entities -> entities.map { it.toDomain() } }

    override fun findingsForSnapshot(snapshotId: String): Flow<List<Finding>> =
        findingDao.forSnapshot(snapshotId).map { entities -> entities.map { it.toDomain() } }

    override fun latestEvents(limit: Int): Flow<List<NetworkEvent>> =
        eventDao.latest(limit).map { entities -> entities.map { it.toDomain() } }

    override fun trustedProfiles(): Flow<List<TrustedNetworkProfile>> =
        trustedNetworkDao.all().map { entities -> entities.map { it.toDomain() } }

    override fun trustedProfiles(category: NetworkCategory): Flow<List<TrustedNetworkProfile>> =
        trustedNetworkDao.byCategory(category).map { entities -> entities.map { it.toDomain() } }

    override suspend fun trustedProfilesOnce(): List<TrustedNetworkProfile> {
        return trustedNetworkDao.allOnce().map { it.toDomain() }
    }

    override suspend fun trustedProfilesOnce(category: NetworkCategory): List<TrustedNetworkProfile> {
        return trustedNetworkDao.byCategoryOnce(category).map { it.toDomain() }
    }

    override suspend fun findTrustedProfile(snapshot: NetworkSnapshot): TrustedNetworkProfile? {
        val profiles = trustedNetworkDao.allOnce().map { it.toDomain() }
        val ssid = snapshot.ssid
        val normalizedSsid = ssid?.trim()?.lowercase()
        return when {
            !ssid.isNullOrBlank() -> profiles.firstOrNull { it.ssid?.trim()?.lowercase() == normalizedSsid }
            snapshot.bssid != null -> profiles.firstOrNull { it.allowedBssids.contains(snapshot.bssid) }
            else -> null
        }
    }

    override suspend fun latestSnapshotsOnce(limit: Int): List<NetworkSnapshot> {
        return snapshotDao.latestList(limit).map { it.toDomain() }
    }

    override suspend fun latestFindingsOnce(limit: Int): List<Finding> {
        return findingDao.latestOnce(limit).map { it.toDomain() }
    }

    override suspend fun latestEventsOnce(limit: Int): List<NetworkEvent> {
        return eventDao.latestOnce(limit).map { it.toDomain() }
    }

    override suspend fun findingsForSnapshotOnce(snapshotId: String): List<Finding> {
        return findingDao.forSnapshotOnce(snapshotId).map { it.toDomain() }
    }

    override suspend fun latestFindingTimestamp(dedupKey: String): Long? {
        return findingDao.latestTimestampForDedupKey(dedupKey)
    }

    override suspend fun saveSnapshot(snapshot: NetworkSnapshot) {
        snapshotDao.upsert(snapshot.toEntity())
    }

    override suspend fun saveFindings(findings: List<Finding>, timestampMs: Long) {
        findingDao.insertAll(findings.map { it.toEntity(timestampMs) })
    }

    override suspend fun saveEvents(events: List<NetworkEvent>) {
        if (events.isEmpty()) return
        eventDao.insertAll(events.map { it.toEntity() })
    }

    override suspend fun upsertTrustedProfile(profile: TrustedNetworkProfile) {
        trustedNetworkDao.upsert(profile.toEntity())
    }

    override suspend fun moveTrustedProfile(profileId: String, category: NetworkCategory) {
        trustedNetworkDao.moveProfile(profileId, category)
    }

    override suspend fun deleteTrustedProfile(profileId: String) {
        trustedNetworkDao.deleteById(profileId)
    }

    override suspend fun recentSnapshots(networkIdHint: String, limit: Int): List<NetworkSnapshot> {
        return snapshotDao.recentByNetworkId(networkIdHint, limit).map { it.toDomain() }
    }
}

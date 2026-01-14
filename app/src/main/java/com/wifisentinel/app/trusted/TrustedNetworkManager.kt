package com.wifisentinel.app.trusted

import android.content.Context
import com.wifisentinel.app.R
import com.wifisentinel.core.storage.NetworkRepository
import com.wifisentinel.core.wifi.BandMapper
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.NetworkSnapshotProvider
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class TrustedNetworkManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val snapshotProvider: NetworkSnapshotProvider,
    private val repository: NetworkRepository
) {
    suspend fun addOrUpdateCurrent(
        category: NetworkCategory,
        meshMode: Boolean,
        allowCreate: Boolean = true
    ): Boolean {
        val snapshot = snapshotProvider.currentSnapshot() ?: return false
        return addOrUpdateSnapshot(snapshot, category, meshMode, allowCreate)
    }

    suspend fun addOrUpdateSnapshot(
        snapshot: NetworkSnapshot,
        category: NetworkCategory,
        meshMode: Boolean,
        allowCreate: Boolean = true
    ): Boolean {
        if (snapshot.ssid.isNullOrBlank() && snapshot.bssid.isNullOrBlank()) {
            return false
        }
        val now = System.currentTimeMillis()
        val existing = repository.findTrustedProfile(snapshot)
        val band = BandMapper.fromFrequencyMhz(snapshot.frequencyMhz)
        val bssidSet = snapshot.bssid?.let { setOf(it) } ?: emptySet()
        val securitySet = setOf(snapshot.securityType)
        val bandSet = band?.let { setOf(it) } ?: emptySet()
        val pinnedDns = when (category) {
            NetworkCategory.PUBLIC -> emptyList()
            else -> snapshot.dnsServers
        }

        val profile = if (existing == null) {
            if (!allowCreate) return false
            TrustedNetworkProfile(
                id = UUID.randomUUID().toString(),
                displayName = snapshot.ssid ?: context.getString(R.string.network_unnamed),
                ssid = snapshot.ssid,
                category = category,
                meshMode = meshMode,
                allowedBssids = bssidSet,
                expectedSecurity = securitySet,
                expectedFreqBands = bandSet,
                pinnedDns = pinnedDns,
                createdAtMs = now,
                lastSeenMs = now,
                maxNewBssidPerDay = defaultMaxNewBssidPerDay(category),
                bssidLearning = false
            )
        } else {
            existing.copy(
                category = category,
                meshMode = meshMode,
                allowedBssids = if (meshMode) existing.allowedBssids + bssidSet else bssidSet.ifEmpty { existing.allowedBssids },
                expectedSecurity = existing.expectedSecurity + securitySet,
                expectedFreqBands = existing.expectedFreqBands + bandSet,
                pinnedDns = if (pinnedDns.isNotEmpty()) pinnedDns else existing.pinnedDns,
                lastSeenMs = now
            )
        }

        repository.upsertTrustedProfile(profile)
        return true
    }

    suspend fun addFromScan(
        scanNet: ScanNet,
        category: NetworkCategory,
        meshMode: Boolean
    ): Boolean {
        if (scanNet.ssid.isNullOrBlank() && scanNet.bssid.isNullOrBlank()) {
            return false
        }
        val profiles = repository.trustedProfilesOnce()
        val normalizedSsid = scanNet.ssid?.trim()?.lowercase()
        val existing = profiles.firstOrNull { it.ssid?.trim()?.lowercase() == normalizedSsid }
            ?: scanNet.bssid?.let { bssid -> profiles.firstOrNull { it.allowedBssids.contains(bssid) } }
        val now = System.currentTimeMillis()
        val band = BandMapper.fromFrequencyMhz(scanNet.frequencyMhz)
        val bssidSet = scanNet.bssid?.let { setOf(it) } ?: emptySet()
        val securitySet = setOf(scanNet.securityType)
        val bandSet = band?.let { setOf(it) } ?: emptySet()

        val profile = if (existing == null) {
            TrustedNetworkProfile(
                id = UUID.randomUUID().toString(),
                displayName = scanNet.ssid ?: context.getString(R.string.network_unnamed),
                ssid = scanNet.ssid,
                category = category,
                meshMode = meshMode,
                allowedBssids = bssidSet,
                expectedSecurity = securitySet,
                expectedFreqBands = bandSet,
                pinnedDns = emptyList(),
                createdAtMs = now,
                lastSeenMs = now,
                maxNewBssidPerDay = defaultMaxNewBssidPerDay(category),
                bssidLearning = false
            )
        } else {
            existing.copy(
                category = category,
                meshMode = meshMode,
                allowedBssids = if (meshMode) existing.allowedBssids + bssidSet else bssidSet.ifEmpty { existing.allowedBssids },
                expectedSecurity = existing.expectedSecurity + securitySet,
                expectedFreqBands = existing.expectedFreqBands + bandSet,
                lastSeenMs = now
            )
        }

        repository.upsertTrustedProfile(profile)
        return true
    }

    suspend fun acceptCurrentFingerprint(profileId: String): Boolean {
        val snapshot = snapshotProvider.currentSnapshot() ?: return false
        val profiles = repository.trustedProfilesOnce()
        val existing = profiles.firstOrNull { it.id == profileId } ?: return false
        val now = System.currentTimeMillis()
        val band = BandMapper.fromFrequencyMhz(snapshot.frequencyMhz)
        val bssidSet = snapshot.bssid?.let { setOf(it) } ?: emptySet()
        val bandSet = band?.let { setOf(it) } ?: emptySet()

        val updated = existing.copy(
            allowedBssids = if (existing.meshMode) existing.allowedBssids + bssidSet else bssidSet,
            expectedSecurity = setOf(snapshot.securityType),
            expectedFreqBands = bandSet,
            pinnedDns = snapshot.dnsServers,
            lastSeenMs = now
        )
        repository.upsertTrustedProfile(updated)
        return true
    }

    suspend fun refreshCurrentTrusted(): Boolean {
        val snapshot = snapshotProvider.currentSnapshot() ?: return false
        val profile = repository.findTrustedProfile(snapshot) ?: return false
        return acceptCurrentFingerprint(profile.id)
    }

    private fun defaultMaxNewBssidPerDay(category: NetworkCategory): Int {
        return when (category) {
            NetworkCategory.HOME -> 1
            NetworkCategory.WORK -> 3
            NetworkCategory.PUBLIC -> 10
        }
    }
}

package com.wifisentinel.core.wifi

interface NetworkSnapshotProvider {
    suspend fun currentSnapshot(): NetworkSnapshot?
}

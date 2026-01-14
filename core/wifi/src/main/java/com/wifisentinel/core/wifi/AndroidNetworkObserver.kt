package com.wifisentinel.core.wifi

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.mapNotNull

class AndroidNetworkObserver(
    private val connectivityManager: ConnectivityManager,
    private val snapshotProvider: NetworkSnapshotProvider
) : NetworkObserver {

    override val snapshots: Flow<NetworkSnapshot> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(Unit)
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: android.net.NetworkCapabilities) {
                trySend(Unit)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: android.net.LinkProperties) {
                trySend(Unit)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
        trySend(Unit)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }.mapNotNull { snapshotProvider.currentSnapshot() }
}

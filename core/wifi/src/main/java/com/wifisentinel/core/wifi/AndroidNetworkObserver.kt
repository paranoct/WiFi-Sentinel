package com.wifisentinel.core.wifi

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AndroidNetworkObserver(
    private val connectivityManager: ConnectivityManager,
    private val snapshotProvider: NetworkSnapshotProvider
) : NetworkObserver {

    override val observations: Flow<NetworkObservation> = callbackFlow {
        val emissionMutex = Mutex()
        var lastConnectedSignature: String? = null
        var lastNetworkToken: String? = null
        var wasDisconnected = true
        var hasInitialized = false
        var lastConnectionEventToken: String? = null
        var lastConnectionEventAtMs: Long = 0L

        fun emitCurrent(network: Network?, hintConnectionEvent: Boolean) {
            launch {
                emissionMutex.withLock {
                    val snapshot = snapshotProvider.currentSnapshot()
                    if (snapshot == null) {
                        if (!wasDisconnected) {
                            wasDisconnected = true
                            lastConnectedSignature = null
                            lastNetworkToken = null
                            lastConnectionEventToken = null
                            lastConnectionEventAtMs = 0L
                            trySend(NetworkObservation.Disconnected)
                        }
                        hasInitialized = true
                        return@withLock
                    }
                    if (snapshot.ssid.isNullOrBlank()) {
                        if (!wasDisconnected) {
                            wasDisconnected = true
                            lastConnectedSignature = null
                            lastNetworkToken = null
                            lastConnectionEventToken = null
                            lastConnectionEventAtMs = 0L
                            trySend(NetworkObservation.Disconnected)
                        }
                        hasInitialized = true
                        return@withLock
                    }

                    val snapshotSignature = signature(snapshot)
                    val networkToken =
                        network?.toString()
                            ?: connectivityManager.activeNetwork?.toString()
                            ?: "active"
                    val tokenChanged = networkToken != lastNetworkToken
                    val firstEmission = !hasInitialized
                    val recoveredAfterDisconnect = hasInitialized && wasDisconnected
                    val tokenChangedAfterInit = hasInitialized && !wasDisconnected && tokenChanged
                    var isConnectionEvent = hintConnectionEvent || recoveredAfterDisconnect || tokenChangedAfterInit

                    val now = System.currentTimeMillis()
                    if (isConnectionEvent) {
                        val duplicatedConnectionEvent =
                            networkToken == lastConnectionEventToken &&
                                now - lastConnectionEventAtMs < CONNECTION_EVENT_DEDUP_MS
                        if (duplicatedConnectionEvent) {
                            isConnectionEvent = false
                        }
                    }

                    val shouldEmit =
                        firstEmission || isConnectionEvent || snapshotSignature != lastConnectedSignature || wasDisconnected
                    if (!shouldEmit) {
                        return@withLock
                    }

                    wasDisconnected = false
                    hasInitialized = true
                    lastConnectedSignature = snapshotSignature
                    lastNetworkToken = networkToken
                    if (isConnectionEvent) {
                        lastConnectionEventToken = networkToken
                        lastConnectionEventAtMs = now
                    }
                    trySend(
                        NetworkObservation.Connected(
                            snapshot = snapshot,
                            isConnectionEvent = isConnectionEvent
                        )
                    )
                }
            }
        }

        fun emitDisconnected() {
            launch {
                emissionMutex.withLock {
                    if (wasDisconnected) return@withLock
                    wasDisconnected = true
                    lastConnectedSignature = null
                    lastNetworkToken = null
                    lastConnectionEventToken = null
                    lastConnectionEventAtMs = 0L
                    trySend(NetworkObservation.Disconnected)
                }
            }
        }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                emitCurrent(network = network, hintConnectionEvent = true)
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                emitCurrent(network = network, hintConnectionEvent = false)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: android.net.LinkProperties) {
                emitCurrent(network = network, hintConnectionEvent = false)
            }

            override fun onLost(network: Network) {
                emitDisconnected()
            }

            override fun onUnavailable() {
                emitDisconnected()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
        emitCurrent(network = null, hintConnectionEvent = false)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }

    private fun signature(snapshot: NetworkSnapshot): String {
        return listOf(
            snapshot.networkIdHint,
            snapshot.bssid?.lowercase().orEmpty(),
            snapshot.securityType.name,
            snapshot.gatewayV4.orEmpty(),
            snapshot.dnsServers.sorted().joinToString(","),
            snapshot.captivePortal.toString()
        ).joinToString("|")
    }

    private companion object {
        const val CONNECTION_EVENT_DEDUP_MS = 3_000L
    }
}

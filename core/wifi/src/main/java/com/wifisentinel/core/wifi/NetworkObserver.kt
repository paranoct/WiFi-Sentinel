package com.wifisentinel.core.wifi

import kotlinx.coroutines.flow.Flow

sealed interface NetworkObservation {
    data class Connected(
        val snapshot: NetworkSnapshot,
        val isConnectionEvent: Boolean
    ) : NetworkObservation
    data object Disconnected : NetworkObservation
}

interface NetworkObserver {
    val observations: Flow<NetworkObservation>
}

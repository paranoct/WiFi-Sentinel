package com.wifisentinel.core.wifi

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    val snapshots: Flow<NetworkSnapshot>
}

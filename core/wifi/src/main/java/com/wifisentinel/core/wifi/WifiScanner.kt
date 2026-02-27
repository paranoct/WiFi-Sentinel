package com.wifisentinel.core.wifi

interface WifiScanner {
    suspend fun scan(): List<ScanNet>
}

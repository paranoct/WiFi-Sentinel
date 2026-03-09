package com.wifisentinel.app

import android.app.Application
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.service.AlwaysOnServiceController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WiFiSentinelApplication : Application() {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var alwaysOnServiceController: AlwaysOnServiceController

    override fun onCreate() {
        super.onCreate()
        networkMonitor.start()
        alwaysOnServiceController.start()
    }
}

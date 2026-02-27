package com.wifisentinel.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.service.AlwaysOnServiceController
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WiFiSentinelApplication : Application(), Configuration.Provider {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var alwaysOnServiceController: AlwaysOnServiceController

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this).cancelUniqueWork(PERIODIC_SCAN_WORK_NAME)
        networkMonitor.start()
        alwaysOnServiceController.start()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private companion object {
        const val PERIODIC_SCAN_WORK_NAME = "wifi_sentinel_health_check"
    }
}

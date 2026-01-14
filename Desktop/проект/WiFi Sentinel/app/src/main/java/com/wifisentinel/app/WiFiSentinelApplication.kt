package com.wifisentinel.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.wifisentinel.app.di.ApplicationScope
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.service.AlwaysOnServiceController
import com.wifisentinel.app.work.BackgroundScanScheduler
import com.wifisentinel.core.storage.settings.SettingsRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class WiFiSentinelApplication : Application(), Configuration.Provider {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var backgroundScanScheduler: BackgroundScanScheduler
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var alwaysOnServiceController: AlwaysOnServiceController
    @Inject @ApplicationScope lateinit var appScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        networkMonitor.start()
        appScope.launch {
            backgroundScanScheduler.scheduleFromSettings()
            val enabled = settingsRepository.settings.first().alwaysOnEnabled
            if (enabled) {
                alwaysOnServiceController.start()
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

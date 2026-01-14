package com.wifisentinel.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.notifications.NotificationHelper
import com.wifisentinel.core.storage.settings.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlwaysOnService : Service() {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var settingsRepository: SettingsRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        networkMonitor.start()
        startForeground(
            FOREGROUND_ID,
            notificationHelper.buildForegroundNotification()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            val enabled = settingsRepository.settings.first().alwaysOnEnabled
            if (!enabled) {
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val FOREGROUND_ID = 4041
    }
}

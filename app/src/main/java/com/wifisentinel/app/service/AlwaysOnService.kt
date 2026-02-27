package com.wifisentinel.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.wifisentinel.app.monitor.NetworkMonitor
import com.wifisentinel.app.notifications.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlwaysOnService : Service() {
    @Inject lateinit var networkMonitor: NetworkMonitor
    @Inject lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        networkMonitor.start()
        startForeground(
            FOREGROUND_ID,
            notificationHelper.buildForegroundNotification()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val FOREGROUND_ID = 4041
    }
}

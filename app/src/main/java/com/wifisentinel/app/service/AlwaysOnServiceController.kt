package com.wifisentinel.app.service

import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlwaysOnServiceController @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun start() {
        val intent = Intent(context, AlwaysOnService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun stop() {
        val intent = Intent(context, AlwaysOnService::class.java)
        context.stopService(intent)
    }
}

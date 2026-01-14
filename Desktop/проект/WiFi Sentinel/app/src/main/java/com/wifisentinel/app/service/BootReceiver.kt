package com.wifisentinel.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.wifisentinel.core.storage.settings.SettingsRepository

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var alwaysOnServiceController: AlwaysOnServiceController

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val enabled = settingsRepository.settings.first().alwaysOnEnabled
                if (enabled) {
                    alwaysOnServiceController.start()
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}

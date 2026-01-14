package com.wifisentinel.app.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.wifisentinel.core.storage.settings.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackgroundScanScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) {
    suspend fun scheduleFromSettings() {
        val settings = settingsRepository.settings.first()
        schedule(settings.scanIntervalHours)
    }

    fun schedule(intervalHours: Int) {
        val safeHours = intervalHours.coerceAtLeast(MIN_INTERVAL_HOURS)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<NetworkHealthWorker>(
            safeHours.toLong(),
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private companion object {
        const val WORK_NAME = "wifi_sentinel_health_check"
        const val MIN_INTERVAL_HOURS = 6
    }
}

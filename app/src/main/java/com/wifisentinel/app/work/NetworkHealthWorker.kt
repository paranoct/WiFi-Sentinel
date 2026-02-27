package com.wifisentinel.app.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wifisentinel.app.monitor.NetworkMonitor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NetworkHealthWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val networkMonitor: NetworkMonitor
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            networkMonitor.runHealthCheck()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}

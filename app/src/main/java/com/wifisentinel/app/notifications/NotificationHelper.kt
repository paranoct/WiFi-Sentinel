package com.wifisentinel.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.wifisentinel.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val manager = NotificationManagerCompat.from(context)

    fun notifyRiskAlert(title: String, message: String) {
        if (!manager.areNotificationsEnabled()) return
        ensureAlertChannel()
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager.notify(ALERT_ID, notification)
    }

    fun notifyNetworkReport(
        notificationId: Int,
        title: String,
        message: String,
        showForgetAction: Boolean,
        ssid: String?
    ) {
        if (!manager.areNotificationsEnabled()) return
        ensureReportChannel()
        val builder = NotificationCompat.Builder(context, REPORT_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (showForgetAction) {
            builder.addAction(
                android.R.drawable.ic_menu_delete,
                context.getString(R.string.notif_action_forget_network),
                forgetNetworkPendingIntent(notificationId, ssid)
            )
        }
        manager.notify(notificationId, builder.build())
    }

    fun buildForegroundNotification(): android.app.Notification {
        ensureForegroundChannel()
        return NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setContentTitle(context.getString(R.string.notif_foreground_title))
            .setContentText(context.getString(R.string.notif_foreground_text))
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .build()
    }

    private fun ensureAlertChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notif_channel_alerts_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = context.getString(R.string.notif_channel_alerts_desc)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun ensureReportChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            REPORT_CHANNEL_ID,
            context.getString(R.string.notif_channel_reports_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = context.getString(R.string.notif_channel_reports_desc)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun ensureForegroundChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            FOREGROUND_CHANNEL_ID,
            context.getString(R.string.notif_channel_foreground_name),
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = context.getString(R.string.notif_channel_foreground_desc)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private companion object {
        const val CHANNEL_ID = "wifi_sentinel_alerts"
        const val REPORT_CHANNEL_ID = "wifi_sentinel_reports"
        const val FOREGROUND_CHANNEL_ID = "wifi_sentinel_foreground"
        const val ALERT_ID = 1001
    }

    private fun forgetNetworkPendingIntent(requestCode: Int, ssid: String?): android.app.PendingIntent {
        val intent = android.content.Intent(context, ForgetNetworkReceiver::class.java).apply {
            action = ForgetNetworkReceiver.ACTION_FORGET
            putExtra(ForgetNetworkReceiver.EXTRA_SSID, ssid)
        }
        return android.app.PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            pendingIntentFlags()
        )
    }

    private fun pendingIntentFlags(): Int {
        var flags = android.app.PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or android.app.PendingIntent.FLAG_IMMUTABLE
        }
        return flags
    }
}

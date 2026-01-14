package com.wifisentinel.app.notifications;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\f\u001a\u00020\nH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J0\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012J\u0016\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012J\b\u0010\u001a\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/wifisentinel/app/notifications/NotificationHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "manager", "Landroidx/core/app/NotificationManagerCompat;", "buildForegroundNotification", "Landroid/app/Notification;", "ensureAlertChannel", "", "ensureForegroundChannel", "ensureReportChannel", "forgetNetworkPendingIntent", "Landroid/app/PendingIntent;", "requestCode", "", "ssid", "", "notifyNetworkReport", "notificationId", "title", "message", "showForgetAction", "", "notifyRiskAlert", "pendingIntentFlags", "Companion", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.core.app.NotificationManagerCompat manager = null;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String CHANNEL_ID = "wifi_sentinel_alerts";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String REPORT_CHANNEL_ID = "wifi_sentinel_reports";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String FOREGROUND_CHANNEL_ID = "wifi_sentinel_foreground";
    @java.lang.Deprecated()
    public static final int ALERT_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.notifications.NotificationHelper.Companion Companion = null;
    
    @javax.inject.Inject()
    public NotificationHelper(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void notifyRiskAlert(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    public final void notifyNetworkReport(int notificationId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, boolean showForgetAction, @org.jetbrains.annotations.Nullable()
    java.lang.String ssid) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Notification buildForegroundNotification() {
        return null;
    }
    
    private final void ensureAlertChannel() {
    }
    
    private final void ensureReportChannel() {
    }
    
    private final void ensureForegroundChannel() {
    }
    
    private final android.app.PendingIntent forgetNetworkPendingIntent(int requestCode, java.lang.String ssid) {
        return null;
    }
    
    private final int pendingIntentFlags() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/wifisentinel/app/notifications/NotificationHelper$Companion;", "", "()V", "ALERT_ID", "", "CHANNEL_ID", "", "FOREGROUND_CHANNEL_ID", "REPORT_CHANNEL_ID", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
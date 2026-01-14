package com.wifisentinel.app.service;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\"\u0010\u001e\u001a\u00020\u001f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fH\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/wifisentinel/app/service/AlwaysOnService;", "Landroid/app/Service;", "()V", "networkMonitor", "Lcom/wifisentinel/app/monitor/NetworkMonitor;", "getNetworkMonitor", "()Lcom/wifisentinel/app/monitor/NetworkMonitor;", "setNetworkMonitor", "(Lcom/wifisentinel/app/monitor/NetworkMonitor;)V", "notificationHelper", "Lcom/wifisentinel/app/notifications/NotificationHelper;", "getNotificationHelper", "()Lcom/wifisentinel/app/notifications/NotificationHelper;", "setNotificationHelper", "(Lcom/wifisentinel/app/notifications/NotificationHelper;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "getSettingsRepository", "()Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "setSettingsRepository", "(Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "", "onDestroy", "onStartCommand", "", "flags", "startId", "Companion", "app_debug"})
public final class AlwaysOnService extends android.app.Service {
    @javax.inject.Inject()
    public com.wifisentinel.app.monitor.NetworkMonitor networkMonitor;
    @javax.inject.Inject()
    public com.wifisentinel.app.notifications.NotificationHelper notificationHelper;
    @javax.inject.Inject()
    public com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    public static final int FOREGROUND_ID = 4041;
    @org.jetbrains.annotations.NotNull()
    public static final com.wifisentinel.app.service.AlwaysOnService.Companion Companion = null;
    
    public AlwaysOnService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.monitor.NetworkMonitor getNetworkMonitor() {
        return null;
    }
    
    public final void setNetworkMonitor(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.monitor.NetworkMonitor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.notifications.NotificationHelper getNotificationHelper() {
        return null;
    }
    
    public final void setNotificationHelper(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.notifications.NotificationHelper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.settings.SettingsRepository getSettingsRepository() {
        return null;
    }
    
    public final void setSettingsRepository(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/wifisentinel/app/service/AlwaysOnService$Companion;", "", "()V", "FOREGROUND_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
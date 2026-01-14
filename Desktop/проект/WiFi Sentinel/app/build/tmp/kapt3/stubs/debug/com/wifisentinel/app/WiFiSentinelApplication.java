package com.wifisentinel.app;

@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010-\u001a\u00020.H\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087.\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\f\u0010\u0003\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u00020\u00188\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001e\u0010\u001d\u001a\u00020\u001e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010&R\u001e\u0010\'\u001a\u00020(8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,\u00a8\u0006/"}, d2 = {"Lcom/wifisentinel/app/WiFiSentinelApplication;", "Landroid/app/Application;", "Landroidx/work/Configuration$Provider;", "()V", "alwaysOnServiceController", "Lcom/wifisentinel/app/service/AlwaysOnServiceController;", "getAlwaysOnServiceController", "()Lcom/wifisentinel/app/service/AlwaysOnServiceController;", "setAlwaysOnServiceController", "(Lcom/wifisentinel/app/service/AlwaysOnServiceController;)V", "appScope", "Lkotlinx/coroutines/CoroutineScope;", "getAppScope$annotations", "getAppScope", "()Lkotlinx/coroutines/CoroutineScope;", "setAppScope", "(Lkotlinx/coroutines/CoroutineScope;)V", "backgroundScanScheduler", "Lcom/wifisentinel/app/work/BackgroundScanScheduler;", "getBackgroundScanScheduler", "()Lcom/wifisentinel/app/work/BackgroundScanScheduler;", "setBackgroundScanScheduler", "(Lcom/wifisentinel/app/work/BackgroundScanScheduler;)V", "networkMonitor", "Lcom/wifisentinel/app/monitor/NetworkMonitor;", "getNetworkMonitor", "()Lcom/wifisentinel/app/monitor/NetworkMonitor;", "setNetworkMonitor", "(Lcom/wifisentinel/app/monitor/NetworkMonitor;)V", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "getSettingsRepository", "()Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "setSettingsRepository", "(Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "workManagerConfiguration", "Landroidx/work/Configuration;", "getWorkManagerConfiguration", "()Landroidx/work/Configuration;", "workerFactory", "Landroidx/hilt/work/HiltWorkerFactory;", "getWorkerFactory", "()Landroidx/hilt/work/HiltWorkerFactory;", "setWorkerFactory", "(Landroidx/hilt/work/HiltWorkerFactory;)V", "onCreate", "", "app_debug"})
public final class WiFiSentinelApplication extends android.app.Application implements androidx.work.Configuration.Provider {
    @javax.inject.Inject()
    public com.wifisentinel.app.monitor.NetworkMonitor networkMonitor;
    @javax.inject.Inject()
    public androidx.hilt.work.HiltWorkerFactory workerFactory;
    @javax.inject.Inject()
    public com.wifisentinel.app.work.BackgroundScanScheduler backgroundScanScheduler;
    @javax.inject.Inject()
    public com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository;
    @javax.inject.Inject()
    public com.wifisentinel.app.service.AlwaysOnServiceController alwaysOnServiceController;
    @javax.inject.Inject()
    public kotlinx.coroutines.CoroutineScope appScope;
    
    public WiFiSentinelApplication() {
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
    public final androidx.hilt.work.HiltWorkerFactory getWorkerFactory() {
        return null;
    }
    
    public final void setWorkerFactory(@org.jetbrains.annotations.NotNull()
    androidx.hilt.work.HiltWorkerFactory p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.work.BackgroundScanScheduler getBackgroundScanScheduler() {
        return null;
    }
    
    public final void setBackgroundScanScheduler(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.work.BackgroundScanScheduler p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.settings.SettingsRepository getSettingsRepository() {
        return null;
    }
    
    public final void setSettingsRepository(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.service.AlwaysOnServiceController getAlwaysOnServiceController() {
        return null;
    }
    
    public final void setAlwaysOnServiceController(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.service.AlwaysOnServiceController p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.CoroutineScope getAppScope() {
        return null;
    }
    
    @com.wifisentinel.app.di.ApplicationScope()
    @java.lang.Deprecated()
    public static void getAppScope$annotations() {
    }
    
    public final void setAppScope(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.work.Configuration getWorkManagerConfiguration() {
        return null;
    }
}
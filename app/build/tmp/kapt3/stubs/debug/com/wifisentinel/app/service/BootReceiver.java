package com.wifisentinel.app.service;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2 = {"Lcom/wifisentinel/app/service/BootReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "alwaysOnServiceController", "Lcom/wifisentinel/app/service/AlwaysOnServiceController;", "getAlwaysOnServiceController", "()Lcom/wifisentinel/app/service/AlwaysOnServiceController;", "setAlwaysOnServiceController", "(Lcom/wifisentinel/app/service/AlwaysOnServiceController;)V", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "getSettingsRepository", "()Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "setSettingsRepository", "(Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_debug"})
public final class BootReceiver extends android.content.BroadcastReceiver {
    @javax.inject.Inject()
    public com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository;
    @javax.inject.Inject()
    public com.wifisentinel.app.service.AlwaysOnServiceController alwaysOnServiceController;
    
    public BootReceiver() {
        super();
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
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
}
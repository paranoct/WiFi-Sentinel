package com.wifisentinel.app.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0012\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0018\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0012\u0010\u0015\u001a\u00020\u00142\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u00a8\u0006\u0018"}, d2 = {"Lcom/wifisentinel/app/di/NetworkModule;", "", "()V", "provideCaptivePortalProbe", "Lcom/wifisentinel/core/net/CaptivePortalProbe;", "provideConnectivityManager", "Landroid/net/ConnectivityManager;", "context", "Landroid/content/Context;", "provideDnsProbe", "Lcom/wifisentinel/core/net/DnsProbe;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "provideNetworkObserver", "Lcom/wifisentinel/core/wifi/NetworkObserver;", "connectivityManager", "snapshotProvider", "Lcom/wifisentinel/core/wifi/NetworkSnapshotProvider;", "provideSnapshotProvider", "wifiManager", "Landroid/net/wifi/WifiManager;", "provideWifiManager", "provideWifiScanner", "Lcom/wifisentinel/core/wifi/WifiScanner;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.wifisentinel.app.di.NetworkModule INSTANCE = null;
    
    private NetworkModule() {
        super();
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final android.net.ConnectivityManager provideConnectivityManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final android.net.wifi.WifiManager provideWifiManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkSnapshotProvider provideSnapshotProvider(@org.jetbrains.annotations.NotNull()
    android.net.ConnectivityManager connectivityManager, @org.jetbrains.annotations.NotNull()
    android.net.wifi.WifiManager wifiManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.WifiScanner provideWifiScanner(@org.jetbrains.annotations.NotNull()
    android.net.wifi.WifiManager wifiManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkObserver provideNetworkObserver(@org.jetbrains.annotations.NotNull()
    android.net.ConnectivityManager connectivityManager, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshotProvider snapshotProvider) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.net.DnsProbe provideDnsProbe(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.net.CaptivePortalProbe provideCaptivePortalProbe() {
        return null;
    }
}
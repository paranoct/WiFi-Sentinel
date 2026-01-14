package com.wifisentinel.app.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"Lcom/wifisentinel/app/di/DetectorModule;", "", "()V", "provideDetectors", "", "Lcom/wifisentinel/core/detectors/Detector;", "dnsProbe", "Lcom/wifisentinel/core/net/DnsProbe;", "portalProbe", "Lcom/wifisentinel/core/net/CaptivePortalProbe;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DetectorModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.wifisentinel.app.di.DetectorModule INSTANCE = null;
    
    private DetectorModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.detectors.Detector> provideDetectors(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.net.DnsProbe dnsProbe, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.net.CaptivePortalProbe portalProbe, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository) {
        return null;
    }
}
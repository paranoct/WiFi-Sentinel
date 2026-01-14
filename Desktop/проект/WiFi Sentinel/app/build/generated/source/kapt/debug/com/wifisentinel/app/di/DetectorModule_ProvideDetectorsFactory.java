package com.wifisentinel.app.di;

import com.wifisentinel.core.detectors.Detector;
import com.wifisentinel.core.net.CaptivePortalProbe;
import com.wifisentinel.core.net.DnsProbe;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DetectorModule_ProvideDetectorsFactory implements Factory<List<Detector>> {
  private final Provider<DnsProbe> dnsProbeProvider;

  private final Provider<CaptivePortalProbe> portalProbeProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public DetectorModule_ProvideDetectorsFactory(Provider<DnsProbe> dnsProbeProvider,
      Provider<CaptivePortalProbe> portalProbeProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.dnsProbeProvider = dnsProbeProvider;
    this.portalProbeProvider = portalProbeProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public List<Detector> get() {
    return provideDetectors(dnsProbeProvider.get(), portalProbeProvider.get(), settingsRepositoryProvider.get());
  }

  public static DetectorModule_ProvideDetectorsFactory create(Provider<DnsProbe> dnsProbeProvider,
      Provider<CaptivePortalProbe> portalProbeProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new DetectorModule_ProvideDetectorsFactory(dnsProbeProvider, portalProbeProvider, settingsRepositoryProvider);
  }

  public static List<Detector> provideDetectors(DnsProbe dnsProbe, CaptivePortalProbe portalProbe,
      SettingsRepository settingsRepository) {
    return Preconditions.checkNotNullFromProvides(DetectorModule.INSTANCE.provideDetectors(dnsProbe, portalProbe, settingsRepository));
  }
}

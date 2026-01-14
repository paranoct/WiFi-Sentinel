package com.wifisentinel.app.di;

import com.wifisentinel.core.net.CaptivePortalProbe;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class NetworkModule_ProvideCaptivePortalProbeFactory implements Factory<CaptivePortalProbe> {
  @Override
  public CaptivePortalProbe get() {
    return provideCaptivePortalProbe();
  }

  public static NetworkModule_ProvideCaptivePortalProbeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CaptivePortalProbe provideCaptivePortalProbe() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCaptivePortalProbe());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideCaptivePortalProbeFactory INSTANCE = new NetworkModule_ProvideCaptivePortalProbeFactory();
  }
}

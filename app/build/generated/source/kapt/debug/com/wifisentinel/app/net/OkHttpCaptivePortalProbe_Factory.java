package com.wifisentinel.app.net;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class OkHttpCaptivePortalProbe_Factory implements Factory<OkHttpCaptivePortalProbe> {
  @Override
  public OkHttpCaptivePortalProbe get() {
    return newInstance();
  }

  public static OkHttpCaptivePortalProbe_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OkHttpCaptivePortalProbe newInstance() {
    return new OkHttpCaptivePortalProbe();
  }

  private static final class InstanceHolder {
    private static final OkHttpCaptivePortalProbe_Factory INSTANCE = new OkHttpCaptivePortalProbe_Factory();
  }
}

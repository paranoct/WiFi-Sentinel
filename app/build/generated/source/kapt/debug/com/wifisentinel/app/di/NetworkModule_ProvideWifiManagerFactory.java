package com.wifisentinel.app.di;

import android.content.Context;
import android.net.wifi.WifiManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NetworkModule_ProvideWifiManagerFactory implements Factory<WifiManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideWifiManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WifiManager get() {
    return provideWifiManager(contextProvider.get());
  }

  public static NetworkModule_ProvideWifiManagerFactory create(Provider<Context> contextProvider) {
    return new NetworkModule_ProvideWifiManagerFactory(contextProvider);
  }

  public static WifiManager provideWifiManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWifiManager(context));
  }
}

package com.wifisentinel.app.di;

import android.net.wifi.WifiManager;
import com.wifisentinel.core.wifi.WifiScanner;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class NetworkModule_ProvideWifiScannerFactory implements Factory<WifiScanner> {
  private final Provider<WifiManager> wifiManagerProvider;

  public NetworkModule_ProvideWifiScannerFactory(Provider<WifiManager> wifiManagerProvider) {
    this.wifiManagerProvider = wifiManagerProvider;
  }

  @Override
  public WifiScanner get() {
    return provideWifiScanner(wifiManagerProvider.get());
  }

  public static NetworkModule_ProvideWifiScannerFactory create(
      Provider<WifiManager> wifiManagerProvider) {
    return new NetworkModule_ProvideWifiScannerFactory(wifiManagerProvider);
  }

  public static WifiScanner provideWifiScanner(WifiManager wifiManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWifiScanner(wifiManager));
  }
}

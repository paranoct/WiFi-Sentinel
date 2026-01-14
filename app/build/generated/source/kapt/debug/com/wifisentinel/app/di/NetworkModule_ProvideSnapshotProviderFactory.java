package com.wifisentinel.app.di;

import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import com.wifisentinel.core.wifi.NetworkSnapshotProvider;
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
public final class NetworkModule_ProvideSnapshotProviderFactory implements Factory<NetworkSnapshotProvider> {
  private final Provider<ConnectivityManager> connectivityManagerProvider;

  private final Provider<WifiManager> wifiManagerProvider;

  public NetworkModule_ProvideSnapshotProviderFactory(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<WifiManager> wifiManagerProvider) {
    this.connectivityManagerProvider = connectivityManagerProvider;
    this.wifiManagerProvider = wifiManagerProvider;
  }

  @Override
  public NetworkSnapshotProvider get() {
    return provideSnapshotProvider(connectivityManagerProvider.get(), wifiManagerProvider.get());
  }

  public static NetworkModule_ProvideSnapshotProviderFactory create(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<WifiManager> wifiManagerProvider) {
    return new NetworkModule_ProvideSnapshotProviderFactory(connectivityManagerProvider, wifiManagerProvider);
  }

  public static NetworkSnapshotProvider provideSnapshotProvider(
      ConnectivityManager connectivityManager, WifiManager wifiManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSnapshotProvider(connectivityManager, wifiManager));
  }
}

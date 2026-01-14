package com.wifisentinel.app.di;

import android.net.ConnectivityManager;
import com.wifisentinel.core.wifi.NetworkObserver;
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
public final class NetworkModule_ProvideNetworkObserverFactory implements Factory<NetworkObserver> {
  private final Provider<ConnectivityManager> connectivityManagerProvider;

  private final Provider<NetworkSnapshotProvider> snapshotProvider;

  public NetworkModule_ProvideNetworkObserverFactory(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider) {
    this.connectivityManagerProvider = connectivityManagerProvider;
    this.snapshotProvider = snapshotProvider;
  }

  @Override
  public NetworkObserver get() {
    return provideNetworkObserver(connectivityManagerProvider.get(), snapshotProvider.get());
  }

  public static NetworkModule_ProvideNetworkObserverFactory create(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider) {
    return new NetworkModule_ProvideNetworkObserverFactory(connectivityManagerProvider, snapshotProvider);
  }

  public static NetworkObserver provideNetworkObserver(ConnectivityManager connectivityManager,
      NetworkSnapshotProvider snapshotProvider) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNetworkObserver(connectivityManager, snapshotProvider));
  }
}

package com.wifisentinel.app.di;

import android.content.Context;
import android.net.ConnectivityManager;
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
public final class NetworkModule_ProvideConnectivityManagerFactory implements Factory<ConnectivityManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideConnectivityManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ConnectivityManager get() {
    return provideConnectivityManager(contextProvider.get());
  }

  public static NetworkModule_ProvideConnectivityManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideConnectivityManagerFactory(contextProvider);
  }

  public static ConnectivityManager provideConnectivityManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideConnectivityManager(context));
  }
}

package com.wifisentinel.app.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NetworkHealthWorker_AssistedFactory_Impl implements NetworkHealthWorker_AssistedFactory {
  private final NetworkHealthWorker_Factory delegateFactory;

  NetworkHealthWorker_AssistedFactory_Impl(NetworkHealthWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public NetworkHealthWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<NetworkHealthWorker_AssistedFactory> create(
      NetworkHealthWorker_Factory delegateFactory) {
    return InstanceFactory.create(new NetworkHealthWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<NetworkHealthWorker_AssistedFactory> createFactoryProvider(
      NetworkHealthWorker_Factory delegateFactory) {
    return InstanceFactory.create(new NetworkHealthWorker_AssistedFactory_Impl(delegateFactory));
  }
}

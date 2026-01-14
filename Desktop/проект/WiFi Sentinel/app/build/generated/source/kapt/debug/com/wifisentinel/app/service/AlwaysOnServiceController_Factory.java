package com.wifisentinel.app.service;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AlwaysOnServiceController_Factory implements Factory<AlwaysOnServiceController> {
  private final Provider<Context> contextProvider;

  public AlwaysOnServiceController_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AlwaysOnServiceController get() {
    return newInstance(contextProvider.get());
  }

  public static AlwaysOnServiceController_Factory create(Provider<Context> contextProvider) {
    return new AlwaysOnServiceController_Factory(contextProvider);
  }

  public static AlwaysOnServiceController newInstance(Context context) {
    return new AlwaysOnServiceController(context);
  }
}

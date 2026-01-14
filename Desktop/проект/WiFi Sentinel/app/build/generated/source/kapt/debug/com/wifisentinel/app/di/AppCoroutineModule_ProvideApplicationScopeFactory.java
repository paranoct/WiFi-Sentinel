package com.wifisentinel.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.wifisentinel.app.di.ApplicationScope")
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
public final class AppCoroutineModule_ProvideApplicationScopeFactory implements Factory<CoroutineScope> {
  @Override
  public CoroutineScope get() {
    return provideApplicationScope();
  }

  public static AppCoroutineModule_ProvideApplicationScopeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineScope provideApplicationScope() {
    return Preconditions.checkNotNullFromProvides(AppCoroutineModule.INSTANCE.provideApplicationScope());
  }

  private static final class InstanceHolder {
    private static final AppCoroutineModule_ProvideApplicationScopeFactory INSTANCE = new AppCoroutineModule_ProvideApplicationScopeFactory();
  }
}

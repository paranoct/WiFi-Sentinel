package com.wifisentinel.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/wifisentinel/app/viewmodel/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "backgroundScanScheduler", "Lcom/wifisentinel/app/work/BackgroundScanScheduler;", "alwaysOnServiceController", "Lcom/wifisentinel/app/service/AlwaysOnServiceController;", "(Lcom/wifisentinel/core/storage/settings/SettingsRepository;Lcom/wifisentinel/app/work/BackgroundScanScheduler;Lcom/wifisentinel/app/service/AlwaysOnServiceController;)V", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/wifisentinel/feature/settings/SettingsUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "setAlwaysOnEnabled", "", "enabled", "", "setDnsCheckEnabled", "setDohProvider", "id", "", "setMaskSensitive", "setNotificationsEnabled", "setScanInterval", "hours", "", "setThemeMode", "mode", "Lcom/wifisentinel/core/storage/settings/ThemeMode;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.work.BackgroundScanScheduler backgroundScanScheduler = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.service.AlwaysOnServiceController alwaysOnServiceController = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.settings.SettingsUiState> uiState = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.work.BackgroundScanScheduler backgroundScanScheduler, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.service.AlwaysOnServiceController alwaysOnServiceController) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.settings.SettingsUiState> getUiState() {
        return null;
    }
    
    public final void setDohProvider(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void setDnsCheckEnabled(boolean enabled) {
    }
    
    public final void setNotificationsEnabled(boolean enabled) {
    }
    
    public final void setScanInterval(int hours) {
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.ThemeMode mode) {
    }
    
    public final void setAlwaysOnEnabled(boolean enabled) {
    }
    
    public final void setMaskSensitive(boolean enabled) {
    }
}
package com.wifisentinel.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u001e\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u000fJ\u000e\u0010$\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010%\u001a\u00020\u001dJ\u0016\u0010&\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u0016J\u000e\u0010\'\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u0016J\u0006\u0010(\u001a\u00020\u001dJ\u0006\u0010)\u001a\u00020\u001dJ\u000e\u0010*\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u0014J\u000e\u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020.R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/wifisentinel/app/viewmodel/TrustedNetworksViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "trustedNetworkManager", "Lcom/wifisentinel/app/trusted/TrustedNetworkManager;", "networkMonitor", "Lcom/wifisentinel/app/monitor/NetworkMonitor;", "wifiScanner", "Lcom/wifisentinel/core/wifi/WifiScanner;", "(Lcom/wifisentinel/core/storage/NetworkRepository;Lcom/wifisentinel/app/trusted/TrustedNetworkManager;Lcom/wifisentinel/app/monitor/NetworkMonitor;Lcom/wifisentinel/core/wifi/WifiScanner;)V", "errorMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isScanning", "", "pendingAdd", "Lcom/wifisentinel/feature/trusted/TrustedAddCandidate;", "scanResults", "", "Lcom/wifisentinel/core/wifi/ScanNet;", "selectedCategory", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/wifisentinel/feature/trusted/TrustedUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptFingerprint", "", "profileId", "", "confirmAdd", "candidate", "category", "meshMode", "deleteProfile", "dismissSheets", "moveProfile", "onTabSelected", "requestAddCurrent", "requestAddFromScan", "selectScanCandidate", "net", "updateProfile", "profile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class TrustedNetworksViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.trusted.TrustedNetworkManager trustedNetworkManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.monitor.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.WifiScanner wifiScanner = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wifisentinel.core.wifi.NetworkCategory> selectedCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.wifisentinel.core.wifi.ScanNet>> scanResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wifisentinel.feature.trusted.TrustedAddCandidate> pendingAdd = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> isScanning = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.trusted.TrustedUiState> uiState = null;
    
    @javax.inject.Inject()
    public TrustedNetworksViewModel(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.trusted.TrustedNetworkManager trustedNetworkManager, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.monitor.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.WifiScanner wifiScanner) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.trusted.TrustedUiState> getUiState() {
        return null;
    }
    
    public final void onTabSelected(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category) {
    }
    
    public final void requestAddCurrent() {
    }
    
    public final void requestAddFromScan() {
    }
    
    public final void selectScanCandidate(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.ScanNet net) {
    }
    
    public final void dismissSheets() {
    }
    
    public final void confirmAdd(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.feature.trusted.TrustedAddCandidate candidate, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode) {
    }
    
    public final void moveProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category) {
    }
    
    public final void deleteProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId) {
    }
    
    public final void acceptFingerprint(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId) {
    }
    
    public final void updateProfile(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.TrustedNetworkProfile profile) {
    }
}
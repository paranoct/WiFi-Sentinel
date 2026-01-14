package com.wifisentinel.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\f\b\u0007\u0018\u0000 Z2\u00020\u0001:\u0003Z[\\BI\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\u0006\u0010+\u001a\u00020,J\u0018\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.2\b\u00100\u001a\u0004\u0018\u000101H\u0002J \u00102\u001a\u0002032\f\u00104\u001a\b\u0012\u0004\u0012\u0002010.2\b\u00105\u001a\u0004\u0018\u000106H\u0002J2\u00107\u001a\u0002082\u0006\u00105\u001a\u0002062\b\u00109\u001a\u0004\u0018\u0001062\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0.2\b\u0010<\u001a\u0004\u0018\u00010=H\u0002J&\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002010.2\u0006\u0010B\u001a\u00020?H\u0002J\u0017\u0010C\u001a\u00020\u001c2\b\u0010D\u001a\u0004\u0018\u00010?H\u0002\u00a2\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u0004\u0018\u00010?2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002010.H\u0002\u00a2\u0006\u0002\u0010GJ\u001d\u0010H\u001a\u0004\u0018\u00010?2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002010.H\u0002\u00a2\u0006\u0002\u0010GJ\u0006\u0010I\u001a\u00020,J\u0006\u0010J\u001a\u00020,J\u0019\u0010K\u001a\u0004\u0018\u00010?2\b\u0010L\u001a\u0004\u0018\u00010?H\u0002\u00a2\u0006\u0002\u0010MJ\u0010\u0010N\u001a\u00020,2\u0006\u00105\u001a\u000206H\u0002J \u0010O\u001a\u00020,2\b\u00105\u001a\u0004\u0018\u0001062\u0006\u0010P\u001a\u00020QH\u0082@\u00a2\u0006\u0002\u0010RJ\"\u0010S\u001a\u0004\u0018\u0001012\f\u00104\u001a\b\u0012\u0004\u0012\u0002010.2\b\u00105\u001a\u0004\u0018\u000106H\u0002J\u0006\u0010P\u001a\u00020,J\u0017\u0010T\u001a\u00020?2\b\u0010U\u001a\u0004\u0018\u00010?H\u0002\u00a2\u0006\u0002\u0010VJ\u0006\u0010W\u001a\u00020,J\u0017\u0010X\u001a\u00020\u001c2\b\u0010Y\u001a\u0004\u0018\u00010?H\u0002\u00a2\u0006\u0002\u0010ER\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00150!\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00180(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006]"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "riskEngine", "Lcom/wifisentinel/core/risk/RiskEngine;", "trustedNetworkManager", "Lcom/wifisentinel/app/trusted/TrustedNetworkManager;", "networkMonitor", "Lcom/wifisentinel/app/monitor/NetworkMonitor;", "reportExporter", "Lcom/wifisentinel/app/report/ReportExporter;", "wifiScanner", "Lcom/wifisentinel/core/wifi/WifiScanner;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "(Landroid/content/Context;Lcom/wifisentinel/core/storage/NetworkRepository;Lcom/wifisentinel/core/risk/RiskEngine;Lcom/wifisentinel/app/trusted/TrustedNetworkManager;Lcom/wifisentinel/app/monitor/NetworkMonitor;Lcom/wifisentinel/app/report/ReportExporter;Lcom/wifisentinel/core/wifi/WifiScanner;Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "_reportEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent;", "baseStateFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/wifisentinel/feature/dashboard/DashboardUiState;", "lastAutoScanMs", "", "lastAutoScanNetworkId", "", "lastRiskSummary", "Lcom/wifisentinel/core/risk/RiskSummary;", "lastSnapshotKey", "reportEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getReportEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "scanState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ScanState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addCurrentToTrusted", "", "buildCurrentSections", "", "Lcom/wifisentinel/feature/dashboard/RouterSettingsSection;", "currentNet", "Lcom/wifisentinel/core/wifi/ScanNet;", "buildRecommendations", "Lcom/wifisentinel/feature/dashboard/RouterRecommendations;", "scanResults", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "buildSecurityLabel", "Lcom/wifisentinel/feature/dashboard/SecurityNutritionLabelState;", "previous", "findings", "Lcom/wifisentinel/core/detectors/Finding;", "trustedProfile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "channelInterferenceScore", "", "candidate", "nets", "maxDistance", "channelWidthLabel", "channelWidth", "(Ljava/lang/Integer;)Ljava/lang/String;", "choose24Channel", "(Ljava/util/List;)Ljava/lang/Integer;", "choose5Channel", "exitDemoMode", "exportReport", "frequencyToChannel", "frequencyMhz", "(Ljava/lang/Integer;)Ljava/lang/Integer;", "maybeAutoScan", "performScan", "refreshSnapshot", "", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pickCurrentNetwork", "rssiWeight", "rssiDbm", "(Ljava/lang/Integer;)I", "scanNow", "wifiStandardLabel", "standard", "Companion", "ReportEvent", "ScanState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.risk.RiskEngine riskEngine = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.trusted.TrustedNetworkManager trustedNetworkManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.monitor.NetworkMonitor networkMonitor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.report.ReportExporter reportExporter = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.WifiScanner wifiScanner = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private com.wifisentinel.core.risk.RiskSummary lastRiskSummary;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastSnapshotKey;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastAutoScanNetworkId;
    private long lastAutoScanMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.wifisentinel.feature.dashboard.DashboardUiState> baseStateFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.wifisentinel.app.viewmodel.DashboardViewModel.ScanState> scanState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.dashboard.DashboardUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent> _reportEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent> reportEvents = null;
    @java.lang.Deprecated()
    public static final long AUTO_SCAN_THROTTLE_MS = 5000L;
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.viewmodel.DashboardViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.risk.RiskEngine riskEngine, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.trusted.TrustedNetworkManager trustedNetworkManager, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.monitor.NetworkMonitor networkMonitor, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.report.ReportExporter reportExporter, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.WifiScanner wifiScanner, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.dashboard.DashboardUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent> getReportEvents() {
        return null;
    }
    
    public final void exportReport() {
    }
    
    public final void addCurrentToTrusted() {
    }
    
    public final void refreshSnapshot() {
    }
    
    public final void scanNow() {
    }
    
    public final void exitDemoMode() {
    }
    
    private final com.wifisentinel.feature.dashboard.RouterRecommendations buildRecommendations(java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults, com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.Integer choose24Channel(java.util.List<com.wifisentinel.core.wifi.ScanNet> nets) {
        return null;
    }
    
    private final java.lang.Integer choose5Channel(java.util.List<com.wifisentinel.core.wifi.ScanNet> nets) {
        return null;
    }
    
    private final java.lang.Integer frequencyToChannel(java.lang.Integer frequencyMhz) {
        return null;
    }
    
    private final int rssiWeight(java.lang.Integer rssiDbm) {
        return 0;
    }
    
    private final int channelInterferenceScore(int candidate, java.util.List<com.wifisentinel.core.wifi.ScanNet> nets, int maxDistance) {
        return 0;
    }
    
    private final com.wifisentinel.core.wifi.ScanNet pickCurrentNetwork(java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults, com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.feature.dashboard.RouterSettingsSection> buildCurrentSections(com.wifisentinel.core.wifi.ScanNet currentNet) {
        return null;
    }
    
    private final java.lang.String wifiStandardLabel(java.lang.Integer standard) {
        return null;
    }
    
    private final java.lang.String channelWidthLabel(java.lang.Integer channelWidth) {
        return null;
    }
    
    private final void maybeAutoScan(com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
    }
    
    private final java.lang.Object performScan(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, boolean refreshSnapshot, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.wifisentinel.feature.dashboard.SecurityNutritionLabelState buildSecurityLabel(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, com.wifisentinel.core.wifi.NetworkSnapshot previous, java.util.List<com.wifisentinel.core.detectors.Finding> findings, com.wifisentinel.core.wifi.TrustedNetworkProfile trustedProfile) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel$Companion;", "", "()V", "AUTO_SCAN_THROTTLE_MS", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent;", "", "Error", "Share", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent$Error;", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent$Share;", "app_debug"})
    public static abstract interface ReportEvent {
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent$Error;", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent;", "messageResId", "", "(I)V", "getMessageResId", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
        public static final class Error implements com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent {
            private final int messageResId = 0;
            
            public Error(int messageResId) {
                super();
            }
            
            public final int getMessageResId() {
                return 0;
            }
            
            public final int component1() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent.Error copy(int messageResId) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent$Share;", "Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ReportEvent;", "uri", "Landroid/net/Uri;", "(Landroid/net/Uri;)V", "getUri", "()Landroid/net/Uri;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Share implements com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent {
            @org.jetbrains.annotations.NotNull()
            private final android.net.Uri uri = null;
            
            public Share(@org.jetbrains.annotations.NotNull()
            android.net.Uri uri) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.net.Uri getUri() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.net.Uri component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.wifisentinel.app.viewmodel.DashboardViewModel.ReportEvent.Share copy(@org.jetbrains.annotations.NotNull()
            android.net.Uri uri) {
                return null;
            }
            
            @java.lang.Override()
            public boolean equals(@org.jetbrains.annotations.Nullable()
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override()
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override()
            @org.jetbrains.annotations.NotNull()
            public java.lang.String toString() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J.\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001b"}, d2 = {"Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ScanState;", "", "inProgress", "", "lastScanMs", "", "recommendations", "Lcom/wifisentinel/feature/dashboard/RouterRecommendations;", "(ZLjava/lang/Long;Lcom/wifisentinel/feature/dashboard/RouterRecommendations;)V", "getInProgress", "()Z", "getLastScanMs", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getRecommendations", "()Lcom/wifisentinel/feature/dashboard/RouterRecommendations;", "component1", "component2", "component3", "copy", "(ZLjava/lang/Long;Lcom/wifisentinel/feature/dashboard/RouterRecommendations;)Lcom/wifisentinel/app/viewmodel/DashboardViewModel$ScanState;", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class ScanState {
        private final boolean inProgress = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long lastScanMs = null;
        @org.jetbrains.annotations.NotNull()
        private final com.wifisentinel.feature.dashboard.RouterRecommendations recommendations = null;
        
        public ScanState(boolean inProgress, @org.jetbrains.annotations.Nullable()
        java.lang.Long lastScanMs, @org.jetbrains.annotations.NotNull()
        com.wifisentinel.feature.dashboard.RouterRecommendations recommendations) {
            super();
        }
        
        public final boolean getInProgress() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getLastScanMs() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.feature.dashboard.RouterRecommendations getRecommendations() {
            return null;
        }
        
        public ScanState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.feature.dashboard.RouterRecommendations component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.app.viewmodel.DashboardViewModel.ScanState copy(boolean inProgress, @org.jetbrains.annotations.Nullable()
        java.lang.Long lastScanMs, @org.jetbrains.annotations.NotNull()
        com.wifisentinel.feature.dashboard.RouterRecommendations recommendations) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}
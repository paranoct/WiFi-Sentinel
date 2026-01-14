package com.wifisentinel.app.monitor;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00b4\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 Y2\u00020\u0001:\u0001YB^\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0011\u0010\f\u001a\r\u0012\t\u0012\u00070\u000e\u00a2\u0006\u0002\b\u000f0\r\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0001\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0002J.\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\r2\u0006\u0010%\u001a\u00020&2\b\u0010)\u001a\u0004\u0018\u00010&2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020$0\rH\u0002J\b\u0010+\u001a\u00020,H\u0002J$\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\r2\u0006\u0010%\u001a\u00020&2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020.0\rH\u0002J*\u00100\u001a\b\u0012\u0004\u0012\u00020$0\r2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020$0\r2\u0006\u00101\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u00102J \u00103\u001a\u0004\u0018\u0001042\u0006\u0010%\u001a\u00020&2\f\u00105\u001a\b\u0012\u0004\u0012\u0002040\rH\u0002J\u0010\u00106\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$H\u0002J\u001c\u00107\u001a\u00020\u001d2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d09H\u0002J\u0010\u0010:\u001a\u00020,2\u0006\u0010%\u001a\u00020&H\u0002J.\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u0001042\u0006\u0010%\u001a\u00020&2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020&0\rH\u0082@\u00a2\u0006\u0002\u0010?J\u001e\u0010@\u001a\b\u0012\u0004\u0012\u00020.0\r2\b\b\u0002\u0010A\u001a\u00020,H\u0082@\u00a2\u0006\u0002\u0010BJ\u0010\u0010C\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&H\u0002J$\u0010D\u001a\u00020<2\u0006\u0010%\u001a\u00020&2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020$0\rH\u0082@\u00a2\u0006\u0002\u0010EJ,\u0010F\u001a\u00020<2\u0006\u0010%\u001a\u00020&2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020$0\r2\u0006\u0010A\u001a\u00020,H\u0082@\u00a2\u0006\u0002\u0010GJ \u0010H\u001a\u00020<2\u0006\u0010%\u001a\u00020&2\b\b\u0002\u0010A\u001a\u00020,H\u0082@\u00a2\u0006\u0002\u0010IJ\u0010\u0010J\u001a\u00020<2\b\b\u0002\u0010A\u001a\u00020,J\u0018\u0010K\u001a\u00020,2\b\b\u0002\u0010A\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010BJ\u0016\u0010L\u001a\u00020\u001d2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020$0\rH\u0002J\u0010\u0010M\u001a\u00020\u001d2\u0006\u0010N\u001a\u00020OH\u0002J \u0010P\u001a\u00020,2\u0006\u0010Q\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001a2\u0006\u0010R\u001a\u00020\u001aH\u0002J\u0017\u0010S\u001a\u00020\u001d2\b\u0010T\u001a\u0004\u0018\u00010UH\u0002\u00a2\u0006\u0002\u0010VJ\u0010\u0010W\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&H\u0002J\u0006\u0010X\u001a\u00020<R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\r\u0012\t\u0012\u00070\u000e\u00a2\u0006\u0002\b\u000f0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001a0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006Z"}, d2 = {"Lcom/wifisentinel/app/monitor/NetworkMonitor;", "", "context", "Landroid/content/Context;", "networkObserver", "Lcom/wifisentinel/core/wifi/NetworkObserver;", "snapshotProvider", "Lcom/wifisentinel/core/wifi/NetworkSnapshotProvider;", "wifiScanner", "Lcom/wifisentinel/core/wifi/WifiScanner;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "detectors", "", "Lcom/wifisentinel/core/detectors/Detector;", "Lkotlin/jvm/JvmSuppressWildcards;", "notificationHelper", "Lcom/wifisentinel/app/notifications/NotificationHelper;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "appScope", "Lkotlinx/coroutines/CoroutineScope;", "(Landroid/content/Context;Lcom/wifisentinel/core/wifi/NetworkObserver;Lcom/wifisentinel/core/wifi/NetworkSnapshotProvider;Lcom/wifisentinel/core/wifi/WifiScanner;Lcom/wifisentinel/core/storage/NetworkRepository;Ljava/util/List;Lcom/wifisentinel/app/notifications/NotificationHelper;Lcom/wifisentinel/core/storage/settings/SettingsRepository;Lkotlinx/coroutines/CoroutineScope;)V", "hasConnectedOnce", "Ljava/util/concurrent/atomic/AtomicBoolean;", "lastAlertMs", "", "lastEventTimestamps", "", "", "lastScanMs", "processingMutex", "Lkotlinx/coroutines/sync/Mutex;", "started", "buildDedupKey", "finding", "Lcom/wifisentinel/core/detectors/Finding;", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "buildEvents", "Lcom/wifisentinel/core/storage/NetworkEvent;", "previous", "findings", "canRecordEvents", "", "ensureCurrentNetworkScan", "Lcom/wifisentinel/core/wifi/ScanNet;", "scanResults", "filterFindingsForCooldown", "now", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findTrustedProfile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "profiles", "friendlyFindingDetail", "hashEvidence", "evidence", "", "isConnectedSnapshot", "maybeLearnBssid", "", "trustedProfile", "history", "(Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;Lcom/wifisentinel/core/wifi/NetworkSnapshot;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "maybeScan", "force", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "normalizedNetworkKey", "notifyIfNeeded", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "notifyNetworkReport", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Ljava/util/List;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processSnapshot", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshSnapshot", "runHealthCheck", "safetyLabel", "securityLabel", "type", "Lcom/wifisentinel/core/wifi/SecurityType;", "shouldLogEvent", "key", "throttleMs", "signalLabel", "rssiDbm", "", "(Ljava/lang/Integer;)Ljava/lang/String;", "snapshotKey", "start", "Companion", "app_debug"})
public final class NetworkMonitor {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.NetworkObserver networkObserver = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.NetworkSnapshotProvider snapshotProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.WifiScanner wifiScanner = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wifisentinel.core.detectors.Detector> detectors = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.notifications.NotificationHelper notificationHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope appScope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean started = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex processingMutex = null;
    private long lastScanMs = 0L;
    private long lastAlertMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Long> lastEventTimestamps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean hasConnectedOnce = null;
    @java.lang.Deprecated()
    public static final long ALERT_THROTTLE_MS = 120000L;
    @java.lang.Deprecated()
    public static final long EVENT_THROTTLE_MS = 600000L;
    @java.lang.Deprecated()
    public static final long FINDING_THROTTLE_MS = 1800000L;
    @java.lang.Deprecated()
    public static final long FINDING_DEDUP_THROTTLE_MS = 600000L;
    @java.lang.Deprecated()
    public static final long CONNECT_THROTTLE_MS = 120000L;
    @java.lang.Deprecated()
    public static final long REPORT_THROTTLE_MS = 60000L;
    @java.lang.Deprecated()
    public static final long CONNECT_SCAN_THROTTLE_MS = 5000L;
    @java.lang.Deprecated()
    public static final long RECONNECT_SCAN_THRESHOLD_MS = 1000L;
    @java.lang.Deprecated()
    public static final long FORCE_SCAN_THROTTLE_MS = 10000L;
    @java.lang.Deprecated()
    public static final long RECONNECT_REPORT_THROTTLE_MS = 10000L;
    @java.lang.Deprecated()
    public static final long ONE_DAY_MS = 86400000L;
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.monitor.NetworkMonitor.Companion Companion = null;
    
    @javax.inject.Inject()
    public NetworkMonitor(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkObserver networkObserver, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshotProvider snapshotProvider, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.WifiScanner wifiScanner, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.detectors.Detector> detectors, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.notifications.NotificationHelper notificationHelper, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository, @com.wifisentinel.app.di.ApplicationScope()
    @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope appScope) {
        super();
    }
    
    public final void start() {
    }
    
    public final void refreshSnapshot(boolean force) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object runHealthCheck(boolean force, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.String snapshotKey(com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.Object processSnapshot(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, boolean force, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object filterFindingsForCooldown(java.util.List<com.wifisentinel.core.detectors.Finding> findings, long now, kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.detectors.Finding>> $completion) {
        return null;
    }
    
    private final java.lang.String buildDedupKey(com.wifisentinel.core.detectors.Finding finding, com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.String normalizedNetworkKey(com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.Object maybeLearnBssid(com.wifisentinel.core.wifi.TrustedNetworkProfile trustedProfile, com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot> history, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String hashEvidence(java.util.Map<java.lang.String, java.lang.String> evidence) {
        return null;
    }
    
    private final java.lang.Object maybeScan(boolean force, kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.ScanNet>> $completion) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.storage.NetworkEvent> buildEvents(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, com.wifisentinel.core.wifi.NetworkSnapshot previous, java.util.List<com.wifisentinel.core.detectors.Finding> findings) {
        return null;
    }
    
    private final java.lang.Object notifyIfNeeded(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.detectors.Finding> findings, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object notifyNetworkReport(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.detectors.Finding> findings, boolean force, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final boolean canRecordEvents() {
        return false;
    }
    
    private final com.wifisentinel.core.wifi.TrustedNetworkProfile findTrustedProfile(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> profiles) {
        return null;
    }
    
    private final boolean isConnectedSnapshot(com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return false;
    }
    
    private final boolean shouldLogEvent(java.lang.String key, long now, long throttleMs) {
        return false;
    }
    
    private final java.lang.String securityLabel(com.wifisentinel.core.wifi.SecurityType type) {
        return null;
    }
    
    private final java.lang.String safetyLabel(java.util.List<com.wifisentinel.core.detectors.Finding> findings) {
        return null;
    }
    
    private final java.lang.String signalLabel(java.lang.Integer rssiDbm) {
        return null;
    }
    
    private final java.lang.String friendlyFindingDetail(com.wifisentinel.core.detectors.Finding finding) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.wifi.ScanNet> ensureCurrentNetworkScan(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/wifisentinel/app/monitor/NetworkMonitor$Companion;", "", "()V", "ALERT_THROTTLE_MS", "", "CONNECT_SCAN_THROTTLE_MS", "CONNECT_THROTTLE_MS", "EVENT_THROTTLE_MS", "FINDING_DEDUP_THROTTLE_MS", "FINDING_THROTTLE_MS", "FORCE_SCAN_THROTTLE_MS", "ONE_DAY_MS", "RECONNECT_REPORT_THROTTLE_MS", "RECONNECT_SCAN_THRESHOLD_MS", "REPORT_THROTTLE_MS", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
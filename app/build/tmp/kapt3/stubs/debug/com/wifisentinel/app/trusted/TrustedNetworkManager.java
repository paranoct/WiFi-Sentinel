package com.wifisentinel.app.trusted;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ&\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0017J0\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u001e\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/wifisentinel/app/trusted/TrustedNetworkManager;", "", "context", "Landroid/content/Context;", "snapshotProvider", "Lcom/wifisentinel/core/wifi/NetworkSnapshotProvider;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "(Landroid/content/Context;Lcom/wifisentinel/core/wifi/NetworkSnapshotProvider;Lcom/wifisentinel/core/storage/NetworkRepository;)V", "acceptCurrentFingerprint", "", "profileId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addFromScan", "scanNet", "Lcom/wifisentinel/core/wifi/ScanNet;", "category", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "meshMode", "(Lcom/wifisentinel/core/wifi/ScanNet;Lcom/wifisentinel/core/wifi/NetworkCategory;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOrUpdateCurrent", "allowCreate", "(Lcom/wifisentinel/core/wifi/NetworkCategory;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOrUpdateSnapshot", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Lcom/wifisentinel/core/wifi/NetworkCategory;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "defaultMaxNewBssidPerDay", "", "refreshCurrentTrusted", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TrustedNetworkManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.NetworkSnapshotProvider snapshotProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    
    @javax.inject.Inject()
    public TrustedNetworkManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshotProvider snapshotProvider, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateCurrent(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode, boolean allowCreate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateSnapshot(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode, boolean allowCreate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addFromScan(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.ScanNet scanNet, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object acceptCurrentFingerprint(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshCurrentTrusted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final int defaultMaxNewBssidPerDay(com.wifisentinel.core.wifi.NetworkCategory category) {
        return 0;
    }
}
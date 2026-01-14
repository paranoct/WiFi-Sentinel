package com.wifisentinel.core.storage;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u00162\u0006\u0010\u0019\u001a\u00020\u000eH\u0016J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00170\u00162\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010 J\u0018\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u00162\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001c\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010 J\u0010\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0016H\u0016J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0013H\u0096@\u00a2\u0006\u0002\u0010(J\u001c\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010 J\u001e\u0010*\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020,H\u0096@\u00a2\u0006\u0002\u0010-J$\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\u0006\u0010/\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u00100J\u001c\u00101\u001a\u00020\f2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0017H\u0096@\u00a2\u0006\u0002\u00103J$\u00104\u001a\u00020\f2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u00106\u001a\u00020\"H\u0096@\u00a2\u0006\u0002\u00107J\u0016\u00108\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010\u0014J\u0014\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00170\u0016H\u0016J\u001c\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00170\u00162\u0006\u0010+\u001a\u00020,H\u0016J\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017H\u0096@\u00a2\u0006\u0002\u0010(J\u001c\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00110\u00172\u0006\u0010+\u001a\u00020,H\u0096@\u00a2\u0006\u0002\u0010;J\u0016\u0010<\u001a\u00020\f2\u0006\u0010=\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010>R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/wifisentinel/core/storage/DefaultNetworkRepository;", "Lcom/wifisentinel/core/storage/NetworkRepository;", "snapshotDao", "Lcom/wifisentinel/core/storage/db/dao/SnapshotDao;", "findingDao", "Lcom/wifisentinel/core/storage/db/dao/FindingDao;", "trustedNetworkDao", "Lcom/wifisentinel/core/storage/db/dao/TrustedNetworkDao;", "eventDao", "Lcom/wifisentinel/core/storage/db/dao/EventDao;", "(Lcom/wifisentinel/core/storage/db/dao/SnapshotDao;Lcom/wifisentinel/core/storage/db/dao/FindingDao;Lcom/wifisentinel/core/storage/db/dao/TrustedNetworkDao;Lcom/wifisentinel/core/storage/db/dao/EventDao;)V", "deleteTrustedProfile", "", "profileId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findTrustedProfile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findingsForSnapshot", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wifisentinel/core/detectors/Finding;", "snapshotId", "findingsForSnapshotOnce", "latestEvents", "Lcom/wifisentinel/core/storage/NetworkEvent;", "limit", "", "latestEventsOnce", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latestFindingTimestamp", "", "dedupKey", "latestFindings", "latestFindingsOnce", "latestSnapshot", "latestSnapshotOnce", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latestSnapshotsOnce", "moveTrustedProfile", "category", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "(Ljava/lang/String;Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recentSnapshots", "networkIdHint", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveEvents", "events", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFindings", "findings", "timestampMs", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSnapshot", "trustedProfiles", "trustedProfilesOnce", "(Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertTrustedProfile", "profile", "(Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "storage_debug"})
public final class DefaultNetworkRepository implements com.wifisentinel.core.storage.NetworkRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.db.dao.SnapshotDao snapshotDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.db.dao.FindingDao findingDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.db.dao.TrustedNetworkDao trustedNetworkDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.db.dao.EventDao eventDao = null;
    
    public DefaultNetworkRepository(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.db.dao.SnapshotDao snapshotDao, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.db.dao.FindingDao findingDao, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.db.dao.TrustedNetworkDao trustedNetworkDao, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.db.dao.EventDao eventDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.wifisentinel.core.wifi.NetworkSnapshot> latestSnapshot() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object latestSnapshotOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wifisentinel.core.wifi.NetworkSnapshot> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.detectors.Finding>> latestFindings(int limit) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.detectors.Finding>> findingsForSnapshot(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.NetworkEvent>> latestEvents(int limit) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> trustedProfiles() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> trustedProfiles(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object trustedProfilesOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object trustedProfilesOnce(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object findTrustedProfile(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wifisentinel.core.wifi.TrustedNetworkProfile> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object latestSnapshotsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object latestFindingsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.detectors.Finding>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object latestEventsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.NetworkEvent>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object findingsForSnapshotOnce(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.detectors.Finding>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object latestFindingTimestamp(@org.jetbrains.annotations.NotNull()
    java.lang.String dedupKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveSnapshot(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveFindings(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.detectors.Finding> findings, long timestampMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.storage.NetworkEvent> events, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object upsertTrustedProfile(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.TrustedNetworkProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object moveTrustedProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteTrustedProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object recentSnapshots(@org.jetbrains.annotations.NotNull()
    java.lang.String networkIdHint, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot>> $completion) {
        return null;
    }
}
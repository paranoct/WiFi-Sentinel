package com.wifisentinel.core.storage;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0013\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r2\u0006\u0010\u0010\u001a\u00020\u0005H&J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u000e0\r2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H&J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H&J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\rH&J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\nH\u00a6@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010!\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#H\u00a6@\u00a2\u0006\u0002\u0010$J&\u0010%\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\u0006\u0010&\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\'J\u001c\u0010(\u001a\u00020\u00032\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00130\u000eH\u00a6@\u00a2\u0006\u0002\u0010*J$\u0010+\u001a\u00020\u00032\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010-\u001a\u00020\u0019H\u00a6@\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\rH&J\u001c\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\r2\u0006\u0010\"\u001a\u00020#H&J\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u001fJ\u001c\u00101\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0006\u0010\"\u001a\u00020#H\u00a6@\u00a2\u0006\u0002\u00102J\u0016\u00103\u001a\u00020\u00032\u0006\u00104\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u00105\u00a8\u00066"}, d2 = {"Lcom/wifisentinel/core/storage/NetworkRepository;", "", "deleteTrustedProfile", "", "profileId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findTrustedProfile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findingsForSnapshot", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wifisentinel/core/detectors/Finding;", "snapshotId", "findingsForSnapshotOnce", "latestEvents", "Lcom/wifisentinel/core/storage/NetworkEvent;", "limit", "", "latestEventsOnce", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latestFindingTimestamp", "", "dedupKey", "latestFindings", "latestFindingsOnce", "latestSnapshot", "latestSnapshotOnce", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latestSnapshotsOnce", "moveTrustedProfile", "category", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "(Ljava/lang/String;Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recentSnapshots", "networkIdHint", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveEvents", "events", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFindings", "findings", "timestampMs", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSnapshot", "trustedProfiles", "trustedProfilesOnce", "(Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertTrustedProfile", "profile", "(Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "storage_debug"})
public abstract interface NetworkRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.wifisentinel.core.wifi.NetworkSnapshot> latestSnapshot();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestSnapshotOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wifisentinel.core.wifi.NetworkSnapshot> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.detectors.Finding>> latestFindings(int limit);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.detectors.Finding>> findingsForSnapshot(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.NetworkEvent>> latestEvents(int limit);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> trustedProfiles();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> trustedProfiles(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object trustedProfilesOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object trustedProfilesOnce(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findTrustedProfile(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wifisentinel.core.wifi.TrustedNetworkProfile> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestSnapshotsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestFindingsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.detectors.Finding>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestEventsOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.NetworkEvent>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findingsForSnapshotOnce(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.detectors.Finding>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestFindingTimestamp(@org.jetbrains.annotations.NotNull()
    java.lang.String dedupKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveSnapshot(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveFindings(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.detectors.Finding> findings, long timestampMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.storage.NetworkEvent> events, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsertTrustedProfile(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.TrustedNetworkProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object moveTrustedProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTrustedProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object recentSnapshots(@org.jetbrains.annotations.NotNull()
    java.lang.String networkIdHint, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}
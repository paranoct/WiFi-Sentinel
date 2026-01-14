package com.wifisentinel.core.storage.db.entity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b)\b\u0087\b\u0018\u00002\u00020\u0001B\u0087\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u000b\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000b\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\t\u00a2\u0006\u0002\u0010\u0018J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0013H\u00c6\u0003J\t\u0010/\u001a\u00020\u0013H\u00c6\u0003J\t\u00100\u001a\u00020\u0016H\u00c6\u0003J\t\u00101\u001a\u00020\tH\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0007H\u00c6\u0003J\t\u00105\u001a\u00020\tH\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u000f\u00107\u001a\b\u0012\u0004\u0012\u00020\r0\u000bH\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000bH\u00c6\u0003J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011H\u00c6\u0003J\u00a5\u0001\u0010:\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u000b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000b2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\tH\u00c6\u0001J\u0013\u0010;\u001a\u00020\t2\b\u0010<\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010=\u001a\u00020\u0016H\u00d6\u0001J\t\u0010>\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0017\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0011\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\"\u00a8\u0006?"}, d2 = {"Lcom/wifisentinel/core/storage/db/entity/TrustedNetworkProfileEntity;", "", "id", "", "displayName", "ssid", "category", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "meshMode", "", "allowedBssids", "", "expectedSecurity", "Lcom/wifisentinel/core/wifi/SecurityType;", "expectedFreqBands", "Lcom/wifisentinel/core/wifi/Band;", "pinnedDns", "", "createdAtMs", "", "lastSeenMs", "maxNewBssidPerDay", "", "bssidLearning", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/wifisentinel/core/wifi/NetworkCategory;ZLjava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/List;JJIZ)V", "getAllowedBssids", "()Ljava/util/Set;", "getBssidLearning", "()Z", "getCategory", "()Lcom/wifisentinel/core/wifi/NetworkCategory;", "getCreatedAtMs", "()J", "getDisplayName", "()Ljava/lang/String;", "getExpectedFreqBands", "getExpectedSecurity", "getId", "getLastSeenMs", "getMaxNewBssidPerDay", "()I", "getMeshMode", "getPinnedDns", "()Ljava/util/List;", "getSsid", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "storage_debug"})
@androidx.room.Entity(tableName = "trusted_networks", indices = {@androidx.room.Index(value = {"category"})})
public final class TrustedNetworkProfileEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ssid = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.NetworkCategory category = null;
    private final boolean meshMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> allowedBssids = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.wifisentinel.core.wifi.SecurityType> expectedSecurity = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.wifisentinel.core.wifi.Band> expectedFreqBands = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> pinnedDns = null;
    private final long createdAtMs = 0L;
    private final long lastSeenMs = 0L;
    private final int maxNewBssidPerDay = 0;
    private final boolean bssidLearning = false;
    
    public TrustedNetworkProfileEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.Nullable()
    java.lang.String ssid, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> allowedBssids, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.wifisentinel.core.wifi.SecurityType> expectedSecurity, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.wifisentinel.core.wifi.Band> expectedFreqBands, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> pinnedDns, long createdAtMs, long lastSeenMs, int maxNewBssidPerDay, boolean bssidLearning) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSsid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkCategory getCategory() {
        return null;
    }
    
    public final boolean getMeshMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getAllowedBssids() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.SecurityType> getExpectedSecurity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.Band> getExpectedFreqBands() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPinnedDns() {
        return null;
    }
    
    public final long getCreatedAtMs() {
        return 0L;
    }
    
    public final long getLastSeenMs() {
        return 0L;
    }
    
    public final int getMaxNewBssidPerDay() {
        return 0;
    }
    
    public final boolean getBssidLearning() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkCategory component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.SecurityType> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.Band> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.Nullable()
    java.lang.String ssid, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, boolean meshMode, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> allowedBssids, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.wifisentinel.core.wifi.SecurityType> expectedSecurity, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.wifisentinel.core.wifi.Band> expectedFreqBands, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> pinnedDns, long createdAtMs, long lastSeenMs, int maxNewBssidPerDay, boolean bssidLearning) {
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
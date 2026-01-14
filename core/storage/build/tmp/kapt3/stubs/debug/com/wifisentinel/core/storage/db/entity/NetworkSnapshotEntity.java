package com.wifisentinel.core.storage.db.entity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b(\b\u0087\b\u0018\u00002\u00020\u0001Bw\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u00c6\u0003J\t\u0010*\u001a\u00020\u0012H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\tH\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u0010\u00101\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001cJ\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0098\u0001\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u00105J\u0013\u00106\u001a\u00020\u00122\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u00020\u000bH\u00d6\u0001J\t\u00109\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0015\u0010\f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u001d\u001a\u0004\b\"\u0010\u001cR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'\u00a8\u0006:"}, d2 = {"Lcom/wifisentinel/core/storage/db/entity/NetworkSnapshotEntity;", "", "id", "", "timestampMs", "", "ssid", "bssid", "securityType", "Lcom/wifisentinel/core/wifi/SecurityType;", "frequencyMhz", "", "rssiDbm", "ipV4", "gatewayV4", "dnsServers", "", "captivePortal", "", "networkIdHint", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Lcom/wifisentinel/core/wifi/SecurityType;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;)V", "getBssid", "()Ljava/lang/String;", "getCaptivePortal", "()Z", "getDnsServers", "()Ljava/util/List;", "getFrequencyMhz", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getGatewayV4", "getId", "getIpV4", "getNetworkIdHint", "getRssiDbm", "getSecurityType", "()Lcom/wifisentinel/core/wifi/SecurityType;", "getSsid", "getTimestampMs", "()J", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Lcom/wifisentinel/core/wifi/SecurityType;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;)Lcom/wifisentinel/core/storage/db/entity/NetworkSnapshotEntity;", "equals", "other", "hashCode", "toString", "storage_debug"})
@androidx.room.Entity(tableName = "network_snapshots")
public final class NetworkSnapshotEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    private final long timestampMs = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ssid = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bssid = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.SecurityType securityType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer frequencyMhz = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer rssiDbm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ipV4 = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String gatewayV4 = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> dnsServers = null;
    private final boolean captivePortal = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String networkIdHint = null;
    
    public NetworkSnapshotEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, long timestampMs, @org.jetbrains.annotations.Nullable()
    java.lang.String ssid, @org.jetbrains.annotations.Nullable()
    java.lang.String bssid, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.SecurityType securityType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer frequencyMhz, @org.jetbrains.annotations.Nullable()
    java.lang.Integer rssiDbm, @org.jetbrains.annotations.Nullable()
    java.lang.String ipV4, @org.jetbrains.annotations.Nullable()
    java.lang.String gatewayV4, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> dnsServers, boolean captivePortal, @org.jetbrains.annotations.NotNull()
    java.lang.String networkIdHint) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final long getTimestampMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSsid() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBssid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.SecurityType getSecurityType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getFrequencyMhz() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getRssiDbm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getIpV4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGatewayV4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getDnsServers() {
        return null;
    }
    
    public final boolean getCaptivePortal() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNetworkIdHint() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.SecurityType component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, long timestampMs, @org.jetbrains.annotations.Nullable()
    java.lang.String ssid, @org.jetbrains.annotations.Nullable()
    java.lang.String bssid, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.SecurityType securityType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer frequencyMhz, @org.jetbrains.annotations.Nullable()
    java.lang.Integer rssiDbm, @org.jetbrains.annotations.Nullable()
    java.lang.String ipV4, @org.jetbrains.annotations.Nullable()
    java.lang.String gatewayV4, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> dnsServers, boolean captivePortal, @org.jetbrains.annotations.NotNull()
    java.lang.String networkIdHint) {
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
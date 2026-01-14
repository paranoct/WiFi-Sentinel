package com.wifisentinel.app.replay;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u00c6\u0003JK\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013\u00a8\u0006$"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayPayload;", "", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "scanResults", "", "Lcom/wifisentinel/core/wifi/ScanNet;", "dnsCheck", "Lcom/wifisentinel/app/replay/DnsCheckPayload;", "portalCheck", "Lcom/wifisentinel/core/net/CaptivePortalCheck;", "trustedProfiles", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Ljava/util/List;Lcom/wifisentinel/app/replay/DnsCheckPayload;Lcom/wifisentinel/core/net/CaptivePortalCheck;Ljava/util/List;)V", "getDnsCheck", "()Lcom/wifisentinel/app/replay/DnsCheckPayload;", "getPortalCheck", "()Lcom/wifisentinel/core/net/CaptivePortalCheck;", "getScanResults", "()Ljava/util/List;", "getSnapshot", "()Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "getTrustedProfiles", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ReplayPayload {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.NetworkSnapshot snapshot = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults = null;
    @org.jetbrains.annotations.Nullable()
    private final com.wifisentinel.app.replay.DnsCheckPayload dnsCheck = null;
    @org.jetbrains.annotations.Nullable()
    private final com.wifisentinel.core.net.CaptivePortalCheck portalCheck = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> trustedProfiles = null;
    
    public ReplayPayload(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults, @org.jetbrains.annotations.Nullable()
    com.wifisentinel.app.replay.DnsCheckPayload dnsCheck, @org.jetbrains.annotations.Nullable()
    com.wifisentinel.core.net.CaptivePortalCheck portalCheck, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> trustedProfiles) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkSnapshot getSnapshot() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.wifi.ScanNet> getScanResults() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wifisentinel.app.replay.DnsCheckPayload getDnsCheck() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wifisentinel.core.net.CaptivePortalCheck getPortalCheck() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> getTrustedProfiles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkSnapshot component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.wifi.ScanNet> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wifisentinel.app.replay.DnsCheckPayload component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wifisentinel.core.net.CaptivePortalCheck component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.replay.ReplayPayload copy(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults, @org.jetbrains.annotations.Nullable()
    com.wifisentinel.app.replay.DnsCheckPayload dnsCheck, @org.jetbrains.annotations.Nullable()
    com.wifisentinel.core.net.CaptivePortalCheck portalCheck, @org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> trustedProfiles) {
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
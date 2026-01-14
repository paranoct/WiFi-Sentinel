package com.wifisentinel.app.replay;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\b\u0007\u0018\u0000 J2\u00020\u0001:\u0003JKLB9\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00192\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0019H\u0002J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010$J \u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020&0\u0019H\u0002J\u001c\u0010(\u001a\u00020\u00102\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100*H\u0002J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00192\b\u0010.\u001a\u0004\u0018\u00010/H\u0002J\u0018\u00100\u001a\b\u0012\u0004\u0012\u00020\u00120\u00192\b\u0010.\u001a\u0004\u0018\u00010/H\u0002J\u0012\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u000204H\u0002J\u0012\u00105\u001a\u0004\u0018\u00010\u00142\u0006\u00106\u001a\u000204H\u0002J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u0010H\u0002J\u0010\u0010:\u001a\u00020;2\u0006\u00109\u001a\u00020\u0010H\u0002J\u0016\u0010<\u001a\u00020#2\u0006\u0010=\u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010>J\u0016\u0010?\u001a\u00020@2\u0006\u0010\u001b\u001a\u00020\u001cH\u0082@\u00a2\u0006\u0002\u0010AJ\u0016\u0010B\u001a\u00020@2\u0006\u0010\u001b\u001a\u000202H\u0082@\u00a2\u0006\u0002\u0010CJ\u001b\u0010D\u001a\u0004\u0018\u00010E*\u0002042\u0006\u0010F\u001a\u00020\u0010H\u0002\u00a2\u0006\u0002\u0010GJ\u0016\u0010H\u001a\u0004\u0018\u00010\u0010*\u0002042\u0006\u0010F\u001a\u00020\u0010H\u0002J\u0012\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019*\u00020/H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006M"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayManager;", "", "context", "Landroid/content/Context;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "wifiScanner", "Lcom/wifisentinel/core/wifi/WifiScanner;", "dnsProbe", "Lcom/wifisentinel/core/net/DnsProbe;", "portalProbe", "Lcom/wifisentinel/core/net/CaptivePortalProbe;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "(Landroid/content/Context;Lcom/wifisentinel/core/storage/NetworkRepository;Lcom/wifisentinel/core/wifi/WifiScanner;Lcom/wifisentinel/core/net/DnsProbe;Lcom/wifisentinel/core/net/CaptivePortalProbe;Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "buildDedupKey", "", "finding", "Lcom/wifisentinel/core/detectors/Finding;", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "buildDnsCheck", "Lcom/wifisentinel/app/replay/DnsCheckPayload;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildReplayDetectors", "", "Lcom/wifisentinel/core/detectors/Detector;", "payload", "Lcom/wifisentinel/app/replay/ReplayPayload;", "ensureCurrentNetworkScan", "Lcom/wifisentinel/core/wifi/ScanNet;", "scanResults", "exportCurrent", "Landroid/net/Uri;", "maskSensitive", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findTrustedProfile", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "profiles", "hashEvidence", "evidence", "", "normalizedNetworkKey", "parseReportEvents", "Lcom/wifisentinel/core/storage/NetworkEvent;", "array", "Lorg/json/JSONArray;", "parseReportFindings", "parseReportPayload", "Lcom/wifisentinel/app/replay/ReplayManager$ReportPayload;", "root", "Lorg/json/JSONObject;", "parseReportSnapshot", "json", "parseSecurityType", "Lcom/wifisentinel/core/wifi/SecurityType;", "value", "parseSeverity", "Lcom/wifisentinel/core/detectors/Severity;", "runFromUri", "uri", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runPayload", "", "(Lcom/wifisentinel/app/replay/ReplayPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runReportPayload", "(Lcom/wifisentinel/app/replay/ReplayManager$ReportPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "optNullableInt", "", "key", "(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/Integer;", "optNullableString", "toStringList", "Companion", "ReportPayload", "ReportSnapshot", "app_debug"})
public final class ReplayManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.wifi.WifiScanner wifiScanner = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.net.DnsProbe dnsProbe = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.net.CaptivePortalProbe portalProbe = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> DNS_CONTROL_DOMAINS = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.replay.ReplayManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public ReplayManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.WifiScanner wifiScanner, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.net.DnsProbe dnsProbe, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.net.CaptivePortalProbe portalProbe, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportCurrent(boolean maskSensitive, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.net.Uri> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object runFromUri(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object runPayload(com.wifisentinel.app.replay.ReplayPayload payload, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.detectors.Detector> buildReplayDetectors(com.wifisentinel.app.replay.ReplayPayload payload) {
        return null;
    }
    
    private final com.wifisentinel.app.replay.ReplayManager.ReportPayload parseReportPayload(org.json.JSONObject root) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.NetworkSnapshot parseReportSnapshot(org.json.JSONObject json) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.detectors.Finding> parseReportFindings(org.json.JSONArray array) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.storage.NetworkEvent> parseReportEvents(org.json.JSONArray array) {
        return null;
    }
    
    private final java.lang.Object runReportPayload(com.wifisentinel.app.replay.ReplayManager.ReportPayload payload, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object buildDnsCheck(kotlin.coroutines.Continuation<? super com.wifisentinel.app.replay.DnsCheckPayload> $completion) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.TrustedNetworkProfile findTrustedProfile(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> profiles) {
        return null;
    }
    
    private final java.lang.String buildDedupKey(com.wifisentinel.core.detectors.Finding finding, com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.String normalizedNetworkKey(com.wifisentinel.core.wifi.NetworkSnapshot snapshot) {
        return null;
    }
    
    private final java.lang.String hashEvidence(java.util.Map<java.lang.String, java.lang.String> evidence) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.SecurityType parseSecurityType(java.lang.String value) {
        return null;
    }
    
    private final com.wifisentinel.core.detectors.Severity parseSeverity(java.lang.String value) {
        return null;
    }
    
    private final java.lang.String optNullableString(org.json.JSONObject $this$optNullableString, java.lang.String key) {
        return null;
    }
    
    private final java.lang.Integer optNullableInt(org.json.JSONObject $this$optNullableInt, java.lang.String key) {
        return null;
    }
    
    private final java.util.List<java.lang.String> toStringList(org.json.JSONArray $this$toStringList) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.wifi.ScanNet> ensureCurrentNetworkScan(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayManager$Companion;", "", "()V", "DNS_CONTROL_DOMAINS", "", "", "getDNS_CONTROL_DOMAINS", "()Ljava/util/List;", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getDNS_CONTROL_DOMAINS() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayManager$ReportPayload;", "", "snapshots", "", "Lcom/wifisentinel/app/replay/ReplayManager$ReportSnapshot;", "events", "Lcom/wifisentinel/core/storage/NetworkEvent;", "(Ljava/util/List;Ljava/util/List;)V", "getEvents", "()Ljava/util/List;", "getSnapshots", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class ReportPayload {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.wifisentinel.app.replay.ReplayManager.ReportSnapshot> snapshots = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.wifisentinel.core.storage.NetworkEvent> events = null;
        
        public ReportPayload(@org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.app.replay.ReplayManager.ReportSnapshot> snapshots, @org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.core.storage.NetworkEvent> events) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.app.replay.ReplayManager.ReportSnapshot> getSnapshots() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.core.storage.NetworkEvent> getEvents() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.app.replay.ReplayManager.ReportSnapshot> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.core.storage.NetworkEvent> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.app.replay.ReplayManager.ReportPayload copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.app.replay.ReplayManager.ReportSnapshot> snapshots, @org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.core.storage.NetworkEvent> events) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayManager$ReportSnapshot;", "", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "findings", "", "Lcom/wifisentinel/core/detectors/Finding;", "(Lcom/wifisentinel/core/wifi/NetworkSnapshot;Ljava/util/List;)V", "getFindings", "()Ljava/util/List;", "getSnapshot", "()Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class ReportSnapshot {
        @org.jetbrains.annotations.NotNull()
        private final com.wifisentinel.core.wifi.NetworkSnapshot snapshot = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.wifisentinel.core.detectors.Finding> findings = null;
        
        public ReportSnapshot(@org.jetbrains.annotations.NotNull()
        com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.core.detectors.Finding> findings) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.core.wifi.NetworkSnapshot getSnapshot() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.core.detectors.Finding> getFindings() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.core.wifi.NetworkSnapshot component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.wifisentinel.core.detectors.Finding> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.wifisentinel.app.replay.ReplayManager.ReportSnapshot copy(@org.jetbrains.annotations.NotNull()
        com.wifisentinel.core.wifi.NetworkSnapshot snapshot, @org.jetbrains.annotations.NotNull()
        java.util.List<com.wifisentinel.core.detectors.Finding> findings) {
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
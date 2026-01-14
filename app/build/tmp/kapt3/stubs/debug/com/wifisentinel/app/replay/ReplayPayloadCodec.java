package com.wifisentinel.app.replay;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0002J\u001e\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\u0015\u001a\u00020\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00102\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001b\u001a\u00020\nH\u0002J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u0010\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u0004H\u0002J\u000e\u0010\"\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u0004J\u0010\u0010$\u001a\u00020\f2\u0006\u0010!\u001a\u00020\u0004H\u0002J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010&\u001a\u00020\u000eH\u0002J\u0010\u0010\'\u001a\u00020(2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u0010\u0010)\u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0004H\u0002J\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00170\u00102\u0006\u0010&\u001a\u00020\u000eH\u0002J\u001b\u0010+\u001a\u0004\u0018\u00010,*\u00020\u00042\u0006\u0010-\u001a\u00020\u0019H\u0002\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u0004\u0018\u00010\u0019*\u00020\u00042\u0006\u0010-\u001a\u00020\u0019H\u0002J\u001e\u00100\u001a\u000201*\u00020\u00042\u0006\u0010-\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0002J\u0012\u00102\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010*\u00020\u000eH\u0002\u00a8\u00063"}, d2 = {"Lcom/wifisentinel/app/replay/ReplayPayloadCodec;", "", "()V", "buildDnsCheckJson", "Lorg/json/JSONObject;", "payload", "Lcom/wifisentinel/app/replay/DnsCheckPayload;", "buildPayloadJson", "Lcom/wifisentinel/app/replay/ReplayPayload;", "maskSensitive", "", "buildPortalCheckJson", "Lcom/wifisentinel/core/net/CaptivePortalCheck;", "buildScanResultsJson", "Lorg/json/JSONArray;", "scanResults", "", "Lcom/wifisentinel/core/wifi/ScanNet;", "buildSnapshotJson", "snapshot", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "buildTrustedProfilesJson", "profiles", "Lcom/wifisentinel/core/wifi/TrustedNetworkProfile;", "maskValue", "", "value", "enabled", "parseBand", "Lcom/wifisentinel/core/wifi/Band;", "parseCategory", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "parseDnsCheck", "json", "parsePayload", "root", "parsePortalCheck", "parseScanResults", "array", "parseSecurityType", "Lcom/wifisentinel/core/wifi/SecurityType;", "parseSnapshot", "parseTrustedProfiles", "optNullableInt", "", "key", "(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/Integer;", "optNullableString", "putNullable", "", "toStringList", "app_debug"})
public final class ReplayPayloadCodec {
    @org.jetbrains.annotations.NotNull()
    public static final com.wifisentinel.app.replay.ReplayPayloadCodec INSTANCE = null;
    
    private ReplayPayloadCodec() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.json.JSONObject buildPayloadJson(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.replay.ReplayPayload payload, boolean maskSensitive) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.app.replay.ReplayPayload parsePayload(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject root) {
        return null;
    }
    
    private final org.json.JSONObject buildSnapshotJson(com.wifisentinel.core.wifi.NetworkSnapshot snapshot, boolean maskSensitive) {
        return null;
    }
    
    private final org.json.JSONArray buildScanResultsJson(java.util.List<com.wifisentinel.core.wifi.ScanNet> scanResults, boolean maskSensitive) {
        return null;
    }
    
    private final org.json.JSONObject buildDnsCheckJson(com.wifisentinel.app.replay.DnsCheckPayload payload) {
        return null;
    }
    
    private final org.json.JSONObject buildPortalCheckJson(com.wifisentinel.core.net.CaptivePortalCheck payload) {
        return null;
    }
    
    private final org.json.JSONArray buildTrustedProfilesJson(java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> profiles, boolean maskSensitive) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.NetworkSnapshot parseSnapshot(org.json.JSONObject json) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.wifi.ScanNet> parseScanResults(org.json.JSONArray array) {
        return null;
    }
    
    private final com.wifisentinel.app.replay.DnsCheckPayload parseDnsCheck(org.json.JSONObject json) {
        return null;
    }
    
    private final com.wifisentinel.core.net.CaptivePortalCheck parsePortalCheck(org.json.JSONObject json) {
        return null;
    }
    
    private final java.util.List<com.wifisentinel.core.wifi.TrustedNetworkProfile> parseTrustedProfiles(org.json.JSONArray array) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.SecurityType parseSecurityType(java.lang.String value) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.NetworkCategory parseCategory(java.lang.String value) {
        return null;
    }
    
    private final com.wifisentinel.core.wifi.Band parseBand(java.lang.String value) {
        return null;
    }
    
    private final java.lang.String maskValue(java.lang.String value, boolean enabled) {
        return null;
    }
    
    private final void putNullable(org.json.JSONObject $this$putNullable, java.lang.String key, java.lang.Object value) {
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
}
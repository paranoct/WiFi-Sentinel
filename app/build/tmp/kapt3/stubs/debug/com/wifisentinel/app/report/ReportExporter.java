package com.wifisentinel.app.report;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 32\u00020\u0001:\u00013B)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0002J\u001e\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013H\u0002Jb\u0010\u0017\u001a\u00020\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00102\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u001b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001f\u001a\u0004\u0018\u00010\u001c2\b\u0010 \u001a\u0004\u0018\u00010\u001cH\u0002J\u0018\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J6\u0010#\u001a\u00020\u001e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00102\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u001bH\u0082@\u00a2\u0006\u0002\u0010$J8\u0010%\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00102\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00100\u001b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010&\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\'2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010*J\u0010\u0010+\u001a\u00020\u00132\u0006\u0010,\u001a\u00020\u001cH\u0002J\u001c\u0010-\u001a\u0004\u0018\u00010\u001c2\b\u0010.\u001a\u0004\u0018\u00010\u001c2\u0006\u0010/\u001a\u00020\u0013H\u0002J\u001c\u00100\u001a\u0004\u0018\u00010\u001c2\b\u0010.\u001a\u0004\u0018\u00010\u001c2\u0006\u0010/\u001a\u00020\u0013H\u0002J\u001e\u00101\u001a\u000202*\u00020\f2\u0006\u0010,\u001a\u00020\u001c2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/wifisentinel/app/report/ReportExporter;", "", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "riskEngine", "Lcom/wifisentinel/core/risk/RiskEngine;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "context", "Landroid/content/Context;", "(Lcom/wifisentinel/core/storage/NetworkRepository;Lcom/wifisentinel/core/risk/RiskEngine;Lcom/wifisentinel/core/storage/settings/SettingsRepository;Landroid/content/Context;)V", "buildDeviceJson", "Lorg/json/JSONObject;", "buildEventsJson", "Lorg/json/JSONArray;", "events", "", "Lcom/wifisentinel/core/storage/NetworkEvent;", "maskSensitive", "", "buildFindingJson", "finding", "Lcom/wifisentinel/core/detectors/Finding;", "buildReportJson", "snapshots", "Lcom/wifisentinel/core/wifi/NetworkSnapshot;", "findingsBySnapshot", "", "", "riskSummary", "Lcom/wifisentinel/core/risk/RiskSummary;", "reportType", "networkIdHint", "buildRiskJson", "summary", "buildRiskSummary", "(Ljava/util/List;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildSnapshotsJson", "export", "Landroid/net/Uri;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportCurrentNetwork", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isSensitiveKey", "key", "maskSensitiveValue", "value", "enabled", "maskValue", "putNullable", "", "Companion", "app_debug"})
public final class ReportExporter {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.NetworkRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.risk.RiskEngine riskEngine = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @java.lang.Deprecated()
    public static final int SNAPSHOT_LIMIT = 20;
    @java.lang.Deprecated()
    public static final int EVENT_LIMIT = 200;
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.report.ReportExporter.Companion Companion = null;
    
    @javax.inject.Inject()
    public ReportExporter(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.risk.RiskEngine riskEngine, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object export(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.net.Uri> $completion) {
        return null;
    }
    
    private final java.lang.Object buildRiskSummary(java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot> snapshots, java.util.Map<java.lang.String, ? extends java.util.List<com.wifisentinel.core.detectors.Finding>> findingsBySnapshot, kotlin.coroutines.Continuation<? super com.wifisentinel.core.risk.RiskSummary> $completion) {
        return null;
    }
    
    private final org.json.JSONObject buildReportJson(java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot> snapshots, java.util.Map<java.lang.String, ? extends java.util.List<com.wifisentinel.core.detectors.Finding>> findingsBySnapshot, java.util.List<com.wifisentinel.core.storage.NetworkEvent> events, com.wifisentinel.core.risk.RiskSummary riskSummary, boolean maskSensitive, java.lang.String reportType, java.lang.String networkIdHint) {
        return null;
    }
    
    private final org.json.JSONObject buildDeviceJson() {
        return null;
    }
    
    private final org.json.JSONObject buildRiskJson(com.wifisentinel.core.risk.RiskSummary summary, boolean maskSensitive) {
        return null;
    }
    
    private final org.json.JSONArray buildSnapshotsJson(java.util.List<com.wifisentinel.core.wifi.NetworkSnapshot> snapshots, java.util.Map<java.lang.String, ? extends java.util.List<com.wifisentinel.core.detectors.Finding>> findingsBySnapshot, boolean maskSensitive) {
        return null;
    }
    
    private final org.json.JSONObject buildFindingJson(com.wifisentinel.core.detectors.Finding finding, boolean maskSensitive) {
        return null;
    }
    
    private final org.json.JSONArray buildEventsJson(java.util.List<com.wifisentinel.core.storage.NetworkEvent> events, boolean maskSensitive) {
        return null;
    }
    
    private final void putNullable(org.json.JSONObject $this$putNullable, java.lang.String key, java.lang.Object value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportCurrentNetwork(boolean maskSensitive, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.net.Uri> $completion) {
        return null;
    }
    
    private final java.lang.String maskValue(java.lang.String value, boolean enabled) {
        return null;
    }
    
    private final boolean isSensitiveKey(java.lang.String key) {
        return false;
    }
    
    private final java.lang.String maskSensitiveValue(java.lang.String value, boolean enabled) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/wifisentinel/app/report/ReportExporter$Companion;", "", "()V", "EVENT_LIMIT", "", "SNAPSHOT_LIMIT", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
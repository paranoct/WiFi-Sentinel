package com.wifisentinel.core.storage.db.entity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bo\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0006H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\tH\u00c6\u0003J\t\u0010-\u001a\u00020\u000bH\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0015\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000fH\u00c6\u0003J\u0089\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00105\u001a\u00020\u000bH\u00d6\u0001J\t\u00106\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018\u00a8\u00067"}, d2 = {"Lcom/wifisentinel/core/storage/db/entity/FindingEntity;", "", "id", "", "snapshotId", "timestampMs", "", "detectorId", "severity", "Lcom/wifisentinel/core/detectors/Severity;", "scoreDelta", "", "title", "explanation", "evidence", "", "actions", "", "Lcom/wifisentinel/core/detectors/FindingActionType;", "dedupKey", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Lcom/wifisentinel/core/detectors/Severity;ILjava/lang/String;Ljava/lang/String;Ljava/util/Map;Ljava/util/List;Ljava/lang/String;)V", "getActions", "()Ljava/util/List;", "getDedupKey", "()Ljava/lang/String;", "getDetectorId", "getEvidence", "()Ljava/util/Map;", "getExplanation", "getId", "getScoreDelta", "()I", "getSeverity", "()Lcom/wifisentinel/core/detectors/Severity;", "getSnapshotId", "getTimestampMs", "()J", "getTitle", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "storage_debug"})
@androidx.room.Entity(tableName = "findings", indices = {@androidx.room.Index(value = {"dedupKey"})})
public final class FindingEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String snapshotId = null;
    private final long timestampMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String detectorId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.detectors.Severity severity = null;
    private final int scoreDelta = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String explanation = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> evidence = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.wifisentinel.core.detectors.FindingActionType> actions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dedupKey = null;
    
    public FindingEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId, long timestampMs, @org.jetbrains.annotations.NotNull()
    java.lang.String detectorId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.detectors.Severity severity, int scoreDelta, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String explanation, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> evidence, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.wifisentinel.core.detectors.FindingActionType> actions, @org.jetbrains.annotations.NotNull()
    java.lang.String dedupKey) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSnapshotId() {
        return null;
    }
    
    public final long getTimestampMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDetectorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.detectors.Severity getSeverity() {
        return null;
    }
    
    public final int getScoreDelta() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getExplanation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getEvidence() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.detectors.FindingActionType> getActions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDedupKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.detectors.FindingActionType> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.detectors.Severity component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.db.entity.FindingEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId, long timestampMs, @org.jetbrains.annotations.NotNull()
    java.lang.String detectorId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.detectors.Severity severity, int scoreDelta, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String explanation, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> evidence, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.wifisentinel.core.detectors.FindingActionType> actions, @org.jetbrains.annotations.NotNull()
    java.lang.String dedupKey) {
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
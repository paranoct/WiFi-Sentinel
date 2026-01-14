package com.wifisentinel.core.storage.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0007J\u0018\u0010\b\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0007J\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u001e\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00102\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u001e\u0010\u0015\u001a\u00020\u00042\u0014\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0010H\u0007J\u0018\u0010\u0017\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\tH\u0007J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0012H\u0007J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001aH\u0007J\u0018\u0010\u001b\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006H\u0007J\u0018\u0010\u001c\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\tH\u0007J\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006 "}, d2 = {"Lcom/wifisentinel/core/storage/db/Converters;", "", "()V", "actionsToJson", "", "value", "", "Lcom/wifisentinel/core/detectors/FindingActionType;", "bandSetToJson", "", "Lcom/wifisentinel/core/wifi/Band;", "categoryToString", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "jsonToActions", "jsonToBandSet", "jsonToMap", "", "jsonToSecuritySet", "Lcom/wifisentinel/core/wifi/SecurityType;", "jsonToStringList", "jsonToStringSet", "mapToJson", "map", "securitySetToJson", "securityToString", "severityToString", "Lcom/wifisentinel/core/detectors/Severity;", "stringListToJson", "stringSetToJson", "stringToCategory", "stringToSecurity", "stringToSeverity", "storage_debug"})
public final class Converters {
    
    public Converters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String stringListToJson(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> jsonToStringList(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String stringSetToJson(@org.jetbrains.annotations.Nullable()
    java.util.Set<java.lang.String> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> jsonToStringSet(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String securitySetToJson(@org.jetbrains.annotations.Nullable()
    java.util.Set<? extends com.wifisentinel.core.wifi.SecurityType> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.SecurityType> jsonToSecuritySet(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String bandSetToJson(@org.jetbrains.annotations.Nullable()
    java.util.Set<? extends com.wifisentinel.core.wifi.Band> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.wifisentinel.core.wifi.Band> jsonToBandSet(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String severityToString(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.detectors.Severity value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.detectors.Severity stringToSeverity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String securityToString(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.SecurityType value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.SecurityType stringToSecurity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String categoryToString(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.wifi.NetworkCategory stringToCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String mapToJson(@org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> map) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> jsonToMap(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String actionsToJson(@org.jetbrains.annotations.Nullable()
    java.util.List<? extends com.wifisentinel.core.detectors.FindingActionType> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.wifisentinel.core.detectors.FindingActionType> jsonToActions(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
}
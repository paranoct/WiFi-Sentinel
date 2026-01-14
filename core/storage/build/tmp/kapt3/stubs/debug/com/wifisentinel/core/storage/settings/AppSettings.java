package com.wifisentinel.core.storage.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003JY\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010$\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\bH\u00d6\u0001J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/wifisentinel/core/storage/settings/AppSettings;", "", "dohProviderId", "", "dnsCheckEnabled", "", "notificationsEnabled", "scanIntervalHours", "", "themeMode", "Lcom/wifisentinel/core/storage/settings/ThemeMode;", "alwaysOnEnabled", "maskSensitive", "demoModeEnabled", "(Ljava/lang/String;ZZILcom/wifisentinel/core/storage/settings/ThemeMode;ZZZ)V", "getAlwaysOnEnabled", "()Z", "getDemoModeEnabled", "getDnsCheckEnabled", "getDohProviderId", "()Ljava/lang/String;", "getMaskSensitive", "getNotificationsEnabled", "getScanIntervalHours", "()I", "getThemeMode", "()Lcom/wifisentinel/core/storage/settings/ThemeMode;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "storage_debug"})
public final class AppSettings {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dohProviderId = null;
    private final boolean dnsCheckEnabled = false;
    private final boolean notificationsEnabled = false;
    private final int scanIntervalHours = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.ThemeMode themeMode = null;
    private final boolean alwaysOnEnabled = false;
    private final boolean maskSensitive = false;
    private final boolean demoModeEnabled = false;
    
    public AppSettings(@org.jetbrains.annotations.NotNull()
    java.lang.String dohProviderId, boolean dnsCheckEnabled, boolean notificationsEnabled, int scanIntervalHours, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.ThemeMode themeMode, boolean alwaysOnEnabled, boolean maskSensitive, boolean demoModeEnabled) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDohProviderId() {
        return null;
    }
    
    public final boolean getDnsCheckEnabled() {
        return false;
    }
    
    public final boolean getNotificationsEnabled() {
        return false;
    }
    
    public final int getScanIntervalHours() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.settings.ThemeMode getThemeMode() {
        return null;
    }
    
    public final boolean getAlwaysOnEnabled() {
        return false;
    }
    
    public final boolean getMaskSensitive() {
        return false;
    }
    
    public final boolean getDemoModeEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.settings.ThemeMode component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wifisentinel.core.storage.settings.AppSettings copy(@org.jetbrains.annotations.NotNull()
    java.lang.String dohProviderId, boolean dnsCheckEnabled, boolean notificationsEnabled, int scanIntervalHours, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.ThemeMode themeMode, boolean alwaysOnEnabled, boolean maskSensitive, boolean demoModeEnabled) {
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
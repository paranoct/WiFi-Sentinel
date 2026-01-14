package com.wifisentinel.app.notifications;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0002\u00a8\u0006\u0012"}, d2 = {"Lcom/wifisentinel/app/notifications/ForgetNetworkReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "normalizeSsid", "", "raw", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "tryForgetNetwork", "", "wifiManager", "Landroid/net/wifi/WifiManager;", "ssid", "Companion", "app_debug"})
public final class ForgetNetworkReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_FORGET = "com.wifisentinel.action.FORGET_NETWORK";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_SSID = "extra_ssid";
    @org.jetbrains.annotations.NotNull()
    public static final com.wifisentinel.app.notifications.ForgetNetworkReceiver.Companion Companion = null;
    
    public ForgetNetworkReceiver() {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @kotlin.Suppress(names = {"DEPRECATION"})
    private final boolean tryForgetNetwork(android.net.wifi.WifiManager wifiManager, java.lang.String ssid) {
        return false;
    }
    
    private final java.lang.String normalizeSsid(java.lang.String raw) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/wifisentinel/app/notifications/ForgetNetworkReceiver$Companion;", "", "()V", "ACTION_FORGET", "", "EXTRA_SSID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
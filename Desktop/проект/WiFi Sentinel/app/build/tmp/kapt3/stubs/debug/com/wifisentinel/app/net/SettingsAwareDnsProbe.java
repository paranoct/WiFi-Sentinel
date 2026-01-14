package com.wifisentinel.app.net;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\u0006H\u0082@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/wifisentinel/app/net/SettingsAwareDnsProbe;", "Lcom/wifisentinel/core/net/DnsProbe;", "settingsRepository", "Lcom/wifisentinel/core/storage/settings/SettingsRepository;", "(Lcom/wifisentinel/core/storage/settings/SettingsRepository;)V", "cachedDoh", "Lokhttp3/dnsoverhttps/DnsOverHttps;", "cachedProviderId", "", "client", "Lokhttp3/OkHttpClient;", "doh", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveDoh", "", "domain", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveSystem", "app_debug"})
public final class SettingsAwareDnsProbe implements com.wifisentinel.core.net.DnsProbe {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String cachedProviderId;
    @org.jetbrains.annotations.Nullable()
    private okhttp3.dnsoverhttps.DnsOverHttps cachedDoh;
    
    @javax.inject.Inject()
    public SettingsAwareDnsProbe(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.settings.SettingsRepository settingsRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object resolveSystem(@org.jetbrains.annotations.NotNull()
    java.lang.String domain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object resolveDoh(@org.jetbrains.annotations.NotNull()
    java.lang.String domain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    private final java.lang.Object doh(kotlin.coroutines.Continuation<? super okhttp3.dnsoverhttps.DnsOverHttps> $completion) {
        return null;
    }
}
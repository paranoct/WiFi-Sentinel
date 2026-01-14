package com.wifisentinel.app.net;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/wifisentinel/app/net/OkHttpCaptivePortalProbe;", "Lcom/wifisentinel/core/net/CaptivePortalProbe;", "()V", "client", "Lokhttp3/OkHttpClient;", "check", "Lcom/wifisentinel/core/net/CaptivePortalCheck;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class OkHttpCaptivePortalProbe implements com.wifisentinel.core.net.CaptivePortalProbe {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @java.lang.Deprecated()
    public static final int MAX_REDIRECTS = 5;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TEST_URL = "https://connectivitycheck.gstatic.com/generate_204";
    @org.jetbrains.annotations.NotNull()
    private static final com.wifisentinel.app.net.OkHttpCaptivePortalProbe.Companion Companion = null;
    
    @javax.inject.Inject()
    public OkHttpCaptivePortalProbe() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object check(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.wifisentinel.core.net.CaptivePortalCheck> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/wifisentinel/app/net/OkHttpCaptivePortalProbe$Companion;", "", "()V", "MAX_REDIRECTS", "", "TEST_URL", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
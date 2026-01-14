package com.wifisentinel.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0015B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0013\u001a\u00020\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0016"}, d2 = {"Lcom/wifisentinel/app/viewmodel/TimelineViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/wifisentinel/core/storage/NetworkRepository;", "reportExporter", "Lcom/wifisentinel/app/report/ReportExporter;", "(Lcom/wifisentinel/core/storage/NetworkRepository;Lcom/wifisentinel/app/report/ReportExporter;)V", "_reportEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent;", "reportEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getReportEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/wifisentinel/feature/timeline/TimelineUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "exportReport", "", "ReportEvent", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TimelineViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.wifisentinel.app.report.ReportExporter reportExporter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.timeline.TimelineUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent> _reportEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent> reportEvents = null;
    
    @javax.inject.Inject()
    public TimelineViewModel(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.NetworkRepository repository, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.app.report.ReportExporter reportExporter) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.wifisentinel.feature.timeline.TimelineUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent> getReportEvents() {
        return null;
    }
    
    public final void exportReport() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent;", "", "Error", "Share", "Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent$Error;", "Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent$Share;", "app_debug"})
    public static abstract interface ReportEvent {
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent$Error;", "Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent;", "messageResId", "", "(I)V", "getMessageResId", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
        public static final class Error implements com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent {
            private final int messageResId = 0;
            
            public Error(int messageResId) {
                super();
            }
            
            public final int getMessageResId() {
                return 0;
            }
            
            public final int component1() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent.Error copy(int messageResId) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent$Share;", "Lcom/wifisentinel/app/viewmodel/TimelineViewModel$ReportEvent;", "uri", "Landroid/net/Uri;", "(Landroid/net/Uri;)V", "getUri", "()Landroid/net/Uri;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Share implements com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent {
            @org.jetbrains.annotations.NotNull()
            private final android.net.Uri uri = null;
            
            public Share(@org.jetbrains.annotations.NotNull()
            android.net.Uri uri) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.net.Uri getUri() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final android.net.Uri component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.wifisentinel.app.viewmodel.TimelineViewModel.ReportEvent.Share copy(@org.jetbrains.annotations.NotNull()
            android.net.Uri uri) {
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
}
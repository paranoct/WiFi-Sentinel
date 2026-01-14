package com.wifisentinel.core.storage.db.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/wifisentinel/core/storage/db/dao/EventDao;", "", "insertAll", "", "entities", "", "Lcom/wifisentinel/core/storage/db/entity/EventEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latest", "Lkotlinx/coroutines/flow/Flow;", "limit", "", "latestOnce", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "storage_debug"})
@androidx.room.Dao()
public abstract interface EventDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.storage.db.entity.EventEntity> entities, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY timestampMs DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.db.entity.EventEntity>> latest(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY timestampMs DESC LIMIT :limit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.db.entity.EventEntity>> $completion);
}
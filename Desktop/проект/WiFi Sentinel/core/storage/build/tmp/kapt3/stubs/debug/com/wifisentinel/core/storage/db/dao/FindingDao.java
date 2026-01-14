package com.wifisentinel.core.storage.db.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\'J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u0016"}, d2 = {"Lcom/wifisentinel/core/storage/db/dao/FindingDao;", "", "forSnapshot", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wifisentinel/core/storage/db/entity/FindingEntity;", "snapshotId", "", "forSnapshotOnce", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "", "entities", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latest", "limit", "", "latestOnce", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "latestTimestampForDedupKey", "", "dedupKey", "storage_debug"})
@androidx.room.Dao()
public abstract interface FindingDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.wifisentinel.core.storage.db.entity.FindingEntity> entities, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM findings WHERE snapshotId = :snapshotId ORDER BY timestampMs DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.db.entity.FindingEntity>> forSnapshot(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId);
    
    @androidx.room.Query(value = "SELECT * FROM findings WHERE snapshotId = :snapshotId ORDER BY timestampMs DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object forSnapshotOnce(@org.jetbrains.annotations.NotNull()
    java.lang.String snapshotId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.db.entity.FindingEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM findings ORDER BY timestampMs DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.db.entity.FindingEntity>> latest(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM findings ORDER BY timestampMs DESC LIMIT :limit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestOnce(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.db.entity.FindingEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT timestampMs FROM findings WHERE dedupKey = :dedupKey ORDER BY timestampMs DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object latestTimestampForDedupKey(@org.jetbrains.annotations.NotNull()
    java.lang.String dedupKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
}
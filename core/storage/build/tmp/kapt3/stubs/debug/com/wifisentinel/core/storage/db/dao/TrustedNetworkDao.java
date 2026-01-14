package com.wifisentinel.core.storage.db.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\t\u001a\u00020\nH\'J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/wifisentinel/core/storage/db/dao/TrustedNetworkDao;", "", "all", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/wifisentinel/core/storage/db/entity/TrustedNetworkProfileEntity;", "allOnce", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "byCategory", "category", "Lcom/wifisentinel/core/wifi/NetworkCategory;", "byCategoryOnce", "(Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "", "profileId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "moveProfile", "(Ljava/lang/String;Lcom/wifisentinel/core/wifi/NetworkCategory;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsert", "entity", "(Lcom/wifisentinel/core/storage/db/entity/TrustedNetworkProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "storage_debug"})
@androidx.room.Dao()
public abstract interface TrustedNetworkDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity entity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM trusted_networks ORDER BY displayName ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity>> all();
    
    @androidx.room.Query(value = "SELECT * FROM trusted_networks ORDER BY displayName ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object allOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM trusted_networks WHERE category = :category ORDER BY displayName ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity>> byCategory(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category);
    
    @androidx.room.Query(value = "SELECT * FROM trusted_networks WHERE category = :category ORDER BY displayName ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object byCategoryOnce(@org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE trusted_networks SET category = :category WHERE id = :profileId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object moveProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    com.wifisentinel.core.wifi.NetworkCategory category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM trusted_networks WHERE id = :profileId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
package com.wifisentinel.core.storage.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/wifisentinel/core/storage/db/WiFiSentinelDatabase;", "Landroidx/room/RoomDatabase;", "()V", "eventDao", "Lcom/wifisentinel/core/storage/db/dao/EventDao;", "findingDao", "Lcom/wifisentinel/core/storage/db/dao/FindingDao;", "snapshotDao", "Lcom/wifisentinel/core/storage/db/dao/SnapshotDao;", "trustedNetworkDao", "Lcom/wifisentinel/core/storage/db/dao/TrustedNetworkDao;", "storage_debug"})
@androidx.room.Database(entities = {com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity.class, com.wifisentinel.core.storage.db.entity.FindingEntity.class, com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity.class, com.wifisentinel.core.storage.db.entity.EventEntity.class}, version = 4)
@androidx.room.TypeConverters(value = {com.wifisentinel.core.storage.db.Converters.class})
public abstract class WiFiSentinelDatabase extends androidx.room.RoomDatabase {
    
    public WiFiSentinelDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wifisentinel.core.storage.db.dao.SnapshotDao snapshotDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wifisentinel.core.storage.db.dao.FindingDao findingDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wifisentinel.core.storage.db.dao.TrustedNetworkDao trustedNetworkDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wifisentinel.core.storage.db.dao.EventDao eventDao();
}
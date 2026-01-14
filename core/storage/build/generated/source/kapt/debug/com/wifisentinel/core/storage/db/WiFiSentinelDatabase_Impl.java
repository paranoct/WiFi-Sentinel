package com.wifisentinel.core.storage.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.wifisentinel.core.storage.db.dao.EventDao;
import com.wifisentinel.core.storage.db.dao.EventDao_Impl;
import com.wifisentinel.core.storage.db.dao.FindingDao;
import com.wifisentinel.core.storage.db.dao.FindingDao_Impl;
import com.wifisentinel.core.storage.db.dao.SnapshotDao;
import com.wifisentinel.core.storage.db.dao.SnapshotDao_Impl;
import com.wifisentinel.core.storage.db.dao.TrustedNetworkDao;
import com.wifisentinel.core.storage.db.dao.TrustedNetworkDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WiFiSentinelDatabase_Impl extends WiFiSentinelDatabase {
  private volatile SnapshotDao _snapshotDao;

  private volatile FindingDao _findingDao;

  private volatile TrustedNetworkDao _trustedNetworkDao;

  private volatile EventDao _eventDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `network_snapshots` (`id` TEXT NOT NULL, `timestampMs` INTEGER NOT NULL, `ssid` TEXT, `bssid` TEXT, `securityType` TEXT NOT NULL, `frequencyMhz` INTEGER, `rssiDbm` INTEGER, `ipV4` TEXT, `gatewayV4` TEXT, `dnsServers` TEXT NOT NULL, `captivePortal` INTEGER NOT NULL, `networkIdHint` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `findings` (`id` TEXT NOT NULL, `snapshotId` TEXT NOT NULL, `timestampMs` INTEGER NOT NULL, `detectorId` TEXT NOT NULL, `severity` TEXT NOT NULL, `scoreDelta` INTEGER NOT NULL, `title` TEXT NOT NULL, `explanation` TEXT NOT NULL, `evidence` TEXT NOT NULL, `actions` TEXT NOT NULL, `dedupKey` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_findings_dedupKey` ON `findings` (`dedupKey`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trusted_networks` (`id` TEXT NOT NULL, `displayName` TEXT NOT NULL, `ssid` TEXT, `category` TEXT NOT NULL, `meshMode` INTEGER NOT NULL, `allowedBssids` TEXT NOT NULL, `expectedSecurity` TEXT NOT NULL, `expectedFreqBands` TEXT NOT NULL, `pinnedDns` TEXT NOT NULL, `createdAtMs` INTEGER NOT NULL, `lastSeenMs` INTEGER NOT NULL, `maxNewBssidPerDay` INTEGER NOT NULL, `bssidLearning` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_trusted_networks_category` ON `trusted_networks` (`category`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `events` (`id` TEXT NOT NULL, `timestampMs` INTEGER NOT NULL, `title` TEXT NOT NULL, `detail` TEXT NOT NULL, `severity` TEXT NOT NULL, `snapshotId` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '76157e4a2d21e003661f8ea53749d912')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `network_snapshots`");
        db.execSQL("DROP TABLE IF EXISTS `findings`");
        db.execSQL("DROP TABLE IF EXISTS `trusted_networks`");
        db.execSQL("DROP TABLE IF EXISTS `events`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsNetworkSnapshots = new HashMap<String, TableInfo.Column>(12);
        _columnsNetworkSnapshots.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("timestampMs", new TableInfo.Column("timestampMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("ssid", new TableInfo.Column("ssid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("bssid", new TableInfo.Column("bssid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("securityType", new TableInfo.Column("securityType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("frequencyMhz", new TableInfo.Column("frequencyMhz", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("rssiDbm", new TableInfo.Column("rssiDbm", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("ipV4", new TableInfo.Column("ipV4", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("gatewayV4", new TableInfo.Column("gatewayV4", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("dnsServers", new TableInfo.Column("dnsServers", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("captivePortal", new TableInfo.Column("captivePortal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkSnapshots.put("networkIdHint", new TableInfo.Column("networkIdHint", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNetworkSnapshots = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNetworkSnapshots = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNetworkSnapshots = new TableInfo("network_snapshots", _columnsNetworkSnapshots, _foreignKeysNetworkSnapshots, _indicesNetworkSnapshots);
        final TableInfo _existingNetworkSnapshots = TableInfo.read(db, "network_snapshots");
        if (!_infoNetworkSnapshots.equals(_existingNetworkSnapshots)) {
          return new RoomOpenHelper.ValidationResult(false, "network_snapshots(com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity).\n"
                  + " Expected:\n" + _infoNetworkSnapshots + "\n"
                  + " Found:\n" + _existingNetworkSnapshots);
        }
        final HashMap<String, TableInfo.Column> _columnsFindings = new HashMap<String, TableInfo.Column>(11);
        _columnsFindings.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("snapshotId", new TableInfo.Column("snapshotId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("timestampMs", new TableInfo.Column("timestampMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("detectorId", new TableInfo.Column("detectorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("scoreDelta", new TableInfo.Column("scoreDelta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("explanation", new TableInfo.Column("explanation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("evidence", new TableInfo.Column("evidence", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("actions", new TableInfo.Column("actions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindings.put("dedupKey", new TableInfo.Column("dedupKey", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFindings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFindings = new HashSet<TableInfo.Index>(1);
        _indicesFindings.add(new TableInfo.Index("index_findings_dedupKey", false, Arrays.asList("dedupKey"), Arrays.asList("ASC")));
        final TableInfo _infoFindings = new TableInfo("findings", _columnsFindings, _foreignKeysFindings, _indicesFindings);
        final TableInfo _existingFindings = TableInfo.read(db, "findings");
        if (!_infoFindings.equals(_existingFindings)) {
          return new RoomOpenHelper.ValidationResult(false, "findings(com.wifisentinel.core.storage.db.entity.FindingEntity).\n"
                  + " Expected:\n" + _infoFindings + "\n"
                  + " Found:\n" + _existingFindings);
        }
        final HashMap<String, TableInfo.Column> _columnsTrustedNetworks = new HashMap<String, TableInfo.Column>(13);
        _columnsTrustedNetworks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("ssid", new TableInfo.Column("ssid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("meshMode", new TableInfo.Column("meshMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("allowedBssids", new TableInfo.Column("allowedBssids", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("expectedSecurity", new TableInfo.Column("expectedSecurity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("expectedFreqBands", new TableInfo.Column("expectedFreqBands", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("pinnedDns", new TableInfo.Column("pinnedDns", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("createdAtMs", new TableInfo.Column("createdAtMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("lastSeenMs", new TableInfo.Column("lastSeenMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("maxNewBssidPerDay", new TableInfo.Column("maxNewBssidPerDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedNetworks.put("bssidLearning", new TableInfo.Column("bssidLearning", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrustedNetworks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrustedNetworks = new HashSet<TableInfo.Index>(1);
        _indicesTrustedNetworks.add(new TableInfo.Index("index_trusted_networks_category", false, Arrays.asList("category"), Arrays.asList("ASC")));
        final TableInfo _infoTrustedNetworks = new TableInfo("trusted_networks", _columnsTrustedNetworks, _foreignKeysTrustedNetworks, _indicesTrustedNetworks);
        final TableInfo _existingTrustedNetworks = TableInfo.read(db, "trusted_networks");
        if (!_infoTrustedNetworks.equals(_existingTrustedNetworks)) {
          return new RoomOpenHelper.ValidationResult(false, "trusted_networks(com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity).\n"
                  + " Expected:\n" + _infoTrustedNetworks + "\n"
                  + " Found:\n" + _existingTrustedNetworks);
        }
        final HashMap<String, TableInfo.Column> _columnsEvents = new HashMap<String, TableInfo.Column>(6);
        _columnsEvents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("timestampMs", new TableInfo.Column("timestampMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("detail", new TableInfo.Column("detail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("snapshotId", new TableInfo.Column("snapshotId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEvents = new TableInfo("events", _columnsEvents, _foreignKeysEvents, _indicesEvents);
        final TableInfo _existingEvents = TableInfo.read(db, "events");
        if (!_infoEvents.equals(_existingEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "events(com.wifisentinel.core.storage.db.entity.EventEntity).\n"
                  + " Expected:\n" + _infoEvents + "\n"
                  + " Found:\n" + _existingEvents);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "76157e4a2d21e003661f8ea53749d912", "be10252423e2a0abb65b840277a28495");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "network_snapshots","findings","trusted_networks","events");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `network_snapshots`");
      _db.execSQL("DELETE FROM `findings`");
      _db.execSQL("DELETE FROM `trusted_networks`");
      _db.execSQL("DELETE FROM `events`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SnapshotDao.class, SnapshotDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FindingDao.class, FindingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TrustedNetworkDao.class, TrustedNetworkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EventDao.class, EventDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SnapshotDao snapshotDao() {
    if (_snapshotDao != null) {
      return _snapshotDao;
    } else {
      synchronized(this) {
        if(_snapshotDao == null) {
          _snapshotDao = new SnapshotDao_Impl(this);
        }
        return _snapshotDao;
      }
    }
  }

  @Override
  public FindingDao findingDao() {
    if (_findingDao != null) {
      return _findingDao;
    } else {
      synchronized(this) {
        if(_findingDao == null) {
          _findingDao = new FindingDao_Impl(this);
        }
        return _findingDao;
      }
    }
  }

  @Override
  public TrustedNetworkDao trustedNetworkDao() {
    if (_trustedNetworkDao != null) {
      return _trustedNetworkDao;
    } else {
      synchronized(this) {
        if(_trustedNetworkDao == null) {
          _trustedNetworkDao = new TrustedNetworkDao_Impl(this);
        }
        return _trustedNetworkDao;
      }
    }
  }

  @Override
  public EventDao eventDao() {
    if (_eventDao != null) {
      return _eventDao;
    } else {
      synchronized(this) {
        if(_eventDao == null) {
          _eventDao = new EventDao_Impl(this);
        }
        return _eventDao;
      }
    }
  }
}

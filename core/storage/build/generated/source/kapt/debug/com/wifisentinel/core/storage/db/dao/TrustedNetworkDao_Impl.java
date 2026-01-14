package com.wifisentinel.core.storage.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.wifisentinel.core.storage.db.Converters;
import com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity;
import com.wifisentinel.core.wifi.Band;
import com.wifisentinel.core.wifi.NetworkCategory;
import com.wifisentinel.core.wifi.SecurityType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TrustedNetworkDao_Impl implements TrustedNetworkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrustedNetworkProfileEntity> __insertionAdapterOfTrustedNetworkProfileEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfMoveProfile;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public TrustedNetworkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrustedNetworkProfileEntity = new EntityInsertionAdapter<TrustedNetworkProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trusted_networks` (`id`,`displayName`,`ssid`,`category`,`meshMode`,`allowedBssids`,`expectedSecurity`,`expectedFreqBands`,`pinnedDns`,`createdAtMs`,`lastSeenMs`,`maxNewBssidPerDay`,`bssidLearning`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrustedNetworkProfileEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDisplayName());
        }
        if (entity.getSsid() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSsid());
        }
        final String _tmp = __converters.categoryToString(entity.getCategory());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final int _tmp_1 = entity.getMeshMode() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        final String _tmp_2 = __converters.stringSetToJson(entity.getAllowedBssids());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        final String _tmp_3 = __converters.securitySetToJson(entity.getExpectedSecurity());
        if (_tmp_3 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_3);
        }
        final String _tmp_4 = __converters.bandSetToJson(entity.getExpectedFreqBands());
        if (_tmp_4 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_4);
        }
        final String _tmp_5 = __converters.stringListToJson(entity.getPinnedDns());
        if (_tmp_5 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_5);
        }
        statement.bindLong(10, entity.getCreatedAtMs());
        statement.bindLong(11, entity.getLastSeenMs());
        statement.bindLong(12, entity.getMaxNewBssidPerDay());
        final int _tmp_6 = entity.getBssidLearning() ? 1 : 0;
        statement.bindLong(13, _tmp_6);
      }
    };
    this.__preparedStmtOfMoveProfile = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE trusted_networks SET category = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trusted_networks WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final TrustedNetworkProfileEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrustedNetworkProfileEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object moveProfile(final String profileId, final NetworkCategory category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMoveProfile.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.categoryToString(category);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        if (profileId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, profileId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMoveProfile.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String profileId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        if (profileId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, profileId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TrustedNetworkProfileEntity>> all() {
    final String _sql = "SELECT * FROM trusted_networks ORDER BY displayName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trusted_networks"}, new Callable<List<TrustedNetworkProfileEntity>>() {
      @Override
      @NonNull
      public List<TrustedNetworkProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMeshMode = CursorUtil.getColumnIndexOrThrow(_cursor, "meshMode");
          final int _cursorIndexOfAllowedBssids = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedBssids");
          final int _cursorIndexOfExpectedSecurity = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedSecurity");
          final int _cursorIndexOfExpectedFreqBands = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedFreqBands");
          final int _cursorIndexOfPinnedDns = CursorUtil.getColumnIndexOrThrow(_cursor, "pinnedDns");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfLastSeenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeenMs");
          final int _cursorIndexOfMaxNewBssidPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "maxNewBssidPerDay");
          final int _cursorIndexOfBssidLearning = CursorUtil.getColumnIndexOrThrow(_cursor, "bssidLearning");
          final List<TrustedNetworkProfileEntity> _result = new ArrayList<TrustedNetworkProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrustedNetworkProfileEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final NetworkCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToCategory(_tmp);
            final boolean _tmpMeshMode;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMeshMode);
            _tmpMeshMode = _tmp_1 != 0;
            final Set<String> _tmpAllowedBssids;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAllowedBssids)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAllowedBssids);
            }
            _tmpAllowedBssids = __converters.jsonToStringSet(_tmp_2);
            final Set<SecurityType> _tmpExpectedSecurity;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpectedSecurity)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfExpectedSecurity);
            }
            _tmpExpectedSecurity = __converters.jsonToSecuritySet(_tmp_3);
            final Set<Band> _tmpExpectedFreqBands;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfExpectedFreqBands)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfExpectedFreqBands);
            }
            _tmpExpectedFreqBands = __converters.jsonToBandSet(_tmp_4);
            final List<String> _tmpPinnedDns;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfPinnedDns)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfPinnedDns);
            }
            _tmpPinnedDns = __converters.jsonToStringList(_tmp_5);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final long _tmpLastSeenMs;
            _tmpLastSeenMs = _cursor.getLong(_cursorIndexOfLastSeenMs);
            final int _tmpMaxNewBssidPerDay;
            _tmpMaxNewBssidPerDay = _cursor.getInt(_cursorIndexOfMaxNewBssidPerDay);
            final boolean _tmpBssidLearning;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfBssidLearning);
            _tmpBssidLearning = _tmp_6 != 0;
            _item = new TrustedNetworkProfileEntity(_tmpId,_tmpDisplayName,_tmpSsid,_tmpCategory,_tmpMeshMode,_tmpAllowedBssids,_tmpExpectedSecurity,_tmpExpectedFreqBands,_tmpPinnedDns,_tmpCreatedAtMs,_tmpLastSeenMs,_tmpMaxNewBssidPerDay,_tmpBssidLearning);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object allOnce(final Continuation<? super List<TrustedNetworkProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM trusted_networks ORDER BY displayName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TrustedNetworkProfileEntity>>() {
      @Override
      @NonNull
      public List<TrustedNetworkProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMeshMode = CursorUtil.getColumnIndexOrThrow(_cursor, "meshMode");
          final int _cursorIndexOfAllowedBssids = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedBssids");
          final int _cursorIndexOfExpectedSecurity = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedSecurity");
          final int _cursorIndexOfExpectedFreqBands = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedFreqBands");
          final int _cursorIndexOfPinnedDns = CursorUtil.getColumnIndexOrThrow(_cursor, "pinnedDns");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfLastSeenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeenMs");
          final int _cursorIndexOfMaxNewBssidPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "maxNewBssidPerDay");
          final int _cursorIndexOfBssidLearning = CursorUtil.getColumnIndexOrThrow(_cursor, "bssidLearning");
          final List<TrustedNetworkProfileEntity> _result = new ArrayList<TrustedNetworkProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrustedNetworkProfileEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final NetworkCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToCategory(_tmp);
            final boolean _tmpMeshMode;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMeshMode);
            _tmpMeshMode = _tmp_1 != 0;
            final Set<String> _tmpAllowedBssids;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfAllowedBssids)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfAllowedBssids);
            }
            _tmpAllowedBssids = __converters.jsonToStringSet(_tmp_2);
            final Set<SecurityType> _tmpExpectedSecurity;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfExpectedSecurity)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfExpectedSecurity);
            }
            _tmpExpectedSecurity = __converters.jsonToSecuritySet(_tmp_3);
            final Set<Band> _tmpExpectedFreqBands;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfExpectedFreqBands)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfExpectedFreqBands);
            }
            _tmpExpectedFreqBands = __converters.jsonToBandSet(_tmp_4);
            final List<String> _tmpPinnedDns;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfPinnedDns)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfPinnedDns);
            }
            _tmpPinnedDns = __converters.jsonToStringList(_tmp_5);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final long _tmpLastSeenMs;
            _tmpLastSeenMs = _cursor.getLong(_cursorIndexOfLastSeenMs);
            final int _tmpMaxNewBssidPerDay;
            _tmpMaxNewBssidPerDay = _cursor.getInt(_cursorIndexOfMaxNewBssidPerDay);
            final boolean _tmpBssidLearning;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfBssidLearning);
            _tmpBssidLearning = _tmp_6 != 0;
            _item = new TrustedNetworkProfileEntity(_tmpId,_tmpDisplayName,_tmpSsid,_tmpCategory,_tmpMeshMode,_tmpAllowedBssids,_tmpExpectedSecurity,_tmpExpectedFreqBands,_tmpPinnedDns,_tmpCreatedAtMs,_tmpLastSeenMs,_tmpMaxNewBssidPerDay,_tmpBssidLearning);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TrustedNetworkProfileEntity>> byCategory(final NetworkCategory category) {
    final String _sql = "SELECT * FROM trusted_networks WHERE category = ? ORDER BY displayName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.categoryToString(category);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trusted_networks"}, new Callable<List<TrustedNetworkProfileEntity>>() {
      @Override
      @NonNull
      public List<TrustedNetworkProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMeshMode = CursorUtil.getColumnIndexOrThrow(_cursor, "meshMode");
          final int _cursorIndexOfAllowedBssids = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedBssids");
          final int _cursorIndexOfExpectedSecurity = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedSecurity");
          final int _cursorIndexOfExpectedFreqBands = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedFreqBands");
          final int _cursorIndexOfPinnedDns = CursorUtil.getColumnIndexOrThrow(_cursor, "pinnedDns");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfLastSeenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeenMs");
          final int _cursorIndexOfMaxNewBssidPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "maxNewBssidPerDay");
          final int _cursorIndexOfBssidLearning = CursorUtil.getColumnIndexOrThrow(_cursor, "bssidLearning");
          final List<TrustedNetworkProfileEntity> _result = new ArrayList<TrustedNetworkProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrustedNetworkProfileEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final NetworkCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToCategory(_tmp_1);
            final boolean _tmpMeshMode;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMeshMode);
            _tmpMeshMode = _tmp_2 != 0;
            final Set<String> _tmpAllowedBssids;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfAllowedBssids)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfAllowedBssids);
            }
            _tmpAllowedBssids = __converters.jsonToStringSet(_tmp_3);
            final Set<SecurityType> _tmpExpectedSecurity;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfExpectedSecurity)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfExpectedSecurity);
            }
            _tmpExpectedSecurity = __converters.jsonToSecuritySet(_tmp_4);
            final Set<Band> _tmpExpectedFreqBands;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfExpectedFreqBands)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfExpectedFreqBands);
            }
            _tmpExpectedFreqBands = __converters.jsonToBandSet(_tmp_5);
            final List<String> _tmpPinnedDns;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfPinnedDns)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfPinnedDns);
            }
            _tmpPinnedDns = __converters.jsonToStringList(_tmp_6);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final long _tmpLastSeenMs;
            _tmpLastSeenMs = _cursor.getLong(_cursorIndexOfLastSeenMs);
            final int _tmpMaxNewBssidPerDay;
            _tmpMaxNewBssidPerDay = _cursor.getInt(_cursorIndexOfMaxNewBssidPerDay);
            final boolean _tmpBssidLearning;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfBssidLearning);
            _tmpBssidLearning = _tmp_7 != 0;
            _item = new TrustedNetworkProfileEntity(_tmpId,_tmpDisplayName,_tmpSsid,_tmpCategory,_tmpMeshMode,_tmpAllowedBssids,_tmpExpectedSecurity,_tmpExpectedFreqBands,_tmpPinnedDns,_tmpCreatedAtMs,_tmpLastSeenMs,_tmpMaxNewBssidPerDay,_tmpBssidLearning);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object byCategoryOnce(final NetworkCategory category,
      final Continuation<? super List<TrustedNetworkProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM trusted_networks WHERE category = ? ORDER BY displayName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.categoryToString(category);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TrustedNetworkProfileEntity>>() {
      @Override
      @NonNull
      public List<TrustedNetworkProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfMeshMode = CursorUtil.getColumnIndexOrThrow(_cursor, "meshMode");
          final int _cursorIndexOfAllowedBssids = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedBssids");
          final int _cursorIndexOfExpectedSecurity = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedSecurity");
          final int _cursorIndexOfExpectedFreqBands = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedFreqBands");
          final int _cursorIndexOfPinnedDns = CursorUtil.getColumnIndexOrThrow(_cursor, "pinnedDns");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final int _cursorIndexOfLastSeenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeenMs");
          final int _cursorIndexOfMaxNewBssidPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "maxNewBssidPerDay");
          final int _cursorIndexOfBssidLearning = CursorUtil.getColumnIndexOrThrow(_cursor, "bssidLearning");
          final List<TrustedNetworkProfileEntity> _result = new ArrayList<TrustedNetworkProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrustedNetworkProfileEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpDisplayName;
            if (_cursor.isNull(_cursorIndexOfDisplayName)) {
              _tmpDisplayName = null;
            } else {
              _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            }
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final NetworkCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToCategory(_tmp_1);
            final boolean _tmpMeshMode;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMeshMode);
            _tmpMeshMode = _tmp_2 != 0;
            final Set<String> _tmpAllowedBssids;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfAllowedBssids)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfAllowedBssids);
            }
            _tmpAllowedBssids = __converters.jsonToStringSet(_tmp_3);
            final Set<SecurityType> _tmpExpectedSecurity;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfExpectedSecurity)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfExpectedSecurity);
            }
            _tmpExpectedSecurity = __converters.jsonToSecuritySet(_tmp_4);
            final Set<Band> _tmpExpectedFreqBands;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfExpectedFreqBands)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfExpectedFreqBands);
            }
            _tmpExpectedFreqBands = __converters.jsonToBandSet(_tmp_5);
            final List<String> _tmpPinnedDns;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfPinnedDns)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfPinnedDns);
            }
            _tmpPinnedDns = __converters.jsonToStringList(_tmp_6);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            final long _tmpLastSeenMs;
            _tmpLastSeenMs = _cursor.getLong(_cursorIndexOfLastSeenMs);
            final int _tmpMaxNewBssidPerDay;
            _tmpMaxNewBssidPerDay = _cursor.getInt(_cursorIndexOfMaxNewBssidPerDay);
            final boolean _tmpBssidLearning;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfBssidLearning);
            _tmpBssidLearning = _tmp_7 != 0;
            _item = new TrustedNetworkProfileEntity(_tmpId,_tmpDisplayName,_tmpSsid,_tmpCategory,_tmpMeshMode,_tmpAllowedBssids,_tmpExpectedSecurity,_tmpExpectedFreqBands,_tmpPinnedDns,_tmpCreatedAtMs,_tmpLastSeenMs,_tmpMaxNewBssidPerDay,_tmpBssidLearning);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

package com.wifisentinel.core.storage.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.wifisentinel.core.storage.db.Converters;
import com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity;
import com.wifisentinel.core.wifi.SecurityType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SnapshotDao_Impl implements SnapshotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NetworkSnapshotEntity> __insertionAdapterOfNetworkSnapshotEntity;

  private final Converters __converters = new Converters();

  public SnapshotDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNetworkSnapshotEntity = new EntityInsertionAdapter<NetworkSnapshotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `network_snapshots` (`id`,`timestampMs`,`ssid`,`bssid`,`securityType`,`frequencyMhz`,`rssiDbm`,`ipV4`,`gatewayV4`,`dnsServers`,`captivePortal`,`networkIdHint`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NetworkSnapshotEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getTimestampMs());
        if (entity.getSsid() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSsid());
        }
        if (entity.getBssid() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBssid());
        }
        final String _tmp = __converters.securityToString(entity.getSecurityType());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getFrequencyMhz() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getFrequencyMhz());
        }
        if (entity.getRssiDbm() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getRssiDbm());
        }
        if (entity.getIpV4() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getIpV4());
        }
        if (entity.getGatewayV4() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getGatewayV4());
        }
        final String _tmp_1 = __converters.stringListToJson(entity.getDnsServers());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        final int _tmp_2 = entity.getCaptivePortal() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        if (entity.getNetworkIdHint() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getNetworkIdHint());
        }
      }
    };
  }

  @Override
  public Object upsert(final NetworkSnapshotEntity entity, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNetworkSnapshotEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<NetworkSnapshotEntity> latest() {
    final String _sql = "SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"network_snapshots"}, new Callable<NetworkSnapshotEntity>() {
      @Override
      @Nullable
      public NetworkSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfBssid = CursorUtil.getColumnIndexOrThrow(_cursor, "bssid");
          final int _cursorIndexOfSecurityType = CursorUtil.getColumnIndexOrThrow(_cursor, "securityType");
          final int _cursorIndexOfFrequencyMhz = CursorUtil.getColumnIndexOrThrow(_cursor, "frequencyMhz");
          final int _cursorIndexOfRssiDbm = CursorUtil.getColumnIndexOrThrow(_cursor, "rssiDbm");
          final int _cursorIndexOfIpV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "ipV4");
          final int _cursorIndexOfGatewayV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "gatewayV4");
          final int _cursorIndexOfDnsServers = CursorUtil.getColumnIndexOrThrow(_cursor, "dnsServers");
          final int _cursorIndexOfCaptivePortal = CursorUtil.getColumnIndexOrThrow(_cursor, "captivePortal");
          final int _cursorIndexOfNetworkIdHint = CursorUtil.getColumnIndexOrThrow(_cursor, "networkIdHint");
          final NetworkSnapshotEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpBssid;
            if (_cursor.isNull(_cursorIndexOfBssid)) {
              _tmpBssid = null;
            } else {
              _tmpBssid = _cursor.getString(_cursorIndexOfBssid);
            }
            final SecurityType _tmpSecurityType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSecurityType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSecurityType);
            }
            _tmpSecurityType = __converters.stringToSecurity(_tmp);
            final Integer _tmpFrequencyMhz;
            if (_cursor.isNull(_cursorIndexOfFrequencyMhz)) {
              _tmpFrequencyMhz = null;
            } else {
              _tmpFrequencyMhz = _cursor.getInt(_cursorIndexOfFrequencyMhz);
            }
            final Integer _tmpRssiDbm;
            if (_cursor.isNull(_cursorIndexOfRssiDbm)) {
              _tmpRssiDbm = null;
            } else {
              _tmpRssiDbm = _cursor.getInt(_cursorIndexOfRssiDbm);
            }
            final String _tmpIpV4;
            if (_cursor.isNull(_cursorIndexOfIpV4)) {
              _tmpIpV4 = null;
            } else {
              _tmpIpV4 = _cursor.getString(_cursorIndexOfIpV4);
            }
            final String _tmpGatewayV4;
            if (_cursor.isNull(_cursorIndexOfGatewayV4)) {
              _tmpGatewayV4 = null;
            } else {
              _tmpGatewayV4 = _cursor.getString(_cursorIndexOfGatewayV4);
            }
            final List<String> _tmpDnsServers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDnsServers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDnsServers);
            }
            _tmpDnsServers = __converters.jsonToStringList(_tmp_1);
            final boolean _tmpCaptivePortal;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCaptivePortal);
            _tmpCaptivePortal = _tmp_2 != 0;
            final String _tmpNetworkIdHint;
            if (_cursor.isNull(_cursorIndexOfNetworkIdHint)) {
              _tmpNetworkIdHint = null;
            } else {
              _tmpNetworkIdHint = _cursor.getString(_cursorIndexOfNetworkIdHint);
            }
            _result = new NetworkSnapshotEntity(_tmpId,_tmpTimestampMs,_tmpSsid,_tmpBssid,_tmpSecurityType,_tmpFrequencyMhz,_tmpRssiDbm,_tmpIpV4,_tmpGatewayV4,_tmpDnsServers,_tmpCaptivePortal,_tmpNetworkIdHint);
          } else {
            _result = null;
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
  public Object latestOnce(final Continuation<? super NetworkSnapshotEntity> arg0) {
    final String _sql = "SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NetworkSnapshotEntity>() {
      @Override
      @Nullable
      public NetworkSnapshotEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfBssid = CursorUtil.getColumnIndexOrThrow(_cursor, "bssid");
          final int _cursorIndexOfSecurityType = CursorUtil.getColumnIndexOrThrow(_cursor, "securityType");
          final int _cursorIndexOfFrequencyMhz = CursorUtil.getColumnIndexOrThrow(_cursor, "frequencyMhz");
          final int _cursorIndexOfRssiDbm = CursorUtil.getColumnIndexOrThrow(_cursor, "rssiDbm");
          final int _cursorIndexOfIpV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "ipV4");
          final int _cursorIndexOfGatewayV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "gatewayV4");
          final int _cursorIndexOfDnsServers = CursorUtil.getColumnIndexOrThrow(_cursor, "dnsServers");
          final int _cursorIndexOfCaptivePortal = CursorUtil.getColumnIndexOrThrow(_cursor, "captivePortal");
          final int _cursorIndexOfNetworkIdHint = CursorUtil.getColumnIndexOrThrow(_cursor, "networkIdHint");
          final NetworkSnapshotEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpBssid;
            if (_cursor.isNull(_cursorIndexOfBssid)) {
              _tmpBssid = null;
            } else {
              _tmpBssid = _cursor.getString(_cursorIndexOfBssid);
            }
            final SecurityType _tmpSecurityType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSecurityType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSecurityType);
            }
            _tmpSecurityType = __converters.stringToSecurity(_tmp);
            final Integer _tmpFrequencyMhz;
            if (_cursor.isNull(_cursorIndexOfFrequencyMhz)) {
              _tmpFrequencyMhz = null;
            } else {
              _tmpFrequencyMhz = _cursor.getInt(_cursorIndexOfFrequencyMhz);
            }
            final Integer _tmpRssiDbm;
            if (_cursor.isNull(_cursorIndexOfRssiDbm)) {
              _tmpRssiDbm = null;
            } else {
              _tmpRssiDbm = _cursor.getInt(_cursorIndexOfRssiDbm);
            }
            final String _tmpIpV4;
            if (_cursor.isNull(_cursorIndexOfIpV4)) {
              _tmpIpV4 = null;
            } else {
              _tmpIpV4 = _cursor.getString(_cursorIndexOfIpV4);
            }
            final String _tmpGatewayV4;
            if (_cursor.isNull(_cursorIndexOfGatewayV4)) {
              _tmpGatewayV4 = null;
            } else {
              _tmpGatewayV4 = _cursor.getString(_cursorIndexOfGatewayV4);
            }
            final List<String> _tmpDnsServers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDnsServers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDnsServers);
            }
            _tmpDnsServers = __converters.jsonToStringList(_tmp_1);
            final boolean _tmpCaptivePortal;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCaptivePortal);
            _tmpCaptivePortal = _tmp_2 != 0;
            final String _tmpNetworkIdHint;
            if (_cursor.isNull(_cursorIndexOfNetworkIdHint)) {
              _tmpNetworkIdHint = null;
            } else {
              _tmpNetworkIdHint = _cursor.getString(_cursorIndexOfNetworkIdHint);
            }
            _result = new NetworkSnapshotEntity(_tmpId,_tmpTimestampMs,_tmpSsid,_tmpBssid,_tmpSecurityType,_tmpFrequencyMhz,_tmpRssiDbm,_tmpIpV4,_tmpGatewayV4,_tmpDnsServers,_tmpCaptivePortal,_tmpNetworkIdHint);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object latestList(final int limit,
      final Continuation<? super List<NetworkSnapshotEntity>> arg1) {
    final String _sql = "SELECT * FROM network_snapshots ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NetworkSnapshotEntity>>() {
      @Override
      @NonNull
      public List<NetworkSnapshotEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfBssid = CursorUtil.getColumnIndexOrThrow(_cursor, "bssid");
          final int _cursorIndexOfSecurityType = CursorUtil.getColumnIndexOrThrow(_cursor, "securityType");
          final int _cursorIndexOfFrequencyMhz = CursorUtil.getColumnIndexOrThrow(_cursor, "frequencyMhz");
          final int _cursorIndexOfRssiDbm = CursorUtil.getColumnIndexOrThrow(_cursor, "rssiDbm");
          final int _cursorIndexOfIpV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "ipV4");
          final int _cursorIndexOfGatewayV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "gatewayV4");
          final int _cursorIndexOfDnsServers = CursorUtil.getColumnIndexOrThrow(_cursor, "dnsServers");
          final int _cursorIndexOfCaptivePortal = CursorUtil.getColumnIndexOrThrow(_cursor, "captivePortal");
          final int _cursorIndexOfNetworkIdHint = CursorUtil.getColumnIndexOrThrow(_cursor, "networkIdHint");
          final List<NetworkSnapshotEntity> _result = new ArrayList<NetworkSnapshotEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NetworkSnapshotEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpBssid;
            if (_cursor.isNull(_cursorIndexOfBssid)) {
              _tmpBssid = null;
            } else {
              _tmpBssid = _cursor.getString(_cursorIndexOfBssid);
            }
            final SecurityType _tmpSecurityType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSecurityType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSecurityType);
            }
            _tmpSecurityType = __converters.stringToSecurity(_tmp);
            final Integer _tmpFrequencyMhz;
            if (_cursor.isNull(_cursorIndexOfFrequencyMhz)) {
              _tmpFrequencyMhz = null;
            } else {
              _tmpFrequencyMhz = _cursor.getInt(_cursorIndexOfFrequencyMhz);
            }
            final Integer _tmpRssiDbm;
            if (_cursor.isNull(_cursorIndexOfRssiDbm)) {
              _tmpRssiDbm = null;
            } else {
              _tmpRssiDbm = _cursor.getInt(_cursorIndexOfRssiDbm);
            }
            final String _tmpIpV4;
            if (_cursor.isNull(_cursorIndexOfIpV4)) {
              _tmpIpV4 = null;
            } else {
              _tmpIpV4 = _cursor.getString(_cursorIndexOfIpV4);
            }
            final String _tmpGatewayV4;
            if (_cursor.isNull(_cursorIndexOfGatewayV4)) {
              _tmpGatewayV4 = null;
            } else {
              _tmpGatewayV4 = _cursor.getString(_cursorIndexOfGatewayV4);
            }
            final List<String> _tmpDnsServers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDnsServers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDnsServers);
            }
            _tmpDnsServers = __converters.jsonToStringList(_tmp_1);
            final boolean _tmpCaptivePortal;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCaptivePortal);
            _tmpCaptivePortal = _tmp_2 != 0;
            final String _tmpNetworkIdHint;
            if (_cursor.isNull(_cursorIndexOfNetworkIdHint)) {
              _tmpNetworkIdHint = null;
            } else {
              _tmpNetworkIdHint = _cursor.getString(_cursorIndexOfNetworkIdHint);
            }
            _item = new NetworkSnapshotEntity(_tmpId,_tmpTimestampMs,_tmpSsid,_tmpBssid,_tmpSecurityType,_tmpFrequencyMhz,_tmpRssiDbm,_tmpIpV4,_tmpGatewayV4,_tmpDnsServers,_tmpCaptivePortal,_tmpNetworkIdHint);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object recentByNetworkId(final String networkIdHint, final int limit,
      final Continuation<? super List<NetworkSnapshotEntity>> arg2) {
    final String _sql = "SELECT * FROM network_snapshots WHERE networkIdHint = ? ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (networkIdHint == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, networkIdHint);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NetworkSnapshotEntity>>() {
      @Override
      @NonNull
      public List<NetworkSnapshotEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfSsid = CursorUtil.getColumnIndexOrThrow(_cursor, "ssid");
          final int _cursorIndexOfBssid = CursorUtil.getColumnIndexOrThrow(_cursor, "bssid");
          final int _cursorIndexOfSecurityType = CursorUtil.getColumnIndexOrThrow(_cursor, "securityType");
          final int _cursorIndexOfFrequencyMhz = CursorUtil.getColumnIndexOrThrow(_cursor, "frequencyMhz");
          final int _cursorIndexOfRssiDbm = CursorUtil.getColumnIndexOrThrow(_cursor, "rssiDbm");
          final int _cursorIndexOfIpV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "ipV4");
          final int _cursorIndexOfGatewayV4 = CursorUtil.getColumnIndexOrThrow(_cursor, "gatewayV4");
          final int _cursorIndexOfDnsServers = CursorUtil.getColumnIndexOrThrow(_cursor, "dnsServers");
          final int _cursorIndexOfCaptivePortal = CursorUtil.getColumnIndexOrThrow(_cursor, "captivePortal");
          final int _cursorIndexOfNetworkIdHint = CursorUtil.getColumnIndexOrThrow(_cursor, "networkIdHint");
          final List<NetworkSnapshotEntity> _result = new ArrayList<NetworkSnapshotEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NetworkSnapshotEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpSsid;
            if (_cursor.isNull(_cursorIndexOfSsid)) {
              _tmpSsid = null;
            } else {
              _tmpSsid = _cursor.getString(_cursorIndexOfSsid);
            }
            final String _tmpBssid;
            if (_cursor.isNull(_cursorIndexOfBssid)) {
              _tmpBssid = null;
            } else {
              _tmpBssid = _cursor.getString(_cursorIndexOfBssid);
            }
            final SecurityType _tmpSecurityType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSecurityType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSecurityType);
            }
            _tmpSecurityType = __converters.stringToSecurity(_tmp);
            final Integer _tmpFrequencyMhz;
            if (_cursor.isNull(_cursorIndexOfFrequencyMhz)) {
              _tmpFrequencyMhz = null;
            } else {
              _tmpFrequencyMhz = _cursor.getInt(_cursorIndexOfFrequencyMhz);
            }
            final Integer _tmpRssiDbm;
            if (_cursor.isNull(_cursorIndexOfRssiDbm)) {
              _tmpRssiDbm = null;
            } else {
              _tmpRssiDbm = _cursor.getInt(_cursorIndexOfRssiDbm);
            }
            final String _tmpIpV4;
            if (_cursor.isNull(_cursorIndexOfIpV4)) {
              _tmpIpV4 = null;
            } else {
              _tmpIpV4 = _cursor.getString(_cursorIndexOfIpV4);
            }
            final String _tmpGatewayV4;
            if (_cursor.isNull(_cursorIndexOfGatewayV4)) {
              _tmpGatewayV4 = null;
            } else {
              _tmpGatewayV4 = _cursor.getString(_cursorIndexOfGatewayV4);
            }
            final List<String> _tmpDnsServers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDnsServers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDnsServers);
            }
            _tmpDnsServers = __converters.jsonToStringList(_tmp_1);
            final boolean _tmpCaptivePortal;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCaptivePortal);
            _tmpCaptivePortal = _tmp_2 != 0;
            final String _tmpNetworkIdHint;
            if (_cursor.isNull(_cursorIndexOfNetworkIdHint)) {
              _tmpNetworkIdHint = null;
            } else {
              _tmpNetworkIdHint = _cursor.getString(_cursorIndexOfNetworkIdHint);
            }
            _item = new NetworkSnapshotEntity(_tmpId,_tmpTimestampMs,_tmpSsid,_tmpBssid,_tmpSecurityType,_tmpFrequencyMhz,_tmpRssiDbm,_tmpIpV4,_tmpGatewayV4,_tmpDnsServers,_tmpCaptivePortal,_tmpNetworkIdHint);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

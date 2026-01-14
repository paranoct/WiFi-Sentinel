package com.wifisentinel.core.storage.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.wifisentinel.core.detectors.Severity;
import com.wifisentinel.core.storage.db.Converters;
import com.wifisentinel.core.storage.db.entity.EventEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class EventDao_Impl implements EventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EventEntity> __insertionAdapterOfEventEntity;

  private final Converters __converters = new Converters();

  public EventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEventEntity = new EntityInsertionAdapter<EventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `events` (`id`,`timestampMs`,`title`,`detail`,`severity`,`snapshotId`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EventEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getTimestampMs());
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getDetail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDetail());
        }
        final String _tmp = __converters.severityToString(entity.getSeverity());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getSnapshotId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSnapshotId());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<EventEntity> entities, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEventEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<EventEntity>> latest(final int limit) {
    final String _sql = "SELECT * FROM events ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            _item = new EventEntity(_tmpId,_tmpTimestampMs,_tmpTitle,_tmpDetail,_tmpSeverity,_tmpSnapshotId);
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
  public Object latestOnce(final int limit, final Continuation<? super List<EventEntity>> arg1) {
    final String _sql = "SELECT * FROM events ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            _item = new EventEntity(_tmpId,_tmpTimestampMs,_tmpTitle,_tmpDetail,_tmpSeverity,_tmpSnapshotId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

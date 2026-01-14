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
import com.wifisentinel.core.detectors.FindingActionType;
import com.wifisentinel.core.detectors.Severity;
import com.wifisentinel.core.storage.db.Converters;
import com.wifisentinel.core.storage.db.entity.FindingEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FindingDao_Impl implements FindingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FindingEntity> __insertionAdapterOfFindingEntity;

  private final Converters __converters = new Converters();

  public FindingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFindingEntity = new EntityInsertionAdapter<FindingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `findings` (`id`,`snapshotId`,`timestampMs`,`detectorId`,`severity`,`scoreDelta`,`title`,`explanation`,`evidence`,`actions`,`dedupKey`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FindingEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getSnapshotId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getSnapshotId());
        }
        statement.bindLong(3, entity.getTimestampMs());
        if (entity.getDetectorId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDetectorId());
        }
        final String _tmp = __converters.severityToString(entity.getSeverity());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        statement.bindLong(6, entity.getScoreDelta());
        if (entity.getTitle() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTitle());
        }
        if (entity.getExplanation() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getExplanation());
        }
        final String _tmp_1 = __converters.mapToJson(entity.getEvidence());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = __converters.actionsToJson(entity.getActions());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getDedupKey() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDedupKey());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<FindingEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFindingEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FindingEntity>> forSnapshot(final String snapshotId) {
    final String _sql = "SELECT * FROM findings WHERE snapshotId = ? ORDER BY timestampMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (snapshotId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, snapshotId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"findings"}, new Callable<List<FindingEntity>>() {
      @Override
      @NonNull
      public List<FindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfDetectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "detectorId");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfScoreDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "scoreDelta");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfDedupKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dedupKey");
          final List<FindingEntity> _result = new ArrayList<FindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpDetectorId;
            if (_cursor.isNull(_cursorIndexOfDetectorId)) {
              _tmpDetectorId = null;
            } else {
              _tmpDetectorId = _cursor.getString(_cursorIndexOfDetectorId);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final int _tmpScoreDelta;
            _tmpScoreDelta = _cursor.getInt(_cursorIndexOfScoreDelta);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            final Map<String, String> _tmpEvidence;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEvidence);
            }
            _tmpEvidence = __converters.jsonToMap(_tmp_1);
            final List<FindingActionType> _tmpActions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActions);
            }
            _tmpActions = __converters.jsonToActions(_tmp_2);
            final String _tmpDedupKey;
            if (_cursor.isNull(_cursorIndexOfDedupKey)) {
              _tmpDedupKey = null;
            } else {
              _tmpDedupKey = _cursor.getString(_cursorIndexOfDedupKey);
            }
            _item = new FindingEntity(_tmpId,_tmpSnapshotId,_tmpTimestampMs,_tmpDetectorId,_tmpSeverity,_tmpScoreDelta,_tmpTitle,_tmpExplanation,_tmpEvidence,_tmpActions,_tmpDedupKey);
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
  public Object forSnapshotOnce(final String snapshotId,
      final Continuation<? super List<FindingEntity>> $completion) {
    final String _sql = "SELECT * FROM findings WHERE snapshotId = ? ORDER BY timestampMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (snapshotId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, snapshotId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FindingEntity>>() {
      @Override
      @NonNull
      public List<FindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfDetectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "detectorId");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfScoreDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "scoreDelta");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfDedupKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dedupKey");
          final List<FindingEntity> _result = new ArrayList<FindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpDetectorId;
            if (_cursor.isNull(_cursorIndexOfDetectorId)) {
              _tmpDetectorId = null;
            } else {
              _tmpDetectorId = _cursor.getString(_cursorIndexOfDetectorId);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final int _tmpScoreDelta;
            _tmpScoreDelta = _cursor.getInt(_cursorIndexOfScoreDelta);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            final Map<String, String> _tmpEvidence;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEvidence);
            }
            _tmpEvidence = __converters.jsonToMap(_tmp_1);
            final List<FindingActionType> _tmpActions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActions);
            }
            _tmpActions = __converters.jsonToActions(_tmp_2);
            final String _tmpDedupKey;
            if (_cursor.isNull(_cursorIndexOfDedupKey)) {
              _tmpDedupKey = null;
            } else {
              _tmpDedupKey = _cursor.getString(_cursorIndexOfDedupKey);
            }
            _item = new FindingEntity(_tmpId,_tmpSnapshotId,_tmpTimestampMs,_tmpDetectorId,_tmpSeverity,_tmpScoreDelta,_tmpTitle,_tmpExplanation,_tmpEvidence,_tmpActions,_tmpDedupKey);
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
  public Flow<List<FindingEntity>> latest(final int limit) {
    final String _sql = "SELECT * FROM findings ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"findings"}, new Callable<List<FindingEntity>>() {
      @Override
      @NonNull
      public List<FindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfDetectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "detectorId");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfScoreDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "scoreDelta");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfDedupKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dedupKey");
          final List<FindingEntity> _result = new ArrayList<FindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpDetectorId;
            if (_cursor.isNull(_cursorIndexOfDetectorId)) {
              _tmpDetectorId = null;
            } else {
              _tmpDetectorId = _cursor.getString(_cursorIndexOfDetectorId);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final int _tmpScoreDelta;
            _tmpScoreDelta = _cursor.getInt(_cursorIndexOfScoreDelta);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            final Map<String, String> _tmpEvidence;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEvidence);
            }
            _tmpEvidence = __converters.jsonToMap(_tmp_1);
            final List<FindingActionType> _tmpActions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActions);
            }
            _tmpActions = __converters.jsonToActions(_tmp_2);
            final String _tmpDedupKey;
            if (_cursor.isNull(_cursorIndexOfDedupKey)) {
              _tmpDedupKey = null;
            } else {
              _tmpDedupKey = _cursor.getString(_cursorIndexOfDedupKey);
            }
            _item = new FindingEntity(_tmpId,_tmpSnapshotId,_tmpTimestampMs,_tmpDetectorId,_tmpSeverity,_tmpScoreDelta,_tmpTitle,_tmpExplanation,_tmpEvidence,_tmpActions,_tmpDedupKey);
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
  public Object latestOnce(final int limit,
      final Continuation<? super List<FindingEntity>> $completion) {
    final String _sql = "SELECT * FROM findings ORDER BY timestampMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FindingEntity>>() {
      @Override
      @NonNull
      public List<FindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSnapshotId = CursorUtil.getColumnIndexOrThrow(_cursor, "snapshotId");
          final int _cursorIndexOfTimestampMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampMs");
          final int _cursorIndexOfDetectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "detectorId");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfScoreDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "scoreDelta");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfDedupKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dedupKey");
          final List<FindingEntity> _result = new ArrayList<FindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSnapshotId;
            if (_cursor.isNull(_cursorIndexOfSnapshotId)) {
              _tmpSnapshotId = null;
            } else {
              _tmpSnapshotId = _cursor.getString(_cursorIndexOfSnapshotId);
            }
            final long _tmpTimestampMs;
            _tmpTimestampMs = _cursor.getLong(_cursorIndexOfTimestampMs);
            final String _tmpDetectorId;
            if (_cursor.isNull(_cursorIndexOfDetectorId)) {
              _tmpDetectorId = null;
            } else {
              _tmpDetectorId = _cursor.getString(_cursorIndexOfDetectorId);
            }
            final Severity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __converters.stringToSeverity(_tmp);
            final int _tmpScoreDelta;
            _tmpScoreDelta = _cursor.getInt(_cursorIndexOfScoreDelta);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpExplanation;
            if (_cursor.isNull(_cursorIndexOfExplanation)) {
              _tmpExplanation = null;
            } else {
              _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            }
            final Map<String, String> _tmpEvidence;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEvidence);
            }
            _tmpEvidence = __converters.jsonToMap(_tmp_1);
            final List<FindingActionType> _tmpActions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfActions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfActions);
            }
            _tmpActions = __converters.jsonToActions(_tmp_2);
            final String _tmpDedupKey;
            if (_cursor.isNull(_cursorIndexOfDedupKey)) {
              _tmpDedupKey = null;
            } else {
              _tmpDedupKey = _cursor.getString(_cursorIndexOfDedupKey);
            }
            _item = new FindingEntity(_tmpId,_tmpSnapshotId,_tmpTimestampMs,_tmpDetectorId,_tmpSeverity,_tmpScoreDelta,_tmpTitle,_tmpExplanation,_tmpEvidence,_tmpActions,_tmpDedupKey);
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
  public Object latestTimestampForDedupKey(final String dedupKey,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT timestampMs FROM findings WHERE dedupKey = ? ORDER BY timestampMs DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (dedupKey == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, dedupKey);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
          } else {
            _result = null;
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

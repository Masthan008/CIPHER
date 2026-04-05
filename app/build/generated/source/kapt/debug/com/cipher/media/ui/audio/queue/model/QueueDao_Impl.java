package com.cipher.media.ui.audio.queue.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class QueueDao_Impl implements QueueDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlaybackQueueEntity> __insertionAdapterOfPlaybackQueueEntity;

  private final EntityInsertionAdapter<QueueItemEntity> __insertionAdapterOfQueueItemEntity;

  private final EntityDeletionOrUpdateAdapter<PlaybackQueueEntity> __deletionAdapterOfPlaybackQueueEntity;

  private final EntityDeletionOrUpdateAdapter<PlaybackQueueEntity> __updateAdapterOfPlaybackQueueEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateAll;

  private final SharedSQLiteStatement __preparedStmtOfSetActive;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaybackPosition;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaybackMode;

  private final SharedSQLiteStatement __preparedStmtOfClearItems;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItem;

  public QueueDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaybackQueueEntity = new EntityInsertionAdapter<PlaybackQueueEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `playback_queues` (`id`,`name`,`iconName`,`currentIndex`,`currentPositionMs`,`playbackMode`,`createdAt`,`updatedAt`,`isActive`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaybackQueueEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getIconName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getIconName());
        }
        statement.bindLong(4, entity.getCurrentIndex());
        statement.bindLong(5, entity.getCurrentPositionMs());
        if (entity.getPlaybackMode() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPlaybackMode());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
      }
    };
    this.__insertionAdapterOfQueueItemEntity = new EntityInsertionAdapter<QueueItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `queue_items` (`id`,`queueId`,`position`,`uri`,`title`,`artist`,`album`,`albumArtUri`,`durationMs`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QueueItemEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getQueueId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getQueueId());
        }
        statement.bindLong(3, entity.getPosition());
        if (entity.getUri() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUri());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        if (entity.getArtist() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getArtist());
        }
        if (entity.getAlbum() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAlbum());
        }
        if (entity.getAlbumArtUri() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAlbumArtUri());
        }
        statement.bindLong(9, entity.getDurationMs());
      }
    };
    this.__deletionAdapterOfPlaybackQueueEntity = new EntityDeletionOrUpdateAdapter<PlaybackQueueEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `playback_queues` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaybackQueueEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfPlaybackQueueEntity = new EntityDeletionOrUpdateAdapter<PlaybackQueueEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `playback_queues` SET `id` = ?,`name` = ?,`iconName` = ?,`currentIndex` = ?,`currentPositionMs` = ?,`playbackMode` = ?,`createdAt` = ?,`updatedAt` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaybackQueueEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getIconName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getIconName());
        }
        statement.bindLong(4, entity.getCurrentIndex());
        statement.bindLong(5, entity.getCurrentPositionMs());
        if (entity.getPlaybackMode() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPlaybackMode());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeactivateAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playback_queues SET isActive = 0";
        return _query;
      }
    };
    this.__preparedStmtOfSetActive = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playback_queues SET isActive = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlaybackPosition = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playback_queues SET currentIndex = ?, currentPositionMs = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlaybackMode = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE playback_queues SET playbackMode = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearItems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM queue_items WHERE queueId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteItem = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM queue_items WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertQueue(final PlaybackQueueEntity queue,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaybackQueueEntity.insert(queue);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertItems(final List<QueueItemEntity> items,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQueueItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteQueue(final PlaybackQueueEntity queue,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPlaybackQueueEntity.handle(queue);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateQueue(final PlaybackQueueEntity queue,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlaybackQueueEntity.handle(queue);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object replaceQueueItems(final String queueId, final List<QueueItemEntity> items,
      final Continuation<? super Unit> arg2) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> QueueDao.DefaultImpls.replaceQueueItems(QueueDao_Impl.this, queueId, items, __cont), arg2);
  }

  @Override
  public Object deactivateAll(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateAll.acquire();
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
          __preparedStmtOfDeactivateAll.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Object setActive(final String id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetActive.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfSetActive.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updatePlaybackPosition(final String queueId, final int index, final long positionMs,
      final long updatedAt, final Continuation<? super Unit> arg4) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaybackPosition.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, index);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, positionMs);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 4;
        if (queueId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, queueId);
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
          __preparedStmtOfUpdatePlaybackPosition.release(_stmt);
        }
      }
    }, arg4);
  }

  @Override
  public Object updatePlaybackMode(final String queueId, final String mode,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaybackMode.acquire();
        int _argIndex = 1;
        if (mode == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, mode);
        }
        _argIndex = 2;
        if (queueId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, queueId);
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
          __preparedStmtOfUpdatePlaybackMode.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object clearItems(final String queueId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearItems.acquire();
        int _argIndex = 1;
        if (queueId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, queueId);
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
          __preparedStmtOfClearItems.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteItem(final long itemId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItem.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, itemId);
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
          __preparedStmtOfDeleteItem.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<PlaybackQueueEntity>> getAllQueues() {
    final String _sql = "SELECT * FROM playback_queues ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playback_queues"}, new Callable<List<PlaybackQueueEntity>>() {
      @Override
      @NonNull
      public List<PlaybackQueueEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfCurrentPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPositionMs");
          final int _cursorIndexOfPlaybackMode = CursorUtil.getColumnIndexOrThrow(_cursor, "playbackMode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<PlaybackQueueEntity> _result = new ArrayList<PlaybackQueueEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaybackQueueEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIconName;
            if (_cursor.isNull(_cursorIndexOfIconName)) {
              _tmpIconName = null;
            } else {
              _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final long _tmpCurrentPositionMs;
            _tmpCurrentPositionMs = _cursor.getLong(_cursorIndexOfCurrentPositionMs);
            final String _tmpPlaybackMode;
            if (_cursor.isNull(_cursorIndexOfPlaybackMode)) {
              _tmpPlaybackMode = null;
            } else {
              _tmpPlaybackMode = _cursor.getString(_cursorIndexOfPlaybackMode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _item = new PlaybackQueueEntity(_tmpId,_tmpName,_tmpIconName,_tmpCurrentIndex,_tmpCurrentPositionMs,_tmpPlaybackMode,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Object getQueueById(final String id,
      final Continuation<? super PlaybackQueueEntity> arg1) {
    final String _sql = "SELECT * FROM playback_queues WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaybackQueueEntity>() {
      @Override
      @Nullable
      public PlaybackQueueEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfCurrentPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPositionMs");
          final int _cursorIndexOfPlaybackMode = CursorUtil.getColumnIndexOrThrow(_cursor, "playbackMode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final PlaybackQueueEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIconName;
            if (_cursor.isNull(_cursorIndexOfIconName)) {
              _tmpIconName = null;
            } else {
              _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final long _tmpCurrentPositionMs;
            _tmpCurrentPositionMs = _cursor.getLong(_cursorIndexOfCurrentPositionMs);
            final String _tmpPlaybackMode;
            if (_cursor.isNull(_cursorIndexOfPlaybackMode)) {
              _tmpPlaybackMode = null;
            } else {
              _tmpPlaybackMode = _cursor.getString(_cursorIndexOfPlaybackMode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _result = new PlaybackQueueEntity(_tmpId,_tmpName,_tmpIconName,_tmpCurrentIndex,_tmpCurrentPositionMs,_tmpPlaybackMode,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
          } else {
            _result = null;
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
  public Object getActiveQueue(final Continuation<? super PlaybackQueueEntity> arg0) {
    final String _sql = "SELECT * FROM playback_queues WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaybackQueueEntity>() {
      @Override
      @Nullable
      public PlaybackQueueEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCurrentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "currentIndex");
          final int _cursorIndexOfCurrentPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPositionMs");
          final int _cursorIndexOfPlaybackMode = CursorUtil.getColumnIndexOrThrow(_cursor, "playbackMode");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final PlaybackQueueEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIconName;
            if (_cursor.isNull(_cursorIndexOfIconName)) {
              _tmpIconName = null;
            } else {
              _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            }
            final int _tmpCurrentIndex;
            _tmpCurrentIndex = _cursor.getInt(_cursorIndexOfCurrentIndex);
            final long _tmpCurrentPositionMs;
            _tmpCurrentPositionMs = _cursor.getLong(_cursorIndexOfCurrentPositionMs);
            final String _tmpPlaybackMode;
            if (_cursor.isNull(_cursorIndexOfPlaybackMode)) {
              _tmpPlaybackMode = null;
            } else {
              _tmpPlaybackMode = _cursor.getString(_cursorIndexOfPlaybackMode);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _result = new PlaybackQueueEntity(_tmpId,_tmpName,_tmpIconName,_tmpCurrentIndex,_tmpCurrentPositionMs,_tmpPlaybackMode,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Object getQueueCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM playback_queues";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Object getItemsForQueue(final String queueId,
      final Continuation<? super List<QueueItemEntity>> arg1) {
    final String _sql = "SELECT * FROM queue_items WHERE queueId = ? ORDER BY position ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (queueId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, queueId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<QueueItemEntity>>() {
      @Override
      @NonNull
      public List<QueueItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQueueId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueId");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
          final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
          final int _cursorIndexOfAlbumArtUri = CursorUtil.getColumnIndexOrThrow(_cursor, "albumArtUri");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final List<QueueItemEntity> _result = new ArrayList<QueueItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QueueItemEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpQueueId;
            if (_cursor.isNull(_cursorIndexOfQueueId)) {
              _tmpQueueId = null;
            } else {
              _tmpQueueId = _cursor.getString(_cursorIndexOfQueueId);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            final String _tmpUri;
            if (_cursor.isNull(_cursorIndexOfUri)) {
              _tmpUri = null;
            } else {
              _tmpUri = _cursor.getString(_cursorIndexOfUri);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpArtist;
            if (_cursor.isNull(_cursorIndexOfArtist)) {
              _tmpArtist = null;
            } else {
              _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
            }
            final String _tmpAlbum;
            if (_cursor.isNull(_cursorIndexOfAlbum)) {
              _tmpAlbum = null;
            } else {
              _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
            }
            final String _tmpAlbumArtUri;
            if (_cursor.isNull(_cursorIndexOfAlbumArtUri)) {
              _tmpAlbumArtUri = null;
            } else {
              _tmpAlbumArtUri = _cursor.getString(_cursorIndexOfAlbumArtUri);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item = new QueueItemEntity(_tmpId,_tmpQueueId,_tmpPosition,_tmpUri,_tmpTitle,_tmpArtist,_tmpAlbum,_tmpAlbumArtUri,_tmpDurationMs);
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
  public Flow<List<QueueItemEntity>> observeItemsForQueue(final String queueId) {
    final String _sql = "SELECT * FROM queue_items WHERE queueId = ? ORDER BY position ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (queueId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, queueId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"queue_items"}, new Callable<List<QueueItemEntity>>() {
      @Override
      @NonNull
      public List<QueueItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQueueId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueId");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final int _cursorIndexOfUri = CursorUtil.getColumnIndexOrThrow(_cursor, "uri");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
          final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
          final int _cursorIndexOfAlbumArtUri = CursorUtil.getColumnIndexOrThrow(_cursor, "albumArtUri");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final List<QueueItemEntity> _result = new ArrayList<QueueItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QueueItemEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpQueueId;
            if (_cursor.isNull(_cursorIndexOfQueueId)) {
              _tmpQueueId = null;
            } else {
              _tmpQueueId = _cursor.getString(_cursorIndexOfQueueId);
            }
            final int _tmpPosition;
            _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            final String _tmpUri;
            if (_cursor.isNull(_cursorIndexOfUri)) {
              _tmpUri = null;
            } else {
              _tmpUri = _cursor.getString(_cursorIndexOfUri);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpArtist;
            if (_cursor.isNull(_cursorIndexOfArtist)) {
              _tmpArtist = null;
            } else {
              _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
            }
            final String _tmpAlbum;
            if (_cursor.isNull(_cursorIndexOfAlbum)) {
              _tmpAlbum = null;
            } else {
              _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
            }
            final String _tmpAlbumArtUri;
            if (_cursor.isNull(_cursorIndexOfAlbumArtUri)) {
              _tmpAlbumArtUri = null;
            } else {
              _tmpAlbumArtUri = _cursor.getString(_cursorIndexOfAlbumArtUri);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            _item = new QueueItemEntity(_tmpId,_tmpQueueId,_tmpPosition,_tmpUri,_tmpTitle,_tmpArtist,_tmpAlbum,_tmpAlbumArtUri,_tmpDurationMs);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

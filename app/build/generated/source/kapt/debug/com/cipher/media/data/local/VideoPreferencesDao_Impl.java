package com.cipher.media.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cipher.media.data.local.entity.VideoPreferencesEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VideoPreferencesDao_Impl implements VideoPreferencesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VideoPreferencesEntity> __insertionAdapterOfVideoPreferencesEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeletePreferences;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlaybackPosition;

  public VideoPreferencesDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVideoPreferencesEntity = new EntityInsertionAdapter<VideoPreferencesEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `video_preferences` (`videoUri`,`playbackSpeed`,`pitchCorrection`,`subtitleSyncOffsetMs`,`selectedSubtitleTrackId`,`selectedAudioTrackId`,`audioDelayMs`,`cropMode`,`zoomLevel`,`panX`,`panY`,`pointA`,`pointB`,`lastPlaybackPositionMs`,`lastUpdatedTimestamp`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VideoPreferencesEntity entity) {
        if (entity.getVideoUri() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getVideoUri());
        }
        statement.bindDouble(2, entity.getPlaybackSpeed());
        final int _tmp = entity.getPitchCorrection() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getSubtitleSyncOffsetMs());
        if (entity.getSelectedSubtitleTrackId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSelectedSubtitleTrackId());
        }
        if (entity.getSelectedAudioTrackId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSelectedAudioTrackId());
        }
        statement.bindLong(7, entity.getAudioDelayMs());
        if (entity.getCropMode() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCropMode());
        }
        statement.bindDouble(9, entity.getZoomLevel());
        statement.bindDouble(10, entity.getPanX());
        statement.bindDouble(11, entity.getPanY());
        statement.bindLong(12, entity.getPointA());
        statement.bindLong(13, entity.getPointB());
        statement.bindLong(14, entity.getLastPlaybackPositionMs());
        statement.bindLong(15, entity.getLastUpdatedTimestamp());
      }
    };
    this.__preparedStmtOfDeletePreferences = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM video_preferences WHERE videoUri = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlaybackPosition = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE video_preferences SET lastPlaybackPositionMs = ?, lastUpdatedTimestamp = ? WHERE videoUri = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdate(final VideoPreferencesEntity preferences,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVideoPreferencesEntity.insert(preferences);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deletePreferences(final String uri, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePreferences.acquire();
        int _argIndex = 1;
        if (uri == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, uri);
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
          __preparedStmtOfDeletePreferences.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updatePlaybackPosition(final String uri, final long position, final long timestamp,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlaybackPosition.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, position);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        if (uri == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, uri);
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
    }, arg3);
  }

  @Override
  public Flow<VideoPreferencesEntity> getPreferencesAsFlow(final String uri) {
    final String _sql = "SELECT * FROM video_preferences WHERE videoUri = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uri == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uri);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"video_preferences"}, new Callable<VideoPreferencesEntity>() {
      @Override
      @Nullable
      public VideoPreferencesEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVideoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUri");
          final int _cursorIndexOfPlaybackSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "playbackSpeed");
          final int _cursorIndexOfPitchCorrection = CursorUtil.getColumnIndexOrThrow(_cursor, "pitchCorrection");
          final int _cursorIndexOfSubtitleSyncOffsetMs = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitleSyncOffsetMs");
          final int _cursorIndexOfSelectedSubtitleTrackId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedSubtitleTrackId");
          final int _cursorIndexOfSelectedAudioTrackId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedAudioTrackId");
          final int _cursorIndexOfAudioDelayMs = CursorUtil.getColumnIndexOrThrow(_cursor, "audioDelayMs");
          final int _cursorIndexOfCropMode = CursorUtil.getColumnIndexOrThrow(_cursor, "cropMode");
          final int _cursorIndexOfZoomLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "zoomLevel");
          final int _cursorIndexOfPanX = CursorUtil.getColumnIndexOrThrow(_cursor, "panX");
          final int _cursorIndexOfPanY = CursorUtil.getColumnIndexOrThrow(_cursor, "panY");
          final int _cursorIndexOfPointA = CursorUtil.getColumnIndexOrThrow(_cursor, "pointA");
          final int _cursorIndexOfPointB = CursorUtil.getColumnIndexOrThrow(_cursor, "pointB");
          final int _cursorIndexOfLastPlaybackPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlaybackPositionMs");
          final int _cursorIndexOfLastUpdatedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedTimestamp");
          final VideoPreferencesEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpVideoUri;
            if (_cursor.isNull(_cursorIndexOfVideoUri)) {
              _tmpVideoUri = null;
            } else {
              _tmpVideoUri = _cursor.getString(_cursorIndexOfVideoUri);
            }
            final float _tmpPlaybackSpeed;
            _tmpPlaybackSpeed = _cursor.getFloat(_cursorIndexOfPlaybackSpeed);
            final boolean _tmpPitchCorrection;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPitchCorrection);
            _tmpPitchCorrection = _tmp != 0;
            final long _tmpSubtitleSyncOffsetMs;
            _tmpSubtitleSyncOffsetMs = _cursor.getLong(_cursorIndexOfSubtitleSyncOffsetMs);
            final String _tmpSelectedSubtitleTrackId;
            if (_cursor.isNull(_cursorIndexOfSelectedSubtitleTrackId)) {
              _tmpSelectedSubtitleTrackId = null;
            } else {
              _tmpSelectedSubtitleTrackId = _cursor.getString(_cursorIndexOfSelectedSubtitleTrackId);
            }
            final String _tmpSelectedAudioTrackId;
            if (_cursor.isNull(_cursorIndexOfSelectedAudioTrackId)) {
              _tmpSelectedAudioTrackId = null;
            } else {
              _tmpSelectedAudioTrackId = _cursor.getString(_cursorIndexOfSelectedAudioTrackId);
            }
            final long _tmpAudioDelayMs;
            _tmpAudioDelayMs = _cursor.getLong(_cursorIndexOfAudioDelayMs);
            final String _tmpCropMode;
            if (_cursor.isNull(_cursorIndexOfCropMode)) {
              _tmpCropMode = null;
            } else {
              _tmpCropMode = _cursor.getString(_cursorIndexOfCropMode);
            }
            final float _tmpZoomLevel;
            _tmpZoomLevel = _cursor.getFloat(_cursorIndexOfZoomLevel);
            final float _tmpPanX;
            _tmpPanX = _cursor.getFloat(_cursorIndexOfPanX);
            final float _tmpPanY;
            _tmpPanY = _cursor.getFloat(_cursorIndexOfPanY);
            final long _tmpPointA;
            _tmpPointA = _cursor.getLong(_cursorIndexOfPointA);
            final long _tmpPointB;
            _tmpPointB = _cursor.getLong(_cursorIndexOfPointB);
            final long _tmpLastPlaybackPositionMs;
            _tmpLastPlaybackPositionMs = _cursor.getLong(_cursorIndexOfLastPlaybackPositionMs);
            final long _tmpLastUpdatedTimestamp;
            _tmpLastUpdatedTimestamp = _cursor.getLong(_cursorIndexOfLastUpdatedTimestamp);
            _result = new VideoPreferencesEntity(_tmpVideoUri,_tmpPlaybackSpeed,_tmpPitchCorrection,_tmpSubtitleSyncOffsetMs,_tmpSelectedSubtitleTrackId,_tmpSelectedAudioTrackId,_tmpAudioDelayMs,_tmpCropMode,_tmpZoomLevel,_tmpPanX,_tmpPanY,_tmpPointA,_tmpPointB,_tmpLastPlaybackPositionMs,_tmpLastUpdatedTimestamp);
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
  public Object getPreferences(final String uri,
      final Continuation<? super VideoPreferencesEntity> arg1) {
    final String _sql = "SELECT * FROM video_preferences WHERE videoUri = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uri == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uri);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VideoPreferencesEntity>() {
      @Override
      @Nullable
      public VideoPreferencesEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVideoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "videoUri");
          final int _cursorIndexOfPlaybackSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "playbackSpeed");
          final int _cursorIndexOfPitchCorrection = CursorUtil.getColumnIndexOrThrow(_cursor, "pitchCorrection");
          final int _cursorIndexOfSubtitleSyncOffsetMs = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitleSyncOffsetMs");
          final int _cursorIndexOfSelectedSubtitleTrackId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedSubtitleTrackId");
          final int _cursorIndexOfSelectedAudioTrackId = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedAudioTrackId");
          final int _cursorIndexOfAudioDelayMs = CursorUtil.getColumnIndexOrThrow(_cursor, "audioDelayMs");
          final int _cursorIndexOfCropMode = CursorUtil.getColumnIndexOrThrow(_cursor, "cropMode");
          final int _cursorIndexOfZoomLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "zoomLevel");
          final int _cursorIndexOfPanX = CursorUtil.getColumnIndexOrThrow(_cursor, "panX");
          final int _cursorIndexOfPanY = CursorUtil.getColumnIndexOrThrow(_cursor, "panY");
          final int _cursorIndexOfPointA = CursorUtil.getColumnIndexOrThrow(_cursor, "pointA");
          final int _cursorIndexOfPointB = CursorUtil.getColumnIndexOrThrow(_cursor, "pointB");
          final int _cursorIndexOfLastPlaybackPositionMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlaybackPositionMs");
          final int _cursorIndexOfLastUpdatedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedTimestamp");
          final VideoPreferencesEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpVideoUri;
            if (_cursor.isNull(_cursorIndexOfVideoUri)) {
              _tmpVideoUri = null;
            } else {
              _tmpVideoUri = _cursor.getString(_cursorIndexOfVideoUri);
            }
            final float _tmpPlaybackSpeed;
            _tmpPlaybackSpeed = _cursor.getFloat(_cursorIndexOfPlaybackSpeed);
            final boolean _tmpPitchCorrection;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPitchCorrection);
            _tmpPitchCorrection = _tmp != 0;
            final long _tmpSubtitleSyncOffsetMs;
            _tmpSubtitleSyncOffsetMs = _cursor.getLong(_cursorIndexOfSubtitleSyncOffsetMs);
            final String _tmpSelectedSubtitleTrackId;
            if (_cursor.isNull(_cursorIndexOfSelectedSubtitleTrackId)) {
              _tmpSelectedSubtitleTrackId = null;
            } else {
              _tmpSelectedSubtitleTrackId = _cursor.getString(_cursorIndexOfSelectedSubtitleTrackId);
            }
            final String _tmpSelectedAudioTrackId;
            if (_cursor.isNull(_cursorIndexOfSelectedAudioTrackId)) {
              _tmpSelectedAudioTrackId = null;
            } else {
              _tmpSelectedAudioTrackId = _cursor.getString(_cursorIndexOfSelectedAudioTrackId);
            }
            final long _tmpAudioDelayMs;
            _tmpAudioDelayMs = _cursor.getLong(_cursorIndexOfAudioDelayMs);
            final String _tmpCropMode;
            if (_cursor.isNull(_cursorIndexOfCropMode)) {
              _tmpCropMode = null;
            } else {
              _tmpCropMode = _cursor.getString(_cursorIndexOfCropMode);
            }
            final float _tmpZoomLevel;
            _tmpZoomLevel = _cursor.getFloat(_cursorIndexOfZoomLevel);
            final float _tmpPanX;
            _tmpPanX = _cursor.getFloat(_cursorIndexOfPanX);
            final float _tmpPanY;
            _tmpPanY = _cursor.getFloat(_cursorIndexOfPanY);
            final long _tmpPointA;
            _tmpPointA = _cursor.getLong(_cursorIndexOfPointA);
            final long _tmpPointB;
            _tmpPointB = _cursor.getLong(_cursorIndexOfPointB);
            final long _tmpLastPlaybackPositionMs;
            _tmpLastPlaybackPositionMs = _cursor.getLong(_cursorIndexOfLastPlaybackPositionMs);
            final long _tmpLastUpdatedTimestamp;
            _tmpLastUpdatedTimestamp = _cursor.getLong(_cursorIndexOfLastUpdatedTimestamp);
            _result = new VideoPreferencesEntity(_tmpVideoUri,_tmpPlaybackSpeed,_tmpPitchCorrection,_tmpSubtitleSyncOffsetMs,_tmpSelectedSubtitleTrackId,_tmpSelectedAudioTrackId,_tmpAudioDelayMs,_tmpCropMode,_tmpZoomLevel,_tmpPanX,_tmpPanY,_tmpPointA,_tmpPointB,_tmpLastPlaybackPositionMs,_tmpLastUpdatedTimestamp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

package com.cipher.media.data.local;

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
import com.cipher.media.ui.audio.queue.model.QueueDao;
import com.cipher.media.ui.audio.queue.model.QueueDao_Impl;
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
public final class MediaDatabase_Impl extends MediaDatabase {
  private volatile MediaDao _mediaDao;

  private volatile VideoPreferencesDao _videoPreferencesDao;

  private volatile QueueDao _queueDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `media_items` (`id` INTEGER NOT NULL, `uri` TEXT NOT NULL, `displayName` TEXT NOT NULL, `duration` INTEGER NOT NULL, `size` INTEGER NOT NULL, `dateAdded` INTEGER NOT NULL, `path` TEXT NOT NULL, `mimeType` TEXT NOT NULL, `isVaulted` INTEGER NOT NULL, `encryptedPath` TEXT, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `video_preferences` (`videoUri` TEXT NOT NULL, `playbackSpeed` REAL NOT NULL, `pitchCorrection` INTEGER NOT NULL, `subtitleSyncOffsetMs` INTEGER NOT NULL, `selectedSubtitleTrackId` TEXT, `selectedAudioTrackId` TEXT, `audioDelayMs` INTEGER NOT NULL, `cropMode` TEXT NOT NULL, `zoomLevel` REAL NOT NULL, `panX` REAL NOT NULL, `panY` REAL NOT NULL, `pointA` INTEGER NOT NULL, `pointB` INTEGER NOT NULL, `lastPlaybackPositionMs` INTEGER NOT NULL, `lastUpdatedTimestamp` INTEGER NOT NULL, PRIMARY KEY(`videoUri`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `playback_queues` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `iconName` TEXT NOT NULL, `currentIndex` INTEGER NOT NULL, `currentPositionMs` INTEGER NOT NULL, `playbackMode` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `queue_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `queueId` TEXT NOT NULL, `position` INTEGER NOT NULL, `uri` TEXT NOT NULL, `title` TEXT NOT NULL, `artist` TEXT NOT NULL, `album` TEXT NOT NULL, `albumArtUri` TEXT, `durationMs` INTEGER NOT NULL, FOREIGN KEY(`queueId`) REFERENCES `playback_queues`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_queue_items_queueId` ON `queue_items` (`queueId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7f310c039a211058825521878e487174')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `media_items`");
        db.execSQL("DROP TABLE IF EXISTS `video_preferences`");
        db.execSQL("DROP TABLE IF EXISTS `playback_queues`");
        db.execSQL("DROP TABLE IF EXISTS `queue_items`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsMediaItems = new HashMap<String, TableInfo.Column>(11);
        _columnsMediaItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("uri", new TableInfo.Column("uri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("size", new TableInfo.Column("size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("path", new TableInfo.Column("path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("mimeType", new TableInfo.Column("mimeType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("isVaulted", new TableInfo.Column("isVaulted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("encryptedPath", new TableInfo.Column("encryptedPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMediaItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMediaItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMediaItems = new TableInfo("media_items", _columnsMediaItems, _foreignKeysMediaItems, _indicesMediaItems);
        final TableInfo _existingMediaItems = TableInfo.read(db, "media_items");
        if (!_infoMediaItems.equals(_existingMediaItems)) {
          return new RoomOpenHelper.ValidationResult(false, "media_items(com.cipher.media.data.local.entity.MediaEntity).\n"
                  + " Expected:\n" + _infoMediaItems + "\n"
                  + " Found:\n" + _existingMediaItems);
        }
        final HashMap<String, TableInfo.Column> _columnsVideoPreferences = new HashMap<String, TableInfo.Column>(15);
        _columnsVideoPreferences.put("videoUri", new TableInfo.Column("videoUri", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("playbackSpeed", new TableInfo.Column("playbackSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("pitchCorrection", new TableInfo.Column("pitchCorrection", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("subtitleSyncOffsetMs", new TableInfo.Column("subtitleSyncOffsetMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("selectedSubtitleTrackId", new TableInfo.Column("selectedSubtitleTrackId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("selectedAudioTrackId", new TableInfo.Column("selectedAudioTrackId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("audioDelayMs", new TableInfo.Column("audioDelayMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("cropMode", new TableInfo.Column("cropMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("zoomLevel", new TableInfo.Column("zoomLevel", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("panX", new TableInfo.Column("panX", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("panY", new TableInfo.Column("panY", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("pointA", new TableInfo.Column("pointA", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("pointB", new TableInfo.Column("pointB", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("lastPlaybackPositionMs", new TableInfo.Column("lastPlaybackPositionMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVideoPreferences.put("lastUpdatedTimestamp", new TableInfo.Column("lastUpdatedTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVideoPreferences = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVideoPreferences = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVideoPreferences = new TableInfo("video_preferences", _columnsVideoPreferences, _foreignKeysVideoPreferences, _indicesVideoPreferences);
        final TableInfo _existingVideoPreferences = TableInfo.read(db, "video_preferences");
        if (!_infoVideoPreferences.equals(_existingVideoPreferences)) {
          return new RoomOpenHelper.ValidationResult(false, "video_preferences(com.cipher.media.data.local.entity.VideoPreferencesEntity).\n"
                  + " Expected:\n" + _infoVideoPreferences + "\n"
                  + " Found:\n" + _existingVideoPreferences);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaybackQueues = new HashMap<String, TableInfo.Column>(9);
        _columnsPlaybackQueues.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("currentIndex", new TableInfo.Column("currentIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("currentPositionMs", new TableInfo.Column("currentPositionMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("playbackMode", new TableInfo.Column("playbackMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaybackQueues.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaybackQueues = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaybackQueues = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaybackQueues = new TableInfo("playback_queues", _columnsPlaybackQueues, _foreignKeysPlaybackQueues, _indicesPlaybackQueues);
        final TableInfo _existingPlaybackQueues = TableInfo.read(db, "playback_queues");
        if (!_infoPlaybackQueues.equals(_existingPlaybackQueues)) {
          return new RoomOpenHelper.ValidationResult(false, "playback_queues(com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity).\n"
                  + " Expected:\n" + _infoPlaybackQueues + "\n"
                  + " Found:\n" + _existingPlaybackQueues);
        }
        final HashMap<String, TableInfo.Column> _columnsQueueItems = new HashMap<String, TableInfo.Column>(9);
        _columnsQueueItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("queueId", new TableInfo.Column("queueId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("position", new TableInfo.Column("position", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("uri", new TableInfo.Column("uri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("artist", new TableInfo.Column("artist", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("album", new TableInfo.Column("album", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("albumArtUri", new TableInfo.Column("albumArtUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueItems.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQueueItems = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysQueueItems.add(new TableInfo.ForeignKey("playback_queues", "CASCADE", "NO ACTION", Arrays.asList("queueId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesQueueItems = new HashSet<TableInfo.Index>(1);
        _indicesQueueItems.add(new TableInfo.Index("index_queue_items_queueId", false, Arrays.asList("queueId"), Arrays.asList("ASC")));
        final TableInfo _infoQueueItems = new TableInfo("queue_items", _columnsQueueItems, _foreignKeysQueueItems, _indicesQueueItems);
        final TableInfo _existingQueueItems = TableInfo.read(db, "queue_items");
        if (!_infoQueueItems.equals(_existingQueueItems)) {
          return new RoomOpenHelper.ValidationResult(false, "queue_items(com.cipher.media.ui.audio.queue.model.QueueItemEntity).\n"
                  + " Expected:\n" + _infoQueueItems + "\n"
                  + " Found:\n" + _existingQueueItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7f310c039a211058825521878e487174", "b940b6c231580cff821436e3ed40d009");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "media_items","video_preferences","playback_queues","queue_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `media_items`");
      _db.execSQL("DELETE FROM `video_preferences`");
      _db.execSQL("DELETE FROM `playback_queues`");
      _db.execSQL("DELETE FROM `queue_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(MediaDao.class, MediaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VideoPreferencesDao.class, VideoPreferencesDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QueueDao.class, QueueDao_Impl.getRequiredConverters());
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
  public MediaDao mediaDao() {
    if (_mediaDao != null) {
      return _mediaDao;
    } else {
      synchronized(this) {
        if(_mediaDao == null) {
          _mediaDao = new MediaDao_Impl(this);
        }
        return _mediaDao;
      }
    }
  }

  @Override
  public VideoPreferencesDao videoPreferencesDao() {
    if (_videoPreferencesDao != null) {
      return _videoPreferencesDao;
    } else {
      synchronized(this) {
        if(_videoPreferencesDao == null) {
          _videoPreferencesDao = new VideoPreferencesDao_Impl(this);
        }
        return _videoPreferencesDao;
      }
    }
  }

  @Override
  public QueueDao queueDao() {
    if (_queueDao != null) {
      return _queueDao;
    } else {
      synchronized(this) {
        if(_queueDao == null) {
          _queueDao = new QueueDao_Impl(this);
        }
        return _queueDao;
      }
    }
  }
}

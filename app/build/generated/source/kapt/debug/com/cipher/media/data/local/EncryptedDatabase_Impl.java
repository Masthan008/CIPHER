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
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EncryptedDatabase_Impl extends EncryptedDatabase {
  private volatile VaultDao _vaultDao;

  private volatile IntruderLogDao _intruderLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `vault_items` (`id` TEXT NOT NULL, `originalName` TEXT NOT NULL, `encryptedPath` TEXT NOT NULL, `fileType` TEXT NOT NULL, `size` INTEGER NOT NULL, `addedDate` INTEGER NOT NULL, `folderId` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vault_folders` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `intruder_logs` (`id` TEXT NOT NULL, `photoPath` TEXT, `latitude` REAL, `longitude` REAL, `timestamp` INTEGER NOT NULL, `attemptCount` INTEGER NOT NULL, `pinEntered` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f9c08f254d618252047c6a11915bac1c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `vault_items`");
        db.execSQL("DROP TABLE IF EXISTS `vault_folders`");
        db.execSQL("DROP TABLE IF EXISTS `intruder_logs`");
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
        final HashMap<String, TableInfo.Column> _columnsVaultItems = new HashMap<String, TableInfo.Column>(7);
        _columnsVaultItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("originalName", new TableInfo.Column("originalName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("encryptedPath", new TableInfo.Column("encryptedPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("fileType", new TableInfo.Column("fileType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("size", new TableInfo.Column("size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("addedDate", new TableInfo.Column("addedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultItems.put("folderId", new TableInfo.Column("folderId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVaultItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVaultItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaultItems = new TableInfo("vault_items", _columnsVaultItems, _foreignKeysVaultItems, _indicesVaultItems);
        final TableInfo _existingVaultItems = TableInfo.read(db, "vault_items");
        if (!_infoVaultItems.equals(_existingVaultItems)) {
          return new RoomOpenHelper.ValidationResult(false, "vault_items(com.cipher.media.data.local.VaultItemEntity).\n"
                  + " Expected:\n" + _infoVaultItems + "\n"
                  + " Found:\n" + _existingVaultItems);
        }
        final HashMap<String, TableInfo.Column> _columnsVaultFolders = new HashMap<String, TableInfo.Column>(3);
        _columnsVaultFolders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultFolders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultFolders.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVaultFolders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVaultFolders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaultFolders = new TableInfo("vault_folders", _columnsVaultFolders, _foreignKeysVaultFolders, _indicesVaultFolders);
        final TableInfo _existingVaultFolders = TableInfo.read(db, "vault_folders");
        if (!_infoVaultFolders.equals(_existingVaultFolders)) {
          return new RoomOpenHelper.ValidationResult(false, "vault_folders(com.cipher.media.data.local.VaultFolderEntity).\n"
                  + " Expected:\n" + _infoVaultFolders + "\n"
                  + " Found:\n" + _existingVaultFolders);
        }
        final HashMap<String, TableInfo.Column> _columnsIntruderLogs = new HashMap<String, TableInfo.Column>(7);
        _columnsIntruderLogs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("photoPath", new TableInfo.Column("photoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("attemptCount", new TableInfo.Column("attemptCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("pinEntered", new TableInfo.Column("pinEntered", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIntruderLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIntruderLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIntruderLogs = new TableInfo("intruder_logs", _columnsIntruderLogs, _foreignKeysIntruderLogs, _indicesIntruderLogs);
        final TableInfo _existingIntruderLogs = TableInfo.read(db, "intruder_logs");
        if (!_infoIntruderLogs.equals(_existingIntruderLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "intruder_logs(com.cipher.media.data.local.IntruderLogEntity).\n"
                  + " Expected:\n" + _infoIntruderLogs + "\n"
                  + " Found:\n" + _existingIntruderLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f9c08f254d618252047c6a11915bac1c", "c6c06732e19541487e19304c8614d8f1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "vault_items","vault_folders","intruder_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `vault_items`");
      _db.execSQL("DELETE FROM `vault_folders`");
      _db.execSQL("DELETE FROM `intruder_logs`");
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
    _typeConvertersMap.put(VaultDao.class, VaultDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IntruderLogDao.class, IntruderLogDao_Impl.getRequiredConverters());
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
  public VaultDao vaultDao() {
    if (_vaultDao != null) {
      return _vaultDao;
    } else {
      synchronized(this) {
        if(_vaultDao == null) {
          _vaultDao = new VaultDao_Impl(this);
        }
        return _vaultDao;
      }
    }
  }

  @Override
  public IntruderLogDao intruderLogDao() {
    if (_intruderLogDao != null) {
      return _intruderLogDao;
    } else {
      synchronized(this) {
        if(_intruderLogDao == null) {
          _intruderLogDao = new IntruderLogDao_Impl(this);
        }
        return _intruderLogDao;
      }
    }
  }
}

package com.cipher.media.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
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
public final class VaultDao_Impl implements VaultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VaultItemEntity> __insertionAdapterOfVaultItemEntity;

  private final EntityInsertionAdapter<VaultFolderEntity> __insertionAdapterOfVaultFolderEntity;

  private final EntityDeletionOrUpdateAdapter<VaultItemEntity> __deletionAdapterOfVaultItemEntity;

  private final EntityDeletionOrUpdateAdapter<VaultFolderEntity> __deletionAdapterOfVaultFolderEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItemById;

  private final SharedSQLiteStatement __preparedStmtOfMoveItemToFolder;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFolderById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllItems;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllFolders;

  public VaultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVaultItemEntity = new EntityInsertionAdapter<VaultItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vault_items` (`id`,`originalName`,`encryptedPath`,`fileType`,`size`,`addedDate`,`folderId`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultItemEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getOriginalName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getOriginalName());
        }
        if (entity.getEncryptedPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEncryptedPath());
        }
        if (entity.getFileType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getFileType());
        }
        statement.bindLong(5, entity.getSize());
        statement.bindLong(6, entity.getAddedDate());
        if (entity.getFolderId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFolderId());
        }
      }
    };
    this.__insertionAdapterOfVaultFolderEntity = new EntityInsertionAdapter<VaultFolderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vault_folders` (`id`,`name`,`createdAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultFolderEntity entity) {
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
        statement.bindLong(3, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfVaultItemEntity = new EntityDeletionOrUpdateAdapter<VaultItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vault_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultItemEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__deletionAdapterOfVaultFolderEntity = new EntityDeletionOrUpdateAdapter<VaultFolderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vault_folders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultFolderEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteItemById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vault_items WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMoveItemToFolder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE vault_items SET folderId = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteFolderById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vault_folders WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllItems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vault_items";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllFolders = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vault_folders";
        return _query;
      }
    };
  }

  @Override
  public Object insertItem(final VaultItemEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVaultItemEntity.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertFolder(final VaultFolderEntity folder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVaultFolderEntity.insert(folder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteItem(final VaultItemEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVaultItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFolder(final VaultFolderEntity folder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVaultFolderEntity.handle(folder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteItemById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItemById.acquire();
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
          __preparedStmtOfDeleteItemById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object moveItemToFolder(final String itemId, final String folderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMoveItemToFolder.acquire();
        int _argIndex = 1;
        if (folderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, folderId);
        }
        _argIndex = 2;
        if (itemId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, itemId);
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
          __preparedStmtOfMoveItemToFolder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFolderById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFolderById.acquire();
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
          __preparedStmtOfDeleteFolderById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllItems(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllItems.acquire();
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
          __preparedStmtOfDeleteAllItems.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllFolders(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllFolders.acquire();
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
          __preparedStmtOfDeleteAllFolders.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<VaultItemEntity>> getAllItems() {
    final String _sql = "SELECT * FROM vault_items ORDER BY addedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vault_items"}, new Callable<List<VaultItemEntity>>() {
      @Override
      @NonNull
      public List<VaultItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalName = CursorUtil.getColumnIndexOrThrow(_cursor, "originalName");
          final int _cursorIndexOfEncryptedPath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedPath");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfAddedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addedDate");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final List<VaultItemEntity> _result = new ArrayList<VaultItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VaultItemEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpOriginalName;
            if (_cursor.isNull(_cursorIndexOfOriginalName)) {
              _tmpOriginalName = null;
            } else {
              _tmpOriginalName = _cursor.getString(_cursorIndexOfOriginalName);
            }
            final String _tmpEncryptedPath;
            if (_cursor.isNull(_cursorIndexOfEncryptedPath)) {
              _tmpEncryptedPath = null;
            } else {
              _tmpEncryptedPath = _cursor.getString(_cursorIndexOfEncryptedPath);
            }
            final String _tmpFileType;
            if (_cursor.isNull(_cursorIndexOfFileType)) {
              _tmpFileType = null;
            } else {
              _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            }
            final long _tmpSize;
            _tmpSize = _cursor.getLong(_cursorIndexOfSize);
            final long _tmpAddedDate;
            _tmpAddedDate = _cursor.getLong(_cursorIndexOfAddedDate);
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            _item = new VaultItemEntity(_tmpId,_tmpOriginalName,_tmpEncryptedPath,_tmpFileType,_tmpSize,_tmpAddedDate,_tmpFolderId);
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
  public Flow<List<VaultItemEntity>> getItemsByFolder(final String folderId) {
    final String _sql = "SELECT * FROM vault_items WHERE folderId = ? ORDER BY addedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (folderId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, folderId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vault_items"}, new Callable<List<VaultItemEntity>>() {
      @Override
      @NonNull
      public List<VaultItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalName = CursorUtil.getColumnIndexOrThrow(_cursor, "originalName");
          final int _cursorIndexOfEncryptedPath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedPath");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfAddedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addedDate");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final List<VaultItemEntity> _result = new ArrayList<VaultItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VaultItemEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpOriginalName;
            if (_cursor.isNull(_cursorIndexOfOriginalName)) {
              _tmpOriginalName = null;
            } else {
              _tmpOriginalName = _cursor.getString(_cursorIndexOfOriginalName);
            }
            final String _tmpEncryptedPath;
            if (_cursor.isNull(_cursorIndexOfEncryptedPath)) {
              _tmpEncryptedPath = null;
            } else {
              _tmpEncryptedPath = _cursor.getString(_cursorIndexOfEncryptedPath);
            }
            final String _tmpFileType;
            if (_cursor.isNull(_cursorIndexOfFileType)) {
              _tmpFileType = null;
            } else {
              _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            }
            final long _tmpSize;
            _tmpSize = _cursor.getLong(_cursorIndexOfSize);
            final long _tmpAddedDate;
            _tmpAddedDate = _cursor.getLong(_cursorIndexOfAddedDate);
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            _item = new VaultItemEntity(_tmpId,_tmpOriginalName,_tmpEncryptedPath,_tmpFileType,_tmpSize,_tmpAddedDate,_tmpFolderId);
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
  public Object getItemById(final String id,
      final Continuation<? super VaultItemEntity> $completion) {
    final String _sql = "SELECT * FROM vault_items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VaultItemEntity>() {
      @Override
      @Nullable
      public VaultItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalName = CursorUtil.getColumnIndexOrThrow(_cursor, "originalName");
          final int _cursorIndexOfEncryptedPath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedPath");
          final int _cursorIndexOfFileType = CursorUtil.getColumnIndexOrThrow(_cursor, "fileType");
          final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
          final int _cursorIndexOfAddedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addedDate");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final VaultItemEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpOriginalName;
            if (_cursor.isNull(_cursorIndexOfOriginalName)) {
              _tmpOriginalName = null;
            } else {
              _tmpOriginalName = _cursor.getString(_cursorIndexOfOriginalName);
            }
            final String _tmpEncryptedPath;
            if (_cursor.isNull(_cursorIndexOfEncryptedPath)) {
              _tmpEncryptedPath = null;
            } else {
              _tmpEncryptedPath = _cursor.getString(_cursorIndexOfEncryptedPath);
            }
            final String _tmpFileType;
            if (_cursor.isNull(_cursorIndexOfFileType)) {
              _tmpFileType = null;
            } else {
              _tmpFileType = _cursor.getString(_cursorIndexOfFileType);
            }
            final long _tmpSize;
            _tmpSize = _cursor.getLong(_cursorIndexOfSize);
            final long _tmpAddedDate;
            _tmpAddedDate = _cursor.getLong(_cursorIndexOfAddedDate);
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            _result = new VaultItemEntity(_tmpId,_tmpOriginalName,_tmpEncryptedPath,_tmpFileType,_tmpSize,_tmpAddedDate,_tmpFolderId);
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

  @Override
  public Flow<Integer> getItemCount() {
    final String _sql = "SELECT COUNT(*) FROM vault_items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vault_items"}, new Callable<Integer>() {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VaultFolderEntity>> getAllFolders() {
    final String _sql = "SELECT * FROM vault_folders ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vault_folders"}, new Callable<List<VaultFolderEntity>>() {
      @Override
      @NonNull
      public List<VaultFolderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<VaultFolderEntity> _result = new ArrayList<VaultFolderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VaultFolderEntity _item;
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
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new VaultFolderEntity(_tmpId,_tmpName,_tmpCreatedAt);
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

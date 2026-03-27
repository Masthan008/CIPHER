package com.cipher.media.data.local;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

/**
 * DAO for vault items and folders.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00150\u0014H\'J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00150\u0014H\'J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0014H\'J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00150\u00142\u0006\u0010\u001b\u001a\u00020\fH\'J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J \u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\f2\b\u0010\u001b\u001a\u0004\u0018\u00010\fH\u00a7@\u00a2\u0006\u0002\u0010 \u00a8\u0006!"}, d2 = {"Lcom/cipher/media/data/local/VaultDao;", "", "deleteAllFolders", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllItems", "deleteFolder", "folder", "Lcom/cipher/media/data/local/VaultFolderEntity;", "(Lcom/cipher/media/data/local/VaultFolderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFolderById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItem", "item", "Lcom/cipher/media/data/local/VaultItemEntity;", "(Lcom/cipher/media/data/local/VaultItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItemById", "getAllFolders", "Lkotlinx/coroutines/flow/Flow;", "", "getAllItems", "getItemById", "getItemCount", "", "getItemsByFolder", "folderId", "insertFolder", "insertItem", "moveItemToFolder", "itemId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface VaultDao {
    
    @androidx.room.Query(value = "SELECT * FROM vault_items ORDER BY addedDate DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.data.local.VaultItemEntity>> getAllItems();
    
    @androidx.room.Query(value = "SELECT * FROM vault_items WHERE folderId = :folderId ORDER BY addedDate DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.data.local.VaultItemEntity>> getItemsByFolder(@org.jetbrains.annotations.NotNull()
    java.lang.String folderId);
    
    @androidx.room.Query(value = "SELECT * FROM vault_items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.data.local.VaultItemEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItem(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItem(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultItemEntity item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM vault_items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItemById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE vault_items SET folderId = :folderId WHERE id = :itemId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object moveItemToFolder(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.Nullable()
    java.lang.String folderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM vault_items")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getItemCount();
    
    @androidx.room.Query(value = "SELECT * FROM vault_folders ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.data.local.VaultFolderEntity>> getAllFolders();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFolder(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultFolderEntity folder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFolder(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultFolderEntity folder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM vault_folders WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFolderById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM vault_items")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM vault_folders")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllFolders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
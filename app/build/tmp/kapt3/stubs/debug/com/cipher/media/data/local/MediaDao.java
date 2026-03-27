package com.cipher.media.data.local;

import androidx.room.*;
import com.cipher.media.data.local.entity.MediaEntity;

/**
 * Data Access Object for media items.
 * Currently a stub for Phase 3 (Vault feature).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000f\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/cipher/media/data/local/MediaDao;", "", "deleteById", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMedia", "media", "Lcom/cipher/media/data/local/entity/MediaEntity;", "(Lcom/cipher/media/data/local/entity/MediaEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMedia", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVaultedMedia", "insertAll", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMedia", "app_debug"})
@androidx.room.Dao()
public abstract interface MediaDao {
    
    @androidx.room.Query(value = "SELECT * FROM media_items ORDER BY dateAdded DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllMedia(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.data.local.entity.MediaEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM media_items WHERE isVaulted = 1 ORDER BY dateAdded DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVaultedMedia(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.data.local.entity.MediaEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertMedia(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.entity.MediaEntity media, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.local.entity.MediaEntity> media, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMedia(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.entity.MediaEntity media, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM media_items WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
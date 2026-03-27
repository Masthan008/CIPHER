package com.cipher.media.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cipher.media.data.local.entity.VideoPreferencesEntity;
import kotlinx.coroutines.flow.Flow;

/**
 * Data Access Object for per-video memory preferences.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\n2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\rJ(\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/cipher/media/data/local/VideoPreferencesDao;", "", "deletePreferences", "", "uri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPreferences", "Lcom/cipher/media/data/local/entity/VideoPreferencesEntity;", "getPreferencesAsFlow", "Lkotlinx/coroutines/flow/Flow;", "insertOrUpdate", "preferences", "(Lcom/cipher/media/data/local/entity/VideoPreferencesEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaybackPosition", "position", "", "timestamp", "(Ljava/lang/String;JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface VideoPreferencesDao {
    
    @androidx.room.Query(value = "SELECT * FROM video_preferences WHERE videoUri = :uri")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.cipher.media.data.local.entity.VideoPreferencesEntity> getPreferencesAsFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String uri);
    
    @androidx.room.Query(value = "SELECT * FROM video_preferences WHERE videoUri = :uri")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPreferences(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.data.local.entity.VideoPreferencesEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdate(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.entity.VideoPreferencesEntity preferences, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM video_preferences WHERE videoUri = :uri")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePreferences(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE video_preferences SET lastPlaybackPositionMs = :position, lastUpdatedTimestamp = :timestamp WHERE videoUri = :uri")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlaybackPosition(@org.jetbrains.annotations.NotNull()
    java.lang.String uri, long position, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Data Access Object for per-video memory preferences.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}
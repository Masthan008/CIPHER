package com.cipher.media.data.repository;

import com.cipher.media.data.local.VideoPreferencesDao;
import com.cipher.media.data.local.entity.VideoPreferencesEntity;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r2\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/cipher/media/data/repository/VideoPreferencesRepository;", "", "dao", "Lcom/cipher/media/data/local/VideoPreferencesDao;", "(Lcom/cipher/media/data/local/VideoPreferencesDao;)V", "clearPreferences", "", "videoUri", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPreferences", "Lcom/cipher/media/data/local/entity/VideoPreferencesEntity;", "getPreferencesAsFlow", "Lkotlinx/coroutines/flow/Flow;", "savePreferences", "preferences", "(Lcom/cipher/media/data/local/entity/VideoPreferencesEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaybackPosition", "positionMs", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class VideoPreferencesRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.local.VideoPreferencesDao dao = null;
    
    @javax.inject.Inject()
    public VideoPreferencesRepository(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VideoPreferencesDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.cipher.media.data.local.entity.VideoPreferencesEntity> getPreferencesAsFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPreferences(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.data.local.entity.VideoPreferencesEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object savePreferences(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.entity.VideoPreferencesEntity preferences, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Helper to quickly update playback position on pause.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePlaybackPosition(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, long positionMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearPreferences(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}
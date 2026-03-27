package com.cipher.media.data.repository;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.model.MediaItem;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that queries MediaStore for local video and audio files.
 * Runs all queries on Dispatchers.IO.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006H\u0086@\u00a2\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/cipher/media/data/repository/MediaRepository;", "", "contentResolver", "Landroid/content/ContentResolver;", "(Landroid/content/ContentResolver;)V", "getLocalAudio", "", "Lcom/cipher/media/data/model/AudioItem;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalVideos", "Lcom/cipher/media/data/model/MediaItem;", "app_debug"})
public final class MediaRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.ContentResolver contentResolver = null;
    
    @javax.inject.Inject()
    public MediaRepository(@org.jetbrains.annotations.NotNull()
    android.content.ContentResolver contentResolver) {
        super();
    }
    
    /**
     * Fetches all video files from device storage via MediaStore.
     * Sorted by date added (newest first).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLocalVideos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.data.model.MediaItem>> $completion) {
        return null;
    }
    
    /**
     * Fetches all audio files from device storage via MediaStore.Audio.
     * Sorted by title ascending.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLocalAudio(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.data.model.AudioItem>> $completion) {
        return null;
    }
}
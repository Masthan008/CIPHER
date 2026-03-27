package com.cipher.media.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * LRU memory cache for media thumbnails.
 * Max size: 1/8 of available RAM (capped at 100MB).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0012\u001a\u00020\u0005J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/cipher/media/util/ThumbnailCache;", "", "()V", "cache", "Landroid/util/LruCache;", "", "Landroid/graphics/Bitmap;", "cacheSize", "", "currentSize", "getCurrentSize", "()I", "maxMemory", "maxSize", "getMaxSize", "clear", "", "get", "key", "put", "bitmap", "app_debug"})
public final class ThumbnailCache {
    private static final int maxMemory = 0;
    private static final int cacheSize = 0;
    @org.jetbrains.annotations.NotNull()
    private static final android.util.LruCache<java.lang.String, android.graphics.Bitmap> cache = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.util.ThumbnailCache INSTANCE = null;
    
    private ThumbnailCache() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap get(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return null;
    }
    
    public final void put(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap) {
    }
    
    public final void clear() {
    }
    
    public final int getCurrentSize() {
        return 0;
    }
    
    public final int getMaxSize() {
        return 0;
    }
}
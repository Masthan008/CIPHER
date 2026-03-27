package com.cipher.media.util

import android.graphics.Bitmap
import android.util.LruCache

/**
 * LRU memory cache for media thumbnails.
 * Max size: 1/8 of available RAM (capped at 100MB).
 */
object ThumbnailCache {

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = minOf(maxMemory / 8, 100 * 1024) // 100MB cap

    private val cache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }

    fun get(key: String): Bitmap? = cache.get(key)

    fun put(key: String, bitmap: Bitmap) {
        if (cache.get(key) == null) {
            cache.put(key, bitmap)
        }
    }

    fun clear() {
        cache.evictAll()
    }

    val currentSize: Int get() = cache.size()
    val maxSize: Int get() = cache.maxSize()
}

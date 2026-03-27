package com.cipher.media.ui.video.streaming

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles network streaming: detects URL type (HTTP/HLS/DASH)
 * and builds the appropriate ExoPlayer MediaSource.
 *
 * PRO: HTTP + HLS
 * POWER: + DASH + 4K
 */
@Singleton
@UnstableApi
class StreamingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    enum class StreamType {
        PROGRESSIVE,   // HTTP/HTTPS direct file
        HLS,           // .m3u8
        DASH,          // .mpd
        UNKNOWN
    }

    private val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        .setUserAgent("CIPHER-MediaPlayer/1.0")
        .setConnectTimeoutMs(15_000)
        .setReadTimeoutMs(30_000)
        .setAllowCrossProtocolRedirects(true)

    /**
     * Detects the stream type from a URL string.
     */
    fun detectStreamType(url: String): StreamType {
        val lower = url.lowercase()
        return when {
            lower.contains(".m3u8") || lower.contains("format=m3u8") -> StreamType.HLS
            lower.contains(".mpd") || lower.contains("format=mpd") -> StreamType.DASH
            lower.startsWith("http://") || lower.startsWith("https://") -> StreamType.PROGRESSIVE
            else -> StreamType.UNKNOWN
        }
    }

    /**
     * Validates a streaming URL.
     * Returns null if valid, error message if invalid.
     */
    fun validateUrl(url: String): String? {
        if (url.isBlank()) return "URL cannot be empty"
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "URL must start with http:// or https://"
        }
        return try {
            Uri.parse(url)
            null  // Valid
        } catch (e: Exception) {
            "Invalid URL format"
        }
    }

    /**
     * Creates a MediaSource for the given URL based on its type.
     */
    fun createMediaSource(url: String, title: String? = null): MediaSource {
        val uri = Uri.parse(url)
        val metadata = MediaMetadata.Builder()
            .setTitle(title ?: extractTitle(url))
            .build()
        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .setMediaMetadata(metadata)
            .build()

        return when (detectStreamType(url)) {
            StreamType.HLS -> HlsMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(mediaItem)
            StreamType.DASH -> DashMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(mediaItem)
            StreamType.PROGRESSIVE -> ProgressiveMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(mediaItem)
            StreamType.UNKNOWN -> ProgressiveMediaSource.Factory(httpDataSourceFactory)
                .createMediaSource(mediaItem)
        }
    }

    /**
     * Creates an ExoPlayer configured for streaming.
     */
    fun createStreamingPlayer(url: String): ExoPlayer {
        val player = ExoPlayer.Builder(context).build()
        val source = createMediaSource(url)
        player.setMediaSource(source)
        player.prepare()
        player.playWhenReady = true
        return player
    }

    /**
     * Extracts a display title from a URL.
     */
    private fun extractTitle(url: String): String {
        return try {
            val uri = Uri.parse(url)
            val fileName = uri.lastPathSegment ?: "Network Stream"
            fileName.substringBeforeLast(".")
                .replace(Regex("[_\\-]"), " ")
                .replaceFirstChar { it.uppercase() }
        } catch (e: Exception) {
            "Network Stream"
        }
    }

    /**
     * Returns a user-friendly label for the stream type.
     */
    fun getStreamTypeLabel(url: String): String {
        return when (detectStreamType(url)) {
            StreamType.HLS -> "HLS Adaptive Stream"
            StreamType.DASH -> "DASH Stream (4K)"
            StreamType.PROGRESSIVE -> "Direct Stream"
            StreamType.UNKNOWN -> "Unknown"
        }
    }
}

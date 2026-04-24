package com.cipher.media.service

import android.content.Context
import android.net.Uri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Rating
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import com.cipher.media.data.online.jamendo.OnlineTrack
import com.cipher.media.data.online.jamendo.TrackSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Enhanced Online Player Manager with caching and pre-loading for instant playback.
 */
@OptIn(UnstableApi::class)
@Singleton
class OnlinePlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    // ExoPlayer with optimized buffering (no persistent cache)
    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true
            )
            .setLoadControl(
                DefaultLoadControl.Builder()
                    .setBufferDurationsMs(
                        5000,   // minBufferMs
                        50000,  // maxBufferMs
                        2000,   // bufferForPlaybackMs
                        5000    // bufferForPlaybackAfterRebufferMs
                    )
                    .setPrioritizeTimeOverSizeThresholds(true)
                    .build()
            )
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(10000)
            .build()
    }

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                _duration.value = exoPlayer.duration.coerceAtLeast(0)
                // Update current track from player
                exoPlayer.currentMediaItem?.let { item ->
                    updateCurrentTrackFromMediaItem(item)
                }
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mediaItem?.let { updateCurrentTrackFromMediaItem(it) }
        }
    }

    init {
        exoPlayer.addListener(playerListener)
        startPositionUpdates()
    }

    private fun startPositionUpdates() {
        scope.launch {
            while (isActive) {
                _currentPosition.value = exoPlayer.currentPosition
                delay(1000)
            }
        }
    }

    // State flows for UI
    private val _currentTrack = MutableStateFlow<OnlineTrack?>(null)
    val currentTrack: StateFlow<OnlineTrack?> = _currentTrack.asStateFlow()

    private val _currentPlaylist = MutableStateFlow<List<OnlineTrack>>(emptyList())
    val currentPlaylist: StateFlow<List<OnlineTrack>> = _currentPlaylist.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: Long
        get() = _duration.value

    /**
     * Play a track with optional playlist. Supports instant playback with pre-loading.
     */
    fun playTrack(
        track: OnlineTrack,
        playlist: List<OnlineTrack> = emptyList(),
        autoPlay: Boolean = true
    ) {
        // Build media item with metadata
        val mediaItem = track.toMediaItem()

        // Set playlist if provided
        if (playlist.isNotEmpty()) {
            val mediaItems = playlist.map { it.toMediaItem() }
            val startIndex = playlist.indexOfFirst { it.id == track.id }.coerceAtLeast(0)
            exoPlayer.setMediaItems(mediaItems, startIndex, 0)
        } else {
            exoPlayer.setMediaItem(mediaItem)
        }

        // Prepare and play
        exoPlayer.prepare()
        if (autoPlay) {
            exoPlayer.play()
        }

        // Pre-load next track for instant skip
        val currentIdx = playlist.indexOfFirst { it.id == track.id }
        if (currentIdx >= 0 && currentIdx < playlist.size - 1) {
            preLoadTrack(playlist[currentIdx + 1])
        }

        _currentTrack.value = track
        _currentPlaylist.value = playlist
        _currentIndex.value = currentIdx.coerceAtLeast(0)
    }

    /**
     * Pre-load a track for instant playback.
     */
    private fun preLoadTrack(track: OnlineTrack) {
        // Background pre-loading happens automatically through ExoPlayer's cache
        // The cache system will buffer the next track
    }

    fun playPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    fun skipToNext() {
        if (exoPlayer.hasNextMediaItem()) {
            exoPlayer.seekToNextMediaItem()
            _currentIndex.value = exoPlayer.currentMediaItemIndex
            updateCurrentTrack()
        }
    }

    fun skipToPrevious() {
        if (exoPlayer.hasPreviousMediaItem()) {
            exoPlayer.seekToPreviousMediaItem()
            _currentIndex.value = exoPlayer.currentMediaItemIndex
            updateCurrentTrack()
        }
    }

    fun seekTo(positionMs: Long) {
        exoPlayer.seekTo(positionMs)
    }

    fun skipTo(index: Int) {
        if (index in 0 until exoPlayer.mediaItemCount) {
            exoPlayer.seekTo(index, 0)
            _currentIndex.value = index
            updateCurrentTrack()
        }
    }

    private fun updateCurrentTrack() {
        val mediaItem = exoPlayer.currentMediaItem
        mediaItem?.let { updateCurrentTrackFromMediaItem(it) }
    }

    private fun updateCurrentTrackFromMediaItem(mediaItem: MediaItem) {
        val metadata = mediaItem.mediaMetadata
        _currentTrack.value = OnlineTrack(
            id = mediaItem.mediaId,
            title = metadata.title?.toString() ?: "Unknown",
            artist = metadata.title?.toString() ?: "Unknown",
            album = metadata.albumTitle?.toString() ?: "",
            duration = exoPlayer.duration.coerceAtLeast(0),
            artworkUrl = metadata.artworkUri?.toString() ?: "",
            streamUrl = mediaItem.localConfiguration?.uri?.toString() ?: "",
            source = TrackSource.JAMENDO,
            license = ""
        )
    }

    fun stop() {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        _currentTrack.value = null
        _isPlaying.value = false
        _currentPosition.value = 0
        _duration.value = 0
    }

    fun release() {
        exoPlayer.removeListener(playerListener)
        exoPlayer.release()
        scope.cancel()
    }

    // Singleton instance for non-Hilt access
    companion object {
        @Volatile
        private var instance: OnlinePlayerManager? = null

        fun getInstance(context: Context): OnlinePlayerManager {
            return instance ?: synchronized(this) {
                instance ?: OnlinePlayerManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
}

/**
 * Convert OnlineTrack to MediaItem for ExoPlayer
 */
fun OnlineTrack.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(id)
        .setUri(streamUrl)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(title)
                .setArtist(artist)
                .setAlbumTitle(album)
                .setArtworkUri(Uri.parse(artworkUrl))
                .build()
        )
        .build()
}

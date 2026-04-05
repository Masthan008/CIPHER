package com.cipher.media.service

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central player manager for coordinating playback across the app.
 * Works with MediaController to control the AudioPlaybackService.
 */
@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var mediaController: MediaController? = null
    private var isConnected = false

    private val _currentTrack = MutableStateFlow<MediaItem?>(null)
    val currentTrack: StateFlow<MediaItem?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                _duration.value = mediaController?.duration ?: 0L
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            _currentTrack.value = mediaItem
        }
    }

    init {
        connectToService()
    }

    private fun connectToService() {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, AudioPlaybackService::class.java)
        )

        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()
                mediaController?.addListener(playerListener)
                isConnected = true

                // Sync initial state
                _isPlaying.value = mediaController?.isPlaying ?: false
                _currentTrack.value = mediaController?.currentMediaItem
            },
            MoreExecutors.directExecutor()
        )
    }

    /**
     * Play an online track from Jamendo or other sources.
     * Clears existing playlist and starts fresh playback.
     */
    fun playOnlineTrack(mediaItem: MediaItem) {
        val controller = mediaController ?: return
        controller.clearMediaItems()
        controller.setMediaItem(mediaItem)
        controller.prepare()
        controller.play()
        _currentTrack.value = mediaItem
    }

    /**
     * Play a list of online tracks starting from a specific track.
     */
    fun playOnlinePlaylist(mediaItems: List<MediaItem>, startIndex: Int = 0) {
        val controller = mediaController ?: return
        if (mediaItems.isEmpty()) return

        controller.clearMediaItems()
        controller.setMediaItems(mediaItems, startIndex, 0)
        controller.prepare()
        controller.play()
        _currentTrack.value = mediaItems.getOrNull(startIndex)
    }

    fun playPause() {
        val controller = mediaController ?: return
        if (controller.isPlaying) {
            controller.pause()
        } else {
            controller.play()
        }
    }

    fun skipNext() {
        mediaController?.seekToNextMediaItem()
    }

    fun skipPrevious() {
        mediaController?.seekToPreviousMediaItem()
    }

    fun seekTo(positionMs: Long) {
        mediaController?.seekTo(positionMs)
    }

    fun stop() {
        mediaController?.stop()
        mediaController?.clearMediaItems()
        _currentTrack.value = null
        _isPlaying.value = false
    }

    fun release() {
        mediaController?.removeListener(playerListener)
        mediaController?.release()
        mediaController = null
        isConnected = false
    }
}

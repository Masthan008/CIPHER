package com.cipher.media.ui.video.streaming

import android.content.Context
import android.util.Log
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.CastStateListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages Google Cast (Chromecast) integration.
 *
 * PRO: Basic casting
 * POWER: Multiple targets + audio-only mode
 */
@Singleton
@UnstableApi
class CastManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val TAG = "CastManager"

    data class CastUiState(
        val isCasting: Boolean = false,
        val castDeviceName: String? = null,
        val isDiscovering: Boolean = false,
        val hasCastDevices: Boolean = false,
        val currentTitle: String? = null,
        val isPlaying: Boolean = false,
        val currentPosition: Long = 0L,
        val duration: Long = 0L
    )

    private val _uiState = MutableStateFlow(CastUiState())
    val uiState: StateFlow<CastUiState> = _uiState.asStateFlow()

    private var castContext: CastContext? = null
    private var castPlayer: CastPlayer? = null

    /**
     * Initialize Cast SDK. Call from Activity.
     */
    fun initialize() {
        try {
            castContext = CastContext.getSharedInstance(context)
            castContext?.addCastStateListener(castStateListener)

            castPlayer = CastPlayer(castContext!!)
            castPlayer?.setSessionAvailabilityListener(sessionListener)

            Log.d(TAG, "Cast SDK initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Cast SDK init failed: ${e.message}")
        }
    }

    private val castStateListener = CastStateListener { state ->
        _uiState.value = _uiState.value.copy(
            hasCastDevices = state != CastState.NO_DEVICES_AVAILABLE,
            isDiscovering = state == CastState.NOT_CONNECTED
        )
    }

    private val sessionListener = object : SessionAvailabilityListener {
        override fun onCastSessionAvailable() {
            val deviceName = castContext?.sessionManager?.currentCastSession
                ?.castDevice?.friendlyName
            _uiState.value = _uiState.value.copy(
                isCasting = true,
                castDeviceName = deviceName
            )
            Log.d(TAG, "Cast session connected: $deviceName")
        }

        override fun onCastSessionUnavailable() {
            _uiState.value = _uiState.value.copy(
                isCasting = false,
                castDeviceName = null
            )
            Log.d(TAG, "Cast session disconnected")
        }
    }

    /**
     * Cast a media URL to the connected device.
     */
    fun castMedia(url: String, title: String = "CIPHER Stream") {
        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .build()
            )
            .build()

        castPlayer?.setMediaItem(mediaItem)
        castPlayer?.prepare()
        castPlayer?.play()

        _uiState.value = _uiState.value.copy(
            currentTitle = title,
            isPlaying = true
        )
    }

    /**
     * Play/pause toggle on cast device.
     */
    fun togglePlayPause() {
        castPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
                _uiState.value = _uiState.value.copy(isPlaying = false)
            } else {
                player.play()
                _uiState.value = _uiState.value.copy(isPlaying = true)
            }
        }
    }

    /**
     * Seek cast playback to position.
     */
    fun seekTo(positionMs: Long) {
        castPlayer?.seekTo(positionMs)
    }

    /**
     * Stop casting and return to local playback.
     */
    fun stopCasting() {
        castPlayer?.stop()
        castContext?.sessionManager?.endCurrentSession(true)
        _uiState.value = CastUiState()
    }

    /**
     * Get the CastPlayer for transfer between local/remote.
     */
    fun getCastPlayer(): CastPlayer? = castPlayer

    /**
     * Cleanup resources.
     */
    fun release() {
        castPlayer?.setSessionAvailabilityListener(null)
        castPlayer?.release()
        castContext?.removeCastStateListener(castStateListener)
    }
}

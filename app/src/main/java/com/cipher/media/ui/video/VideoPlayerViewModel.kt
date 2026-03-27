package com.cipher.media.ui.video

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * UI state for video player — includes speed, aspect ratio, PiP controls.
 */
data class PlayerUiState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val bufferedPosition: Long = 0L,
    val isControlsVisible: Boolean = true,
    val isLocked: Boolean = false,              // Screen rotation lock
    val playbackSpeed: Float = 1.0f,
    val aspectRatioMode: AspectRatioMode = AspectRatioMode.FIT,
    val showSpeedMenu: Boolean = false
) {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration else 0f
    val bufferedProgress: Float
        get() = if (duration > 0) bufferedPosition.toFloat() / duration else 0f
}

enum class AspectRatioMode(val label: String) {
    FIT("Fit"),
    FILL("Fill"),
    ZOOM("Zoom")
}

/**
 * ViewModel for video player screen.
 * Manages playback state, speed, aspect ratio, and control visibility.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val playerBuilder: ExoPlayer.Builder
) : ViewModel() {

    companion object {
        val SPEED_OPTIONS = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
    }

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun updatePlayingState(isPlaying: Boolean) {
        _uiState.value = _uiState.value.copy(isPlaying = isPlaying)
    }

    fun updatePosition(position: Long, duration: Long, buffered: Long) {
        _uiState.value = _uiState.value.copy(
            currentPosition = position,
            duration = duration,
            bufferedPosition = buffered
        )
    }

    fun toggleControls() {
        _uiState.value = _uiState.value.copy(
            isControlsVisible = !_uiState.value.isControlsVisible
        )
    }

    fun setControlsVisible(visible: Boolean) {
        _uiState.value = _uiState.value.copy(isControlsVisible = visible)
    }

    fun toggleRotationLock() {
        _uiState.value = _uiState.value.copy(isLocked = !_uiState.value.isLocked)
    }

    fun setPlaybackSpeed(speed: Float) {
        _uiState.value = _uiState.value.copy(playbackSpeed = speed, showSpeedMenu = false)
    }

    fun toggleSpeedMenu() {
        _uiState.value = _uiState.value.copy(showSpeedMenu = !_uiState.value.showSpeedMenu)
    }

    fun cycleAspectRatio() {
        val next = when (_uiState.value.aspectRatioMode) {
            AspectRatioMode.FIT -> AspectRatioMode.FILL
            AspectRatioMode.FILL -> AspectRatioMode.ZOOM
            AspectRatioMode.ZOOM -> AspectRatioMode.FIT
        }
        _uiState.value = _uiState.value.copy(aspectRatioMode = next)
    }
}

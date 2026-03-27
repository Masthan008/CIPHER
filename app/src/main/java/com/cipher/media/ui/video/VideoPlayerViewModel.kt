package com.cipher.media.ui.video

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * UI state for video player.
 */
data class PlayerUiState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val bufferedPosition: Long = 0L,
    val isControlsVisible: Boolean = true,
    val isLocked: Boolean = false,          // Screen rotation lock
    val playbackSpeed: Float = 1.0f
) {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration else 0f
    val bufferedProgress: Float
        get() = if (duration > 0) bufferedPosition.toFloat() / duration else 0f
}

/**
 * ViewModel for video player screen.
 * Provides player state and exposes ExoPlayer.Builder for the screen to construct
 * the actual player instance within the appropriate lifecycle.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val playerBuilder: ExoPlayer.Builder
) : ViewModel() {

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
}

package com.cipher.media.ui.video

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.cipher.media.billing.model.SubscriptionTier

// FREE FEATURE — All player controls available to every user

/**
 * UI state for video player — includes speed, aspect ratio, screen lock, PiP controls.
 */
data class PlayerUiState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val bufferedPosition: Long = 0L,
    val isControlsVisible: Boolean = true,
    val isLocked: Boolean = false,              // Screen rotation lock
    val isScreenLocked: Boolean = false,         // Kids mode — blocks all touch
    val playbackSpeed: Float = 1.0f,
    val aspectRatioMode: AspectRatioMode = AspectRatioMode.FIT,
    val showSpeedMenu: Boolean = false,
    val isPro: Boolean = false                   // Pro tier status
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
 * Manages playback state, speed, aspect ratio, screen lock, and control visibility.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    playerBuilder: ExoPlayer.Builder,
    private val billingRepository: com.cipher.media.billing.BillingRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        val SPEED_OPTIONS = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
    }

    private val trackSelector = DefaultTrackSelector(context)
    val playerBuilder: ExoPlayer.Builder = playerBuilder.setTrackSelector(trackSelector)

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        // Collect active tier and apply resolution limits
        viewModelScope.launch {
            billingRepository.activeTier.collect { tier ->
                _uiState.value = _uiState.value.copy(
                    isPro = tier == SubscriptionTier.PRO || tier == SubscriptionTier.POWER
                )
                
                // Enforce resolution limits based on tier
                val parametersBuilder = trackSelector.buildUponParameters()
                when (tier) {
                    SubscriptionTier.FREE -> {
                        // Max 720p
                        parametersBuilder.setMaxVideoSize(1280, 720)
                    }
                    SubscriptionTier.PRO -> {
                        // Max 1080p
                        parametersBuilder.setMaxVideoSize(1920, 1080)
                    }
                    SubscriptionTier.POWER -> {
                        // Max 4K (unlimited)
                        parametersBuilder.clearVideoSizeConstraints()
                    }
                }
                trackSelector.setParameters(parametersBuilder)
            }
        }
    }

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

    // FREE FEATURE: Screen lock (Kids Mode)
    fun toggleScreenLock() {
        val newLocked = !_uiState.value.isScreenLocked
        _uiState.value = _uiState.value.copy(
            isScreenLocked = newLocked,
            isControlsVisible = !newLocked  // Hide controls when locking
        )
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

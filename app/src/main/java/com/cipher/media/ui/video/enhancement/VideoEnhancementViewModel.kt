package com.cipher.media.ui.video.enhancement

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.ExoPlayer
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ═══════════════════════════════════════════════
//  UI STATE
// ═══════════════════════════════════════════════

data class EnhancementUiState(
    // Tier
    val tier: SubscriptionTier = SubscriptionTier.FREE,
    val isPro: Boolean = false,
    val isPower: Boolean = false,

    // Speed
    val currentSpeed: Float = 1.0f,
    val pitchCorrectionEnabled: Boolean = true,
    val showSpeedSelector: Boolean = false,

    // Sleep Timer
    val sleepTimerActive: Boolean = false,
    val sleepTimerRemainingMs: Long = 0L,
    val sleepTimerAction: SleepTimerAction = SleepTimerAction.PAUSE,
    val showSleepTimerDialog: Boolean = false,

    // A-B Repeat
    val abRepeatEnabled: Boolean = false,
    val pointA: Long = -1L,
    val pointB: Long = -1L,

    // Crop / Zoom
    val zoomLevel: Float = 1.0f,
    val panX: Float = 0f,
    val panY: Float = 0f,
    val cropMode: CropMode = CropMode.FIT,
    val showCropZoomDialog: Boolean = false,

    // Screenshot
    val screenshotInProgress: Boolean = false,
    val lastScreenshotPath: String? = null,
    val showScreenshotFeedback: Boolean = false
) {
    val sleepTimerMinutesRemaining: Int
        get() = ((sleepTimerRemainingMs / 1000) / 60).toInt()
    val sleepTimerSecondsRemaining: Int
        get() = ((sleepTimerRemainingMs / 1000) % 60).toInt()
    val sleepTimerDisplay: String
        get() = "${sleepTimerMinutesRemaining}:${sleepTimerSecondsRemaining.toString().padStart(2, '0')}"
    val hasPointA: Boolean get() = pointA >= 0
    val hasPointB: Boolean get() = pointB >= 0
    val isABLooping: Boolean get() = abRepeatEnabled && hasPointA && hasPointB
}

enum class SleepTimerAction(val label: String) {
    PAUSE("Pause Playback"),
    CLOSE("Close App")
}

enum class CropMode(val label: String, val proOnly: Boolean = false) {
    FIT("Fit", false),
    FILL("Fill", false),
    RATIO_16_9("16:9", true),
    RATIO_4_3("4:3", true),
    CUSTOM("Custom", true)
}

// ═══════════════════════════════════════════════
//  VIEW MODEL
// ═══════════════════════════════════════════════

@HiltViewModel
class VideoEnhancementViewModel @Inject constructor(
    private val billingRepository: BillingRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        val FREE_SPEEDS = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
        val PRO_SPEEDS = listOf(0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 2.5f, 3.0f, 4.0f)

        val FREE_SLEEP_PRESETS = listOf(15, 30, 60, 90, 120) // minutes
    }

    private val _uiState = MutableStateFlow(EnhancementUiState())
    val uiState: StateFlow<EnhancementUiState> = _uiState.asStateFlow()

    private var player: ExoPlayer? = null
    private var sleepTimer: CountDownTimer? = null
    private var abRepeatJob: Job? = null

    init {
        viewModelScope.launch {
            billingRepository.activeTier.collect { tier ->
                _uiState.value = _uiState.value.copy(
                    tier = tier,
                    isPro = tier == SubscriptionTier.PRO || tier == SubscriptionTier.POWER,
                    isPower = tier == SubscriptionTier.POWER
                )
            }
        }
    }

    fun attachPlayer(exoPlayer: ExoPlayer) {
        player = exoPlayer
    }

    // ═══════════ SPEED ═══════════

    fun getAvailableSpeeds(): List<Float> =
        if (_uiState.value.isPro) PRO_SPEEDS else FREE_SPEEDS

    fun setSpeed(speed: Float) {
        val state = _uiState.value
        // Enforce tier limits
        val clampedSpeed = if (!state.isPro) {
            speed.coerceIn(0.5f, 2.0f)
        } else {
            speed.coerceIn(0.25f, 4.0f)
        }

        val pitch = if (state.isPro && state.pitchCorrectionEnabled) 1.0f else clampedSpeed
        player?.playbackParameters = PlaybackParameters(clampedSpeed, pitch)

        _uiState.value = state.copy(currentSpeed = clampedSpeed, showSpeedSelector = false)
    }

    fun togglePitchCorrection() {
        if (!_uiState.value.isPro) return
        val newVal = !_uiState.value.pitchCorrectionEnabled
        _uiState.value = _uiState.value.copy(pitchCorrectionEnabled = newVal)
        // Re-apply current speed with new pitch setting
        setSpeed(_uiState.value.currentSpeed)
    }

    fun showSpeedSelector() { _uiState.value = _uiState.value.copy(showSpeedSelector = true) }
    fun hideSpeedSelector() { _uiState.value = _uiState.value.copy(showSpeedSelector = false) }

    // ═══════════ SLEEP TIMER ═══════════

    fun showSleepTimerDialog() { _uiState.value = _uiState.value.copy(showSleepTimerDialog = true) }
    fun hideSleepTimerDialog() { _uiState.value = _uiState.value.copy(showSleepTimerDialog = false) }

    fun setSleepTimerAction(action: SleepTimerAction) {
        _uiState.value = _uiState.value.copy(sleepTimerAction = action)
    }

    fun startSleepTimer(minutes: Int) {
        // Enforce: custom > 120 min requires Pro
        if (minutes > 120 && !_uiState.value.isPro) return

        cancelSleepTimer()

        val durationMs = minutes * 60 * 1000L
        sleepTimer = object : CountDownTimer(durationMs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _uiState.value = _uiState.value.copy(
                    sleepTimerActive = true,
                    sleepTimerRemainingMs = millisUntilFinished
                )
            }

            override fun onFinish() {
                executeSleepAction()
                _uiState.value = _uiState.value.copy(
                    sleepTimerActive = false,
                    sleepTimerRemainingMs = 0L
                )
            }
        }.start()

        _uiState.value = _uiState.value.copy(
            showSleepTimerDialog = false,
            sleepTimerActive = true,
            sleepTimerRemainingMs = durationMs
        )
    }

    fun cancelSleepTimer() {
        sleepTimer?.cancel()
        sleepTimer = null
        _uiState.value = _uiState.value.copy(
            sleepTimerActive = false,
            sleepTimerRemainingMs = 0L
        )
    }

    private fun executeSleepAction() {
        when (_uiState.value.sleepTimerAction) {
            SleepTimerAction.PAUSE -> player?.pause()
            SleepTimerAction.CLOSE -> {
                player?.pause()
                (context as? Activity)?.finishAffinity()
            }
        }
    }

    // ═══════════ A-B REPEAT ═══════════

    fun setPointA() {
        if (!_uiState.value.isPro) return
        val pos = player?.currentPosition ?: return
        _uiState.value = _uiState.value.copy(pointA = pos, abRepeatEnabled = true)
        // If both points set, start looping
        if (_uiState.value.hasPointB) startABLoop()
    }

    fun setPointB() {
        if (!_uiState.value.isPro) return
        val pos = player?.currentPosition ?: return
        if (pos <= _uiState.value.pointA) return // B must be after A
        _uiState.value = _uiState.value.copy(pointB = pos, abRepeatEnabled = true)
        startABLoop()
    }

    fun clearABRepeat() {
        abRepeatJob?.cancel()
        abRepeatJob = null
        _uiState.value = _uiState.value.copy(
            abRepeatEnabled = false,
            pointA = -1L,
            pointB = -1L
        )
    }

    private fun startABLoop() {
        abRepeatJob?.cancel()
        abRepeatJob = viewModelScope.launch {
            while (true) {
                val currentPos = player?.currentPosition ?: break
                val b = _uiState.value.pointB
                if (b > 0 && currentPos >= b) {
                    player?.seekTo(_uiState.value.pointA.coerceAtLeast(0))
                }
                delay(50) // Check every 50ms
            }
        }
    }

    // ═══════════ CROP / ZOOM ═══════════

    fun showCropZoomDialog() { _uiState.value = _uiState.value.copy(showCropZoomDialog = true) }
    fun hideCropZoomDialog() { _uiState.value = _uiState.value.copy(showCropZoomDialog = false) }

    fun setZoomLevel(zoom: Float) {
        if (!_uiState.value.isPro) return
        _uiState.value = _uiState.value.copy(
            zoomLevel = zoom.coerceIn(1f, 3f)
        )
    }

    fun setPan(dx: Float, dy: Float) {
        if (_uiState.value.zoomLevel <= 1f) return
        _uiState.value = _uiState.value.copy(
            panX = (_uiState.value.panX + dx).coerceIn(-1f, 1f),
            panY = (_uiState.value.panY + dy).coerceIn(-1f, 1f)
        )
    }

    fun setCropMode(mode: CropMode) {
        if (mode.proOnly && !_uiState.value.isPro) return
        _uiState.value = _uiState.value.copy(cropMode = mode)
    }

    fun resetZoomPan() {
        _uiState.value = _uiState.value.copy(zoomLevel = 1f, panX = 0f, panY = 0f)
    }

    // ═══════════ SCREENSHOT ═══════════

    fun showScreenshotFeedback(path: String) {
        _uiState.value = _uiState.value.copy(
            lastScreenshotPath = path,
            showScreenshotFeedback = true
        )
        viewModelScope.launch {
            delay(2500)
            _uiState.value = _uiState.value.copy(showScreenshotFeedback = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sleepTimer?.cancel()
        abRepeatJob?.cancel()
    }
}

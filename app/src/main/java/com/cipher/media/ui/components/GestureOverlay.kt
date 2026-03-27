package com.cipher.media.ui.components

import android.content.Context
import android.media.AudioManager
import android.view.Window
import androidx.compose.animation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.abs

/**
 * Transparent overlay that captures gesture inputs for the video player.
 *
 * Gestures:
 * - Horizontal drag: Seek forward/backward
 * - Vertical drag (right half): Volume control
 * - Vertical drag (left half): Brightness control
 * - Double tap left: Seek -10s
 * - Double tap right: Seek +10s
 * - Single tap: Toggle controls visibility
 *
 * @param onToggleControls Called when user taps to show/hide controls
 * @param onSeekDelta Called with time delta in ms (positive = forward)
 * @param onBrightnessChange Called with brightness delta (-1f to 1f)
 * @param onVolumeChange Called with volume delta (-1f to 1f)
 * @param window Activity window for brightness control
 */
@Composable
fun GestureOverlay(
    onToggleControls: () -> Unit,
    onSeekDelta: (Long) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit,
    window: Window?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    // Feedback display state
    var feedbackText by remember { mutableStateOf<String?>(null) }
    var feedbackIcon by remember { mutableStateOf(Icons.Default.FastForward) }

    // Double-tap detection
    var lastTapTime by remember { mutableLongStateOf(0L) }
    var lastTapX by remember { mutableFloatStateOf(0f) }

    // Auto-hide feedback after delay
    LaunchedEffect(feedbackText) {
        if (feedbackText != null) {
            delay(800)
            feedbackText = null
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val now = System.currentTimeMillis()
                        val timeDiff = now - lastTapTime
                        val isDoubleTap = timeDiff < 300

                        if (isDoubleTap) {
                            // Double tap: seek ±10s
                            val isRightSide = offset.x > size.width / 2
                            val seekMs = if (isRightSide) 10_000L else -10_000L
                            onSeekDelta(seekMs)
                            feedbackIcon = if (isRightSide) Icons.Default.FastForward else Icons.Default.FastRewind
                            feedbackText = if (isRightSide) "+10s" else "-10s"
                            lastTapTime = 0 // Reset to avoid triple-tap
                        } else {
                            lastTapTime = now
                            lastTapX = offset.x
                            // Single tap: toggle controls (with small delay to check for double-tap)
                            // Using a coroutine to wait and check
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                // Drag gesture for seek, volume, brightness
                var totalDragX = 0f
                var totalDragY = 0f
                var dragStartX = 0f
                var gestureType: GestureType? = null

                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: continue

                        if (change.pressed && change.previousPressed) {
                            val dragX = change.position.x - change.previousPosition.x
                            val dragY = change.position.y - change.previousPosition.y

                            // Determine gesture type on first significant movement
                            if (gestureType == null) {
                                totalDragX += dragX
                                totalDragY += dragY

                                if (abs(totalDragX) > 20 || abs(totalDragY) > 20) {
                                    gestureType = if (abs(totalDragX) > abs(totalDragY)) {
                                        GestureType.SEEK
                                    } else {
                                        val isRightHalf = change.position.x > size.width / 2
                                        if (isRightHalf) GestureType.VOLUME else GestureType.BRIGHTNESS
                                    }
                                    dragStartX = change.position.x
                                }
                            }

                            // Apply gesture
                            when (gestureType) {
                                GestureType.SEEK -> {
                                    // Seek: 1px = ~100ms, with acceleration
                                    val seekMs = (dragX * 100).toLong()
                                    onSeekDelta(seekMs)
                                }
                                GestureType.VOLUME -> {
                                    // Volume: drag up = louder, drag down = quieter
                                    val volumeDelta = -dragY / size.height
                                    onVolumeChange(volumeDelta)

                                    val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                    val curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                                    val pct = (curVol * 100 / maxVol)
                                    feedbackIcon = when {
                                        pct > 50 -> Icons.Default.VolumeUp
                                        pct > 0 -> Icons.Default.VolumeDown
                                        else -> Icons.Default.VolumeOff
                                    }
                                    feedbackText = "Vol: $pct%"
                                }
                                GestureType.BRIGHTNESS -> {
                                    // Brightness: drag up = brighter
                                    val brightDelta = -dragY / size.height
                                    onBrightnessChange(brightDelta)

                                    val currentBrightness = window?.attributes?.screenBrightness ?: 0.5f
                                    val pct = (currentBrightness * 100).toInt().coerceIn(0, 100)
                                    feedbackIcon = if (pct > 50) Icons.Default.BrightnessHigh else Icons.Default.BrightnessLow
                                    feedbackText = "Bright: $pct%"
                                }
                                null -> {}
                            }
                        }

                        // Reset on finger up
                        if (!change.pressed && change.previousPressed) {
                            totalDragX = 0f
                            totalDragY = 0f
                            gestureType = null

                            // If no gesture was detected, it's a tap → toggle controls
                            if (abs(totalDragX) < 10 && abs(totalDragY) < 10) {
                                onToggleControls()
                            }
                        }
                    }
                }
            }
    ) {
        // Feedback indicator (centered)
        AnimatedVisibility(
            visible = feedbackText != null,
            modifier = Modifier.align(Alignment.Center),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = feedbackIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = feedbackText ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}

private enum class GestureType {
    SEEK, VOLUME, BRIGHTNESS
}

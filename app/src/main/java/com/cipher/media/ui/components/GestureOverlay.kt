package com.cipher.media.ui.components

import android.content.Context
import android.media.AudioManager
import android.view.Window
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.CIPHERPrimary
import kotlinx.coroutines.*
import kotlin.math.abs

// FREE FEATURE — All gesture controls are available to every user

/**
 * Industry-leading gesture overlay for video player.
 * 
 * Gesture Zones:
 * ┌────────┬──────────────┬────────┐
 * │ LEFT   │   CENTER     │ RIGHT  │
 * │ 33%    │    34%       │  33%   │
 * │        │              │        │
 * │Bright  │   Seek /     │ Volume │
 * │ drag   │  Play/Pause  │  drag  │
 * │        │              │        │
 * │Dbl-tap │  Dbl-tap     │Dbl-tap │
 * │ -10s   │ Play/Pause   │  +10s  │
 * └────────┴──────────────┴────────┘
 * 
 * + Long press: 2x speed (release resets)
 * + Horizontal drag: Continuous seek with preview
 * + Pinch: Zoom (via aspect ratio cycling)
 */
@Composable
fun GestureOverlay(
    onToggleControls: () -> Unit,
    onSeekDelta: (Long) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit,
    onPlayPause: (() -> Unit)? = null,
    onLongPressSpeed: ((Float) -> Unit)? = null,
    onZoomCycle: (() -> Unit)? = null,
    isScreenLocked: Boolean = false,
    window: Window?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val scope = rememberCoroutineScope()

    // ── Feedback display state ──
    var feedbackText by remember { mutableStateOf<String?>(null) }
    var feedbackIcon by remember { mutableStateOf<ImageVector>(Icons.Default.FastForward) }

    // ── Brightness / Volume overlay bar state ──
    var brightnessPercent by remember { mutableIntStateOf(-1) }  // -1 = hidden
    var volumePercent by remember { mutableIntStateOf(-1) }      // -1 = hidden

    // ── Seek preview state ──
    var seekPreviewText by remember { mutableStateOf<String?>(null) }

    // ── Long press 2x indicator ──
    var isLongPressing by remember { mutableStateOf(false) }

    // ── Double-tap ripple animation ──
    var doubleTapSide by remember { mutableStateOf<DoubleTapSide?>(null) }
    var doubleTapPosition by remember { mutableStateOf(Offset.Zero) }

    // Auto-hide feedback
    LaunchedEffect(feedbackText) {
        if (feedbackText != null) {
            delay(800)
            feedbackText = null
        }
    }
    LaunchedEffect(brightnessPercent) {
        if (brightnessPercent >= 0) {
            delay(1200)
            brightnessPercent = -1
        }
    }
    LaunchedEffect(volumePercent) {
        if (volumePercent >= 0) {
            delay(1200)
            volumePercent = -1
        }
    }
    LaunchedEffect(seekPreviewText) {
        if (seekPreviewText != null) {
            delay(1000)
            seekPreviewText = null
        }
    }
    LaunchedEffect(doubleTapSide) {
        if (doubleTapSide != null) {
            delay(600)
            doubleTapSide = null
        }
    }

    // ── SCREEN LOCK: block all gestures ──
    if (isScreenLocked) {
        Box(modifier = modifier.fillMaxSize())
        return
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // Combined gesture handler with tap disambiguation
                var lastTapTimeMs = 0L
                var lastTapX = 0f
                var pendingTapJob: Job? = null
                var gestureType: GestureType? = null
                var totalDragX = 0f
                var totalDragY = 0f
                var isLongPressActive = false
                var longPressJob: Job? = null
                var seekAccumulator = 0L
                var pinchStartDist = 0f

                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val changes = event.changes

                        when (event.type) {
                            PointerEventType.Press -> {
                                val change = changes.firstOrNull() ?: continue
                                totalDragX = 0f
                                totalDragY = 0f
                                gestureType = null
                                seekAccumulator = 0L

                                // Pinch detection - 2 fingers
                                if (changes.size >= 2) {
                                    val dx = changes[0].position.x - changes[1].position.x
                                    val dy = changes[0].position.y - changes[1].position.y
                                    pinchStartDist = kotlin.math.sqrt(dx * dx + dy * dy)
                                    gestureType = GestureType.PINCH
                                }

                                // Start long press timer (single finger only)
                                if (changes.size == 1) {
                                    longPressJob = scope.launch {
                                        delay(500)  // 500ms = long press threshold
                                        isLongPressActive = true
                                        isLongPressing = true
                                        onLongPressSpeed?.invoke(2.0f)
                                        feedbackIcon = Icons.Default.Speed
                                        feedbackText = "⚡ 2x Speed"
                                    }
                                }
                            }

                            PointerEventType.Move -> {
                                val change = changes.firstOrNull() ?: continue

                                // Pinch: detect zoom
                                if (changes.size >= 2 && gestureType == GestureType.PINCH) {
                                    val dx = changes[0].position.x - changes[1].position.x
                                    val dy = changes[0].position.y - changes[1].position.y
                                    val currentDist = kotlin.math.sqrt(dx * dx + dy * dy)
                                    val scaleDiff = currentDist - pinchStartDist
                                    if (abs(scaleDiff) > 100) {
                                        onZoomCycle?.invoke()
                                        pinchStartDist = currentDist
                                        feedbackIcon = Icons.Default.ZoomIn
                                        feedbackText = "Zoom"
                                    }
                                    continue
                                }

                                if (change.pressed && change.previousPressed && changes.size == 1) {
                                    val dragX = change.position.x - change.previousPosition.x
                                    val dragY = change.position.y - change.previousPosition.y

                                    // Determine gesture type on first significant movement
                                    if (gestureType == null) {
                                        totalDragX += dragX
                                        totalDragY += dragY

                                        if (abs(totalDragX) > 20 || abs(totalDragY) > 20) {
                                            // Cancel long press if drag detected
                                            longPressJob?.cancel()
                                            longPressJob = null

                                            gestureType = if (abs(totalDragX) > abs(totalDragY)) {
                                                GestureType.SEEK
                                            } else {
                                                // Left 33% = brightness, Right 33% = volume
                                                val normalizedX = change.position.x / size.width
                                                if (normalizedX < 0.33f) GestureType.BRIGHTNESS
                                                else GestureType.VOLUME
                                            }
                                        }
                                    }

                                    // Apply gesture
                                    when (gestureType) {
                                        GestureType.SEEK -> {
                                            // FREE FEATURE: Continuous seek — 1px ≈ 100ms
                                            val seekMs = (dragX * 100).toLong()
                                            seekAccumulator += seekMs
                                            onSeekDelta(seekMs)

                                            val deltaSeconds = seekAccumulator / 1000
                                            val sign = if (deltaSeconds >= 0) "+" else ""
                                            seekPreviewText = "${sign}${deltaSeconds}s"
                                            feedbackIcon = if (deltaSeconds >= 0) Icons.Default.FastForward else Icons.Default.FastRewind
                                        }
                                        GestureType.VOLUME -> {
                                            // FREE FEATURE: Volume control drag
                                            val volumeDelta = -dragY / size.height
                                            onVolumeChange(volumeDelta)

                                            val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                            val curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                                            volumePercent = (curVol * 100 / maxVol)
                                        }
                                        GestureType.BRIGHTNESS -> {
                                            // FREE FEATURE: Brightness control drag
                                            val brightDelta = -dragY / size.height
                                            onBrightnessChange(brightDelta)

                                            val currentBrightness = window?.attributes?.screenBrightness ?: 0.5f
                                            brightnessPercent = (currentBrightness * 100).toInt().coerceIn(0, 100)
                                        }
                                        else -> {}
                                    }
                                }
                            }

                            PointerEventType.Release -> {
                                // Cancel long press timer
                                longPressJob?.cancel()
                                longPressJob = null

                                // Release long press 2x speed
                                if (isLongPressActive) {
                                    isLongPressActive = false
                                    isLongPressing = false
                                    onLongPressSpeed?.invoke(1.0f)
                                    feedbackText = null
                                }

                                // Handle tap (no significant drag)
                                if (gestureType == null && changes.size <= 1) {
                                    val change = changes.firstOrNull() ?: continue
                                    val tapX = change.position.x
                                    val now = System.currentTimeMillis()
                                    val timeDiff = now - lastTapTimeMs

                                    if (timeDiff < 300 && abs(tapX - lastTapX) < 100) {
                                        // ── DOUBLE TAP ──
                                        pendingTapJob?.cancel()
                                        lastTapTimeMs = 0L

                                        val normalizedX = tapX / size.width
                                        when {
                                            normalizedX < 0.33f -> {
                                                // FREE FEATURE: Double-tap left → -10s
                                                onSeekDelta(-10_000L)
                                                doubleTapSide = DoubleTapSide.LEFT
                                                doubleTapPosition = change.position
                                                feedbackIcon = Icons.Default.FastRewind
                                                feedbackText = "-10s"
                                            }
                                            normalizedX > 0.67f -> {
                                                // FREE FEATURE: Double-tap right → +10s
                                                onSeekDelta(10_000L)
                                                doubleTapSide = DoubleTapSide.RIGHT
                                                doubleTapPosition = change.position
                                                feedbackIcon = Icons.Default.FastForward
                                                feedbackText = "+10s"
                                            }
                                            else -> {
                                                // FREE FEATURE: Double-tap center → play/pause
                                                onPlayPause?.invoke()
                                                doubleTapSide = DoubleTapSide.CENTER
                                                doubleTapPosition = change.position
                                            }
                                        }
                                    } else {
                                        // Potential single tap — wait for double-tap window
                                        lastTapTimeMs = now
                                        lastTapX = tapX
                                        pendingTapJob?.cancel()
                                        pendingTapJob = scope.launch {
                                            delay(300)
                                            // Confirmed single tap → toggle controls
                                            onToggleControls()
                                        }
                                    }
                                }

                                gestureType = null
                                totalDragX = 0f
                                totalDragY = 0f
                                seekAccumulator = 0L
                            }
                        }
                    }
                }
            }
    ) {
        // ══════════════════════════════════════════════
        //  VISUAL OVERLAYS
        // ══════════════════════════════════════════════

        // ── Brightness vertical bar (left side) ──
        AnimatedVisibility(
            visible = brightnessPercent >= 0,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp),
            enter = fadeIn() + slideInHorizontally { -it },
            exit = fadeOut() + slideOutHorizontally { -it }
        ) {
            VerticalIndicatorBar(
                icon = if (brightnessPercent > 50) Icons.Default.BrightnessHigh else Icons.Default.BrightnessLow,
                percent = brightnessPercent,
                color = Color(0xFFFFD54F)  // Warm yellow
            )
        }

        // ── Volume vertical bar (right side) ──
        AnimatedVisibility(
            visible = volumePercent >= 0,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp),
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut() + slideOutHorizontally { it }
        ) {
            VerticalIndicatorBar(
                icon = when {
                    volumePercent > 50 -> Icons.Default.VolumeUp
                    volumePercent > 0 -> Icons.Default.VolumeDown
                    else -> Icons.Default.VolumeOff
                },
                percent = volumePercent,
                color = CIPHERPrimary
            )
        }

        // ── Seek preview (top center) ──
        AnimatedVisibility(
            visible = seekPreviewText != null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp),
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = seekPreviewText ?: "",
                    color = CIPHERPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        // ── Long press 2x speed indicator ──
        AnimatedVisibility(
            visible = isLongPressing,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CIPHERPrimary.copy(alpha = 0.9f))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.Speed, null, tint = Color.Black, modifier = Modifier.size(18.dp))
                    Text("2x", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }
            }
        }

        // ── Center feedback indicator (for double-tap ±10s) ──
        AnimatedVisibility(
            visible = feedbackText != null && !isLongPressing && brightnessPercent < 0 && volumePercent < 0,
            modifier = Modifier.align(Alignment.Center),
            enter = fadeIn() + scaleIn(initialScale = 0.5f),
            exit = fadeOut() + scaleOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = feedbackIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Text(
                    text = feedbackText ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // ── Double-tap ripple animation ──
        doubleTapSide?.let { side ->
            val rippleAlpha by animateFloatAsState(
                targetValue = 0f,
                animationSpec = tween(600),
                label = "ripple"
            )
            val alignment = when (side) {
                DoubleTapSide.LEFT -> Alignment.CenterStart
                DoubleTapSide.RIGHT -> Alignment.CenterEnd
                DoubleTapSide.CENTER -> Alignment.Center
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(if (side == DoubleTapSide.CENTER) 0.34f else 0.33f)
                    .align(alignment)
                    .background(
                        Color.White.copy(alpha = 0.08f * (1f - rippleAlpha))
                    )
            )
        }
    }
}

/**
 * Vertical progress indicator bar for brightness/volume overlays.
 * Shows an icon at top, percentage text, and a vertical fill bar.
 */
@Composable
private fun VerticalIndicatorBar(
    icon: ImageVector,
    percent: Int,
    color: Color
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.Black.copy(alpha = 0.7f))
            .padding(horizontal = 12.dp, vertical = 16.dp)
            .width(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(22.dp)
        )

        // Vertical bar container
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            // Fill from bottom
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = percent / 100f)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(color, color.copy(alpha = 0.6f))
                        )
                    )
            )
        }

        Text(
            text = "$percent%",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private enum class GestureType {
    SEEK, VOLUME, BRIGHTNESS, PINCH
}

private enum class DoubleTapSide {
    LEFT, CENTER, RIGHT
}

package com.cipher.media.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.PlayerSeekBarActive
import com.cipher.media.ui.theme.PlayerSeekBarBuffered
import com.cipher.media.ui.theme.PlayerSeekBarInactive

/**
 * Custom seek bar for the video player.
 *
 * @param progress Current playback position (0f..1f)
 * @param bufferedProgress Buffered position (0f..1f)
 * @param onSeek Called when user drags to a new position (0f..1f)
 * @param onSeekStarted Called when drag begins
 * @param onSeekFinished Called when drag ends
 */
@Composable
fun SeekBar(
    progress: Float,
    bufferedProgress: Float,
    onSeek: (Float) -> Unit,
    onSeekStarted: () -> Unit = {},
    onSeekFinished: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isDragging by remember { mutableStateOf(false) }
    var dragProgress by remember { mutableFloatStateOf(0f) }
    val displayProgress = if (isDragging) dragProgress else progress

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newProgress = (offset.x / size.width).coerceIn(0f, 1f)
                    onSeek(newProgress)
                }
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset ->
                        isDragging = true
                        dragProgress = (offset.x / size.width).coerceIn(0f, 1f)
                        onSeekStarted()
                    },
                    onDragEnd = {
                        isDragging = false
                        onSeek(dragProgress)
                        onSeekFinished()
                    },
                    onDragCancel = {
                        isDragging = false
                        onSeekFinished()
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragProgress = (dragProgress + dragAmount / size.width).coerceIn(0f, 1f)
                        onSeek(dragProgress)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
        ) {
            val trackHeight = size.height

            // Background track
            drawRect(
                color = PlayerSeekBarInactive,
                size = Size(size.width, trackHeight)
            )

            // Buffered track
            drawRect(
                color = PlayerSeekBarBuffered,
                size = Size(size.width * bufferedProgress, trackHeight)
            )

            // Progress track
            drawRect(
                color = PlayerSeekBarActive,
                size = Size(size.width * displayProgress, trackHeight)
            )
        }

        // Thumb indicator
        Canvas(
            modifier = Modifier.fillMaxWidth()
        ) {
            val thumbX = size.width * displayProgress
            drawCircle(
                color = PlayerSeekBarActive,
                radius = if (isDragging) 10.dp.toPx() else 7.dp.toPx(),
                center = Offset(thumbX, 0f)
            )
        }
    }
}

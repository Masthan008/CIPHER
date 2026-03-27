package com.cipher.media.ui.video.streaming.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.util.TimeUtil

/**
 * Remote cast control overlay: shows what's playing on the Chromecast
 * with play/pause, seek, and stop controls.
 */
@Composable
fun CastControls(
    deviceName: String?,
    title: String?,
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.md)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.lg)
        ) {
            // Cast header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CastConnected, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text(
                    "Casting to ${deviceName ?: "Device"}",
                    color = CIPHERPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(Spacing.md))

            // Title
            Text(
                title ?: "Unknown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(Spacing.md))

            // Seek bar
            var seekPosition by remember { mutableFloatStateOf(0f) }
            val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f

            Slider(
                value = progress,
                onValueChange = { seekPosition = it },
                onValueChangeFinished = {
                    onSeek((seekPosition * duration).toLong())
                },
                colors = SliderDefaults.colors(
                    thumbColor = CIPHERPrimary,
                    activeTrackColor = CIPHERPrimary,
                    inactiveTrackColor = CIPHERDivider
                )
            )

            // Time labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(TimeUtil.formatDuration(currentPosition), color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
                Text(TimeUtil.formatDuration(duration), color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
            }

            Spacer(Modifier.height(Spacing.md))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rewind 10s
                IconButton(onClick = { onSeek(maxOf(0, currentPosition - 10_000)) }) {
                    Icon(Icons.Default.Replay10, "Rewind", tint = CIPHEROnSurface)
                }

                // Play/Pause (big)
                FilledIconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.size(56.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = CIPHERPrimary)
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        "Play/Pause",
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Forward 10s
                IconButton(onClick = { onSeek(minOf(duration, currentPosition + 10_000)) }) {
                    Icon(Icons.Default.Forward10, "Forward", tint = CIPHEROnSurface)
                }

                // Stop casting
                IconButton(onClick = onStop) {
                    Icon(Icons.Default.StopCircle, "Stop", tint = CIPHERError)
                }
            }
        }
    }
}

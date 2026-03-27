package com.cipher.media.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.theme.*

/**
 * Glassmorphic mini player with blurred background, progress bar, and controls.
 */
@Composable
fun MiniPlayer(
    currentAudio: AudioItem?,
    isPlaying: Boolean,
    onTap: () -> Unit,
    onPlayPause: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentAudio == null) return

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.sm, vertical = Spacing.xs)
            .height(Spacing.miniPlayerHeight)
            .pointerInput(Unit) { detectTapGestures(onTap = { onTap() }) },
        shape = RoundedCornerShape(Corners.large),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album art
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(Corners.medium))
                    .background(CIPHERSurfaceVariant)
            ) {
                if (currentAudio.albumArtUri != null) {
                    AsyncImage(
                        model = currentAudio.albumArtUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        Icons.Default.MusicNote, null,
                        tint = CIPHERPrimary.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp).align(Alignment.Center)
                    )
                }
            }

            // Title & artist
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.sm)
            ) {
                Text(
                    currentAudio.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = CIPHEROnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    currentAudio.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Play/Pause
            IconButton(onClick = onPlayPause) {
                Icon(
                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = CIPHERPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Close
            IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) {
                Icon(
                    Icons.Default.Close, "Dismiss",
                    tint = CIPHEROnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

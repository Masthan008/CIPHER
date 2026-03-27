package com.cipher.media.ui.audio.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.theme.*

/**
 * FREE FEATURE: Queue management bottom sheet.
 * Shows the full playback queue with current track highlight,
 * and allows clear queue + tap-to-play.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueBottomSheet(
    audioList: List<AudioItem>,
    currentAudio: AudioItem?,
    onTrackClick: (AudioItem) -> Unit,
    onClearQueue: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.QueueMusic, null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text(
                    "Play Queue",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CIPHEROnSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "${audioList.size} tracks",
                    color = CIPHEROnSurfaceVariant,
                    fontSize = 13.sp
                )
                Spacer(Modifier.width(Spacing.sm))
                if (audioList.isNotEmpty()) {
                    TextButton(onClick = onClearQueue) {
                        Text("Clear", color = CIPHERError, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.md))

            if (audioList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(Spacing.xl),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.MusicOff, null, tint = CIPHEROnSurfaceVariant, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(Spacing.md))
                    Text("Queue is empty", color = CIPHEROnSurface, fontWeight = FontWeight.SemiBold)
                    Text("Play a song to start", color = CIPHEROnSurfaceVariant, fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .animateContentSize()
                ) {
                    itemsIndexed(audioList) { index, track ->
                        val isCurrentTrack = track.uri == currentAudio?.uri

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(Corners.medium))
                                .background(
                                    if (isCurrentTrack) CIPHERPrimary.copy(alpha = 0.12f)
                                    else Color.Transparent
                                )
                                .clickable {
                                    onTrackClick(track)
                                    onDismiss()
                                }
                                .padding(vertical = 10.dp, horizontal = Spacing.sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Track number / now-playing indicator
                            if (isCurrentTrack) {
                                Icon(Icons.Default.GraphicEq, null, tint = CIPHERPrimary, modifier = Modifier.size(22.dp))
                            } else {
                                Text(
                                    "${index + 1}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CIPHEROnSurfaceVariant,
                                    modifier = Modifier.width(22.dp)
                                )
                            }
                            Spacer(Modifier.width(Spacing.sm))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    track.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isCurrentTrack) CIPHERPrimary else CIPHEROnSurface,
                                    fontWeight = if (isCurrentTrack) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    track.artist,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CIPHEROnSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

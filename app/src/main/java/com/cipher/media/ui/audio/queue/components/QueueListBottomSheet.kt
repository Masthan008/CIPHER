package com.cipher.media.ui.audio.queue.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity
import com.cipher.media.ui.theme.*

/**
 * Bottom sheet showing all queues with switch, rename, duplicate, delete actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueListBottomSheet(
    queues: List<PlaybackQueueEntity>,
    activeQueueId: String?,
    isPro: Boolean,
    canCreateMore: Boolean,
    onSwitchQueue: (String) -> Unit,
    onDeleteQueue: (String) -> Unit,
    onDuplicateQueue: (String) -> Unit,
    onCreateNew: () -> Unit,
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
                    "Your Queues",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CIPHEROnSurface,
                    modifier = Modifier.weight(1f)
                )
                Text("${queues.size} queues", color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
            }

            Spacer(Modifier.height(Spacing.md))

            // Queue list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                items(queues) { queue ->
                    val isActive = queue.id == activeQueueId
                    var showOptions by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isActive) CIPHERPrimary.copy(alpha = 0.12f)
                                else Color.Transparent
                            )
                            .clickable { onSwitchQueue(queue.id) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isActive) CIPHERPrimary.copy(alpha = 0.2f)
                                    else CIPHERSurfaceVariant
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                getQueueIcon(queue.iconName),
                                null,
                                tint = if (isActive) CIPHERPrimary else CIPHEROnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        // Queue info
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    queue.name,
                                    color = if (isActive) CIPHERPrimary else CIPHEROnSurface,
                                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                if (isActive) {
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        "● Playing",
                                        color = CIPHERPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                "${queue.playbackMode.lowercase()} • ${queue.currentIndex + 1} track",
                                color = CIPHEROnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        // Actions
                        IconButton(onClick = { showOptions = !showOptions }) {
                            Icon(
                                Icons.Default.MoreVert, null,
                                tint = CIPHEROnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Expanded options
                    if (showOptions) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 52.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AssistChip(
                                onClick = { onDuplicateQueue(queue.id); showOptions = false },
                                label = { Text("Duplicate", fontSize = 11.sp) },
                                leadingIcon = { Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(14.dp)) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Transparent,
                                    labelColor = CIPHEROnSurfaceVariant,
                                    leadingIconContentColor = CIPHEROnSurfaceVariant
                                ),
                                modifier = Modifier.height(28.dp)
                            )
                            if (queues.size > 1) {
                                AssistChip(
                                    onClick = { onDeleteQueue(queue.id); showOptions = false },
                                    label = { Text("Delete", fontSize = 11.sp) },
                                    leadingIcon = { Icon(Icons.Default.Delete, null, modifier = Modifier.size(14.dp)) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color.Transparent,
                                        labelColor = CIPHERError,
                                        leadingIconContentColor = CIPHERError
                                    ),
                                    modifier = Modifier.height(28.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Create new button
            OutlinedButton(
                onClick = onCreateNew,
                modifier = Modifier.fillMaxWidth(),
                enabled = canCreateMore,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CIPHERPrimary),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(CIPHERPrimary.copy(alpha = 0.4f))
                )
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("New Queue")
                if (!canCreateMore && !isPro) {
                    Spacer(Modifier.width(6.dp))
                    Text("(PRO)", color = CIPHERPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

/**
 * Map icon name string to Material icon.
 */
fun getQueueIcon(iconName: String): ImageVector = when (iconName) {
    "headphones" -> Icons.Default.Headphones
    "fitness" -> Icons.Default.FitnessCenter
    "bedtime" -> Icons.Default.Bedtime
    "school" -> Icons.Default.School
    "work" -> Icons.Default.Work
    "celebration" -> Icons.Default.Celebration
    "favorite" -> Icons.Default.Favorite
    "music_note" -> Icons.Default.MusicNote
    "directions_car" -> Icons.Default.DirectionsCar
    else -> Icons.Default.QueueMusic
}

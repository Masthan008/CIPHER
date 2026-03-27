package com.cipher.media.ui.audio.queue.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity
import com.cipher.media.ui.theme.*

/**
 * Horizontal queue selector strip — sits above the mini-player.
 * Shows all queues as compact chips with quick-switch on tap.
 */
@Composable
fun QueueSelector(
    queues: List<PlaybackQueueEntity>,
    activeQueueId: String?,
    canCreateMore: Boolean,
    onSwitchQueue: (String) -> Unit,
    onCreateNew: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentPadding = PaddingValues(horizontal = Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(queues) { queue ->
            val isActive = queue.id == activeQueueId

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isActive) CIPHERPrimary.copy(alpha = 0.2f)
                        else CIPHERSurfaceVariant.copy(alpha = 0.5f)
                    )
                    .clickable { onSwitchQueue(queue.id) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    getQueueIcon(queue.iconName),
                    null,
                    tint = if (isActive) CIPHERPrimary else CIPHEROnSurfaceVariant,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    queue.name,
                    color = if (isActive) CIPHERPrimary else CIPHEROnSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(CIPHERPrimary)
                    )
                }
            }
        }

        // Add new queue chip
        if (canCreateMore) {
            item {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .clickable { onCreateNew() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Default.Add, null, tint = CIPHEROnSurfaceVariant.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                    Text("New", color = CIPHEROnSurfaceVariant.copy(alpha = 0.5f), fontSize = 11.sp)
                }
            }
        }
    }
}

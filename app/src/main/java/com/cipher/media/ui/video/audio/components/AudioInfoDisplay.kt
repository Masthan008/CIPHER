package com.cipher.media.ui.video.audio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.video.audio.model.AudioTrackInfo
import com.cipher.media.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioInfoDisplay(
    tracks: List<AudioTrackInfo>,
    onTrackSelected: (AudioTrackInfo) -> Unit,
    isHiRes: Boolean,
    isProUser: Boolean,
    modifier: Modifier = Modifier
) {
    val activeTrack = tracks.find { it.isSelected }
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = CIPHERSurface,
        shape = RoundedCornerShape(Corners.medium),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Corners.medium))
            .clickable(enabled = isProUser && tracks.size > 1) { expanded = true }
    ) {
        Row(
            modifier = Modifier.padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Audiotrack,
                contentDescription = null,
                tint = CIPHERPrimary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(Spacing.md))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activeTrack?.displayTitle ?: "Unknown Track",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CIPHEROnSurface,
                    fontWeight = FontWeight.Bold
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = activeTrack?.codec ?: "Audio",
                        style = MaterialTheme.typography.labelSmall,
                        color = CIPHEROnSurfaceVariant
                    )
                    
                    if (isHiRes) {
                        Spacer(modifier = Modifier.width(Spacing.sm))
                        Box(
                            modifier = Modifier
                                .background(CIPHERAccent, RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                "HI-RES", 
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                color = CIPHERBackground,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
            
            if (tracks.size > 1 && !isProUser) {
                // Upsell Badge
                Box(
                    modifier = Modifier
                        .background(com.cipher.media.ui.theme.ProGold.copy(alpha=0.2f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "PRO",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = com.cipher.media.ui.theme.ProGold
                    )
                }
            } else if (tracks.size > 1) {
                Text(
                    text = "${tracks.size} Tracks",
                    style = MaterialTheme.typography.labelSmall,
                    color = CIPHERPrimary
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(CIPHERSurface)
        ) {
            tracks.forEach { track ->
                DropdownMenuItem(
                    text = { 
                        Column {
                            Text(track.displayTitle, color = CIPHEROnSurface)
                            Text(track.codec, style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
                        }
                    },
                    onClick = {
                        onTrackSelected(track)
                        expanded = false
                    },
                    trailingIcon = {
                        if (track.isSelected) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = CIPHERPrimary)
                        }
                    }
                )
            }
        }
    }
}

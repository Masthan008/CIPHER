package com.cipher.media.ui.audio

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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Full-screen audio player: blurred album art background, breathing animation,
 * prominent seek bar, shuffle/prev/play(80dp)/next/repeat controls.
 */
@Composable
fun AudioPlayerScreen(
    viewModel: AudioPlayerViewModel,
    onBack: () -> Unit,
    onOpenEqualizer: () -> Unit
) {
    val currentAudio by viewModel.currentAudio.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()

    val audio = currentAudio ?: return
    val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f

    fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return "%d:%02d".format(min, sec)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Blurred album art background
        AsyncImage(
            model = audio.albumArtUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().blur(40.dp)
        )
        Box(modifier = Modifier.fillMaxSize().background(CIPHERBackground.copy(alpha = 0.8f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.sm),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.KeyboardArrowDown, onClick = onBack)
                Text("Now Playing", style = MaterialTheme.typography.titleSmall, color = CIPHEROnSurfaceVariant)
                CIPHERIconButton(icon = Icons.Default.MoreVert, onClick = { })
            }

            Spacer(Modifier.weight(0.1f))

            // Album art with breathing
            BreathingEffect { mod ->
                Box(
                    modifier = mod
                        .size(280.dp)
                        .clip(RoundedCornerShape(Corners.extraLarge))
                        .background(CIPHERSurfaceVariant)
                ) {
                    if (audio.albumArtUri != null) {
                        AsyncImage(
                            model = audio.albumArtUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            Icons.Default.MusicNote, null,
                            tint = CIPHERPrimary.copy(alpha = 0.3f),
                            modifier = Modifier.size(80.dp).align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(Modifier.height(Spacing.xl))

            // Title & artist
            Text(
                text = audio.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = audio.artist,
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(Spacing.xl))

            // Seek bar
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = progress,
                    onValueChange = { frac ->
                        viewModel.seekTo((frac * duration).toLong())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = PlayerSeekBarInactive
                    )
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(formatTime(currentPosition), style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
                    Text(formatTime(duration), style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Main controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.Shuffle, onClick = { viewModel.toggleShuffle() })
                CIPHERIconButton(icon = Icons.Default.SkipPrevious, onClick = { viewModel.skipPrevious() }, size = Spacing.minTouchTarget)
                FloatingActionButton(
                    onClick = { viewModel.togglePlayPause() },
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    containerColor = CIPHERPrimary,
                    contentColor = CIPHEROnPrimary
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        null, modifier = Modifier.size(36.dp)
                    )
                }
                CIPHERIconButton(icon = Icons.Default.SkipNext, onClick = { viewModel.skipNext() }, size = Spacing.minTouchTarget)
                CIPHERIconButton(icon = Icons.Default.Repeat, onClick = { viewModel.cycleRepeatMode() })
            }

            Spacer(Modifier.height(Spacing.lg))

            // Action row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CIPHERIconButton(icon = Icons.Default.Equalizer, onClick = onOpenEqualizer, tint = CIPHERPrimary)
                CIPHERIconButton(icon = Icons.Default.FavoriteBorder, onClick = { })
                CIPHERIconButton(icon = Icons.Default.QueueMusic, onClick = { })
            }

            Spacer(Modifier.weight(0.1f))
        }
    }
}

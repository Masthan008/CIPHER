package com.cipher.media.ui.audio

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.cipher.media.ui.audio.components.AlbumArtPager
import com.cipher.media.ui.audio.components.QueueBottomSheet
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * FREE FEATURE: Full-screen audio player with gesture controls.
 *
 * Gestures:
 *  - Swipe left/right on album art: Change track
 *  - Swipe up: Add current to queue / show queue
 *  - Tap album art: Toggle fullscreen mode
 *  - Long press album art: Show lyrics placeholder
 *
 * Controls: Play/Pause, Prev/Next, Shuffle, Repeat (All/One/Off)
 * Gapless playback via ExoPlayer setMediaItems.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerScreen(
    viewModel: AudioPlayerViewModel,
    onBack: () -> Unit
) {
    val currentAudio by viewModel.currentAudio.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val shuffleEnabled by viewModel.shuffleEnabled.collectAsState()
    val repeatMode by viewModel.repeatMode.collectAsState()
    val audioList by viewModel.audioList.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    val context = LocalContext.current

    val audio = currentAudio ?: return
    val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f

    // Resolve EQ ViewModel at screen level where Hilt ViewModelStoreOwner is available
    val eqViewModel: com.cipher.media.ui.audio.audiofx.EqualizerViewModel = hiltViewModel()

    // FREE FEATURE: Queue management
    var showQueueSheet by remember { mutableStateOf(false) }
    var showEqualizer by remember { mutableStateOf(false) }
    var showLyricsToast by remember { mutableStateOf(false) }
    var isFullscreenArt by remember { mutableStateOf(false) }

    // Current track index in playlist
    val currentIndex = remember(audio, audioList) {
        audioList.indexOfFirst { it.uri == audio.uri }.coerceAtLeast(0)
    }

    fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return "%d:%02d".format(min, sec)
    }

    // FREE FEATURE: Queue bottom sheet
    if (showQueueSheet) {
        QueueBottomSheet(
            audioList = audioList,
            currentAudio = audio,
            onTrackClick = { track -> viewModel.playAudio(track, audioList) },
            onClearQueue = { viewModel.stopPlayback() },
            onDismiss = { showQueueSheet = false }
        )
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

        // FREE FEATURE: Swipe up gesture on the whole screen to show queue
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = Spacing.lg)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < -50) {
                            // Swipe up → show queue
                            showQueueSheet = true
                        }
                    }
                },
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
                CIPHERIconButton(icon = Icons.Default.QueueMusic, onClick = { showQueueSheet = true })
            }

            Spacer(Modifier.weight(0.08f))

            // FREE FEATURE: Swipeable album art pager with gestures
            AlbumArtPager(
                playlist = audioList,
                currentIndex = currentIndex,
                onTrackChanged = { newIndex ->
                    val track = audioList.getOrNull(newIndex)
                    if (track != null) {
                        viewModel.playAudio(track, audioList)
                    }
                },
                onTap = {
                    isFullscreenArt = !isFullscreenArt
                },
                onLongPress = {
                    Toast.makeText(context, "🎵 Lyrics coming soon!", Toast.LENGTH_SHORT).show()
                }
            )

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
                CIPHERIconButton(
                    icon = Icons.Default.Shuffle,
                    onClick = { viewModel.toggleShuffle() },
                    tint = if (shuffleEnabled) CIPHERPrimary else CIPHEROnSurfaceVariant
                )
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
                CIPHERIconButton(
                    icon = when (repeatMode) {
                        Player.REPEAT_MODE_ONE -> Icons.Default.RepeatOne
                        else -> Icons.Default.Repeat
                    },
                    onClick = { viewModel.cycleRepeatMode() },
                    tint = if (repeatMode != Player.REPEAT_MODE_OFF) CIPHERPrimary else CIPHEROnSurfaceVariant
                )
            }

            Spacer(Modifier.height(Spacing.lg))

            // Action row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(
                    modifier = Modifier
                        .clickable { showEqualizer = true }
                ) {
                    CIPHERIconButton(
                        icon = Icons.Default.Equalizer,
                        onClick = { showEqualizer = true },
                        tint = CIPHERPrimary
                    )
                }
                CIPHERIconButton(
                    icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    onClick = {
                        viewModel.toggleFavorite()
                        Toast.makeText(
                            context,
                            if (!isFavorite) "Added to favourites" else "Removed from favourites",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    tint = if (isFavorite) CIPHERPrimary else CIPHEROnSurfaceVariant
                )
                CIPHERIconButton(
                    icon = Icons.Default.Share,
                    onClick = {
                        Toast.makeText(context, "Share coming soon!", Toast.LENGTH_SHORT).show()
                    },
                    tint = CIPHEROnSurfaceVariant
                )
            }

            // Gesture hint
            Spacer(Modifier.weight(0.05f))
            Text(
                "↑ Swipe up for queue • Swipe album art to change track",
                color = CIPHEROnSurfaceVariant.copy(alpha = 0.5f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(Spacing.md))
        }

        if (showEqualizer) {
            com.cipher.media.ui.audio.audiofx.components.EqualizerDialog(
                onDismiss = { showEqualizer = false },
                viewModel = eqViewModel
            )
        }
    }
}

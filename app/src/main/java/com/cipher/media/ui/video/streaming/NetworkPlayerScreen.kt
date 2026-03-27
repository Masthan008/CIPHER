package com.cipher.media.ui.video.streaming

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.streaming.components.CastControls
import com.cipher.media.ui.video.streaming.components.CastDeviceSelector
import com.cipher.media.util.TimeUtil
import kotlinx.coroutines.delay

/**
 * Full-screen network video player for streaming URLs (HTTP/HLS/DASH).
 * Includes cast button, stream info overlay, and remote controls.
 */
@OptIn(UnstableApi::class)
@Composable
fun NetworkPlayerScreen(
    streamUrl: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val streamingManager = remember { StreamingManager(context) }
    val streamType = remember { streamingManager.getStreamTypeLabel(streamUrl) }

    // Create player with appropriate source
    val player = remember {
        streamingManager.createStreamingPlayer(streamUrl)
    }

    var isPlaying by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var bufferedPosition by remember { mutableLongStateOf(0L) }
    var isControlsVisible by remember { mutableStateOf(true) }
    var showCastSelector by remember { mutableStateOf(false) }

    // Player listener
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    // Progress updater
    LaunchedEffect(player) {
        while (true) {
            currentPosition = player.currentPosition
            duration = player.duration.coerceAtLeast(0)
            bufferedPosition = player.bufferedPosition
            delay(200)
        }
    }

    // Auto-hide controls
    LaunchedEffect(isControlsVisible) {
        if (isControlsVisible && isPlaying) {
            delay(4000)
            isControlsVisible = false
        }
    }

    // Cast selector bottom sheet
    if (showCastSelector) {
        CastDeviceSelector(
            isCasting = false,
            castDeviceName = null,
            hasCastDevices = false,
            onCastRequested = { showCastSelector = false },
            onStopCasting = { showCastSelector = false },
            onDismiss = { showCastSelector = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground)
            .clickable { isControlsVisible = !isControlsVisible }
    ) {
        // Video surface
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = false
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Controls overlay
        if (isControlsVisible) {
            // Top bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PlayerOverlayBackground)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { player.release(); onBack() }) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            extractTitle(streamUrl),
                            color = CIPHEROnSurface,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp
                        )
                        Text(
                            streamType,
                            color = CIPHERPrimary,
                            fontSize = 12.sp
                        )
                    }

                    // Cast button
                    IconButton(onClick = { showCastSelector = true }) {
                        Icon(Icons.Default.Cast, "Cast", tint = CIPHEROnSurface)
                    }
                }
            }

            // Center play/pause
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rewind
                    IconButton(onClick = { player.seekTo(maxOf(0, player.currentPosition - 10_000)) }) {
                        Icon(Icons.Default.Replay10, "Rewind", tint = CIPHEROnSurface, modifier = Modifier.size(36.dp))
                    }

                    // Play/Pause
                    FilledIconButton(
                        onClick = { if (isPlaying) player.pause() else player.play() },
                        modifier = Modifier.size(64.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = CIPHERPrimary.copy(alpha = 0.9f))
                    ) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            "Play",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Forward
                    IconButton(onClick = { player.seekTo(minOf(duration, player.currentPosition + 10_000)) }) {
                        Icon(Icons.Default.Forward10, "Forward", tint = CIPHEROnSurface, modifier = Modifier.size(36.dp))
                    }
                }
            }

            // Bottom seek bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(PlayerOverlayBackground)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Seek slider
                val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
                val buffered = if (duration > 0) bufferedPosition.toFloat() / duration.toFloat() else 0f

                Slider(
                    value = progress,
                    onValueChange = { player.seekTo((it * duration).toLong()) },
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = CIPHERDivider
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(TimeUtil.formatDuration(currentPosition), color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
                    // Buffering indicator
                    if (bufferedPosition > currentPosition + 1000) {
                        Text("Buffered: ${TimeUtil.formatDuration(bufferedPosition)}", color = CIPHERPrimary.copy(alpha = 0.7f), fontSize = 11.sp)
                    }
                    Text(TimeUtil.formatDuration(duration), color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
                }
            }
        }
    }
}

private fun extractTitle(url: String): String {
    return try {
        val uri = Uri.parse(url)
        val fileName = uri.lastPathSegment ?: "Network Stream"
        fileName.substringBeforeLast(".")
            .replace(Regex("[_\\-]"), " ")
            .replaceFirstChar { it.uppercase() }
    } catch (e: Exception) {
        "Network Stream"
    }
}

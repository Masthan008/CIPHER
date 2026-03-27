package com.cipher.media.ui.video

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.cipher.media.ui.components.GestureOverlay
import com.cipher.media.ui.components.SeekBar
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.PlayerOverlayBackground
import com.cipher.media.util.TimeUtil
import kotlinx.coroutines.delay

/**
 * Full-screen video player with ExoPlayer, custom controls overlay,
 * and gesture-based interactions.
 */
@Composable
fun VideoPlayerScreen(
    videoUri: String,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val uiState by viewModel.uiState.collectAsState()

    // Create ExoPlayer instance
    val player = remember {
        viewModel.playerBuilder.build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    // Audio manager for volume control
    val audioManager = remember {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    // Player listener for state updates
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                viewModel.updatePlayingState(isPlaying)
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    viewModel.updatePlayingState(false)
                }
            }
        }
        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    // Progress update ticker (every 100ms)
    LaunchedEffect(player) {
        while (true) {
            viewModel.updatePosition(
                position = player.currentPosition,
                duration = player.duration.coerceAtLeast(0),
                buffered = player.bufferedPosition
            )
            delay(100)
        }
    }

    // Auto-hide controls after 3 seconds
    LaunchedEffect(uiState.isControlsVisible) {
        if (uiState.isControlsVisible && uiState.isPlaying) {
            delay(3000)
            viewModel.setControlsVisible(false)
        }
    }

    // Enter immersive mode
    DisposableEffect(Unit) {
        activity?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, false)
            val controller = WindowInsetsControllerCompat(it.window, it.window.decorView)
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        onDispose {
            activity?.let {
                WindowCompat.setDecorFitsSystemWindows(it.window, true)
                val controller = WindowInsetsControllerCompat(it.window, it.window.decorView)
                controller.show(WindowInsetsCompat.Type.systemBars())
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    // Handle rotation lock
    LaunchedEffect(uiState.isLocked) {
        activity?.requestedOrientation = if (uiState.isLocked) {
            ActivityInfo.SCREEN_ORIENTATION_LOCKED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }

    // Handle back press
    BackHandler { onBack() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // ExoPlayer video surface
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = false  // We use our own custom controls
                    setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Gesture overlay (captures all touch events)
        GestureOverlay(
            onToggleControls = { viewModel.toggleControls() },
            onSeekDelta = { deltaMs ->
                val newPos = (player.currentPosition + deltaMs)
                    .coerceIn(0, player.duration.coerceAtLeast(0))
                player.seekTo(newPos)
            },
            onBrightnessChange = { delta ->
                activity?.window?.let { window ->
                    val attrs = window.attributes
                    val current = if (attrs.screenBrightness < 0) 0.5f else attrs.screenBrightness
                    attrs.screenBrightness = (current + delta).coerceIn(0.01f, 1f)
                    window.attributes = attrs
                }
            },
            onVolumeChange = { delta ->
                val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                val curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                val newVol = (curVol + (delta * maxVol).toInt()).coerceIn(0, maxVol)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVol, 0)
            },
            window = activity?.window,
            modifier = Modifier.fillMaxSize()
        )

        // Custom controls overlay (animated visibility)
        AnimatedVisibility(
            visible = uiState.isControlsVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlayerOverlayBackground)
            ) {
                // Top bar: Back button + title + rotation lock
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Rotation lock button
                    IconButton(onClick = { viewModel.toggleRotationLock() }) {
                        Icon(
                            imageVector = if (uiState.isLocked)
                                Icons.Default.ScreenLockRotation
                            else
                                Icons.Default.ScreenRotation,
                            contentDescription = "Rotation Lock",
                            tint = if (uiState.isLocked) CIPHERPrimary else Color.White
                        )
                    }
                }

                // Center play/pause button
                IconButton(
                    onClick = {
                        if (player.isPlaying) player.pause() else player.play()
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(72.dp)
                ) {
                    Icon(
                        imageVector = if (uiState.isPlaying)
                            Icons.Default.Pause
                        else
                            Icons.Default.PlayArrow,
                        contentDescription = if (uiState.isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                }

                // Bottom controls: time + seek bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Seek bar
                    SeekBar(
                        progress = uiState.progress,
                        bufferedProgress = uiState.bufferedProgress,
                        onSeek = { fraction ->
                            val seekPos = (fraction * player.duration).toLong()
                            player.seekTo(seekPos.coerceIn(0, player.duration.coerceAtLeast(0)))
                        }
                    )

                    // Time display
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = TimeUtil.formatDuration(uiState.currentPosition),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        Text(
                            text = TimeUtil.formatDuration(uiState.duration),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

package com.cipher.media.ui.video

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.util.Rational
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.cipher.media.ui.components.GestureOverlay
import com.cipher.media.ui.components.SeekBar
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.PlayerOverlayBackground
import com.cipher.media.ui.video.audio.VideoEqualizerViewModel
import com.cipher.media.ui.video.audio.components.EqualizerDialog
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel
import com.cipher.media.ui.video.enhancement.ScreenshotManager
import com.cipher.media.ui.video.enhancement.components.*
import com.cipher.media.ui.video.subtitle.SubtitleRenderer
import com.cipher.media.ui.video.subtitle.SubtitleViewModel
import com.cipher.media.ui.video.subtitle.components.SubtitleBottomSheet
import com.cipher.media.util.TimeUtil
import kotlinx.coroutines.delay
import android.widget.Toast

/**
 * Full-screen video player with ExoPlayer, custom controls overlay,
 * gesture-based interactions, speed control, skip ±10s, aspect ratio,
 * and Picture-in-Picture support.
 */
@Composable
fun VideoPlayerScreen(
    videoUri: String,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
    subtitleViewModel: SubtitleViewModel = hiltViewModel(),
    audioViewModel: VideoEqualizerViewModel = hiltViewModel(),
    enhancementViewModel: VideoEnhancementViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val uiState by viewModel.uiState.collectAsState()
    val subtitleUiState by subtitleViewModel.uiState.collectAsState()
    val audioUiState by audioViewModel.uiState.collectAsState()
    val enhancementState by enhancementViewModel.uiState.collectAsState()

    // Screen lock unlock confirmation dialog
    var showUnlockDialog by remember { mutableStateOf(false) }

    // Create ExoPlayer instance
    val player = remember {
        viewModel.playerBuilder.build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUri))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    // PlayerView reference for aspect ratio
    var playerView by remember { mutableStateOf<PlayerView?>(null) }

    // Audio manager for volume control
    val audioManager = remember {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    // Apply speed changes to player
    LaunchedEffect(uiState.playbackSpeed) {
        player.setPlaybackSpeed(uiState.playbackSpeed)
    }

    // Apply aspect ratio mode
    LaunchedEffect(uiState.aspectRatioMode) {
        playerView?.resizeMode = when (uiState.aspectRatioMode) {
            AspectRatioMode.FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            AspectRatioMode.FILL -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            AspectRatioMode.ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_FILL
        }
    }

    // Attach ExoPlayer to Audio Engine
    LaunchedEffect(player) {
        audioViewModel.attachPlayer(player)
        enhancementViewModel.attachPlayer(player)
    }

    // Auto-load subtitle when URI changes
    LaunchedEffect(videoUri) {
        subtitleViewModel.autoLoadSubtitle(Uri.parse(videoUri))
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

    // Enter immersive mode + landscape
    DisposableEffect(Unit) {
        activity?.let {
            // Force landscape for video playback
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
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

    // ── Unlock confirmation dialog ──
    if (showUnlockDialog) {
        AlertDialog(
            onDismissRequest = { showUnlockDialog = false },
            title = { Text("Unlock Screen?", color = Color.White) },
            text = { Text("Tap Unlock to enable touch controls.", color = Color.White.copy(alpha = 0.7f)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.toggleScreenLock()
                    showUnlockDialog = false
                }) {
                    Text("Unlock", color = CIPHERPrimary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnlockDialog = false }) {
                    Text("Cancel", color = Color.White.copy(alpha = 0.5f))
                }
            },
            containerColor = Color(0xFF1A1A2E)
        )
    }

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
                    playerView = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // ══════════════════════════════════════════════════
        // SUBTITLE OVERLAY
        // ══════════════════════════════════════════════════
        SubtitleRenderer(
            uiState = subtitleUiState,
            modifier = Modifier.fillMaxSize()
        )

        // ══════════════════════════════════════════════════
        // FREE FEATURE: Gesture overlay (captures all touch events)
        // Supports: seek, brightness, volume, double-tap, long-press,
        //           pinch zoom, screen lock passthrough
        // ══════════════════════════════════════════════════
        GestureOverlay(
            onToggleControls = {
                if (uiState.isScreenLocked) {
                    showUnlockDialog = true
                } else {
                    viewModel.toggleControls()
                }
            },
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
            onPlayPause = {
                if (player.isPlaying) player.pause() else player.play()
            },
            onLongPressSpeed = { speed ->
                player.setPlaybackSpeed(speed)
            },
            onZoomCycle = {
                viewModel.cycleAspectRatio()
            },
            isScreenLocked = uiState.isScreenLocked,
            window = activity?.window,
            modifier = Modifier.fillMaxSize()
        )

        // ══════════════════════════════════════════════════
        // SCREEN LOCK OVERLAY (Kids Mode)
        // Shows a centered lock icon when screen is locked
        // ══════════════════════════════════════════════════
        AnimatedVisibility(
            visible = uiState.isScreenLocked,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showUnlockDialog = true },
                contentAlignment = Alignment.Center
            ) {
                // Lock icon bottom-center
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { showUnlockDialog = true }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Lock, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                        Text("Screen Locked", color = Color.White, fontSize = 13.sp)
                    }
                }
            }
        }

        // Custom controls overlay (animated visibility)
        AnimatedVisibility(
            visible = uiState.isControlsVisible && !uiState.isScreenLocked,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PlayerOverlayBackground)
            ) {
                // Top bar: Back + title + controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Screen Lock (Kids Mode)
                    IconButton(onClick = { viewModel.toggleScreenLock() }) {
                        Icon(
                            Icons.Default.LockOpen,
                            "Lock Screen",
                            tint = Color.White
                        )
                    }

                    // PiP button
                    IconButton(onClick = {
                        activity?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                val params = PictureInPictureParams.Builder()
                                    .setAspectRatio(Rational(16, 9))
                                    .build()
                                it.enterPictureInPictureMode(params)
                            }
                        }
                    }) {
                        Icon(Icons.Default.PictureInPicture, "PiP", tint = Color.White)
                    }

                    // Subtitle Button
                    IconButton(onClick = { subtitleViewModel.showBottomSheet() }) {
                        Icon(
                            Icons.Default.Subtitles,
                            "Subtitles",
                            tint = if (subtitleUiState.isLoaded) CIPHERPrimary else Color.White
                        )
                    }

                    // Equalizer / Audio Effects Button
                    IconButton(onClick = { audioViewModel.showBottomSheet() }) {
                        Icon(
                            Icons.Default.GraphicEq,
                            "Audio Equalizer",
                            tint = Color.White
                        )
                    }

                    // Speed button (new tiered)
                    IconButton(onClick = { enhancementViewModel.showSpeedSelector() }) {
                        Text(
                            "${enhancementState.currentSpeed}x",
                            color = if (enhancementState.currentSpeed != 1.0f) CIPHERPrimary else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    // Sleep Timer button
                    IconButton(onClick = { enhancementViewModel.showSleepTimerDialog() }) {
                        Icon(
                            Icons.Default.Timer,
                            "Sleep Timer",
                            tint = if (enhancementState.sleepTimerActive) CIPHERPrimary else Color.White
                        )
                    }

                    // Screenshot button
                    IconButton(onClick = {
                        playerView?.let { pv ->
                            ScreenshotManager.captureScreenshot(
                                playerView = pv,
                                context = context,
                                isPro = enhancementState.isPro
                            ) { path ->
                                if (path != null) {
                                    enhancementViewModel.showScreenshotFeedback(path)
                                    Toast.makeText(context, "Screenshot saved!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Screenshot failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }) {
                        Icon(Icons.Default.CameraAlt, "Screenshot", tint = Color.White)
                    }

                    // Crop/Zoom button
                    IconButton(onClick = { enhancementViewModel.showCropZoomDialog() }) {
                        Icon(
                            Icons.Default.Crop,
                            "Crop & Zoom",
                            tint = if (enhancementState.zoomLevel > 1f) CIPHERPrimary else Color.White
                        )
                    }

                    // Aspect ratio button
                    IconButton(onClick = { viewModel.cycleAspectRatio() }) {
                        Icon(
                            imageVector = when (uiState.aspectRatioMode) {
                                AspectRatioMode.FIT -> Icons.Default.FitScreen
                                AspectRatioMode.FILL -> Icons.Default.Fullscreen
                                AspectRatioMode.ZOOM -> Icons.Default.ZoomOutMap
                            },
                            contentDescription = uiState.aspectRatioMode.label,
                            tint = Color.White
                        )
                    }

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

                // Center controls: skip back + play/pause + skip forward
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // Skip back 10 seconds
                    IconButton(
                        onClick = {
                            val newPos = (player.currentPosition - 10_000L)
                                .coerceAtLeast(0)
                            player.seekTo(newPos)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Replay10,
                            "Skip back 10s",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Play/Pause
                    IconButton(
                        onClick = {
                            if (player.isPlaying) player.pause() else player.play()
                        },
                        modifier = Modifier.size(72.dp)
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

                    // Skip forward 10 seconds
                    IconButton(
                        onClick = {
                            val newPos = (player.currentPosition + 10_000L)
                                .coerceIn(0, player.duration.coerceAtLeast(0))
                            player.seekTo(newPos)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Forward10,
                            "Skip forward 10s",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                // Bottom controls: A-B repeat + time + seek bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // A-B Repeat controls (Pro)
                    if (enhancementState.isPro) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ABRepeatControls(
                                hasPointA = enhancementState.hasPointA,
                                hasPointB = enhancementState.hasPointB,
                                isLooping = enhancementState.isABLooping,
                                isPro = true,
                                viewModel = enhancementViewModel
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // Seek bar with A-B markers
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SeekBar(
                            progress = uiState.progress,
                            bufferedProgress = uiState.bufferedProgress,
                            onSeek = { fraction ->
                                val seekPos = (fraction * player.duration).toLong()
                                player.seekTo(seekPos.coerceIn(0, player.duration.coerceAtLeast(0)))
                            }
                        )
                        // A-B repeat markers overlay
                        if (enhancementState.hasPointA) {
                            ABRepeatMarkers(
                                pointA = enhancementState.pointA,
                                pointB = enhancementState.pointB,
                                duration = uiState.duration
                            )
                        }
                    }

                    // Time display + sleep timer indicator
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
                        // Sleep timer countdown if active
                        if (enhancementState.sleepTimerActive) {
                            Text(
                                text = "\uD83D\uDCA4 ${enhancementState.sleepTimerDisplay}",
                                style = MaterialTheme.typography.labelSmall,
                                color = CIPHERPrimary
                            )
                        } else {
                            Text(
                                text = uiState.aspectRatioMode.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                        Text(
                            text = TimeUtil.formatDuration(uiState.duration),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                // Old speed popup removed — replaced by SpeedSelector BottomSheet
            }
        }
    }

    // ══════════════════════════════════════════════════
    // MODAL BOTTOM SHEETS
    // ══════════════════════════════════════════════════
    
    if (subtitleUiState.showBottomSheet) {
        SubtitleBottomSheet(
            viewModel = subtitleViewModel,
            isPro = uiState.isPro,
            onDismiss = { subtitleViewModel.hideBottomSheet() }
        )
    }

    if (audioUiState.showBottomSheet) {
        EqualizerDialog(
            uiState = audioUiState,
            viewModel = audioViewModel,
            onDismiss = { audioViewModel.hideBottomSheet() }
        )
    }

    // ── Enhancement Bottom Sheets ──

    if (enhancementState.showSpeedSelector) {
        SpeedSelector(
            currentSpeed = enhancementState.currentSpeed,
            isPro = enhancementState.isPro,
            pitchCorrectionEnabled = enhancementState.pitchCorrectionEnabled,
            viewModel = enhancementViewModel,
            onDismiss = { enhancementViewModel.hideSpeedSelector() }
        )
    }

    if (enhancementState.showSleepTimerDialog) {
        SleepTimerDialog(
            isPro = enhancementState.isPro,
            currentAction = enhancementState.sleepTimerAction,
            isTimerActive = enhancementState.sleepTimerActive,
            timerDisplay = enhancementState.sleepTimerDisplay,
            viewModel = enhancementViewModel,
            onDismiss = { enhancementViewModel.hideSleepTimerDialog() }
        )
    }

    if (enhancementState.showCropZoomDialog) {
        CropZoomDialog(
            isPro = enhancementState.isPro,
            currentZoom = enhancementState.zoomLevel,
            currentCropMode = enhancementState.cropMode,
            viewModel = enhancementViewModel,
            onDismiss = { enhancementViewModel.hideCropZoomDialog() }
        )
    }
}

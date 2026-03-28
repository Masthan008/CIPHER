package com.cipher.media.ui.audio.lyrics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.audio.lyrics.AlbumColorExtractor
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics
import com.cipher.media.ui.audio.lyrics.model.VisualizerSettings
import com.cipher.media.ui.audio.lyrics.model.VisualizerType
import com.cipher.media.ui.theme.*

/**
 * Full-screen immersive lyrics mode with visualizer backdrop.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricsFullScreen(
    audio: AudioItem?,
    lyrics: ParsedLyrics,
    currentPositionMs: Long,
    fftData: FloatArray,
    waveformData: ByteArray,
    albumColors: AlbumColorExtractor.AlbumColors,
    userTier: Tier,
    onSeek: (Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPro = userTier != Tier.FREE
    var visualizerSettings by remember {
        mutableStateOf(VisualizerSettings(
            primaryColor = albumColors.vibrant,
            secondaryColor = albumColors.darkVibrant
        ))
    }
    var showVisSettings by remember { mutableStateOf(false) }
    var fontSize by remember { mutableStateOf(20.sp) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        albumColors.darkMuted.copy(alpha = 0.95f),
                        Color.Black
                    )
                )
            )
    ) {
        // Background visualizer (subtle)
        if (isPro) {
            VisualizerOverlay(
                fftData = fftData,
                waveformData = waveformData,
                settings = visualizerSettings.copy(sensitivity = 0.4f),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                height = 300.dp
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ExpandMore, "Minimize", tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        audio?.title ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )
                    Text(
                        audio?.artist ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 1
                    )
                }

                // Font size controls
                IconButton(onClick = {
                    fontSize = (fontSize.value - 2).coerceAtLeast(12f).sp
                }) {
                    Icon(Icons.Default.TextDecrease, "Smaller", tint = Color.White)
                }
                IconButton(onClick = {
                    fontSize = (fontSize.value + 2).coerceAtMost(36f).sp
                }) {
                    Icon(Icons.Default.TextIncrease, "Larger", tint = Color.White)
                }

                if (isPro) {
                    IconButton(onClick = { showVisSettings = !showVisSettings }) {
                        Icon(Icons.Default.Tune, "Visualizer", tint = Color.White)
                    }
                }
            }

            // Lyrics
            SyncedLyricsView(
                lyrics = lyrics,
                currentPositionMs = currentPositionMs,
                isSynced = lyrics.isSync && isPro,
                highlightColor = albumColors.vibrant,
                fontSize = fontSize,
                onSeekToLine = if (isPro && lyrics.isSync) { ms -> onSeek(ms) } else null,
                modifier = Modifier.weight(1f)
            )

            // Visualizer bar at bottom
            if (isPro) {
                VisualizerOverlay(
                    fftData = fftData,
                    waveformData = waveformData,
                    settings = visualizerSettings,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    height = 80.dp
                )
            }
        }

        // Visualizer settings sheet
        if (showVisSettings && isPro) {
            VisualizerSettingsSheet(
                settings = visualizerSettings,
                userTier = userTier,
                onSettingsChange = { visualizerSettings = it },
                onDismiss = { showVisSettings = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VisualizerSettingsSheet(
    settings: VisualizerSettings,
    userTier: Tier,
    onSettingsChange: (VisualizerSettings) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface
    ) {
        Column(modifier = Modifier.padding(Spacing.md)) {
            Text("Visualizer Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            Spacer(Modifier.height(Spacing.md))

            // Type selector
            Text("Style", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val types = if (userTier == Tier.FREE) VisualizerType.FREE_TYPES else VisualizerType.PRO_TYPES
                types.forEach { type ->
                    FilterChip(
                        selected = settings.type == type,
                        onClick = { onSettingsChange(settings.copy(type = type)) },
                        label = { Text(type.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary
                        )
                    )
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Bar count
            Text("Bands: ${settings.barCount}", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
            Slider(
                value = settings.barCount.toFloat(),
                onValueChange = { onSettingsChange(settings.copy(barCount = it.toInt())) },
                valueRange = 16f..64f,
                steps = 5
            )

            // Sensitivity
            Text("Sensitivity: ${"%.1f".format(settings.sensitivity)}x", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
            Slider(
                value = settings.sensitivity,
                onValueChange = { onSettingsChange(settings.copy(sensitivity = it)) },
                valueRange = 0.3f..2.5f
            )

            // Mirror mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mirror Mode", color = CIPHEROnSurface)
                Switch(
                    checked = settings.mirrorMode,
                    onCheckedChange = { onSettingsChange(settings.copy(mirrorMode = it)) },
                    colors = SwitchDefaults.colors(checkedThumbColor = CIPHERPrimary)
                )
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

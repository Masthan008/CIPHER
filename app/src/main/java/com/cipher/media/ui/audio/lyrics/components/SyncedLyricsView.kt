package com.cipher.media.ui.audio.lyrics.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.audio.lyrics.model.LyricLine
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics
import com.cipher.media.ui.theme.CIPHEROnSurface
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERPrimary
import kotlinx.coroutines.launch

/**
 * Synced lyrics view with auto-scroll.
 * - Highlights current line
 * - Dim past/future lines
 * - Tap a line to seek to that timestamp
 * - Smooth spring scrolling
 */
@Composable
fun SyncedLyricsView(
    lyrics: ParsedLyrics,
    currentPositionMs: Long,
    isSynced: Boolean,
    highlightColor: Color = CIPHERPrimary,
    fontSize: TextUnit = 18.sp,
    onSeekToLine: ((Long) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Find current line index
    val currentLineIndex = remember(currentPositionMs, lyrics.lines) {
        if (!isSynced || lyrics.lines.isEmpty()) -1
        else {
            var idx = -1
            for (i in lyrics.lines.indices) {
                if (lyrics.lines[i].timestampMs <= currentPositionMs) {
                    idx = i
                } else break
            }
            idx
        }
    }

    // Auto-scroll to current line
    LaunchedEffect(currentLineIndex) {
        if (currentLineIndex >= 0 && isSynced) {
            scope.launch {
                listState.animateScrollToItem(
                    index = maxOf(0, currentLineIndex - 2), // Show 2 lines above
                    scrollOffset = 0
                )
            }
        }
    }

    if (lyrics.lines.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "No lyrics available",
                style = MaterialTheme.typography.bodyLarge,
                color = CIPHEROnSurfaceVariant
            )
        }
        return
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(lyrics.lines, key = { i, _ -> i }) { index, line ->
            val isCurrent = index == currentLineIndex
            val isPast = isSynced && index < currentLineIndex
            val isFuture = isSynced && index > currentLineIndex

            val targetAlpha = when {
                isCurrent -> 1f
                isPast -> 0.4f
                isFuture -> 0.5f
                else -> 0.8f
            }
            val alpha by animateFloatAsState(targetAlpha, tween(300))

            val targetScale = if (isCurrent) 1.05f else 1f
            val scale by animateFloatAsState(
                targetScale,
                spring(dampingRatio = 0.7f, stiffness = 300f)
            )

            val textColor by animateColorAsState(
                if (isCurrent) highlightColor else CIPHEROnSurface.copy(alpha = 0.7f),
                tween(300)
            )

            Text(
                text = line.text,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = if (isCurrent) fontSize * 1.1f else fontSize,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    lineHeight = fontSize * 1.4f
                ),
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha)
                    .graphicsLayer { scaleX = scale; scaleY = scale }
                    .clickable(enabled = isSynced && onSeekToLine != null) {
                        onSeekToLine?.invoke(line.timestampMs)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}

package com.cipher.media.ui.audio.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.theme.*

/**
 * FREE FEATURE: Swipeable album art pager with gesture controls.
 *  - Swipe left/right: Change track
 *  - Tap: Toggle fullscreen
 *  - Long press: Show lyrics placeholder
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumArtPager(
    playlist: List<AudioItem>,
    currentIndex: Int,
    onTrackChanged: (Int) -> Unit,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (playlist.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = currentIndex.coerceIn(0, playlist.lastIndex),
        pageCount = { playlist.size }
    )

    // Sync pager with external track changes
    LaunchedEffect(currentIndex) {
        if (pagerState.currentPage != currentIndex && currentIndex in playlist.indices) {
            pagerState.animateScrollToPage(currentIndex)
        }
    }

    // Notify parent when user swipes to new page
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != currentIndex) {
            onTrackChanged(pagerState.currentPage)
        }
    }

    // Spring scale animation on page settle
    val scale by animateFloatAsState(
        targetValue = if (pagerState.isScrollInProgress) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "albumScale"
    )

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentPadding = PaddingValues(horizontal = 40.dp),
        pageSpacing = 16.dp
    ) { page ->
        val item = playlist.getOrNull(page) ?: return@HorizontalPager

        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(if (page == pagerState.currentPage) scale else 0.85f)
                .clip(RoundedCornerShape(Corners.extraLarge))
                .background(CIPHERSurfaceVariant)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onTap() },
                        onLongPress = { onLongPress() }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (item.albumArtUri != null) {
                AsyncImage(
                    model = item.albumArtUri,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = CIPHERPrimary.copy(alpha = 0.3f),
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

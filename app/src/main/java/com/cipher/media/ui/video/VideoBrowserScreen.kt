package com.cipher.media.ui.video

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.MediaItem
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Video browser: 2-column grid, FAB, empty state, shimmer loading.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoBrowserScreen(
    onVideoClick: (MediaItem) -> Unit,
    viewModel: VideoBrowserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadVideos() }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text("Videos", style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                },
                actions = {
                    CIPHERIconButton(icon = Icons.Default.Search, onClick = { })
                    CIPHERIconButton(icon = Icons.Default.Settings, onClick = { })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground,
                    scrolledContainerColor = CIPHERSurface
                )
            )
        },
        floatingActionButton = { CIPHERFAB(icon = Icons.Default.Add, onClick = { }) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                uiState.isLoading -> ShimmerGrid(columns = 2, count = 6)

                uiState.videos.isEmpty() -> EmptyState(
                    icon = Icons.Outlined.VideoLibrary,
                    title = "No videos found",
                    subtitle = "Videos on your device will appear here",
                    actionText = "Refresh",
                    onAction = { viewModel.loadVideos() }
                )

                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Spacing.screenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap),
                    verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
                ) {
                    items(
                        count = uiState.videos.size,
                        key = { index -> uiState.videos[index].id }
                    ) { index ->
                        val video = uiState.videos[index]
                        val durationStr = "%d:%02d".format(video.duration / 60000, (video.duration / 1000) % 60)
                        StaggeredEntrance(index = index) {
                            MediaCard(
                                title = video.displayName,
                                subtitle = durationStr,
                                thumbnailUri = video.uri,
                                duration = durationStr,
                                onClick = { onVideoClick(video) }
                            )
                        }
                    }
                }
            }
        }
    }
}

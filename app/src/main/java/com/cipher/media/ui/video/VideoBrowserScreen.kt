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
import com.cipher.media.ui.video.streaming.StreamingManager
import com.cipher.media.ui.video.streaming.components.StreamInputDialog
import androidx.compose.ui.platform.LocalContext

/**
 * Video browser: 2-column grid, FAB, empty state, shimmer loading.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoBrowserScreen(
    onVideoClick: (MediaItem) -> Unit,
    onSearchClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onStreamUrl: (String) -> Unit = {},
    viewModel: VideoBrowserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var showSortSheet by remember { mutableStateOf(false) }
    var showStreamDialog by remember { mutableStateOf(false) }
    val streamingManager = remember { StreamingManager(context) }

    val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewModel.loadVideos()
    }

    LaunchedEffect(Unit) { viewModel.loadVideos() }

    // Stream URL dialog
    if (showStreamDialog) {
        StreamInputDialog(
            onDismiss = { showStreamDialog = false },
            onConfirm = { url ->
                showStreamDialog = false
                onStreamUrl(url)
            },
            validateUrl = { streamingManager.validateUrl(it) },
            getStreamTypeLabel = { streamingManager.getStreamTypeLabel(it) }
        )
    }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text("Videos", style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                },
                actions = {
                    CIPHERIconButton(icon = Icons.Default.Language, onClick = { showStreamDialog = true })
                    CIPHERIconButton(icon = Icons.Default.Search, onClick = onSearchClick)
                    CIPHERIconButton(icon = Icons.Default.Settings, onClick = onSettingsClick)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground,
                    scrolledContainerColor = CIPHERSurface
                )
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.End,
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                if (android.os.Build.VERSION.SDK_INT >= 34) {
                    CIPHERFAB(
                        icon = Icons.Default.Add,
                        onClick = {
                            permissionLauncher.launch(
                                arrayOf(
                                    "android.permission.READ_MEDIA_VISUAL_USER_SELECTED",
                                    android.Manifest.permission.READ_MEDIA_VIDEO
                                )
                            )
                        }
                    )
                }
                CIPHERFAB(icon = Icons.Default.Sort, onClick = { showSortSheet = true })
            }
        }
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
                    modifier = Modifier.fillMaxSize(),
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

    // Sort/Filter bottom sheet
    if (showSortSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSortSheet = false },
            containerColor = CIPHERSurface
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                Text("Sort By", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                Spacer(Modifier.height(Spacing.md))
                listOf("Name (A-Z)", "Name (Z-A)", "Date (Newest)", "Date (Oldest)", "Size (Largest)", "Duration (Longest)").forEach { option ->
                    TextButton(
                        onClick = { showSortSheet = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(option, color = CIPHEROnSurface, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(Spacing.lg))
            }
        }
    }
}

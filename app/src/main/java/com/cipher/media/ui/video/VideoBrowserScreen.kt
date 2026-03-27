package com.cipher.media.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.MediaItem
import com.cipher.media.ui.components.VideoThumbnail
import com.cipher.media.ui.theme.CIPHERBackground
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.util.PermissionsUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VideoBrowserScreen(
    onVideoClick: (MediaItem) -> Unit,
    viewModel: VideoBrowserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val permission = PermissionsUtil.getVideoPermission()
    val permissionState = rememberPermissionState(permission)

    // Request permission and load videos when granted
    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            viewModel.loadVideos()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CIPHERBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar
            TopAppBar(
                title = {
                    Text(
                        text = "Videos",
                        style = MaterialTheme.typography.headlineSmall,
                        color = CIPHERPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground
                )
            )

            when {
                // Permission not granted
                !permissionState.status.isGranted -> {
                    PermissionRequest(
                        onRequestPermission = { permissionState.launchPermissionRequest() }
                    )
                }

                // Loading
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = CIPHERPrimary)
                    }
                }

                // Empty state
                uiState.videos.isEmpty() -> {
                    EmptyState()
                }

                // Video grid
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = uiState.videos,
                            key = { it.id }
                        ) { video ->
                            VideoThumbnail(
                                mediaItem = video,
                                modifier = Modifier.clickable { onVideoClick(video) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PermissionRequest(onRequestPermission: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = CIPHERPrimary.copy(alpha = 0.6f)
            )
            Text(
                text = "Permission Required",
                style = MaterialTheme.typography.titleLarge,
                color = CIPHERPrimary
            )
            Text(
                text = "CIPHER needs access to your video files to display them.",
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRequestPermission,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CIPHERPrimary
                )
            ) {
                Text("Grant Access")
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.VideoLibrary,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = CIPHERPrimary.copy(alpha = 0.4f)
            )
            Text(
                text = "No Videos Found",
                style = MaterialTheme.typography.titleLarge,
                color = CIPHERPrimary
            )
            Text(
                text = "Your video library is empty.",
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant
            )
        }
    }
}

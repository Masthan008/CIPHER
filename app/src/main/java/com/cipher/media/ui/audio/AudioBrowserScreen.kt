package com.cipher.media.ui.audio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Audio browser: vertical list with staggered entrance, search, shimmer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioBrowserScreen(
    onAudioClick: (AudioItem, List<AudioItem>) -> Unit,
    viewModel: AudioPlayerViewModel = hiltViewModel()
) {
    val audioList by viewModel.audioList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Music",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = CIPHEROnSurface
                    )
                },
                actions = {
                    CIPHERIconButton(icon = Icons.Default.Search, onClick = { })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground,
                    scrolledContainerColor = CIPHERSurface
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                isLoading -> ShimmerGrid(columns = 1, count = 8)

                audioList.isEmpty() -> EmptyState(
                    icon = Icons.Outlined.MusicNote,
                    title = "No music found",
                    subtitle = "Music on your device will appear here"
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(Spacing.screenPadding),
                    verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
                ) {
                    itemsIndexed(audioList) { index, audio ->
                        StaggeredEntrance(index = index) {
                            MediaListItem(
                                title = audio.title,
                                subtitle = audio.artist,
                                thumbnailUri = audio.albumArtUri,
                                duration = "%d:%02d".format(audio.duration / 60000, (audio.duration / 1000) % 60),
                                onClick = { onAudioClick(audio, audioList) }
                            )
                        }
                    }
                }
            }
        }
    }
}

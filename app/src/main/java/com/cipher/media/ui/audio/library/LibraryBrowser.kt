package com.cipher.media.ui.audio.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.data.model.library.LibraryTab
import com.cipher.media.data.model.library.SortOption
import com.cipher.media.ui.audio.library.components.*
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Full Library Browser with tabbed navigation:
 * Songs | Albums | Artists | Genres | Folders | Playlists
 * Supports multi-select (long press), batch editing, tag editing, and search.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryBrowser(
    onAudioClick: (AudioItem, List<AudioItem>) -> Unit,
    onSearchClick: () -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showSortSheet by remember { mutableStateOf(false) }
    var showBatchSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    if (state.isSelectionMode) {
                        Text(
                            "${state.selectedIds.size} selected",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CIPHEROnSurface
                        )
                    } else {
                        Text(
                            "Library",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = CIPHEROnSurface
                        )
                    }
                },
                navigationIcon = {
                    if (state.isSelectionMode) {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel", tint = CIPHEROnSurface)
                        }
                    }
                },
                actions = {
                    if (state.isSelectionMode) {
                        IconButton(onClick = { showBatchSheet = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Batch Edit", tint = CIPHERPrimary)
                        }
                    } else {
                        CIPHERIconButton(icon = Icons.Default.Search, onClick = onSearchClick)
                        CIPHERIconButton(icon = Icons.Default.Sort, onClick = { showSortSheet = true })
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground,
                    scrolledContainerColor = CIPHERSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = LibraryTab.entries.indexOf(state.activeTab),
                containerColor = CIPHERBackground,
                contentColor = CIPHERPrimary,
                edgePadding = Spacing.md,
                divider = {}
            ) {
                LibraryTab.entries.forEach { tab ->
                    Tab(
                        selected = state.activeTab == tab,
                        onClick = { viewModel.setTab(tab) },
                        text = {
                            Text(
                                tab.label,
                                fontWeight = if (state.activeTab == tab) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = CIPHERPrimary,
                        unselectedContentColor = CIPHEROnSurfaceVariant
                    )
                }
            }

            // Content per tab
            when (state.activeTab) {
                LibraryTab.SONGS -> SongsList(
                    songs = viewModel.getFilteredSongs(),
                    isLoading = state.isLoading,
                    selectedIds = state.selectedIds,
                    isSelectionMode = state.isSelectionMode,
                    onSongClick = { audio -> onAudioClick(audio, state.songs) },
                    onSongLongClick = { id -> viewModel.toggleSelection(id) },
                    onSongInfoClick = { audio -> viewModel.loadTagForEditing(audio) }
                )
                LibraryTab.ALBUMS -> AlbumGrid(
                    albums = state.albums,
                    onAlbumClick = { /* TODO: navigate to album detail */ }
                )
                LibraryTab.ARTISTS -> ArtistGrid(
                    artists = state.artists,
                    onArtistClick = { /* TODO: navigate to artist detail */ }
                )
                LibraryTab.GENRES -> GenreList(
                    genres = state.genres,
                    onGenreClick = { /* TODO: filter by genre */ }
                )
                LibraryTab.FOLDERS -> FolderTree(
                    folders = state.folders,
                    onFolderClick = { /* TODO: navigate into folder */ }
                )
                LibraryTab.PLAYLISTS -> {
                    // Placeholder for playlists
                    EmptyState(
                        icon = Icons.Outlined.MusicNote,
                        title = "No playlists yet",
                        subtitle = "Create playlists from your library"
                    )
                }
            }
        }
    }

    // Tag editor dialog
    state.editingTag?.let { tag ->
        TagEditDialog(
            tag = tag,
            userTier = state.userTier,
            onSave = { viewModel.saveTags(it) },
            onDismiss = { viewModel.dismissTagEditor() }
        )
    }

    // Sort sheet
    if (showSortSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSortSheet = false },
            containerColor = CIPHERSurface
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                Text("Sort By", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                Spacer(Modifier.height(Spacing.md))
                SortOption.entries.forEach { option ->
                    val isSelected = state.sortOption == option
                    TextButton(
                        onClick = {
                            viewModel.setSort(option)
                            showSortSheet = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                option.label,
                                color = if (isSelected) CIPHERPrimary else CIPHEROnSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = CIPHERPrimary)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(Spacing.lg))
            }
        }
    }

    // Batch edit sheet (Pro only)
    if (showBatchSheet) {
        if (state.userTier == Tier.FREE) {
            AlertDialog(
                onDismissRequest = { showBatchSheet = false },
                title = { Text("Pro Feature") },
                text = { Text("Batch editing is available in Pro tier. Upgrade to unlock.") },
                confirmButton = {
                    TextButton(onClick = { showBatchSheet = false }) {
                        Text("OK", color = CIPHERPrimary)
                    }
                }
            )
        } else {
            BatchEditBottomSheet(
                selectedCount = state.selectedIds.size,
                onDismiss = { showBatchSheet = false },
                onBatchSetField = { field, value -> viewModel.batchSetField(field, value) },
                onAutoNumber = { viewModel.batchAutoNumber() },
                onSelectAll = { viewModel.selectAll() },
                onClearSelection = { viewModel.clearSelection() }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SongsList(
    songs: List<AudioItem>,
    isLoading: Boolean,
    selectedIds: Set<Long>,
    isSelectionMode: Boolean,
    onSongClick: (AudioItem) -> Unit,
    onSongLongClick: (Long) -> Unit,
    onSongInfoClick: (AudioItem) -> Unit
) {
    when {
        isLoading -> ShimmerGrid(columns = 1, count = 8)
        songs.isEmpty() -> EmptyState(
            icon = Icons.Outlined.MusicNote,
            title = "No songs found",
            subtitle = "Music on your device will appear here"
        )
        else -> LazyColumn(
            contentPadding = PaddingValues(Spacing.screenPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
        ) {
            itemsIndexed(songs, key = { _, item -> item.id }) { index, audio ->
                val isSelected = selectedIds.contains(audio.id)
                StaggeredEntrance(index = index) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (isSelectionMode) onSongLongClick(audio.id)
                                    else onSongClick(audio)
                                },
                                onLongClick = { onSongLongClick(audio.id) }
                            ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(Corners.medium),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) CIPHERPrimary.copy(alpha = 0.12f) else CIPHERSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isSelectionMode) {
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { onSongLongClick(audio.id) },
                                    colors = CheckboxDefaults.colors(checkedColor = CIPHERPrimary)
                                )
                                Spacer(Modifier.width(4.dp))
                            }

                            // Thumbnail
                            AsyncImage(
                                model = audio.albumArtUri,
                                contentDescription = audio.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(Corners.medium))
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(Modifier.weight(1f)) {
                                Text(
                                    audio.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = CIPHEROnSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    "${audio.artist} • ${audio.album}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CIPHEROnSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Duration
                            Text(
                                "%d:%02d".format(audio.duration / 60000, (audio.duration / 1000) % 60),
                                style = MaterialTheme.typography.labelSmall,
                                color = CIPHEROnSurfaceVariant
                            )

                            // Info button for tag editing
                            IconButton(onClick = { onSongInfoClick(audio) }) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Tags",
                                    tint = CIPHEROnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

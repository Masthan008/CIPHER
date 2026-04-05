package com.cipher.media.ui.online

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cipher.media.data.online.jamendo.OnlineTrack
import com.cipher.media.util.TimeUtil
import com.cipher.media.ui.theme.CIPHERBackground
import com.cipher.media.ui.theme.CIPHEROnSurface
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.CIPHERSurface

private fun formatDuration(millis: Long): String = TimeUtil.formatDuration(millis)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineMusicScreen(
    viewModel: OnlineMusicViewModel = hiltViewModel(),
    onNavigateToPlayer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    val languages = listOf(
        "all" to "All",
        "hindi" to "हिन्दी",
        "tamil" to "தமிழ்",
        "telugu" to "తెలుగు",
        "punjabi" to "ਪੰਜਾਬੀ",
        "marathi" to "मराठी",
        "bengali" to "বাংলা",
        "english" to "English",
        "malayalam" to "മലയാളം",
        "kannada" to "ಕನ್ನಡ",
        "gujarati" to "ગુજરાતી"
    )

    val genres = listOf("Pop", "Rock", "Electronic", "Hip-Hop", "Jazz", "Classical", "Folk", "Indian")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🌐 Online Music",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CIPHERBackground
                ),
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = CIPHERPrimary
                        )
                    }
                }
            )
        },
        bottomBar = {
            OnlineMiniPlayer(
                onExpand = onNavigateToPlayer
            )
        },
        containerColor = CIPHERBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::searchSongs,
                onClear = viewModel::clearSearch,
                placeholder = "Search 2M+ free songs..."
            )

            // Language Filter Chips
            SectionHeader(title = "🎵 Languages")
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(languages) { (code, name) ->
                    LanguageChip(
                        name = name,
                        isSelected = selectedLanguage == code || (code == "all" && selectedLanguage == null),
                        onClick = { viewModel.selectLanguage(if (code == "all") null else code) }
                    )
                }
            }

            // Trending Songs Section
            SectionHeader(title = "🔥 Trending Now")
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = CIPHERPrimary)
                }
            } else if (uiState.trendingSongs.isEmpty() && !uiState.isLoading) {
                EmptyState(message = "No trending songs available")
            } else {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.trendingSongs) { track ->
                        TrendingSongCard(
                            track = track,
                            onClick = { 
                                // FIXED: Pass entire playlist for continuous playback
                                viewModel.playTrack(track, uiState.trendingSongs)
                            }
                        )
                    }
                }
            }

            // Genres Section
            SectionHeader(title = "🎸 Genres")
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(genres) { genre ->
                    GenreChip(
                        genre = genre,
                        onClick = { viewModel.searchSongs(genre) }
                    )
                }
            }

            // Search Results Section
            AnimatedVisibility(
                visible = uiState.searchResults.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    SectionHeader(title = "🔍 Search Results")
                    uiState.searchResults.forEach { track ->
                        SearchResultItem(
                            track = track,
                            onClick = { 
                                // FIXED: Pass search results as playlist
                                viewModel.playTrack(track, uiState.searchResults)
                            }
                        )
                    }
                }
            }

            // Error Message
            uiState.error?.let { error ->
                ErrorMessage(
                    message = error,
                    onRetry = { viewModel.refresh() }
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    placeholder: String
) {
    var text by remember { mutableStateOf(query) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onQueryChange(it)
        },
        placeholder = { Text(placeholder, color = Color.Gray) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    text = ""
                    onClear()
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CIPHERSurface,
            unfocusedContainerColor = CIPHERSurface,
            focusedBorderColor = CIPHERPrimary,
            unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun LanguageChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (isSelected) CIPHERPrimary else CIPHERSurface,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.height(36.dp)
    ) {
        Text(
            text = name,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun GenreChip(
    genre: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color(0xFF1A237E),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = genre,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun TrendingSongCard(
    track: OnlineTrack,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(track.artworkUrl)
                .crossfade(true)
                .build(),
            contentDescription = track.title,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = track.title,
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = track.artist,
            color = Color.Gray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SearchResultItem(
    track: OnlineTrack,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(track.artworkUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.title,
                color = Color.White,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${track.artist} • ${track.album}",
                color = Color.Gray,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = formatDuration(track.duration),
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
    )
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ErrorMessage(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = Color.Red,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun OnlineMiniPlayer(
    viewModel: OnlineMusicViewModel = hiltViewModel(),
    onExpand: () -> Unit
) {
    val currentTrack by viewModel.currentTrack.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    if (currentTrack == null) return

    Surface(
        onClick = onExpand,
        color = CIPHERSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentTrack?.mediaMetadata?.artworkUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = currentTrack?.mediaMetadata?.title?.toString() ?: "",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentTrack?.mediaMetadata?.artist?.toString() ?: "",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { viewModel.playPause() }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = CIPHERPrimary
                )
            }
            IconButton(onClick = { viewModel.playNext() }) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = null,
                    tint = CIPHEROnSurface
                )
            }
        }
    }
}

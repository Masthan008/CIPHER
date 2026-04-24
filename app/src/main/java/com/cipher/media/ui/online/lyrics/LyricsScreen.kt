package com.cipher.media.ui.online.lyrics

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.online.jamendo.OnlineTrack
import com.cipher.media.service.OnlinePlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Full-screen lyrics display with auto-scroll and sync.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricsScreen(
    track: OnlineTrack,
    onBack: () -> Unit,
    playerManager: OnlinePlayerManager,
    viewModel: LyricsViewModel = hiltViewModel()
) {
    val lyricsLines by viewModel.lyricsLines.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentPosition by playerManager.currentPosition.collectAsState()

    // Auto-scroll list state
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Load lyrics on first composition
    LaunchedEffect(track.id) {
        viewModel.loadLyrics(track.id)
    }

    // Find current line index based on position
    val currentLineIndex = remember(lyricsLines, currentPosition) {
        if (lyricsLines.isEmpty()) 0
        else lyricsLines.indexOfLast { it.timeMs <= currentPosition }
            .coerceAtLeast(0)
            .coerceAtMost(lyricsLines.size - 1)
    }

    // Auto-scroll to current line
    LaunchedEffect(currentLineIndex) {
        if (lyricsLines.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(
                    index = currentLineIndex,
                    scrollOffset = -300 // Center on screen
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            "Lyrics",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            track.title,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF0D0D0D)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFFF9933)
                    )
                }
                error != null -> {
                    ErrorState(
                        message = error!!,
                        onRetry = { viewModel.loadLyrics(track.id) }
                    )
                }
                lyricsLines.isEmpty() -> {
                    EmptyLyricsState()
                }
                else -> {
                    // Lyrics List with centered current line
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 200.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(lyricsLines) { index, line ->
                            val isCurrentLine = index == currentLineIndex
                            val isPastLine = index < currentLineIndex

                            LyricLineItem(
                                text = line.text,
                                isCurrent = isCurrentLine,
                                isPast = isPastLine,
                                onClick = {
                                    // Seek to this line when tapped
                                    playerManager.seekTo(line.timeMs)
                                }
                            )
                        }
                    }

                    // Current position indicator at bottom
                    Text(
                        text = formatTime(currentPosition),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LyricLineItem(
    text: String,
    isCurrent: Boolean,
    isPast: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isCurrent) 1.15f else 1.0f,
        animationSpec = tween(300),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = when {
            isCurrent -> 1.0f
            isPast -> 0.4f
            else -> 0.6f
        },
        animationSpec = tween(300),
        label = "alpha"
    )

    val color = when {
        isCurrent -> Color(0xFFFF9933) // Orange accent
        isPast -> Color.Gray
        else -> Color.White
    }

    Text(
        text = text,
        color = color,
        fontSize = if (isCurrent) 22.sp else 18.sp,
        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .scale(scale)
            .alpha(alpha)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun EmptyLyricsState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.MusicNote,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No lyrics available",
            color = Color.Gray,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This track doesn't have lyrics in our database",
            color = Color.DarkGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9933)
            )
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retry")
        }
    }
}

private fun formatTime(ms: Long): String {
    val seconds = (ms / 1000) % 60
    val minutes = (ms / 1000) / 60
    return "%d:%02d".format(minutes, seconds)
}

/**
 * ViewModel for LyricsScreen.
 */
@HiltViewModel
class LyricsViewModel @Inject constructor(
    private val lyricsManager: LyricsManager
) : ViewModel() {

    private val _lyricsLines = MutableStateFlow<List<LyricLine>>(emptyList())
    val lyricsLines: StateFlow<List<LyricLine>> = _lyricsLines.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadLyrics(trackId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val lyricsText = lyricsManager.fetchLyrics(trackId)

                if (lyricsText != null) {
                    val parsed = lyricsManager.parseLyrics(lyricsText)
                    if (parsed.isNotEmpty()) {
                        _lyricsLines.value = parsed
                    } else {
                        _error.value = "No lyrics available for this track"
                    }
                } else {
                    _error.value = "No lyrics found in database"
                }
            } catch (e: Exception) {
                _error.value = "Failed to load lyrics: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

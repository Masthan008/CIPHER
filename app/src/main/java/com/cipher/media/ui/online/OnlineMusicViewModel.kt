package com.cipher.media.ui.online

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.cipher.media.data.online.OnlineMusicRepository
import com.cipher.media.data.online.jamendo.JamendoTrack
import com.cipher.media.data.online.jamendo.OnlineTrack
import com.cipher.media.service.PlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnlineMusicViewModel @Inject constructor(
    private val repository: OnlineMusicRepository,
    private val playerManager: PlayerManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnlineMusicUiState())
    val uiState: StateFlow<OnlineMusicUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedLanguage = MutableStateFlow<String?>(null)
    val selectedLanguage: StateFlow<String?> = _selectedLanguage.asStateFlow()

    // Store current playlist for continuous playback
    private var currentPlaylist: List<OnlineTrack> = emptyList()
    private var currentTrackIndex: Int = 0

    // Expose PlayerManager state for UI
    val currentTrack: StateFlow<androidx.media3.common.MediaItem?> = playerManager.currentTrack
    val isPlaying: StateFlow<Boolean> = playerManager.isPlaying

    init {
        loadTrendingSongs()
    }

    fun loadTrendingSongs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getTrendingSongs(language = _selectedLanguage.value).fold(
                onSuccess = { tracks ->
                    _uiState.value = _uiState.value.copy(
                        trendingSongs = tracks.map { it.toOnlineTrack() },
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun searchSongs(query: String) {
        _searchQuery.value = query
        if (query.length < 2) {
            _uiState.value = _uiState.value.copy(searchResults = emptyList())
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true, error = null)
            // Don't pass language to search - let it search globally for more results
            repository.searchSongs(
                query = query,
                language = null  // Remove language restriction for text search
            ).fold(
                onSuccess = { tracks ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = tracks.map { it.toOnlineTrack() },
                        isSearching = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isSearching = false,
                        error = error.message
                    )
                }
            )
        }
    }

    // FIXED: Search by genre tag - don't add language to get more results
    fun searchByGenre(genre: String) {
        _searchQuery.value = genre
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true, error = null)
            repository.searchSongs(
                query = genre.lowercase(),  // Pass genre as query
                language = null
            ).fold(
                onSuccess = { tracks ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = tracks.map { it.toOnlineTrack() },
                        isSearching = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isSearching = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun selectLanguage(language: String?) {
        _selectedLanguage.value = language
        loadTrendingSongs()
    }

    // FIXED: Play track with playlist context for continuous playback
    fun playTrack(track: OnlineTrack, playlist: List<OnlineTrack>? = null) {
        // Update playlist if provided
        playlist?.let {
            currentPlaylist = it
            currentTrackIndex = it.indexOf(track)
        }

        // If no playlist, create single-item playlist
        if (playlist == null) {
            currentPlaylist = listOf(track)
            currentTrackIndex = 0
        }

        // Build media items for entire playlist - filter out tracks with missing stream URLs
        val mediaItems = currentPlaylist.mapNotNull { onlineTrack ->
            // Skip tracks with missing stream URLs
            if (onlineTrack.streamUrl.isNullOrBlank()) {
                Log.w("OnlineMusicViewModel", "Skipping track with missing streamUrl: ${onlineTrack.id}")
                return@mapNotNull null
            }
            
            val artworkUri = try {
                if (!onlineTrack.artworkUrl.isNullOrBlank()) {
                    Uri.parse(onlineTrack.artworkUrl)
                } else null
            } catch (e: Exception) {
                Log.w("OnlineMusicViewModel", "Failed to parse artworkUrl: ${onlineTrack.artworkUrl}")
                null
            }
            
            MediaItem.Builder()
                .setMediaId(onlineTrack.id)
                .setUri(onlineTrack.streamUrl)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(onlineTrack.title ?: "Unknown")
                        .setArtist(onlineTrack.artist ?: "Unknown Artist")
                        .setAlbumTitle(onlineTrack.album ?: "")
                        .apply {
                            artworkUri?.let { setArtworkUri(it) }
                        }
                        .build()
                )
                .build()
        }

        if (mediaItems.isEmpty()) {
            Log.e("OnlineMusicViewModel", "No valid media items to play")
            return
        }

        // Find the index of the clicked track in the filtered list
        val filteredIndex = mediaItems.indexOfFirst { it.mediaId == track.id }
        val startIndex = if (filteredIndex >= 0) filteredIndex else 0

        // Set entire playlist and play from selected index
        playerManager.playOnlinePlaylist(mediaItems, startIndex)
    }

    // FIXED: Play next in playlist
    fun playNext() {
        if (currentTrackIndex < currentPlaylist.size - 1) {
            currentTrackIndex++
            playerManager.skipNext()
        }
    }

    // FIXED: Play previous
    fun playPrevious() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            playerManager.skipPrevious()
        }
    }

    // Get current playlist for UI
    fun getCurrentPlaylist(): List<OnlineTrack> = currentPlaylist

    fun playPause() {
        playerManager.playPause()
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = _uiState.value.copy(searchResults = emptyList())
    }

    fun refresh() {
        loadTrendingSongs()
    }

    data class OnlineMusicUiState(
        val isLoading: Boolean = false,
        val isSearching: Boolean = false,
        val trendingSongs: List<OnlineTrack> = emptyList(),
        val searchResults: List<OnlineTrack> = emptyList(),
        val error: String? = null
    )
}

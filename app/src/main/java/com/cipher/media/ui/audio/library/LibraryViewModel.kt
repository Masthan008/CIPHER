package com.cipher.media.ui.audio.library

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.data.model.library.LibraryTab
import com.cipher.media.data.model.library.MusicTag
import com.cipher.media.data.model.library.SortOption
import com.cipher.media.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryUiState(
    val isLoading: Boolean = true,
    val activeTab: LibraryTab = LibraryTab.SONGS,
    val songs: List<AudioItem> = emptyList(),
    val albums: List<Triple<String, String, Uri?>> = emptyList(), // name, artist, artUri
    val artists: List<Pair<String, Int>> = emptyList(), // name, count
    val genres: List<Pair<String, Int>> = emptyList(), // name, count
    val folders: List<String> = emptyList(),
    val sortOption: SortOption = SortOption.NAME_ASC,
    val searchQuery: String = "",
    val selectedIds: Set<Long> = emptySet(),
    val isSelectionMode: Boolean = false,
    val editingTag: MusicTag? = null,
    val userTier: Tier = Tier.FREE
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val tagEditor: TagEditorManager
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryUiState())
    val state: StateFlow<LibraryUiState> = _state.asStateFlow()

    init {
        loadAll()
    }

    fun loadAll() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val songs = mediaRepository.getLocalAudio()
            val albums = tagEditor.getAlbums()
            val artists = tagEditor.getArtists()
            val genres = tagEditor.getGenres()
            val folders = tagEditor.getAudioFolders()

            _state.update {
                it.copy(
                    isLoading = false,
                    songs = songs,
                    albums = albums,
                    artists = artists,
                    genres = genres,
                    folders = folders
                )
            }
        }
    }

    fun setTab(tab: LibraryTab) {
        _state.update { it.copy(activeTab = tab) }
    }

    fun setSort(option: SortOption) {
        _state.update { st ->
            val sorted = when (option) {
                SortOption.NAME_ASC -> st.songs.sortedBy { it.title.lowercase() }
                SortOption.NAME_DESC -> st.songs.sortedByDescending { it.title.lowercase() }
                SortOption.DATE_NEWEST -> st.songs.sortedByDescending { it.dateAdded }
                SortOption.DATE_OLDEST -> st.songs.sortedBy { it.dateAdded }
                SortOption.ARTIST -> st.songs.sortedBy { it.artist.lowercase() }
                SortOption.ALBUM -> st.songs.sortedBy { it.album.lowercase() }
                SortOption.DURATION_LONG -> st.songs.sortedByDescending { it.duration }
                SortOption.DURATION_SHORT -> st.songs.sortedBy { it.duration }
            }
            st.copy(sortOption = option, songs = sorted)
        }
    }

    fun setSearch(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun getFilteredSongs(): List<AudioItem> {
        val st = _state.value
        if (st.searchQuery.isBlank()) return st.songs
        val q = st.searchQuery.lowercase()
        return st.songs.filter {
            it.title.lowercase().contains(q) ||
            it.artist.lowercase().contains(q) ||
            it.album.lowercase().contains(q)
        }
    }

    fun getSongsInFolder(folder: String): List<AudioItem> {
        return _state.value.songs.filter { it.path.startsWith(folder) }
    }

    fun getSongsForAlbum(albumName: String): List<AudioItem> {
        return _state.value.songs.filter { it.album == albumName }
    }

    fun getSongsForArtist(artistName: String): List<AudioItem> {
        return _state.value.songs.filter { it.artist == artistName }
    }

    // Selection mode
    fun toggleSelection(id: Long) {
        _state.update { st ->
            val newSet = st.selectedIds.toMutableSet()
            if (newSet.contains(id)) newSet.remove(id) else newSet.add(id)
            st.copy(selectedIds = newSet, isSelectionMode = newSet.isNotEmpty())
        }
    }

    fun clearSelection() {
        _state.update { it.copy(selectedIds = emptySet(), isSelectionMode = false) }
    }

    fun selectAll() {
        _state.update { st ->
            st.copy(selectedIds = st.songs.map { it.id }.toSet(), isSelectionMode = true)
        }
    }

    // Tag editing
    fun loadTagForEditing(audio: AudioItem) {
        viewModelScope.launch {
            val tag = tagEditor.readTags(audio)
            _state.update { it.copy(editingTag = tag) }
        }
    }

    fun saveTags(tag: MusicTag) {
        if (_state.value.userTier == Tier.FREE) return // Pro lock
        viewModelScope.launch {
            tagEditor.writeTags(tag)
            _state.update { it.copy(editingTag = null) }
            loadAll() // refresh
        }
    }

    fun dismissTagEditor() {
        _state.update { it.copy(editingTag = null) }
    }

    // Batch operations (Pro)
    fun batchSetField(field: String, value: String) {
        if (_state.value.userTier == Tier.FREE) return
        viewModelScope.launch {
            tagEditor.batchWriteField(_state.value.selectedIds.toList(), field, value)
            clearSelection()
            loadAll()
        }
    }

    fun batchAutoNumber() {
        if (_state.value.userTier == Tier.FREE) return
        viewModelScope.launch {
            tagEditor.autoNumberTracks(_state.value.selectedIds.toList())
            clearSelection()
            loadAll()
        }
    }
}

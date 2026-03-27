package com.cipher.media.ui.search

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.model.VaultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchFilter { ALL, VIDEO, MUSIC, VAULT }

data class SearchResult(
    val title: String,
    val subtitle: String,
    val type: SearchFilter,
    val uri: String = ""
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val contentResolver: ContentResolver
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _filter = MutableStateFlow(SearchFilter.ALL)
    val filter: StateFlow<SearchFilter> = _filter.asStateFlow()

    private val _results = MutableStateFlow<List<SearchResult>>(emptyList())
    val results: StateFlow<List<SearchResult>> = _results.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    init {
        // Debounce search queries
        viewModelScope.launch {
            _query.debounce(300)
                .combine(_filter) { q, f -> Pair(q, f) }
                .collect { (q, f) ->
                    if (q.length >= 2) {
                        performSearch(q, f)
                    } else {
                        _results.value = emptyList()
                    }
                }
        }
    }

    fun updateQuery(newQuery: String) { _query.value = newQuery }
    fun setFilter(newFilter: SearchFilter) { _filter.value = newFilter }

    private fun performSearch(query: String, filter: SearchFilter) {
        viewModelScope.launch(Dispatchers.IO) {
            _isSearching.value = true
            val results = mutableListOf<SearchResult>()

            if (filter == SearchFilter.ALL || filter == SearchFilter.VIDEO) {
                results.addAll(searchMedia(query, isVideo = true))
            }
            if (filter == SearchFilter.ALL || filter == SearchFilter.MUSIC) {
                results.addAll(searchMedia(query, isVideo = false))
            }

            _results.value = results
            _isSearching.value = false
        }
    }

    private fun searchMedia(query: String, isVideo: Boolean): List<SearchResult> {
        val results = mutableListOf<SearchResult>()
        val uri = if (isVideo) MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                  else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val type = if (isVideo) SearchFilter.VIDEO else SearchFilter.MUSIC

        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns._ID
        )
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} LIKE ?"
        val selectionArgs = arrayOf("%$query%")

        contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            while (cursor.moveToNext() && results.size < 20) {
                results.add(SearchResult(
                    title = cursor.getString(nameCol),
                    subtitle = if (isVideo) "Video" else "Audio",
                    type = type
                ))
            }
        }
        return results
    }
}

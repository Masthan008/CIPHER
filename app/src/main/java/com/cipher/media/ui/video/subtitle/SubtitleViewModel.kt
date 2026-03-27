package com.cipher.media.ui.video.subtitle

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.ui.video.subtitle.model.*
import com.cipher.media.ui.video.subtitle.online.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State for subtitle display — track, cues, sync, and styling.
 */
data class SubtitleUiState(
    val isLoaded: Boolean = false,
    val track: SubtitleTrack? = null,
    val currentCueText: String? = null,
    val syncOffsetMs: Long = 0L,            // User-configured sync offset
    val style: SubtitleStyle = SubtitleStyle(),
    val isVisible: Boolean = true,
    val showBottomSheet: Boolean = false,
    // Online search state
    val onlineResults: List<OnlineSubtitleResult> = emptyList(),
    val isSearching: Boolean = false,
    val searchError: String? = null,
    val isDownloading: Boolean = false,
    val downloadMessage: String? = null
)

/**
 * ViewModel managing subtitle loading, sync, style, and cue lookup.
 */
@HiltViewModel
class SubtitleViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubtitleUiState())
    val uiState: StateFlow<SubtitleUiState> = _uiState.asStateFlow()

    private var cues: List<SubtitleCue> = emptyList()

    // PRO FEATURE: Online subtitle search repository
    private val onlineRepo = OnlineSubtitleRepository(
        context = context,
        apiKey = getApiKey()
    )

    private fun getApiKey(): String {
        return try {
            val props = java.util.Properties()
            context.assets.open("env.properties").use { props.load(it) }
            props.getProperty("OPENSUBTITLES_API_KEY", "")
        } catch (e: Exception) {
            // Fallback: try BuildConfig or empty
            ""
        }
    }

    // ── Loading ──

    /**
     * Load subtitle from a content URI (file picker result).
     */
    fun loadSubtitle(uri: Uri) {
        val fileName = getFileName(uri) ?: "subtitle.srt"
        try {
            val (track, parsedCues) = SubtitleEngine.loadFromUri(context, uri, fileName)
            cues = parsedCues
            _uiState.value = _uiState.value.copy(
                isLoaded = true,
                track = track,
                syncOffsetMs = 0L,
                isVisible = true
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Unload current subtitle.
     */
    fun clearSubtitle() {
        cues = emptyList()
        _uiState.value = SubtitleUiState()
    }

    /**
     * Auto-load a subtitle with the same name as the video file if it exists.
     * Supports .srt, .vtt, and .ass extensions.
     */
    fun autoLoadSubtitle(videoUri: Uri) {
        if (_uiState.value.isLoaded) return // Already loaded
        
        try {
            val videoPath = videoUri.path ?: return
            val baseName = videoPath.substringBeforeLast(".")
            
            // Generate URIs for common subtitle extensions
            val srtUri = videoUri.buildUpon().path("$baseName.srt").build()
            val vttUri = videoUri.buildUpon().path("$baseName.vtt").build()
            val assUri = videoUri.buildUpon().path("$baseName.ass").build()
            
            // Try loading them one by one
            listOf(srtUri, vttUri, assUri).forEach { uri ->
                try {
                    // Check if file exists by trying to open input stream
                    context.contentResolver.openInputStream(uri)?.use {
                        // If it succeeds, load it and return
                        loadSubtitle(uri)
                        return
                    }
                } catch (e: Exception) {
                    // Ignore and try next format
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ── Cue lookup ──

    /**
     * Called every 100ms from the player to find and display the active cue.
     */
    fun updatePosition(positionMs: Long) {
        if (!_uiState.value.isLoaded || !_uiState.value.isVisible) {
            if (_uiState.value.currentCueText != null) {
                _uiState.value = _uiState.value.copy(currentCueText = null)
            }
            return
        }
        val activeCue = SubtitleEngine.findActiveCue(cues, positionMs, _uiState.value.syncOffsetMs)
        val newText = activeCue?.text
        if (newText != _uiState.value.currentCueText) {
            _uiState.value = _uiState.value.copy(currentCueText = newText)
        }
    }

    // ── Sync ──

    fun setSyncOffset(offsetMs: Long) {
        _uiState.value = _uiState.value.copy(syncOffsetMs = offsetMs)
    }

    fun resetSync() {
        _uiState.value = _uiState.value.copy(syncOffsetMs = 0L)
    }

    // ── Visibility ──

    fun toggleVisibility() {
        _uiState.value = _uiState.value.copy(isVisible = !_uiState.value.isVisible)
    }

    // ── Style ──

    fun setFontSize(size: Int) {
        _uiState.value = _uiState.value.copy(
            style = _uiState.value.style.copy(fontSize = size)
        )
    }

    fun setFontColor(color: androidx.compose.ui.graphics.Color) {
        _uiState.value = _uiState.value.copy(
            style = _uiState.value.style.copy(fontColor = color)
        )
    }

    fun setPosition(position: SubtitlePosition) {
        _uiState.value = _uiState.value.copy(
            style = _uiState.value.style.copy(position = position)
        )
    }

    fun setBackgroundOpacity(opacity: Float) {
        _uiState.value = _uiState.value.copy(
            style = _uiState.value.style.copy(
                backgroundOpacity = opacity,
                backgroundColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = opacity)
            )
        )
    }

    fun setFontFamily(font: SubtitleFont) {
        _uiState.value = _uiState.value.copy(
            style = _uiState.value.style.copy(fontFamily = font)
        )
    }

    // ── Bottom sheet ──

    fun showBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = true)
    }

    fun hideBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = false)
    }

    // ── Online subtitle search (PRO FEATURE) ──

    /**
     * Search for subtitles online using smart fallback (OpenSubtitles → Wyzie).
     */
    fun searchOnline(query: String, language: String = "en") {
        _uiState.value = _uiState.value.copy(
            isSearching = true,
            searchError = null,
            onlineResults = emptyList()
        )
        viewModelScope.launch {
            try {
                val results = onlineRepo.searchSubtitles(query, language)
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    onlineResults = results,
                    searchError = if (results.isEmpty()) "No subtitles found" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    searchError = "Search failed: ${e.message}"
                )
            }
        }
    }

    /**
     * Download an online subtitle and load it into the player.
     */
    fun downloadAndLoad(result: OnlineSubtitleResult, isPro: Boolean) {
        _uiState.value = _uiState.value.copy(isDownloading = true, downloadMessage = null)
        viewModelScope.launch {
            when (val downloadResult = onlineRepo.downloadSubtitle(result, isPro)) {
                is DownloadResult.Success -> {
                    // Load the downloaded subtitle file
                    val uri = android.net.Uri.fromFile(downloadResult.file)
                    loadSubtitle(uri)
                    _uiState.value = _uiState.value.copy(
                        isDownloading = false,
                        downloadMessage = "✓ Subtitle loaded: ${result.fileName}"
                    )
                }
                is DownloadResult.QuotaExceeded -> {
                    val msg = if (downloadResult.isPro) {
                        "Daily limit reached (${downloadResult.limit}/day)"
                    } else {
                        "Free limit reached (${downloadResult.limit}/day). Upgrade to Pro for 50/day!"
                    }
                    _uiState.value = _uiState.value.copy(
                        isDownloading = false,
                        downloadMessage = msg
                    )
                }
                is DownloadResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isDownloading = false,
                        downloadMessage = "Download failed: ${downloadResult.message}"
                    )
                }
            }
        }
    }

    /**
     * Get remaining downloads for today.
     */
    fun getRemainingDownloads(isPro: Boolean): Int = onlineRepo.getRemainingDownloads(isPro)

    fun clearSearchResults() {
        _uiState.value = _uiState.value.copy(
            onlineResults = emptyList(),
            searchError = null,
            downloadMessage = null
        )
    }

    // ── Utility ──

    private fun getFileName(uri: Uri): String? {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            if (nameIndex >= 0) cursor.getString(nameIndex) else null
        }
    }

    fun getCueCount(): Int = cues.size
}

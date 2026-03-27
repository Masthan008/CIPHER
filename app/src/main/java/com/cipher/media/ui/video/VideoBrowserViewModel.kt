package com.cipher.media.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.model.MediaItem
import com.cipher.media.domain.usecase.GetLocalMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the video browser screen.
 */
data class VideoBrowserUiState(
    val videos: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the video browser. Uses GetLocalMediaUseCase to fetch videos.
 */
@HiltViewModel
class VideoBrowserViewModel @Inject constructor(
    private val getLocalMediaUseCase: GetLocalMediaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoBrowserUiState())
    val uiState: StateFlow<VideoBrowserUiState> = _uiState.asStateFlow()

    fun loadVideos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val videos = getLocalMediaUseCase()
                _uiState.value = _uiState.value.copy(
                    videos = videos,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load videos"
                )
            }
        }
    }
}

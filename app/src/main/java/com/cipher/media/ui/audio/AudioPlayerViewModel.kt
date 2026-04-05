package com.cipher.media.ui.audio

import android.content.ComponentName
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem as Media3MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.repository.MediaRepository
import com.cipher.media.data.repository.FavoritesRepository
import com.cipher.media.service.AudioPlaybackService
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val favoritesRepository: FavoritesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _audioList = MutableStateFlow<List<AudioItem>>(emptyList())
    val audioList: StateFlow<List<AudioItem>> = _audioList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentAudio = MutableStateFlow<AudioItem?>(null)
    val currentAudio: StateFlow<AudioItem?> = _currentAudio.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _shuffleEnabled = MutableStateFlow(false)
    val shuffleEnabled: StateFlow<Boolean> = _shuffleEnabled.asStateFlow()

    private val _repeatMode = MutableStateFlow(Player.REPEAT_MODE_OFF)
    val repeatMode: StateFlow<Int> = _repeatMode.asStateFlow()

    var mediaController: MediaController? = null
        private set

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                _duration.value = mediaController?.duration ?: 0L
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onMediaItemTransition(mediaItem: Media3MediaItem?, reason: Int) {
            updateCurrentTrackFromPlayer()
        }
    }

    init {
        loadAudio()
        connectToService()
    }

    fun loadAudio() {
        viewModelScope.launch {
            _isLoading.value = true
            _audioList.value = mediaRepository.getLocalAudio()
            _isLoading.value = false
        }
    }

    private fun connectToService() {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, AudioPlaybackService::class.java)
        )

        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()
                mediaController?.addListener(playerListener)

                // Sync UI if already playing
                if (mediaController?.isPlaying == true) {
                    _isPlaying.value = true
                    _duration.value = mediaController?.duration ?: 0L
                    updateCurrentTrackFromPlayer()
                }

                // Start position polling
                startPositionUpdater()
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun startPositionUpdater() {
        viewModelScope.launch {
            while (true) {
                mediaController?.let { controller ->
                    _currentPosition.value = controller.currentPosition
                }
                kotlinx.coroutines.delay(250)
            }
        }
    }

    fun playAudio(audio: AudioItem, playlist: List<AudioItem>) {
        val controller = mediaController ?: return
        _currentAudio.value = audio
        checkFavoriteState(audio.id)

        // Build media items for gapless playback
        val mediaItems = playlist.map { item ->
            Media3MediaItem.Builder()
                .setUri(item.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(item.title)
                        .setArtist(item.artist)
                        .setAlbumTitle(item.album)
                        .setArtworkUri(item.albumArtUri)
                        .build()
                )
                .build()
        }

        val startIndex = playlist.indexOf(audio).coerceAtLeast(0)
        controller.setMediaItems(mediaItems, startIndex, 0)
        controller.prepare()
        controller.play()
    }

    fun togglePlayPause() {
        val controller = mediaController ?: return
        if (controller.isPlaying) {
            controller.pause()
        } else {
            controller.play()
        }
    }

    fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    fun skipNext() {
        mediaController?.seekToNextMediaItem()
        updateCurrentTrackFromPlayer()
    }

    fun skipPrevious() {
        mediaController?.seekToPreviousMediaItem()
        updateCurrentTrackFromPlayer()
    }

    fun toggleShuffle() {
        val controller = mediaController ?: return
        val newState = !controller.shuffleModeEnabled
        controller.shuffleModeEnabled = newState
        _shuffleEnabled.value = newState
    }

    fun cycleRepeatMode() {
        val controller = mediaController ?: return
        val newMode = when (controller.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        controller.repeatMode = newMode
        _repeatMode.value = newMode
    }

    fun stopPlayback() {
        mediaController?.stop()
        mediaController?.clearMediaItems()
        _currentAudio.value = null
        _isPlaying.value = false
    }

    private fun updateCurrentTrackFromPlayer() {
        val controller = mediaController ?: return
        val currentIndex = controller.currentMediaItemIndex
        val list = _audioList.value
        if (currentIndex in list.indices) {
            val audioItem = list[currentIndex]
            _currentAudio.value = audioItem
            checkFavoriteState(audioItem.id)
        }
    }

    private fun checkFavoriteState(mediaId: Long) {
        viewModelScope.launch {
            _isFavorite.value = favoritesRepository.isFavorite(mediaId)
        }
    }

    fun toggleFavorite() {
        val currentAudio = _currentAudio.value ?: return
        viewModelScope.launch {
            _isFavorite.value = favoritesRepository.toggleFavorite(currentAudio)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaController?.removeListener(playerListener)
        mediaController?.release()
    }
}

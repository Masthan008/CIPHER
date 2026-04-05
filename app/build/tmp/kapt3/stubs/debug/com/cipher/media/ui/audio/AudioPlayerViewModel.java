package com.cipher.media.ui.audio;

import android.content.ComponentName;
import android.net.Uri;
import androidx.lifecycle.ViewModel;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.repository.MediaRepository;
import com.cipher.media.data.repository.FavoritesRepository;
import com.cipher.media.service.AudioPlaybackService;
import com.google.common.util.concurrent.MoreExecutors;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.content.Context;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import androidx.media3.common.MediaItem;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0013\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u000fH\u0002J\b\u00103\u001a\u000201H\u0002J\u0006\u00104\u001a\u000201J\u0006\u00105\u001a\u000201J\b\u00106\u001a\u000201H\u0014J\u001c\u00107\u001a\u0002012\u0006\u00108\u001a\u00020\f2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u000e\u0010:\u001a\u0002012\u0006\u0010;\u001a\u00020\u000fJ\u0006\u0010<\u001a\u000201J\u0006\u0010=\u001a\u000201J\b\u0010>\u001a\u000201H\u0002J\u0006\u0010?\u001a\u000201J\u0006\u0010@\u001a\u000201J\u0006\u0010A\u001a\u000201J\u0006\u0010B\u001a\u000201J\b\u0010C\u001a\u000201H\u0002R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\"\u0010\'\u001a\u0004\u0018\u00010&2\b\u0010%\u001a\u0004\u0018\u00010&@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00160\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001bR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00120\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001b\u00a8\u0006D"}, d2 = {"Lcom/cipher/media/ui/audio/AudioPlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "mediaRepository", "Lcom/cipher/media/data/repository/MediaRepository;", "favoritesRepository", "Lcom/cipher/media/data/repository/FavoritesRepository;", "context", "Landroid/content/Context;", "(Lcom/cipher/media/data/repository/MediaRepository;Lcom/cipher/media/data/repository/FavoritesRepository;Landroid/content/Context;)V", "_audioList", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/cipher/media/data/model/AudioItem;", "_currentAudio", "_currentPosition", "", "_duration", "_isFavorite", "", "_isLoading", "_isPlaying", "_repeatMode", "", "_shuffleEnabled", "audioList", "Lkotlinx/coroutines/flow/StateFlow;", "getAudioList", "()Lkotlinx/coroutines/flow/StateFlow;", "currentAudio", "getCurrentAudio", "currentPosition", "getCurrentPosition", "duration", "getDuration", "isFavorite", "isLoading", "isPlaying", "<set-?>", "Landroidx/media3/session/MediaController;", "mediaController", "getMediaController", "()Landroidx/media3/session/MediaController;", "playerListener", "Landroidx/media3/common/Player$Listener;", "repeatMode", "getRepeatMode", "shuffleEnabled", "getShuffleEnabled", "checkFavoriteState", "", "mediaId", "connectToService", "cycleRepeatMode", "loadAudio", "onCleared", "playAudio", "audio", "playlist", "seekTo", "position", "skipNext", "skipPrevious", "startPositionUpdater", "stopPlayback", "toggleFavorite", "togglePlayPause", "toggleShuffle", "updateCurrentTrackFromPlayer", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AudioPlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.repository.MediaRepository mediaRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.repository.FavoritesRepository favoritesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.cipher.media.data.model.AudioItem>> _audioList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.AudioItem>> audioList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.data.model.AudioItem> _currentAudio = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.AudioItem> currentAudio = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isFavorite = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _currentPosition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> currentPosition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _duration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> duration = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _shuffleEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> shuffleEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _repeatMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> repeatMode = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.session.MediaController mediaController;
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.common.Player.Listener playerListener = null;
    
    @javax.inject.Inject()
    public AudioPlayerViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.repository.MediaRepository mediaRepository, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.repository.FavoritesRepository favoritesRepository, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.AudioItem>> getAudioList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.AudioItem> getCurrentAudio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavorite() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getCurrentPosition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShuffleEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getRepeatMode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.media3.session.MediaController getMediaController() {
        return null;
    }
    
    public final void loadAudio() {
    }
    
    private final void connectToService() {
    }
    
    private final void startPositionUpdater() {
    }
    
    public final void playAudio(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.AudioItem audio, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> playlist) {
    }
    
    public final void togglePlayPause() {
    }
    
    public final void seekTo(long position) {
    }
    
    public final void skipNext() {
    }
    
    public final void skipPrevious() {
    }
    
    public final void toggleShuffle() {
    }
    
    public final void cycleRepeatMode() {
    }
    
    public final void stopPlayback() {
    }
    
    private final void updateCurrentTrackFromPlayer() {
    }
    
    private final void checkFavoriteState(long mediaId) {
    }
    
    public final void toggleFavorite() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}
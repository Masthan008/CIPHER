package com.cipher.media.ui.video;

import androidx.lifecycle.ViewModel;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * ViewModel for video player screen.
 * Provides player state and exposes ExoPlayer.Builder for the screen to construct
 * the actual player instance within the appropriate lifecycle.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0011J\u001e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001b"}, d2 = {"Lcom/cipher/media/ui/video/VideoPlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "playerBuilder", "Landroidx/media3/exoplayer/ExoPlayer$Builder;", "(Landroidx/media3/exoplayer/ExoPlayer$Builder;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/PlayerUiState;", "getPlayerBuilder", "()Landroidx/media3/exoplayer/ExoPlayer$Builder;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "setControlsVisible", "", "visible", "", "toggleControls", "toggleRotationLock", "updatePlayingState", "isPlaying", "updatePosition", "position", "", "duration", "buffered", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoPlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.exoplayer.ExoPlayer.Builder playerBuilder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.PlayerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.PlayerUiState> uiState = null;
    
    @javax.inject.Inject()
    public VideoPlayerViewModel(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer.Builder playerBuilder) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.media3.exoplayer.ExoPlayer.Builder getPlayerBuilder() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.PlayerUiState> getUiState() {
        return null;
    }
    
    public final void updatePlayingState(boolean isPlaying) {
    }
    
    public final void updatePosition(long position, long duration, long buffered) {
    }
    
    public final void toggleControls() {
    }
    
    public final void setControlsVisible(boolean visible) {
    }
    
    public final void toggleRotationLock() {
    }
}
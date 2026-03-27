package com.cipher.media.ui.video;

import androidx.lifecycle.ViewModel;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * ViewModel for video player screen.
 * Manages playback state, speed, aspect ratio, and control visibility.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0004\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u000fJ\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fJ\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0012J\u001e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006!"}, d2 = {"Lcom/cipher/media/ui/video/VideoPlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "playerBuilder", "Landroidx/media3/exoplayer/ExoPlayer$Builder;", "(Landroidx/media3/exoplayer/ExoPlayer$Builder;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/PlayerUiState;", "getPlayerBuilder", "()Landroidx/media3/exoplayer/ExoPlayer$Builder;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "cycleAspectRatio", "", "setControlsVisible", "visible", "", "setPlaybackSpeed", "speed", "", "toggleControls", "toggleRotationLock", "toggleSpeedMenu", "updatePlayingState", "isPlaying", "updatePosition", "position", "", "duration", "buffered", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoPlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.exoplayer.ExoPlayer.Builder playerBuilder = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Float> SPEED_OPTIONS = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.PlayerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.PlayerUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.VideoPlayerViewModel.Companion Companion = null;
    
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
    
    public final void setPlaybackSpeed(float speed) {
    }
    
    public final void toggleSpeedMenu() {
    }
    
    public final void cycleAspectRatio() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/ui/video/VideoPlayerViewModel$Companion;", "", "()V", "SPEED_OPTIONS", "", "", "getSPEED_OPTIONS", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Float> getSPEED_OPTIONS() {
            return null;
        }
    }
}
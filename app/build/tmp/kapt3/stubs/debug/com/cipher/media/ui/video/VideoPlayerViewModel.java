package com.cipher.media.ui.video;

import androidx.lifecycle.ViewModel;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import com.cipher.media.billing.model.SubscriptionTier;

/**
 * ViewModel for video player screen.
 * Manages playback state, speed, aspect ratio, screen lock, and control visibility.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0004\b\u0007\u0018\u0000 \'2\u00020\u0001:\u0001\'B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0015J\u0006\u0010\u001d\u001a\u00020\u0015J\u0006\u0010\u001e\u001a\u00020\u0015J\u0006\u0010\u001f\u001a\u00020\u0015J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0018J\u001e\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020$R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006("}, d2 = {"Lcom/cipher/media/ui/video/VideoPlayerViewModel;", "Landroidx/lifecycle/ViewModel;", "playerBuilder", "Landroidx/media3/exoplayer/ExoPlayer$Builder;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "context", "Landroid/content/Context;", "(Landroidx/media3/exoplayer/ExoPlayer$Builder;Lcom/cipher/media/billing/BillingRepository;Landroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/PlayerUiState;", "getPlayerBuilder", "()Landroidx/media3/exoplayer/ExoPlayer$Builder;", "trackSelector", "Landroidx/media3/exoplayer/trackselection/DefaultTrackSelector;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "cycleAspectRatio", "", "setControlsVisible", "visible", "", "setPlaybackSpeed", "speed", "", "toggleControls", "toggleRotationLock", "toggleScreenLock", "toggleSpeedMenu", "updatePlayingState", "isPlaying", "updatePosition", "position", "", "duration", "buffered", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoPlayerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Float> SPEED_OPTIONS = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.exoplayer.trackselection.DefaultTrackSelector trackSelector = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.exoplayer.ExoPlayer.Builder playerBuilder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.PlayerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.PlayerUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.VideoPlayerViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public VideoPlayerViewModel(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer.Builder playerBuilder, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
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
    
    public final void toggleScreenLock() {
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
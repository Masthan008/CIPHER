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
 * UI state for video player — includes speed, aspect ratio, screen lock, PiP controls.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\fH\u00c6\u0003J\t\u0010*\u001a\u00020\u000eH\u00c6\u0003Jw\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010,\u001a\u00020\u00032\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020/H\u00d6\u0001J\t\u00100\u001a\u000201H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001bR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001bR\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001bR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\u001d\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001b\u00a8\u00062"}, d2 = {"Lcom/cipher/media/ui/video/PlayerUiState;", "", "isPlaying", "", "currentPosition", "", "duration", "bufferedPosition", "isControlsVisible", "isLocked", "isScreenLocked", "playbackSpeed", "", "aspectRatioMode", "Lcom/cipher/media/ui/video/AspectRatioMode;", "showSpeedMenu", "isPro", "(ZJJJZZZFLcom/cipher/media/ui/video/AspectRatioMode;ZZ)V", "getAspectRatioMode", "()Lcom/cipher/media/ui/video/AspectRatioMode;", "getBufferedPosition", "()J", "bufferedProgress", "getBufferedProgress", "()F", "getCurrentPosition", "getDuration", "()Z", "getPlaybackSpeed", "progress", "getProgress", "getShowSpeedMenu", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class PlayerUiState {
    private final boolean isPlaying = false;
    private final long currentPosition = 0L;
    private final long duration = 0L;
    private final long bufferedPosition = 0L;
    private final boolean isControlsVisible = false;
    private final boolean isLocked = false;
    private final boolean isScreenLocked = false;
    private final float playbackSpeed = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.AspectRatioMode aspectRatioMode = null;
    private final boolean showSpeedMenu = false;
    private final boolean isPro = false;
    
    public PlayerUiState(boolean isPlaying, long currentPosition, long duration, long bufferedPosition, boolean isControlsVisible, boolean isLocked, boolean isScreenLocked, float playbackSpeed, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.AspectRatioMode aspectRatioMode, boolean showSpeedMenu, boolean isPro) {
        super();
    }
    
    public final boolean isPlaying() {
        return false;
    }
    
    public final long getCurrentPosition() {
        return 0L;
    }
    
    public final long getDuration() {
        return 0L;
    }
    
    public final long getBufferedPosition() {
        return 0L;
    }
    
    public final boolean isControlsVisible() {
        return false;
    }
    
    public final boolean isLocked() {
        return false;
    }
    
    public final boolean isScreenLocked() {
        return false;
    }
    
    public final float getPlaybackSpeed() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.AspectRatioMode getAspectRatioMode() {
        return null;
    }
    
    public final boolean getShowSpeedMenu() {
        return false;
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final float getProgress() {
        return 0.0F;
    }
    
    public final float getBufferedProgress() {
        return 0.0F;
    }
    
    public PlayerUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.AspectRatioMode component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.PlayerUiState copy(boolean isPlaying, long currentPosition, long duration, long bufferedPosition, boolean isControlsVisible, boolean isLocked, boolean isScreenLocked, float playbackSpeed, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.AspectRatioMode aspectRatioMode, boolean showSpeedMenu, boolean isPro) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}
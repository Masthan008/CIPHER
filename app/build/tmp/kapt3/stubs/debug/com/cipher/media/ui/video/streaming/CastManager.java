package com.cipher.media.ui.video.streaming;

import android.content.Context;
import android.util.Log;
import androidx.media3.cast.CastPlayer;
import androidx.media3.cast.SessionAvailabilityListener;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.util.UnstableApi;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages Google Cast (Chromecast) integration.
 *
 * PRO: Basic casting
 * POWER: Multiple targets + audio-only mode
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\"B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00062\b\b\u0002\u0010\u0019\u001a\u00020\u0006J\b\u0010\u001a\u001a\u0004\u0018\u00010\rJ\u0006\u0010\u001b\u001a\u00020\u0017J\u0006\u0010\u001c\u001a\u00020\u0017J\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u0017J\u0006\u0010!\u001a\u00020\u0017R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006#"}, d2 = {"Lcom/cipher/media/ui/video/streaming/CastManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/streaming/CastManager$CastUiState;", "castContext", "Lcom/google/android/gms/cast/framework/CastContext;", "castPlayer", "Landroidx/media3/cast/CastPlayer;", "castStateListener", "Lcom/google/android/gms/cast/framework/CastStateListener;", "sessionListener", "Landroidx/media3/cast/SessionAvailabilityListener;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "castMedia", "", "url", "title", "getCastPlayer", "initialize", "release", "seekTo", "positionMs", "", "stopCasting", "togglePlayPause", "CastUiState", "app_debug"})
@androidx.media3.common.util.UnstableApi()
public final class CastManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "CastManager";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.streaming.CastManager.CastUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.streaming.CastManager.CastUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.cast.framework.CastContext castContext;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.cast.CastPlayer castPlayer;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.cast.framework.CastStateListener castStateListener = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.cast.SessionAvailabilityListener sessionListener = null;
    
    @javax.inject.Inject()
    public CastManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.streaming.CastManager.CastUiState> getUiState() {
        return null;
    }
    
    /**
     * Initialize Cast SDK. Call from Activity.
     */
    public final void initialize() {
    }
    
    /**
     * Cast a media URL to the connected device.
     */
    public final void castMedia(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    /**
     * Play/pause toggle on cast device.
     */
    public final void togglePlayPause() {
    }
    
    /**
     * Seek cast playback to position.
     */
    public final void seekTo(long positionMs) {
    }
    
    /**
     * Stop casting and return to local playback.
     */
    public final void stopCasting() {
    }
    
    /**
     * Get the CastPlayer for transfer between local/remote.
     */
    @org.jetbrains.annotations.Nullable()
    public final androidx.media3.cast.CastPlayer getCastPlayer() {
        return null;
    }
    
    /**
     * Cleanup resources.
     */
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u000bH\u00c6\u0003J]\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\u00032\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0015\u00a8\u0006$"}, d2 = {"Lcom/cipher/media/ui/video/streaming/CastManager$CastUiState;", "", "isCasting", "", "castDeviceName", "", "isDiscovering", "hasCastDevices", "currentTitle", "isPlaying", "currentPosition", "", "duration", "(ZLjava/lang/String;ZZLjava/lang/String;ZJJ)V", "getCastDeviceName", "()Ljava/lang/String;", "getCurrentPosition", "()J", "getCurrentTitle", "getDuration", "getHasCastDevices", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class CastUiState {
        private final boolean isCasting = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String castDeviceName = null;
        private final boolean isDiscovering = false;
        private final boolean hasCastDevices = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String currentTitle = null;
        private final boolean isPlaying = false;
        private final long currentPosition = 0L;
        private final long duration = 0L;
        
        public CastUiState(boolean isCasting, @org.jetbrains.annotations.Nullable()
        java.lang.String castDeviceName, boolean isDiscovering, boolean hasCastDevices, @org.jetbrains.annotations.Nullable()
        java.lang.String currentTitle, boolean isPlaying, long currentPosition, long duration) {
            super();
        }
        
        public final boolean isCasting() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCastDeviceName() {
            return null;
        }
        
        public final boolean isDiscovering() {
            return false;
        }
        
        public final boolean getHasCastDevices() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCurrentTitle() {
            return null;
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
        
        public CastUiState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        public final boolean component6() {
            return false;
        }
        
        public final long component7() {
            return 0L;
        }
        
        public final long component8() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.ui.video.streaming.CastManager.CastUiState copy(boolean isCasting, @org.jetbrains.annotations.Nullable()
        java.lang.String castDeviceName, boolean isDiscovering, boolean hasCastDevices, @org.jetbrains.annotations.Nullable()
        java.lang.String currentTitle, boolean isPlaying, long currentPosition, long duration) {
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
}
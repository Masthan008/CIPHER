package com.cipher.media.audio;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.util.Log;

/**
 * Manages Android audio effects (Equalizer, BassBoost, Virtualizer)
 * attached to a given audio session ID from ExoPlayer.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\fJ\u0016\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fJ\u000e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u000fJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/cipher/media/audio/AudioEffectsManager;", "", "()V", "TAG", "", "bassBoost", "Landroid/media/audiofx/BassBoost;", "equalizer", "Landroid/media/audiofx/Equalizer;", "virtualizer", "Landroid/media/audiofx/Virtualizer;", "applyBandGains", "", "gains", "", "", "attach", "audioSessionId", "release", "setBandLevel", "bandIndex", "gainMillibels", "setBassBoostStrength", "strength", "setVirtualizerStrength", "app_debug"})
public final class AudioEffectsManager {
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Equalizer equalizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.BassBoost bassBoost;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Virtualizer virtualizer;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "AudioEffectsManager";
    
    public AudioEffectsManager() {
        super();
    }
    
    /**
     * Attach effects to the given audio session ID.
     * Must be called after ExoPlayer is created.
     */
    public final void attach(int audioSessionId) {
    }
    
    /**
     * Set the gain (in millibels, -1500 to +1500) for a band index.
     * The actual number of bands depends on the device; we map our 10-band
     * model to the device's available bands.
     */
    public final void setBandLevel(int bandIndex, int gainMillibels) {
    }
    
    /**
     * Apply all 10 band gains at once.
     */
    public final void applyBandGains(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> gains) {
    }
    
    /**
     * Set bass boost strength (0-1000).
     */
    public final void setBassBoostStrength(int strength) {
    }
    
    /**
     * Set virtualizer strength (0-1000).
     */
    public final void setVirtualizerStrength(int strength) {
    }
    
    public final void release() {
    }
}
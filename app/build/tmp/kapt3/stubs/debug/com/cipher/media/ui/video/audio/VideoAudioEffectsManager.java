package com.cipher.media.ui.video.audio;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.LoudnessEnhancer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.util.Log;

/**
 * Manages advanced Android audio effects attached to a given audio session ID.
 * Maps 10 logical UI bands to the device's physical equalizer bands.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\n\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0015J\u0006\u0010\u0018\u001a\u00020\u0012J\u000e\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0015J\u0016\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0015J\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/cipher/media/ui/video/audio/VideoAudioEffectsManager;", "", "()V", "TAG", "", "bassBoost", "Landroid/media/audiofx/BassBoost;", "equalizer", "Landroid/media/audiofx/Equalizer;", "logicalFrequencies", "", "loudnessEnhancer", "Landroid/media/audiofx/LoudnessEnhancer;", "presetReverb", "Landroid/media/audiofx/PresetReverb;", "virtualizer", "Landroid/media/audiofx/Virtualizer;", "apply10BandGains", "", "gainsMillibels", "", "", "attach", "audioSessionId", "release", "setBassBoostStrength", "strength", "setLogicalBandLevel", "logicalIndex", "gainMillibels", "setLoudnessEnhancerGain", "gainMb", "setReverbPreset", "preset", "", "setVirtualizerStrength", "app_debug"})
public final class VideoAudioEffectsManager {
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Equalizer equalizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.BassBoost bassBoost;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Virtualizer virtualizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.LoudnessEnhancer loudnessEnhancer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.PresetReverb presetReverb;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "VideoAudioFxManager";
    @org.jetbrains.annotations.NotNull()
    private final int[] logicalFrequencies = {31000, 63000, 125000, 250000, 500000, 1000000, 2000000, 4000000, 8000000, 16000000};
    
    public VideoAudioEffectsManager() {
        super();
    }
    
    public final void attach(int audioSessionId) {
    }
    
    /**
     * Set gain for a logical 10-band index (0-9).
     * We map this logical 10-band index to the closest physical device band.
     */
    public final void setLogicalBandLevel(int logicalIndex, int gainMillibels) {
    }
    
    public final void apply10BandGains(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> gainsMillibels) {
    }
    
    public final void setBassBoostStrength(int strength) {
    }
    
    public final void setVirtualizerStrength(int strength) {
    }
    
    public final void setLoudnessEnhancerGain(int gainMb) {
    }
    
    public final void setReverbPreset(short preset) {
    }
    
    public final void release() {
    }
}
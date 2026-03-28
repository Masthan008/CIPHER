package com.cipher.media.ui.audio.audiofx;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.LoudnessEnhancer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.util.Log;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\n\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000fJ\u0006\u0010\u0015\u001a\u00020\u0013J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000fJ\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000fJ\u000e\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000fR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/cipher/media/ui/audio/audiofx/AudioEffectsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bassBoost", "Landroid/media/audiofx/BassBoost;", "equalizer", "Landroid/media/audiofx/Equalizer;", "loudnessEnhancer", "Landroid/media/audiofx/LoudnessEnhancer;", "reverb", "Landroid/media/audiofx/PresetReverb;", "virtualCenterFreqs", "", "", "virtualizer", "Landroid/media/audiofx/Virtualizer;", "attachToAudioSession", "", "audioSessionId", "release", "setBandLevel", "virtualBand", "levelDb", "setBassBoost", "strength", "setReverbPreset", "preset", "", "setVirtualizer", "app_debug"})
public final class AudioEffectsManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Equalizer equalizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.BassBoost bassBoost;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.Virtualizer virtualizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.PresetReverb reverb;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.LoudnessEnhancer loudnessEnhancer;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> virtualCenterFreqs = null;
    
    @javax.inject.Inject()
    public AudioEffectsManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void attachToAudioSession(int audioSessionId) {
    }
    
    public final void release() {
    }
    
    /**
     * Map virtual 10-band slider to the closest native hardware band.
     * @param virtualBand 0-9
     * @param levelDb -15 to +15 dB
     */
    public final void setBandLevel(int virtualBand, int levelDb) {
    }
    
    public final void setBassBoost(int strength) {
    }
    
    public final void setVirtualizer(int strength) {
    }
    
    public final void setReverbPreset(short preset) {
    }
}
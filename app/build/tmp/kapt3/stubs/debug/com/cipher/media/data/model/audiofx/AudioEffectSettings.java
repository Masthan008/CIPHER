package com.cipher.media.data.model.audiofx;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\n\n\u0000\n\u0002\u0010\t\n\u0002\b \n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Be\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\fH\u00c6\u0003J\t\u0010\'\u001a\u00020\u000eH\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003Ji\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010+\u001a\u00020\u00032\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\bH\u00d6\u0001J\t\u0010.\u001a\u00020/H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013\u00a8\u00060"}, d2 = {"Lcom/cipher/media/data/model/audiofx/AudioEffectSettings;", "", "enabled", "", "selectedPreset", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "customBands", "", "", "bassBoostStrength", "virtualizerStrength", "reverbPreset", "", "crossfadeDurationMs", "", "replayGainEnabled", "hiResEnabled", "(ZLcom/cipher/media/data/model/audiofx/EQPreset;Ljava/util/List;IISJZZ)V", "getBassBoostStrength", "()I", "getCrossfadeDurationMs", "()J", "getCustomBands", "()Ljava/util/List;", "getEnabled", "()Z", "getHiResEnabled", "getReplayGainEnabled", "getReverbPreset", "()S", "getSelectedPreset", "()Lcom/cipher/media/data/model/audiofx/EQPreset;", "getVirtualizerStrength", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class AudioEffectSettings {
    private final boolean enabled = false;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.model.audiofx.EQPreset selectedPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> customBands = null;
    private final int bassBoostStrength = 0;
    private final int virtualizerStrength = 0;
    private final short reverbPreset = 0;
    private final long crossfadeDurationMs = 0L;
    private final boolean replayGainEnabled = false;
    private final boolean hiResEnabled = false;
    
    public AudioEffectSettings(boolean enabled, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.EQPreset selectedPreset, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> customBands, int bassBoostStrength, int virtualizerStrength, short reverbPreset, long crossfadeDurationMs, boolean replayGainEnabled, boolean hiResEnabled) {
        super();
    }
    
    public final boolean getEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.audiofx.EQPreset getSelectedPreset() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getCustomBands() {
        return null;
    }
    
    public final int getBassBoostStrength() {
        return 0;
    }
    
    public final int getVirtualizerStrength() {
        return 0;
    }
    
    public final short getReverbPreset() {
        return 0;
    }
    
    public final long getCrossfadeDurationMs() {
        return 0L;
    }
    
    public final boolean getReplayGainEnabled() {
        return false;
    }
    
    public final boolean getHiResEnabled() {
        return false;
    }
    
    public AudioEffectSettings() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.audiofx.EQPreset component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final short component6() {
        return 0;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.audiofx.AudioEffectSettings copy(boolean enabled, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.EQPreset selectedPreset, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> customBands, int bassBoostStrength, int virtualizerStrength, short reverbPreset, long crossfadeDurationMs, boolean replayGainEnabled, boolean hiResEnabled) {
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
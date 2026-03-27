package com.cipher.media.data.model;

/**
 * An equalizer preset with gain values for 10 bands.
 * Gain range: -15dB to +15dB (stored as millibels internally: -1500 to +1500).
 * Bands: 31Hz, 63Hz, 125Hz, 250Hz, 500Hz, 1kHz, 2kHz, 4kHz, 8kHz, 16kHz
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/cipher/media/data/model/EqualizerPreset;", "", "name", "", "bandGains", "", "", "(Ljava/lang/String;Ljava/util/List;)V", "getBandGains", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
public final class EqualizerPreset {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> bandGains = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> BAND_LABELS = null;
    public static final int MIN_GAIN_MB = -1500;
    public static final int MAX_GAIN_MB = 1500;
    public static final int NUM_BANDS = 10;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset FLAT = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset BASS_BOOST = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset VOCAL = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset TREBLE = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset ROCK = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset POP = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset JAZZ = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.cipher.media.data.model.EqualizerPreset CLASSICAL = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.cipher.media.data.model.EqualizerPreset> ALL_PRESETS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.data.model.EqualizerPreset.Companion Companion = null;
    
    public EqualizerPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> bandGains) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getBandGains() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.EqualizerPreset copy(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> bandGains) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\u0012\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u000e\u0010\u0014\u001a\u00020\u0015X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\rR\u0011\u0010\u001a\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\rR\u0011\u0010\u001c\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\rR\u0011\u0010\u001e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\r\u00a8\u0006 "}, d2 = {"Lcom/cipher/media/data/model/EqualizerPreset$Companion;", "", "()V", "ALL_PRESETS", "", "Lcom/cipher/media/data/model/EqualizerPreset;", "getALL_PRESETS", "()Ljava/util/List;", "BAND_LABELS", "", "getBAND_LABELS", "BASS_BOOST", "getBASS_BOOST", "()Lcom/cipher/media/data/model/EqualizerPreset;", "CLASSICAL", "getCLASSICAL", "FLAT", "getFLAT", "JAZZ", "getJAZZ", "MAX_GAIN_MB", "", "MIN_GAIN_MB", "NUM_BANDS", "POP", "getPOP", "ROCK", "getROCK", "TREBLE", "getTREBLE", "VOCAL", "getVOCAL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getBAND_LABELS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getFLAT() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getBASS_BOOST() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getVOCAL() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getTREBLE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getROCK() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getPOP() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getJAZZ() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.EqualizerPreset getCLASSICAL() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.cipher.media.data.model.EqualizerPreset> getALL_PRESETS() {
            return null;
        }
    }
}
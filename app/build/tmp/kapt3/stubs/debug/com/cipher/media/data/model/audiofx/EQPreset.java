package com.cipher.media.data.model.audiofx;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u000e2\u00020\u0001:\n\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015B\u001d\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0001\t\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u00a8\u0006\u001f"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset;", "", "name", "", "bands", "", "", "(Ljava/lang/String;Ljava/util/List;)V", "getBands", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "BassBoost", "Classical", "Companion", "Custom", "Flat", "Jazz", "Pop", "Rock", "Treble", "Vocal", "Lcom/cipher/media/data/model/audiofx/EQPreset$BassBoost;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Classical;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Custom;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Flat;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Jazz;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Pop;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Rock;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Treble;", "Lcom/cipher/media/data/model/audiofx/EQPreset$Vocal;", "app_debug"})
public abstract class EQPreset {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> bands = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.cipher.media.data.model.audiofx.EQPreset> ALL = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.cipher.media.data.model.audiofx.EQPreset> FREE_TIER = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.data.model.audiofx.EQPreset.Companion Companion = null;
    
    private EQPreset(java.lang.String name, java.util.List<java.lang.Integer> bands) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getBands() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$BassBoost;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class BassBoost extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.BassBoost INSTANCE = null;
        
        private BassBoost() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Classical;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Classical extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Classical INSTANCE = null;
        
        private Classical() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\u00a8\u0006\n"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Companion;", "", "()V", "ALL", "", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "getALL", "()Ljava/util/List;", "FREE_TIER", "getFREE_TIER", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.cipher.media.data.model.audiofx.EQPreset> getALL() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.cipher.media.data.model.audiofx.EQPreset> getFREE_TIER() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Custom;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "bands", "", "", "(Ljava/util/List;)V", "app_debug"})
    public static final class Custom extends com.cipher.media.data.model.audiofx.EQPreset {
        
        public Custom(@org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.Integer> bands) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Flat;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Flat extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Flat INSTANCE = null;
        
        private Flat() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Jazz;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Jazz extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Jazz INSTANCE = null;
        
        private Jazz() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Pop;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Pop extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Pop INSTANCE = null;
        
        private Pop() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Rock;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Rock extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Rock INSTANCE = null;
        
        private Rock() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Treble;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Treble extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Treble INSTANCE = null;
        
        private Treble() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/audiofx/EQPreset$Vocal;", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "()V", "app_debug"})
    public static final class Vocal extends com.cipher.media.data.model.audiofx.EQPreset {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.audiofx.EQPreset.Vocal INSTANCE = null;
        
        private Vocal() {
        }
    }
}
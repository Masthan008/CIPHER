package com.cipher.media.ui.video.subtitle.model;

/**
 * Supported subtitle formats.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u0015\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\r"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/model/SubtitleFormat;", "", "extensions", "", "", "(Ljava/lang/String;ILjava/util/List;)V", "getExtensions", "()Ljava/util/List;", "SRT", "ASS", "VTT", "UNKNOWN", "Companion", "app_debug"})
public enum SubtitleFormat {
    /*public static final*/ SRT /* = new SRT(null) */,
    /*public static final*/ ASS /* = new ASS(null) */,
    /*public static final*/ VTT /* = new VTT(null) */,
    /*public static final*/ UNKNOWN /* = new UNKNOWN(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> extensions = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.subtitle.model.SubtitleFormat.Companion Companion = null;
    
    SubtitleFormat(java.util.List<java.lang.String> extensions) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getExtensions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.video.subtitle.model.SubtitleFormat> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/model/SubtitleFormat$Companion;", "", "()V", "fromFileName", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleFormat;", "name", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.ui.video.subtitle.model.SubtitleFormat fromFileName(@org.jetbrains.annotations.NotNull()
        java.lang.String name) {
            return null;
        }
    }
}
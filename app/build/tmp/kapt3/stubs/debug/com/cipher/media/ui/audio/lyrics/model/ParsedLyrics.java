package com.cipher.media.ui.audio.lyrics.model;

/**
 * Parsed lyrics container.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003JA\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\u00102\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u000f\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\r\u00a8\u0006$"}, d2 = {"Lcom/cipher/media/ui/audio/lyrics/model/ParsedLyrics;", "", "title", "", "artist", "album", "lines", "", "Lcom/cipher/media/ui/audio/lyrics/model/LyricLine;", "source", "Lcom/cipher/media/ui/audio/lyrics/model/LyricsSource;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/cipher/media/ui/audio/lyrics/model/LyricsSource;)V", "getAlbum", "()Ljava/lang/String;", "getArtist", "isSync", "", "()Z", "getLines", "()Ljava/util/List;", "plainText", "getPlainText", "getSource", "()Lcom/cipher/media/ui/audio/lyrics/model/LyricsSource;", "getTitle", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ParsedLyrics {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String artist = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String album = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.ui.audio.lyrics.model.LyricLine> lines = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.audio.lyrics.model.LyricsSource source = null;
    
    public ParsedLyrics(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, @org.jetbrains.annotations.NotNull()
    java.lang.String album, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.lyrics.model.LyricLine> lines, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.lyrics.model.LyricsSource source) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getArtist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlbum() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.audio.lyrics.model.LyricLine> getLines() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.lyrics.model.LyricsSource getSource() {
        return null;
    }
    
    public final boolean isSync() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPlainText() {
        return null;
    }
    
    public ParsedLyrics() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.audio.lyrics.model.LyricLine> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.lyrics.model.LyricsSource component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.lyrics.model.ParsedLyrics copy(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, @org.jetbrains.annotations.NotNull()
    java.lang.String album, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.lyrics.model.LyricLine> lines, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.lyrics.model.LyricsSource source) {
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
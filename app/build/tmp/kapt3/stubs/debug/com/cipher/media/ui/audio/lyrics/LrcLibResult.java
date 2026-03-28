package com.cipher.media.ui.audio.lyrics;

import android.content.Context;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.ui.audio.lyrics.model.LyricLine;
import com.cipher.media.ui.audio.lyrics.model.LyricsSource;
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lcom/cipher/media/ui/audio/lyrics/LrcLibResult;", "", "trackName", "", "artistName", "albumName", "hasSyncedLyrics", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getAlbumName", "()Ljava/lang/String;", "getArtistName", "getHasSyncedLyrics", "()Z", "getTrackName", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class LrcLibResult {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String trackName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String artistName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String albumName = null;
    private final boolean hasSyncedLyrics = false;
    
    public LrcLibResult(@org.jetbrains.annotations.NotNull()
    java.lang.String trackName, @org.jetbrains.annotations.NotNull()
    java.lang.String artistName, @org.jetbrains.annotations.NotNull()
    java.lang.String albumName, boolean hasSyncedLyrics) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTrackName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getArtistName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlbumName() {
        return null;
    }
    
    public final boolean getHasSyncedLyrics() {
        return false;
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
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.lyrics.LrcLibResult copy(@org.jetbrains.annotations.NotNull()
    java.lang.String trackName, @org.jetbrains.annotations.NotNull()
    java.lang.String artistName, @org.jetbrains.annotations.NotNull()
    java.lang.String albumName, boolean hasSyncedLyrics) {
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
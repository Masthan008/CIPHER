package com.cipher.media.ui.video.subtitle.online;

import android.content.Context;
import kotlinx.coroutines.Dispatchers;
import org.json.JSONObject;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * OpenSubtitles REST API v1 client.
 * PRO FEATURE: Online subtitle search and download.
 *
 * Free account: 20 downloads/day
 * Rate limit: 40 req/10 seconds
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0002J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J&\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u0003H\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/online/OpenSubtitlesApi;", "", "apiKey", "", "(Ljava/lang/String;)V", "downloadToFile", "Ljava/io/File;", "context", "Landroid/content/Context;", "downloadUrl", "fileName", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "encode", "s", "getDownloadLink", "fileId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "", "Lcom/cipher/media/ui/video/subtitle/online/OnlineSubtitleResult;", "query", "language", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class OpenSubtitlesApi {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiKey = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "https://api.opensubtitles.com/api/v1";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_AGENT = "CIPHER Media Player v1.0";
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.subtitle.online.OpenSubtitlesApi.Companion Companion = null;
    
    public OpenSubtitlesApi(@org.jetbrains.annotations.NotNull()
    java.lang.String apiKey) {
        super();
    }
    
    /**
     * Search subtitles by query string and language.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object search(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult>> $completion) {
        return null;
    }
    
    /**
     * Get download link for a subtitle file.
     * Consumes one download from the daily quota.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDownloadLink(int fileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Download subtitle file from URL to cache directory.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object downloadToFile(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String downloadUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
    
    private final java.lang.String encode(java.lang.String s) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/online/OpenSubtitlesApi$Companion;", "", "()V", "BASE_URL", "", "USER_AGENT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
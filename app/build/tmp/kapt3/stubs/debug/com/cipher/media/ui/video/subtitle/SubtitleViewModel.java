package com.cipher.media.ui.video.subtitle;

import android.content.Context;
import android.net.Uri;
import android.provider.OpenableColumns;
import androidx.lifecycle.ViewModel;
import com.cipher.media.ui.video.subtitle.model.*;
import com.cipher.media.ui.video.subtitle.online.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

/**
 * ViewModel managing subtitle loading, sync, style, and cue lookup.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0012J\u0006\u0010\u0016\u001a\u00020\u0012J\u0016\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010 \u001a\u0004\u0018\u00010\u001d2\u0006\u0010!\u001a\u00020\u0014H\u0002J\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010#\u001a\u00020\u0012J\u000e\u0010$\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u0014J\u0006\u0010%\u001a\u00020\u0012J\u0018\u0010&\u001a\u00020\u00122\u0006\u0010\'\u001a\u00020\u001d2\b\b\u0002\u0010(\u001a\u00020\u001dJ\u000e\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+J\u0018\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020.\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u00100J\u000e\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u000203J\u000e\u00104\u001a\u00020\u00122\u0006\u00105\u001a\u00020\u001fJ\u000e\u00106\u001a\u00020\u00122\u0006\u00107\u001a\u000208J\u000e\u00109\u001a\u00020\u00122\u0006\u0010:\u001a\u00020;J\u0006\u0010<\u001a\u00020\u0012J\u0006\u0010=\u001a\u00020\u0012J\u000e\u0010>\u001a\u00020\u00122\u0006\u0010?\u001a\u00020;R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006@"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/SubtitleViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/subtitle/SubtitleUiState;", "cues", "", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleCue;", "onlineRepo", "Lcom/cipher/media/ui/video/subtitle/online/OnlineSubtitleRepository;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "autoLoadSubtitle", "", "videoUri", "Landroid/net/Uri;", "clearSearchResults", "clearSubtitle", "downloadAndLoad", "result", "Lcom/cipher/media/ui/video/subtitle/online/OnlineSubtitleResult;", "isPro", "", "getApiKey", "", "getCueCount", "", "getFileName", "uri", "getRemainingDownloads", "hideBottomSheet", "loadSubtitle", "resetSync", "searchOnline", "query", "language", "setBackgroundOpacity", "opacity", "", "setFontColor", "color", "Landroidx/compose/ui/graphics/Color;", "setFontColor-8_81llA", "(J)V", "setFontFamily", "font", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleFont;", "setFontSize", "size", "setPosition", "position", "Lcom/cipher/media/ui/video/subtitle/model/SubtitlePosition;", "setSyncOffset", "offsetMs", "", "showBottomSheet", "toggleVisibility", "updatePosition", "positionMs", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SubtitleViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.subtitle.SubtitleUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.subtitle.SubtitleUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> cues;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.subtitle.online.OnlineSubtitleRepository onlineRepo = null;
    
    @javax.inject.Inject()
    public SubtitleViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.subtitle.SubtitleUiState> getUiState() {
        return null;
    }
    
    private final java.lang.String getApiKey() {
        return null;
    }
    
    /**
     * Load subtitle from a content URI (file picker result).
     */
    public final void loadSubtitle(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    /**
     * Unload current subtitle.
     */
    public final void clearSubtitle() {
    }
    
    /**
     * Auto-load a subtitle with the same name as the video file if it exists.
     * Supports .srt, .vtt, and .ass extensions.
     */
    public final void autoLoadSubtitle(@org.jetbrains.annotations.NotNull()
    android.net.Uri videoUri) {
    }
    
    /**
     * Called every 100ms from the player to find and display the active cue.
     */
    public final void updatePosition(long positionMs) {
    }
    
    public final void setSyncOffset(long offsetMs) {
    }
    
    public final void resetSync() {
    }
    
    public final void toggleVisibility() {
    }
    
    public final void setFontSize(int size) {
    }
    
    public final void setPosition(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.model.SubtitlePosition position) {
    }
    
    public final void setBackgroundOpacity(float opacity) {
    }
    
    public final void setFontFamily(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.model.SubtitleFont font) {
    }
    
    public final void showBottomSheet() {
    }
    
    public final void hideBottomSheet() {
    }
    
    /**
     * Search for subtitles online using smart fallback (OpenSubtitles → Wyzie).
     */
    public final void searchOnline(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    /**
     * Download an online subtitle and load it into the player.
     */
    public final void downloadAndLoad(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult result, boolean isPro) {
    }
    
    /**
     * Get remaining downloads for today.
     */
    public final int getRemainingDownloads(boolean isPro) {
        return 0;
    }
    
    public final void clearSearchResults() {
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    public final int getCueCount() {
        return 0;
    }
}
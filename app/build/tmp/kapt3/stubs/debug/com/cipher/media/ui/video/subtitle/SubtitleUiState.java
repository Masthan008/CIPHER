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
 * State for subtitle display — track, cues, sync, and styling.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u008b\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0015J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\tH\u00c6\u0003J\t\u0010+\u001a\u00020\u000bH\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u008f\u0001\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u00101\u001a\u00020\u00032\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u000204H\u00d6\u0001J\t\u00105\u001a\u00020\u0007H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019R\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0019R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0019R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#\u00a8\u00066"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/SubtitleUiState;", "", "isLoaded", "", "track", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleTrack;", "currentCueText", "", "syncOffsetMs", "", "style", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleStyle;", "isVisible", "showBottomSheet", "onlineResults", "", "Lcom/cipher/media/ui/video/subtitle/online/OnlineSubtitleResult;", "isSearching", "searchError", "isDownloading", "downloadMessage", "(ZLcom/cipher/media/ui/video/subtitle/model/SubtitleTrack;Ljava/lang/String;JLcom/cipher/media/ui/video/subtitle/model/SubtitleStyle;ZZLjava/util/List;ZLjava/lang/String;ZLjava/lang/String;)V", "getCurrentCueText", "()Ljava/lang/String;", "getDownloadMessage", "()Z", "getOnlineResults", "()Ljava/util/List;", "getSearchError", "getShowBottomSheet", "getStyle", "()Lcom/cipher/media/ui/video/subtitle/model/SubtitleStyle;", "getSyncOffsetMs", "()J", "getTrack", "()Lcom/cipher/media/ui/video/subtitle/model/SubtitleTrack;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class SubtitleUiState {
    private final boolean isLoaded = false;
    @org.jetbrains.annotations.Nullable()
    private final com.cipher.media.ui.video.subtitle.model.SubtitleTrack track = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String currentCueText = null;
    private final long syncOffsetMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.subtitle.model.SubtitleStyle style = null;
    private final boolean isVisible = false;
    private final boolean showBottomSheet = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult> onlineResults = null;
    private final boolean isSearching = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String searchError = null;
    private final boolean isDownloading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String downloadMessage = null;
    
    public SubtitleUiState(boolean isLoaded, @org.jetbrains.annotations.Nullable()
    com.cipher.media.ui.video.subtitle.model.SubtitleTrack track, @org.jetbrains.annotations.Nullable()
    java.lang.String currentCueText, long syncOffsetMs, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.model.SubtitleStyle style, boolean isVisible, boolean showBottomSheet, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult> onlineResults, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String searchError, boolean isDownloading, @org.jetbrains.annotations.Nullable()
    java.lang.String downloadMessage) {
        super();
    }
    
    public final boolean isLoaded() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.ui.video.subtitle.model.SubtitleTrack getTrack() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCurrentCueText() {
        return null;
    }
    
    public final long getSyncOffsetMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.subtitle.model.SubtitleStyle getStyle() {
        return null;
    }
    
    public final boolean isVisible() {
        return false;
    }
    
    public final boolean getShowBottomSheet() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult> getOnlineResults() {
        return null;
    }
    
    public final boolean isSearching() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSearchError() {
        return null;
    }
    
    public final boolean isDownloading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDownloadMessage() {
        return null;
    }
    
    public SubtitleUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.ui.video.subtitle.model.SubtitleTrack component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.subtitle.model.SubtitleStyle component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult> component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.subtitle.SubtitleUiState copy(boolean isLoaded, @org.jetbrains.annotations.Nullable()
    com.cipher.media.ui.video.subtitle.model.SubtitleTrack track, @org.jetbrains.annotations.Nullable()
    java.lang.String currentCueText, long syncOffsetMs, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.model.SubtitleStyle style, boolean isVisible, boolean showBottomSheet, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.subtitle.online.OnlineSubtitleResult> onlineResults, boolean isSearching, @org.jetbrains.annotations.Nullable()
    java.lang.String searchError, boolean isDownloading, @org.jetbrains.annotations.Nullable()
    java.lang.String downloadMessage) {
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
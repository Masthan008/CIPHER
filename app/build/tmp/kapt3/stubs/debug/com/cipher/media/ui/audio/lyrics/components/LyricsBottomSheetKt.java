package com.cipher.media.ui.audio.lyrics.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.ui.audio.lyrics.model.LyricsSource;
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u0084\u0001\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0010H\u0007\u00a8\u0006\u0014"}, d2 = {"LyricsBottomSheet", "", "audio", "Lcom/cipher/media/data/model/AudioItem;", "lyrics", "Lcom/cipher/media/ui/audio/lyrics/model/ParsedLyrics;", "currentPositionMs", "", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "isSearching", "", "onDismiss", "Lkotlin/Function0;", "onDownload", "onPaste", "Lkotlin/Function1;", "", "onFullScreen", "onSeek", "app_debug"})
public final class LyricsBottomSheetKt {
    
    /**
     * Bottom sheet showing lyrics with download/paste options.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LyricsBottomSheet(@org.jetbrains.annotations.Nullable()
    com.cipher.media.data.model.AudioItem audio, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.lyrics.model.ParsedLyrics lyrics, long currentPositionMs, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier, boolean isSearching, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDownload, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPaste, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFullScreen, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek) {
    }
}
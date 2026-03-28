package com.cipher.media.ui.audio.lyrics.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.ui.audio.lyrics.AlbumColorExtractor;
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics;
import com.cipher.media.ui.audio.lyrics.model.VisualizerSettings;
import com.cipher.media.ui.audio.lyrics.model.VisualizerType;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001an\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001a:\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u00a8\u0006\u001a"}, d2 = {"LyricsFullScreen", "", "audio", "Lcom/cipher/media/data/model/AudioItem;", "lyrics", "Lcom/cipher/media/ui/audio/lyrics/model/ParsedLyrics;", "currentPositionMs", "", "fftData", "", "waveformData", "", "albumColors", "Lcom/cipher/media/ui/audio/lyrics/AlbumColorExtractor$AlbumColors;", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "onSeek", "Lkotlin/Function1;", "onDismiss", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "VisualizerSettingsSheet", "settings", "Lcom/cipher/media/ui/audio/lyrics/model/VisualizerSettings;", "onSettingsChange", "app_debug"})
public final class LyricsFullScreenKt {
    
    /**
     * Full-screen immersive lyrics mode with visualizer backdrop.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LyricsFullScreen(@org.jetbrains.annotations.Nullable()
    com.cipher.media.data.model.AudioItem audio, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.lyrics.model.ParsedLyrics lyrics, long currentPositionMs, @org.jetbrains.annotations.NotNull()
    float[] fftData, @org.jetbrains.annotations.NotNull()
    byte[] waveformData, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.lyrics.AlbumColorExtractor.AlbumColors albumColors, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeek, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void VisualizerSettingsSheet(com.cipher.media.ui.audio.lyrics.model.VisualizerSettings settings, com.cipher.media.data.model.audiofx.Tier userTier, kotlin.jvm.functions.Function1<? super com.cipher.media.ui.audio.lyrics.model.VisualizerSettings, kotlin.Unit> onSettingsChange, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}
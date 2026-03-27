package com.cipher.media.ui.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aV\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"SeekBar", "", "progress", "", "bufferedProgress", "onSeek", "Lkotlin/Function1;", "onSeekStarted", "Lkotlin/Function0;", "onSeekFinished", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class SeekBarKt {
    
    /**
     * Custom seek bar for the video player.
     *
     * @param progress Current playback position (0f..1f)
     * @param bufferedProgress Buffered position (0f..1f)
     * @param onSeek Called when user drags to a new position (0f..1f)
     * @param onSeekStarted Called when drag begins
     * @param onSeekFinished Called when drag ends
     */
    @androidx.compose.runtime.Composable()
    public static final void SeekBar(float progress, float bufferedProgress, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onSeek, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSeekStarted, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSeekFinished, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}
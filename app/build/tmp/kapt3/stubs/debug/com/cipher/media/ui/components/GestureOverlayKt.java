package com.cipher.media.ui.components;

import android.content.Context;
import android.media.AudioManager;
import android.view.Window;
import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.text.font.FontWeight;
import kotlinx.coroutines.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u00ac\u0001\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00052\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00032\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00052\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\u001a*\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001c"}, d2 = {"GestureOverlay", "", "onToggleControls", "Lkotlin/Function0;", "onSeekDelta", "Lkotlin/Function1;", "", "onBrightnessChange", "", "onVolumeChange", "onPlayPause", "onLongPressSpeed", "onZoomCycle", "isScreenLocked", "", "window", "Landroid/view/Window;", "modifier", "Landroidx/compose/ui/Modifier;", "VerticalIndicatorBar", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "percent", "", "color", "Landroidx/compose/ui/graphics/Color;", "VerticalIndicatorBar-mxwnekA", "(Landroidx/compose/ui/graphics/vector/ImageVector;IJ)V", "app_debug"})
public final class GestureOverlayKt {
    
    /**
     * Industry-leading gesture overlay for video player.
     *
     * Gesture Zones:
     * ┌────────┬──────────────┬────────┐
     * │ LEFT   │   CENTER     │ RIGHT  │
     * │ 33%    │    34%       │  33%   │
     * │        │              │        │
     * │Bright  │   Seek /     │ Volume │
     * │ drag   │  Play/Pause  │  drag  │
     * │        │              │        │
     * │Dbl-tap │  Dbl-tap     │Dbl-tap │
     * │ -10s   │ Play/Pause   │  +10s  │
     * └────────┴──────────────┴────────┘
     *
     * + Long press: 2x speed (release resets)
     * + Horizontal drag: Continuous seek with preview
     * + Pinch: Zoom (via aspect ratio cycling)
     */
    @androidx.compose.runtime.Composable()
    public static final void GestureOverlay(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleControls, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSeekDelta, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onBrightnessChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onVolumeChange, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPause, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onLongPressSpeed, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onZoomCycle, boolean isScreenLocked, @org.jetbrains.annotations.Nullable()
    android.view.Window window, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}
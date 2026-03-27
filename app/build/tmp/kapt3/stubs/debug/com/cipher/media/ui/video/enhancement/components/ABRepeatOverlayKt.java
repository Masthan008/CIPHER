package com.cipher.media.ui.video.enhancement.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.ui.theme.*;
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a*\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u0010"}, d2 = {"ABRepeatControls", "", "hasPointA", "", "hasPointB", "isLooping", "isPro", "viewModel", "Lcom/cipher/media/ui/video/enhancement/VideoEnhancementViewModel;", "modifier", "Landroidx/compose/ui/Modifier;", "ABRepeatMarkers", "pointA", "", "pointB", "duration", "app_debug"})
public final class ABRepeatOverlayKt {
    
    /**
     * A-B Repeat controls: floating buttons + seek bar markers.
     * PRO ONLY feature.
     */
    @androidx.compose.runtime.Composable()
    public static final void ABRepeatControls(boolean hasPointA, boolean hasPointB, boolean isLooping, boolean isPro, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel viewModel, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    /**
     * Visual markers rendered on the seek bar for A and B points.
     */
    @androidx.compose.runtime.Composable()
    public static final void ABRepeatMarkers(long pointA, long pointB, long duration, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}
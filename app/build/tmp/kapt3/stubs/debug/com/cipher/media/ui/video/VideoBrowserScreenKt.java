package com.cipher.media.ui.video;

import android.net.Uri;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.data.model.MediaItem;
import com.cipher.media.ui.components.*;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u00a8\u0006\n"}, d2 = {"VideoBrowserScreen", "", "onVideoClick", "Lkotlin/Function1;", "Lcom/cipher/media/data/model/MediaItem;", "onSearchClick", "Lkotlin/Function0;", "onSettingsClick", "viewModel", "Lcom/cipher/media/ui/video/VideoBrowserViewModel;", "app_debug"})
public final class VideoBrowserScreenKt {
    
    /**
     * Video browser: 2-column grid, FAB, empty state, shimmer loading.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoBrowserScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.data.model.MediaItem, kotlin.Unit> onVideoClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSearchClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSettingsClick, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.VideoBrowserViewModel viewModel) {
    }
}
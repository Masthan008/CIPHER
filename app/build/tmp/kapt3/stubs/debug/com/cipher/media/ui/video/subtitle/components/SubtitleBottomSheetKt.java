package com.cipher.media.ui.video.subtitle.components;

import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.ui.theme.*;
import com.cipher.media.ui.video.subtitle.SubtitleViewModel;
import com.cipher.media.ui.video.subtitle.model.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u0003\u001a(\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0006\u001a\u00020\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a8\u0006\u000f"}, d2 = {"SectionHeader", "", "title", "", "isFree", "", "isPro", "SubtitleBottomSheet", "viewModel", "Lcom/cipher/media/ui/video/subtitle/SubtitleViewModel;", "onDismiss", "Lkotlin/Function0;", "formatSyncOffset", "ms", "", "app_debug"})
public final class SubtitleBottomSheetKt {
    
    /**
     * Bottom sheet for subtitle controls: load, sync, style with Free/Pro gating.
     * @param isPro Whether user has Pro subscription
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SubtitleBottomSheet(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.SubtitleViewModel viewModel, boolean isPro, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    /**
     * Section header with optional Pro badge.
     */
    @androidx.compose.runtime.Composable()
    private static final void SectionHeader(java.lang.String title, boolean isFree, boolean isPro) {
    }
    
    private static final java.lang.String formatSyncOffset(long ms) {
        return null;
    }
}
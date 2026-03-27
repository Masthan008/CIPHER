package com.cipher.media.ui.audio.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aP\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00a8\u0006\u000b"}, d2 = {"QueueBottomSheet", "", "audioList", "", "Lcom/cipher/media/data/model/AudioItem;", "currentAudio", "onTrackClick", "Lkotlin/Function1;", "onClearQueue", "Lkotlin/Function0;", "onDismiss", "app_debug"})
public final class QueueBottomSheetKt {
    
    /**
     * FREE FEATURE: Queue management bottom sheet.
     * Shows the full playback queue with current track highlight,
     * and allows clear queue + tap-to-play.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void QueueBottomSheet(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> audioList, @org.jetbrains.annotations.Nullable()
    com.cipher.media.data.model.AudioItem currentAudio, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.data.model.AudioItem, kotlin.Unit> onTrackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClearQueue, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}
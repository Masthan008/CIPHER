package com.cipher.media.ui.audio.library.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.window.DialogProperties;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.data.model.library.MusicTag;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a4\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006\u0011"}, d2 = {"TagEditDialog", "", "tag", "Lcom/cipher/media/data/model/library/MusicTag;", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "onSave", "Lkotlin/Function1;", "onDismiss", "Lkotlin/Function0;", "TagField", "label", "", "value", "editable", "", "onValueChange", "app_debug"})
public final class TagEditDialogKt {
    
    /**
     * Full-screen tag editor dialog for viewing (Free) or editing (Pro) audio metadata.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TagEditDialog(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.MusicTag tag, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.data.model.library.MusicTag, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TagField(java.lang.String label, java.lang.String value, boolean editable, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange) {
    }
}
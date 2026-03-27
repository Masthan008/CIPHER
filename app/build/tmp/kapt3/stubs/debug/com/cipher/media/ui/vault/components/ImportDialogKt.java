package com.cipher.media.ui.vault.components;

import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aN\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u000326\u0010\u0004\u001a2\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\f"}, d2 = {"ImportDialog", "", "onDismiss", "Lkotlin/Function0;", "onImport", "Lkotlin/Function2;", "Landroid/net/Uri;", "Lkotlin/ParameterName;", "name", "uri", "", "deleteOriginal", "app_debug"})
public final class ImportDialogKt {
    
    /**
     * Dialog for importing files into the vault.
     * Offers Move (encrypt + delete original) or Copy (encrypt only).
     */
    @androidx.compose.runtime.Composable()
    public static final void ImportDialog(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super android.net.Uri, ? super java.lang.Boolean, kotlin.Unit> onImport) {
    }
}
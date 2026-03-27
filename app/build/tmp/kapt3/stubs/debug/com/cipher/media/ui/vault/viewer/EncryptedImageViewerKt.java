package com.cipher.media.ui.vault.viewer;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.WindowManager;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.cipher.media.data.model.VaultItem;
import com.cipher.media.ui.theme.*;
import com.cipher.media.ui.vault.VaultViewModel;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u00a8\u0006\t"}, d2 = {"EncryptedImageViewer", "", "item", "Lcom/cipher/media/data/model/VaultItem;", "viewModel", "Lcom/cipher/media/ui/vault/VaultViewModel;", "onBack", "Lkotlin/Function0;", "onDelete", "app_debug"})
public final class EncryptedImageViewerKt {
    
    /**
     * Decrypts and displays a vault image in-memory.
     * Supports pinch-to-zoom. FLAG_SECURE is set.
     * No file is written to disk — decoded directly from byte array.
     */
    @androidx.compose.runtime.Composable()
    public static final void EncryptedImageViewer(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.VaultItem item, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.vault.VaultViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
}
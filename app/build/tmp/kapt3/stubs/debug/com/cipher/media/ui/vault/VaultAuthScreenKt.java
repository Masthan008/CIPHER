package com.cipher.media.ui.vault;

import android.widget.Toast;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.fragment.app.FragmentActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.cipher.media.security.IntruderCameraManager;
import com.cipher.media.ui.components.*;
import com.cipher.media.ui.stealth.StealthViewModel;
import com.cipher.media.ui.theme.*;
import java.security.MessageDigest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a8\u0006\f"}, d2 = {"VaultAuthScreen", "", "onAuthenticated", "Lkotlin/Function0;", "onDecoyAuthenticated", "viewModel", "Lcom/cipher/media/ui/vault/VaultViewModel;", "stealthViewModel", "Lcom/cipher/media/ui/stealth/StealthViewModel;", "hashPin", "", "pin", "app_debug"})
public final class VaultAuthScreenKt {
    
    /**
     * Vault auth: circuit pattern bg, shield pulse, PIN pad, biometric toggle.
     * Now with proper PIN hash verification, decoy PIN routing,
     * biometric unlock, intruder selfie, and self-destruct.
     */
    @androidx.compose.runtime.Composable()
    public static final void VaultAuthScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAuthenticated, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDecoyAuthenticated, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.vault.VaultViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.stealth.StealthViewModel stealthViewModel) {
    }
    
    /**
     * SHA-256 hash of a PIN string
     */
    private static final java.lang.String hashPin(java.lang.String pin) {
        return null;
    }
}
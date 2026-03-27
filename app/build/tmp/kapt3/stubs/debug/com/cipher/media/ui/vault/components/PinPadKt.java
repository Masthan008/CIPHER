package com.cipher.media.ui.vault.components;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001aB\u0010\u0006\u001a\u00020\u00012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"AnimatedPinKey", "", "key", "", "onClick", "Lkotlin/Function0;", "PinPad", "onDigit", "Lkotlin/Function1;", "onBackspace", "onSubmit", "modifier", "Landroidx/compose/ui/Modifier;", "app_debug"})
public final class PinPadKt {
    
    /**
     * Premium 3x4 PIN pad with:
     * - Spring-physics scale on press (bouncy)
     * - Radial glow ripple emanating from touch point
     * - Staggered entrance animation for each key
     */
    @androidx.compose.runtime.Composable()
    public static final void PinPad(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Character, kotlin.Unit> onDigit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackspace, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSubmit, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AnimatedPinKey(char key, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}
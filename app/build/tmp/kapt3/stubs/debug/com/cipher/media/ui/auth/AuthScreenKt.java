package com.cipher.media.ui.auth;

import android.Manifest;
import android.os.Build;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.cipher.media.ui.theme.*;
import com.google.accompanist.permissions.ExperimentalPermissionsApi;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u00a8\u0006\u0004"}, d2 = {"AuthScreen", "", "onAuthSuccess", "Lkotlin/Function0;", "app_debug"})
public final class AuthScreenKt {
    
    /**
     * Permission request screen with gradient CTA.
     * Requests READ_MEDIA_VIDEO/AUDIO on API 33+, else READ_EXTERNAL_STORAGE.
     */
    @kotlin.OptIn(markerClass = {com.google.accompanist.permissions.ExperimentalPermissionsApi.class})
    @androidx.compose.runtime.Composable()
    public static final void AuthScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAuthSuccess) {
    }
}
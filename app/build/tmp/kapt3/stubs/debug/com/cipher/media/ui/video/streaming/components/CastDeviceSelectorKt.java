package com.cipher.media.ui.video.streaming.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.ui.theme.*;
import com.cipher.media.ui.video.streaming.dlna.DLNADevice;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u00b2\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u00a8\u0006\u0015"}, d2 = {"CastDeviceSelector", "", "isCasting", "", "castDeviceName", "", "hasCastDevices", "dlnaDevices", "", "Lcom/cipher/media/ui/video/streaming/dlna/DLNADevice;", "isDLNACasting", "dlnaCastDeviceName", "isPro", "onCastRequested", "Lkotlin/Function0;", "onDLNADeviceSelected", "Lkotlin/Function1;", "onStopCasting", "onStopDLNA", "onRefreshDLNA", "onDismiss", "app_debug"})
public final class CastDeviceSelectorKt {
    
    /**
     * Bottom sheet showing discovered Cast + DLNA devices
     * for the user to select a casting target.
     * PRO FEATURE: DLNA devices shown to Pro/Power users.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CastDeviceSelector(boolean isCasting, @org.jetbrains.annotations.Nullable()
    java.lang.String castDeviceName, boolean hasCastDevices, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.streaming.dlna.DLNADevice> dlnaDevices, boolean isDLNACasting, @org.jetbrains.annotations.Nullable()
    java.lang.String dlnaCastDeviceName, boolean isPro, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCastRequested, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.ui.video.streaming.dlna.DLNADevice, kotlin.Unit> onDLNADeviceSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopCasting, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopDLNA, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefreshDLNA, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}
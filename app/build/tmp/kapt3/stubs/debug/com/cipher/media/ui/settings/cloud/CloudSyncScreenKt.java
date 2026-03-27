package com.cipher.media.ui.settings.cloud;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.billing.model.SubscriptionTier;
import com.cipher.media.cloud.SyncStatus;
import com.cipher.media.cloud.VaultSyncEngine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0007\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0002\u00a8\u0006\u0012"}, d2 = {"CloudSyncScreen", "", "onNavigateBack", "Lkotlin/Function0;", "viewModel", "Lcom/cipher/media/ui/settings/cloud/CloudSyncViewModel;", "StorageCard", "tier", "Lcom/cipher/media/billing/model/SubscriptionTier;", "usedBytes", "", "totalBytes", "SyncStatusCard", "status", "Lcom/cipher/media/cloud/SyncStatus;", "formatBytes", "", "bytes", "app_debug"})
public final class CloudSyncScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CloudSyncScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.cloud.CloudSyncViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StorageCard(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.model.SubscriptionTier tier, long usedBytes, long totalBytes) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SyncStatusCard(@org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.SyncStatus status) {
    }
    
    private static final java.lang.String formatBytes(long bytes) {
        return null;
    }
}
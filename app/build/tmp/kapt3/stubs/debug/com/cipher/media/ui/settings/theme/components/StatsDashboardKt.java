package com.cipher.media.ui.settings.theme.components;

import android.content.Context;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0003\u001a\u001a\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a8\u0006\u000f"}, d2 = {"StatCard", "", "label", "", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "modifier", "Landroidx/compose/ui/Modifier;", "StatsDashboard", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "formatMinutes", "totalMin", "", "app_debug"})
public final class StatsDashboardKt {
    
    /**
     * Listening stats dashboard (Pro feature).
     * Tracks total listening time, songs played, most played artists.
     */
    @androidx.compose.runtime.Composable()
    public static final void StatsDashboard(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatCard(java.lang.String label, java.lang.String value, androidx.compose.ui.graphics.vector.ImageVector icon, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String formatMinutes(long totalMin) {
        return null;
    }
}
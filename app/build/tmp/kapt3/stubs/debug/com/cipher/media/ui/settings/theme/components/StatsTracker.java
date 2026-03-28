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

/**
 * Call this from AudioPlayerViewModel every time a song plays.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u00a8\u0006\u000b"}, d2 = {"Lcom/cipher/media/ui/settings/theme/components/StatsTracker;", "", "()V", "recordSongPlayed", "", "context", "Landroid/content/Context;", "durationMs", "", "artist", "", "app_debug"})
public final class StatsTracker {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.settings.theme.components.StatsTracker INSTANCE = null;
    
    private StatsTracker() {
        super();
    }
    
    public final void recordSongPlayed(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long durationMs, @org.jetbrains.annotations.NotNull()
    java.lang.String artist) {
    }
}
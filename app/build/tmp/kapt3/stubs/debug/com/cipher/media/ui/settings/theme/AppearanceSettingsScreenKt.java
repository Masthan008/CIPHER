package com.cipher.media.ui.settings.theme;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.ui.settings.theme.components.*;
import com.cipher.media.ui.settings.theme.model.*;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0080\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u001bH\u0007\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0006H\u0003\u001a>\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00142\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010#\u001a\u00020\u0014H\u0003\u00a8\u0006$"}, d2 = {"AppearanceSettingsScreen", "", "themes", "", "Lcom/cipher/media/ui/settings/theme/model/ThemeConfig;", "selectedThemeId", "", "selectedAccent", "Lcom/cipher/media/ui/settings/theme/model/AccentColor;", "selectedLayout", "Lcom/cipher/media/ui/settings/theme/model/NowPlayingLayout;", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "extendedSettings", "Lcom/cipher/media/ui/settings/theme/model/ExtendedSettings;", "onThemeSelected", "Lkotlin/Function1;", "onAccentSelected", "onLayoutSelected", "onGaplessChanged", "", "onSkipSilenceChanged", "onCrossfadeChanged", "onResumeHeadsetChanged", "onIncognitoChanged", "onMaterialYouChanged", "onBack", "Lkotlin/Function0;", "SectionHeader", "title", "SettingsSwitch", "label", "description", "checked", "onCheckedChange", "enabled", "app_debug"})
public final class AppearanceSettingsScreenKt {
    
    /**
     * Complete Appearance & Settings screen combining:
     * - Theme selector (10 themes)
     * - Accent color picker
     * - Now Playing layout
     * - Audio settings
     * - Backup/Restore
     * - Stats dashboard
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AppearanceSettingsScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.settings.theme.model.ThemeConfig> themes, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedThemeId, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.theme.model.AccentColor selectedAccent, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.theme.model.NowPlayingLayout selectedLayout, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.theme.model.ExtendedSettings extendedSettings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.ui.settings.theme.model.ThemeConfig, kotlin.Unit> onThemeSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.ui.settings.theme.model.AccentColor, kotlin.Unit> onAccentSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cipher.media.ui.settings.theme.model.NowPlayingLayout, kotlin.Unit> onLayoutSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onGaplessChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onSkipSilenceChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCrossfadeChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onResumeHeadsetChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onIncognitoChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onMaterialYouChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SectionHeader(java.lang.String title) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsSwitch(java.lang.String label, java.lang.String description, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange, boolean enabled) {
    }
}
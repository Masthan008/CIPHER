package com.cipher.media.ui.settings.theme;

import android.content.Context;
import androidx.compose.material3.ColorScheme;
import com.cipher.media.ui.settings.theme.model.AccentColor;
import com.cipher.media.ui.settings.theme.model.ThemeConfig;
import com.cipher.media.ui.settings.theme.model.ThemeTier;
import com.cipher.media.ui.theme.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Central theme manager that builds ColorScheme from ThemeConfig + accent color.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0010J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/cipher/media/ui/settings/theme/ThemeManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "themes", "", "Lcom/cipher/media/ui/settings/theme/model/ThemeConfig;", "getThemes", "()Ljava/util/List;", "buildColorScheme", "Landroidx/compose/material3/ColorScheme;", "themeId", "", "accent", "Lcom/cipher/media/ui/settings/theme/model/AccentColor;", "getSavedAccent", "getSavedThemeId", "saveAccent", "", "saveTheme", "app_debug"})
public final class ThemeManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    
    /**
     * All available themes
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.ui.settings.theme.model.ThemeConfig> themes = null;
    
    @javax.inject.Inject()
    public ThemeManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * All available themes
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.settings.theme.model.ThemeConfig> getThemes() {
        return null;
    }
    
    /**
     * Build a Material3 ColorScheme from theme + accent
     */
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.material3.ColorScheme buildColorScheme(@org.jetbrains.annotations.NotNull()
    java.lang.String themeId, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.theme.model.AccentColor accent) {
        return null;
    }
    
    public final void saveTheme(@org.jetbrains.annotations.NotNull()
    java.lang.String themeId) {
    }
    
    public final void saveAccent(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.theme.model.AccentColor accent) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSavedThemeId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.theme.model.AccentColor getSavedAccent() {
        return null;
    }
}
package com.cipher.media.ui.settings;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Modern language manager using AppCompatDelegate.setApplicationLocales().
 * Works on Android 13+ (per-app language) and below (activity recreation).
 *
 * BUG FIX: Replaces old Locale.setDefault() approach that didn't persist.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/cipher/media/ui/settings/LanguageManager;", "", "context", "Landroid/content/Context;", "repo", "Lcom/cipher/media/ui/settings/SettingsRepository;", "(Landroid/content/Context;Lcom/cipher/media/ui/settings/SettingsRepository;)V", "applyOnStartup", "", "getCurrentCode", "", "getCurrentDisplayName", "setLanguage", "", "code", "Companion", "app_debug"})
public final class LanguageManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.settings.SettingsRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> SUPPORTED_LANGUAGES = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.settings.LanguageManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public LanguageManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.SettingsRepository repo) {
        super();
    }
    
    /**
     * Apply language change using the modern AndroidX API.
     * - On Android 13+: uses per-app language settings (no restart needed)
     * - On Android <13: triggers activity recreation automatically
     *
     * @param code BCP-47 language code (e.g., "hi", "ta", "en")
     * @return true if restart dialog should be shown (Android <13)
     */
    public final boolean setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentDisplayName() {
        return null;
    }
    
    /**
     * Called from Application.onCreate() to re-apply saved locale on cold start.
     * Critical for Android <13 where the system doesn't persist per-app locale.
     */
    public final void applyOnStartup() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006R#\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/cipher/media/ui/settings/LanguageManager$Companion;", "", "()V", "SUPPORTED_LANGUAGES", "", "Lkotlin/Pair;", "", "getSUPPORTED_LANGUAGES", "()Ljava/util/List;", "codeToDisplayName", "code", "displayNameToCode", "displayName", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getSUPPORTED_LANGUAGES() {
            return null;
        }
        
        /**
         * Convert old display name (English/हिन्दी) to BCP-47 code
         */
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String displayNameToCode(@org.jetbrains.annotations.NotNull()
        java.lang.String displayName) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String codeToDisplayName(@org.jetbrains.annotations.NotNull()
        java.lang.String code) {
            return null;
        }
    }
}
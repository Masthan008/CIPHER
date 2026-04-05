package com.cipher.media.ui.settings

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Modern language manager using AppCompatDelegate.setApplicationLocales().
 * Works on Android 13+ (per-app language) and below (activity recreation).
 *
 * BUG FIX: Replaces old Locale.setDefault() approach that didn't persist.
 */
@Singleton
class LanguageManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: SettingsRepository
) {
    companion object {
        val SUPPORTED_LANGUAGES = listOf(
            "en" to "English",
            "hi" to "हिन्दी",
            "ta" to "தமிழ்",
            "te" to "తెలుగు",
            "mr" to "मराठी",
            "bn" to "বাংলা",
            "gu" to "ગુજરાતી",
            "kn" to "ಕನ್ನಡ",
            "ml" to "മലയാളം",
            "pa" to "ਪੰਜਾਬੀ",
            "ur" to "اردو",
            "or" to "ଓଡ଼ିଆ"
        )

        /** Convert old display name (English/हिन्दी) to BCP-47 code */
        fun displayNameToCode(displayName: String): String {
            return SUPPORTED_LANGUAGES.find { it.second == displayName }?.first ?: "en"
        }

        fun codeToDisplayName(code: String): String {
            return SUPPORTED_LANGUAGES.find { it.first == code }?.second ?: "English"
        }
    }

    /**
     * Apply language change using the modern AndroidX API.
     * - On Android 13+: uses per-app language settings (no restart needed)
     * - On Android <13: triggers activity recreation automatically
     *
     * @param code BCP-47 language code (e.g., "hi", "ta", "en")
     * @return true if restart dialog should be shown (Android <13)
     */
    fun setLanguage(code: String): Boolean {
        repo.languageCode = code

        val localeList = LocaleListCompat.forLanguageTags(code)
        AppCompatDelegate.setApplicationLocales(localeList)

        Log.d("LanguageManager", "Language set to: $code")

        // On Android <33, the call above triggers recreation.
        // On 33+, it's handled by the system with no restart needed.
        return android.os.Build.VERSION.SDK_INT < 33
    }

    fun getCurrentCode(): String = repo.languageCode

    fun getCurrentDisplayName(): String = codeToDisplayName(repo.languageCode)

    /**
     * Called from Application.onCreate() to re-apply saved locale on cold start.
     * Critical for Android <13 where the system doesn't persist per-app locale.
     */
    fun applyOnStartup() {
        val code = repo.languageCode
        if (code != "en") {
            val localeList = LocaleListCompat.forLanguageTags(code)
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }
}

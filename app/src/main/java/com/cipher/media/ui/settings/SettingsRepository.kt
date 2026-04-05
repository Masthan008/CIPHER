package com.cipher.media.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Type-safe settings repository with proper SharedPreferences handling.
 * All keys are constants, all reads have defaults, all writes use .apply().
 * Provides reactive StateFlow for UI observation.
 */
@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(load())
    val state: StateFlow<FullSettings> = _state.asStateFlow()

    // ── Generic accessors with proper .apply() ──

    private fun saveString(key: String, value: String) {
        prefs.edit { putString(key, value) }
        reload()
    }

    private fun saveBool(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
        reload()
    }

    private fun saveInt(key: String, value: Int) {
        prefs.edit { putInt(key, value) }
        reload()
    }

    private fun saveFloat(key: String, value: Float) {
        prefs.edit { putFloat(key, value) }
        reload()
    }

    private fun saveLong(key: String, value: Long) {
        prefs.edit { putLong(key, value) }
        reload()
    }

    fun reload() {
        _state.value = load()
    }

    // ── Appearance ──
    var themeId: String
        get() = prefs.getString(KEY_THEME, "dark") ?: "dark"
        set(v) = saveString(KEY_THEME, v)

    var accentColorName: String
        get() = prefs.getString(KEY_ACCENT, "SAFFRON") ?: "SAFFRON"
        set(v) = saveString(KEY_ACCENT, v)

    var materialYou: Boolean
        get() = prefs.getBoolean(KEY_MATERIAL_YOU, false)
        set(v) = saveBool(KEY_MATERIAL_YOU, v)

    var nowPlayingLayout: String
        get() = prefs.getString(KEY_NOW_PLAYING, "STANDARD") ?: "STANDARD"
        set(v) = saveString(KEY_NOW_PLAYING, v)

    // ── Language ──
    var languageCode: String
        get() = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        set(v) = saveString(KEY_LANGUAGE, v)

    // ── General ──
    var defaultTab: Int
        get() = prefs.getInt(KEY_DEFAULT_TAB, 0)
        set(v) = saveInt(KEY_DEFAULT_TAB, v)

    var biometricEnabled: Boolean
        get() = prefs.getBoolean(KEY_BIOMETRIC, true)
        set(v) = saveBool(KEY_BIOMETRIC, v)

    var autoLockMinutes: Int
        get() = prefs.getInt(KEY_AUTO_LOCK, 5)
        set(v) = saveInt(KEY_AUTO_LOCK, v)

    var autoRotate: Boolean
        get() = prefs.getBoolean(KEY_AUTO_ROTATE, true)
        set(v) = saveBool(KEY_AUTO_ROTATE, v)

    var keepScreenOn: Boolean
        get() = prefs.getBoolean(KEY_KEEP_SCREEN_ON, true)
        set(v) = saveBool(KEY_KEEP_SCREEN_ON, v)

    var doubleTapSeekSec: Int
        get() = prefs.getInt(KEY_DOUBLE_TAP_SEEK, 10)
        set(v) = saveInt(KEY_DOUBLE_TAP_SEEK, v)

    var defaultSpeed: Float
        get() = prefs.getFloat(KEY_DEFAULT_SPEED, 1.0f)
        set(v) = saveFloat(KEY_DEFAULT_SPEED, v)

    var resumePlayback: Boolean
        get() = prefs.getBoolean(KEY_RESUME_PLAYBACK, true)
        set(v) = saveBool(KEY_RESUME_PLAYBACK, v)

    var subtitleSize: String
        get() = prefs.getString(KEY_SUBTITLE_SIZE, "Medium") ?: "Medium"
        set(v) = saveString(KEY_SUBTITLE_SIZE, v)

    // ── PRO Playback ──
    var hiResAudio: Boolean
        get() = prefs.getBoolean(KEY_HIRES, false)
        set(v) = saveBool(KEY_HIRES, v)

    var crossfadeSec: Int
        get() = prefs.getInt(KEY_CROSSFADE, 0)
        set(v) = saveInt(KEY_CROSSFADE, v)

    var gapless: Boolean
        get() = prefs.getBoolean(KEY_GAPLESS, true)
        set(v) = saveBool(KEY_GAPLESS, v)

    var eqPreset: String
        get() = prefs.getString(KEY_EQ_PRESET, "flat") ?: "flat"
        set(v) = saveString(KEY_EQ_PRESET, v)

    var autoDownloadSubs: Boolean
        get() = prefs.getBoolean(KEY_AUTO_SUBS, false)
        set(v) = saveBool(KEY_AUTO_SUBS, v)

    var cloudSync: Boolean
        get() = prefs.getBoolean(KEY_CLOUD_SYNC, false)
        set(v) = saveBool(KEY_CLOUD_SYNC, v)

    // ── POWER ──
    var enable4K: Boolean
        get() = prefs.getBoolean(KEY_4K, false)
        set(v) = saveBool(KEY_4K, v)

    var enableHDR: Boolean
        get() = prefs.getBoolean(KEY_HDR, false)
        set(v) = saveBool(KEY_HDR, v)

    var stealthMode: Boolean
        get() = prefs.getBoolean(KEY_STEALTH, false)
        set(v) = saveBool(KEY_STEALTH, v)

    var decoyPin: Boolean
        get() = prefs.getBoolean(KEY_DECOY_PIN, false)
        set(v) = saveBool(KEY_DECOY_PIN, v)

    var selfDestructAttempts: Int
        get() = prefs.getInt(KEY_SELF_DESTRUCT, 10)
        set(v) = saveInt(KEY_SELF_DESTRUCT, v)

    var incognitoMode: Boolean
        get() = prefs.getBoolean(KEY_INCOGNITO, false)
        set(v) = saveBool(KEY_INCOGNITO, v)

    // ── Tier ──
    var userTier: String
        get() = prefs.getString(KEY_TIER, "FREE") ?: "FREE"
        set(v) = saveString(KEY_TIER, v)

    val isPro: Boolean get() = userTier != "FREE"
    val isPower: Boolean get() = userTier == "POWER"

    // ── Storage ──
    fun getCacheSize(): Long = context.cacheDir.walkTopDown().sumOf { it.length() }

    fun getVaultSize(): Long {
        val dir = java.io.File(context.filesDir, "vault")
        return if (dir.exists()) dir.walkTopDown().sumOf { it.length() } else 0L
    }

    fun clearCache() {
        context.cacheDir.listFiles()?.forEach { it.deleteRecursively() }
        reload()
    }

    // ── Migration from v1 (display-name language → BCP-47 code) ──
    fun migrateIfNeeded() {
        val version = prefs.getInt(KEY_VERSION, 1)
        if (version < 2) {
            // Migrate old display-name languages to BCP-47 codes
            val oldLang = prefs.getString("language", null)
            if (oldLang != null && oldLang.length > 3) {
                val code = LanguageManager.displayNameToCode(oldLang)
                prefs.edit {
                    putString(KEY_LANGUAGE, code)
                    remove("language") // remove old key
                    putInt(KEY_VERSION, 2)
                }
                Log.d("SettingsRepo", "Migrated language '$oldLang' → '$code'")
            } else {
                prefs.edit { putInt(KEY_VERSION, 2) }
            }
            reload()
        }
    }

    // ── Snapshot for UI ──
    private fun load(): FullSettings = FullSettings(
        themeId = themeId,
        accentColorName = accentColorName,
        materialYou = materialYou,
        nowPlayingLayout = nowPlayingLayout,
        languageCode = languageCode,
        defaultTab = defaultTab,
        biometricEnabled = biometricEnabled,
        autoLockMinutes = autoLockMinutes,
        autoRotate = autoRotate,
        keepScreenOn = keepScreenOn,
        doubleTapSeekSec = doubleTapSeekSec,
        defaultSpeed = defaultSpeed,
        resumePlayback = resumePlayback,
        subtitleSize = subtitleSize,
        hiResAudio = hiResAudio,
        crossfadeSec = crossfadeSec,
        gapless = gapless,
        eqPreset = eqPreset,
        autoDownloadSubs = autoDownloadSubs,
        cloudSync = cloudSync,
        enable4K = enable4K,
        enableHDR = enableHDR,
        stealthMode = stealthMode,
        decoyPin = decoyPin,
        selfDestructAttempts = selfDestructAttempts,
        incognitoMode = incognitoMode,
        userTier = userTier,
        cacheSize = 0L, // computed lazily
        vaultSize = 0L
    )

    companion object {
        const val PREFS_NAME = "cipher_settings_v2"
        const val KEY_VERSION = "settings_version"

        // Appearance
        const val KEY_THEME = "theme_id"
        const val KEY_ACCENT = "accent_color"
        const val KEY_MATERIAL_YOU = "material_you"
        const val KEY_NOW_PLAYING = "now_playing_layout"

        // Language
        const val KEY_LANGUAGE = "language_code"

        // General
        const val KEY_DEFAULT_TAB = "default_tab"
        const val KEY_BIOMETRIC = "biometric_enabled"
        const val KEY_AUTO_LOCK = "auto_lock_minutes"
        const val KEY_AUTO_ROTATE = "auto_rotate"
        const val KEY_KEEP_SCREEN_ON = "keep_screen_on"
        const val KEY_DOUBLE_TAP_SEEK = "double_tap_seek_sec"
        const val KEY_DEFAULT_SPEED = "default_speed"
        const val KEY_RESUME_PLAYBACK = "resume_playback"
        const val KEY_SUBTITLE_SIZE = "subtitle_size"

        // PRO
        const val KEY_HIRES = "hi_res_audio"
        const val KEY_CROSSFADE = "crossfade_sec"
        const val KEY_GAPLESS = "gapless_enabled"
        const val KEY_EQ_PRESET = "eq_preset"
        const val KEY_AUTO_SUBS = "auto_download_subs"
        const val KEY_CLOUD_SYNC = "cloud_sync"

        // POWER
        const val KEY_4K = "enable_4k"
        const val KEY_HDR = "enable_hdr"
        const val KEY_STEALTH = "stealth_mode"
        const val KEY_DECOY_PIN = "decoy_pin"
        const val KEY_SELF_DESTRUCT = "self_destruct_attempts"
        const val KEY_INCOGNITO = "incognito_mode"

        const val KEY_TIER = "user_tier"
    }
}

/**
 * Immutable snapshot of all settings for Compose observation.
 */
data class FullSettings(
    // Appearance
    val themeId: String = "dark",
    val accentColorName: String = "SAFFRON",
    val materialYou: Boolean = false,
    val nowPlayingLayout: String = "STANDARD",
    // Language
    val languageCode: String = "en",
    // General
    val defaultTab: Int = 0,
    val biometricEnabled: Boolean = true,
    val autoLockMinutes: Int = 5,
    val autoRotate: Boolean = true,
    val keepScreenOn: Boolean = true,
    val doubleTapSeekSec: Int = 10,
    val defaultSpeed: Float = 1.0f,
    val resumePlayback: Boolean = true,
    val subtitleSize: String = "Medium",
    // PRO
    val hiResAudio: Boolean = false,
    val crossfadeSec: Int = 0,
    val gapless: Boolean = true,
    val eqPreset: String = "flat",
    val autoDownloadSubs: Boolean = false,
    val cloudSync: Boolean = false,
    // POWER
    val enable4K: Boolean = false,
    val enableHDR: Boolean = false,
    val stealthMode: Boolean = false,
    val decoyPin: Boolean = false,
    val selfDestructAttempts: Int = 10,
    val incognitoMode: Boolean = false,
    // Meta
    val userTier: String = "FREE",
    val cacheSize: Long = 0L,
    val vaultSize: Long = 0L
) {
    val isPro get() = userTier != "FREE"
    val isPower get() = userTier == "POWER"
}

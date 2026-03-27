package com.cipher.media.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ThemeMode { DARK, LIGHT, SYSTEM }
enum class AutoLockDuration { ONE_MIN, FIVE_MIN, NEVER }

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val defaultTab: Int = 0, // 0=Video, 1=Music, 2=Vault
    val autoLock: AutoLockDuration = AutoLockDuration.FIVE_MIN,
    val biometricEveryTime: Boolean = true,
    val cacheSize: Long = 0L,
    val vaultSize: Long = 0L,
    val language: String = "English",
    val autoRotate: Boolean = true,
    val playbackSpeed: String = "1x",
    val subtitleSize: String = "Medium"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val prefs = context.getSharedPreferences("cipher_settings", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    fun setTheme(mode: ThemeMode) {
        prefs.edit().putString("theme", mode.name).apply()
        _settings.update { it.copy(themeMode = mode) }
    }

    fun setDefaultTab(tab: Int) {
        prefs.edit().putInt("default_tab", tab).apply()
        _settings.update { it.copy(defaultTab = tab) }
    }

    fun setAutoLock(duration: AutoLockDuration) {
        prefs.edit().putString("auto_lock", duration.name).apply()
        _settings.update { it.copy(autoLock = duration) }
    }

    fun setBiometricMode(everyTime: Boolean) {
        prefs.edit().putBoolean("biometric_every_time", everyTime).apply()
        _settings.update { it.copy(biometricEveryTime = everyTime) }
    }

    fun setLanguage(language: String) {
        prefs.edit().putString("language", language).apply()
        _settings.update { it.copy(language = language) }

        // Map display name to BCP-47 locale tag and apply system-wide
        val localeTag = when (language) {
            "हिन्दी" -> "hi"
            "தமிழ்" -> "ta"
            "తెలుగు" -> "te"
            "मराठी" -> "mr"
            "বাংলা" -> "bn"
            "ગુજરાતી" -> "gu"
            "ಕನ್ನಡ" -> "kn"
            "മലയാളം" -> "ml"
            "ਪੰਜਾਬੀ" -> "pa"
            "اردو" -> "ur"
            else -> "en"
        }
        val appLocale = LocaleListCompat.forLanguageTags(localeTag)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun setAutoRotate(enabled: Boolean) {
        prefs.edit().putBoolean("auto_rotate", enabled).apply()
        _settings.update { it.copy(autoRotate = enabled) }
    }

    fun setPlaybackSpeed(speed: String) {
        prefs.edit().putString("playback_speed", speed).apply()
        _settings.update { it.copy(playbackSpeed = speed) }
    }

    fun setSubtitleSize(size: String) {
        prefs.edit().putString("subtitle_size", size).apply()
        _settings.update { it.copy(subtitleSize = size) }
    }

    fun clearCache() {
        viewModelScope.launch {
            context.cacheDir.listFiles()?.forEach { it.deleteRecursively() }
            _settings.update { it.copy(cacheSize = 0L) }
        }
    }

    fun refreshStorageInfo() {
        viewModelScope.launch {
            val cacheSize = context.cacheDir.walkTopDown().sumOf { it.length() }
            val vaultDir = java.io.File(context.filesDir, "vault")
            val vaultSize = if (vaultDir.exists()) vaultDir.walkTopDown().sumOf { it.length() } else 0L
            _settings.update { it.copy(cacheSize = cacheSize, vaultSize = vaultSize) }
        }
    }

    private fun loadSettings(): AppSettings {
        return AppSettings(
            themeMode = try { ThemeMode.valueOf(prefs.getString("theme", "DARK") ?: "DARK") } catch (_: Exception) { ThemeMode.DARK },
            defaultTab = prefs.getInt("default_tab", 0),
            autoLock = try { AutoLockDuration.valueOf(prefs.getString("auto_lock", "FIVE_MIN") ?: "FIVE_MIN") } catch (_: Exception) { AutoLockDuration.FIVE_MIN },
            biometricEveryTime = prefs.getBoolean("biometric_every_time", true),
            language = prefs.getString("language", "English") ?: "English",
            autoRotate = prefs.getBoolean("auto_rotate", true),
            playbackSpeed = prefs.getString("playback_speed", "1x") ?: "1x",
            subtitleSize = prefs.getString("subtitle_size", "Medium") ?: "Medium"
        )
    }
}

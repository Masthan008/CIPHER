package com.cipher.media.ui.settings

import android.content.Context
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
    val vaultSize: Long = 0L
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
            biometricEveryTime = prefs.getBoolean("biometric_every_time", true)
        )
    }
}

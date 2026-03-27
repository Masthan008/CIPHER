package com.cipher.media.security

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages automatic vault locking via:
 * - Configurable inactivity timer (1-60 min)
 * - Screen-off instant lock
 *
 * PRO TIER feature.
 */
@Singleton
class AutoLockManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("cipher_autolock_prefs", Context.MODE_PRIVATE)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _isVaultLocked = MutableStateFlow(true)
    val isVaultLocked: StateFlow<Boolean> = _isVaultLocked.asStateFlow()

    private var timerJob: Job? = null
    private var screenOffReceiver: BroadcastReceiver? = null

    /** Whether auto-lock is enabled */
    var isEnabled: Boolean
        get() = prefs.getBoolean("autolock_enabled", false)
        set(value) {
            prefs.edit().putBoolean("autolock_enabled", value).apply()
            if (value) startTimer() else stopTimer()
        }

    /** Auto-lock delay in minutes (1-60) */
    var delayMinutes: Int
        get() = prefs.getInt("autolock_delay_minutes", 5)
        set(value) {
            prefs.edit().putInt("autolock_delay_minutes", value.coerceIn(1, 60)).apply()
        }

    /** Whether to lock on screen off */
    var lockOnScreenOff: Boolean
        get() = prefs.getBoolean("lock_on_screen_off", true)
        set(value) {
            prefs.edit().putBoolean("lock_on_screen_off", value).apply()
            if (value) registerScreenOffReceiver() else unregisterScreenOffReceiver()
        }

    /**
     * Call when vault is successfully unlocked.
     */
    fun onVaultUnlocked() {
        _isVaultLocked.value = false
        if (isEnabled) startTimer()
        if (lockOnScreenOff) registerScreenOffReceiver()
    }

    /**
     * Call to immediately lock the vault.
     */
    fun lockVault() {
        _isVaultLocked.value = true
        stopTimer()
        unregisterScreenOffReceiver()
    }

    /**
     * Resets the inactivity timer (call on user interaction).
     */
    fun resetTimer() {
        if (isEnabled && !_isVaultLocked.value) {
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = scope.launch {
            delay(delayMinutes * 60 * 1000L)
            lockVault()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun registerScreenOffReceiver() {
        if (screenOffReceiver != null) return
        screenOffReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                    lockVault()
                }
            }
        }
        context.registerReceiver(screenOffReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    private fun unregisterScreenOffReceiver() {
        screenOffReceiver?.let {
            try { context.unregisterReceiver(it) } catch (_: Exception) { }
            screenOffReceiver = null
        }
    }
}

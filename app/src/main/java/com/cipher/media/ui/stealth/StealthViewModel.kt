package com.cipher.media.ui.stealth

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.local.IntruderLogDao
import com.cipher.media.data.local.IntruderLogEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StealthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val intruderLogDao: IntruderLogDao
) : ViewModel() {

    private val prefs = context.getSharedPreferences("cipher_stealth_prefs", Context.MODE_PRIVATE)

    val intruderLogs = intruderLogDao.getAllLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val logCount = intruderLogDao.getLogCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // -- Stealth Prefs --
    val isStealthEnabled: Boolean
        get() = prefs.getBoolean("stealth_enabled", false)

    val secretCode: String
        get() = prefs.getString("secret_code", "1234") ?: "1234"

    val decoyPinHash: String?
        get() = prefs.getString("decoy_pin_hash", null)

    val isIntruderDetectionEnabled: Boolean
        get() = prefs.getBoolean("intruder_detection", true)

    val selfDestructAttempts: Int
        get() = prefs.getInt("self_destruct_attempts", 10)

    val isSelfDestructEnabled: Boolean
        get() = prefs.getBoolean("self_destruct_enabled", false)

    fun setStealthEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("stealth_enabled", enabled).apply()
        toggleCalculatorAlias(enabled)
    }

    fun setSecretCode(code: String) {
        prefs.edit().putString("secret_code", code).apply()
    }

    fun setDecoyPin(pinHash: String?) {
        prefs.edit().putString("decoy_pin_hash", pinHash).apply()
    }

    fun setIntruderDetection(enabled: Boolean) {
        prefs.edit().putBoolean("intruder_detection", enabled).apply()
    }

    fun setSelfDestruct(enabled: Boolean, attempts: Int = 10) {
        prefs.edit()
            .putBoolean("self_destruct_enabled", enabled)
            .putInt("self_destruct_attempts", attempts)
            .apply()
    }

    /**
     * Toggles between real app icon and calculator alias.
     */
    private fun toggleCalculatorAlias(showCalculator: Boolean) {
        val pm = context.packageManager
        val mainComponent = ComponentName(context, "com.cipher.media.MainActivity")
        val calcComponent = ComponentName(context, "com.cipher.media.CalculatorAlias")

        if (showCalculator) {
            pm.setComponentEnabledSetting(mainComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
            pm.setComponentEnabledSetting(calcComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
        } else {
            pm.setComponentEnabledSetting(calcComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
            pm.setComponentEnabledSetting(mainComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
        }
    }

    /**
     * Logs a break-in attempt with GPS location.
     * Camera capture is handled separately via CameraX.
     */
    fun logIntruderAttempt(attemptCount: Int, photoPath: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            var lat: Double? = null
            var lon: Double? = null

            try {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lat = location?.latitude
                lon = location?.longitude
            } catch (_: SecurityException) {
                // Permission not granted
            }

            intruderLogDao.insertLog(
                IntruderLogEntity(
                    id = UUID.randomUUID().toString(),
                    photoPath = photoPath,
                    latitude = lat,
                    longitude = lon,
                    timestamp = System.currentTimeMillis(),
                    attemptCount = attemptCount,
                    pinEntered = ""
                )
            )
        }
    }

    fun clearLogs() {
        viewModelScope.launch { intruderLogDao.clearAllLogs() }
    }
}

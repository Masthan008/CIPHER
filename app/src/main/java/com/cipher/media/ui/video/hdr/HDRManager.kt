package com.cipher.media.ui.video.hdr

import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.media3.common.Format
import androidx.media3.common.util.UnstableApi
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature 2: HDR10/Dolby Vision Support (₹99/month)
 * Detects and configures HDR playback for Pro users
 */
@Singleton
@OptIn(UnstableApi::class)
class HDRManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val proFeatureGate: ProFeatureGate
) {
    private var currentTier: Tier = Tier.FREE
    private var window: Window? = null

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    fun setWindow(window: Window) {
        this.window = window
    }

    fun configureHDR(format: Format) {
        if (!proFeatureGate.checkAccess(currentTier)) {
            Log.d("HDRManager", "HDR disabled - requires Pro tier")
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val hdrType = detectHDRType(format)

            if (hdrType != HDRType.SDR) {
                // CRITICAL FIX: Check window and attributes before modification
                val currentWindow = window ?: run {
                    Log.w("HDRManager", "Window is null, cannot configure HDR")
                    return
                }
                
                val currentAttributes = currentWindow.attributes ?: run {
                    Log.w("HDRManager", "Window attributes are null, cannot configure HDR")
                    return
                }

                // HDR mode configuration with colorMode and brightness in single operation
                try {
                    val field = WindowManager.LayoutParams::class.java.getField("COLOR_MODE_HDR")
                    val colorModeHdr = field.getInt(null)
                    
                    currentAttributes.colorMode = colorModeHdr
                    currentAttributes.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
                    currentWindow.attributes = currentAttributes
                    
                    Log.d("HDRManager", "Enabled ${hdrType.name}")
                } catch (e: Exception) {
                    // COLOR_MODE_HDR not available on this device
                    Log.w("HDRManager", "Failed to enable HDR mode", e)
                }
            }
        }
    }

    fun detectHDRType(format: Format): HDRType {
        // Check for HDR through sample mime type and codecs since colorTransfer may not be available
        val sampleMimeType = format.sampleMimeType?.lowercase() ?: ""
        val codecs = format.codecs?.lowercase() ?: ""
        
        // Dolby Vision detection
        if (codecs.contains("dv") || codecs.contains("dvh") || sampleMimeType.contains("dolby")) {
            return HDRType.DOLBY_VISION
        }
        
        // HDR10 detection through common HDR mime types
        if (sampleMimeType.contains("hdr") || 
            sampleMimeType.contains("pq") ||
            codecs.contains("hdr10") ||
            codecs.contains("hlg")) {
            return HDRType.HDR10
        }
        
        // HLG detection
        if (codecs.contains("hlg")) {
            return HDRType.HLG
        }
        
        return HDRType.SDR
    }

    fun getHDRBadge(format: Format): String? {
        if (!proFeatureGate.checkAccess(currentTier)) return null

        return when (detectHDRType(format)) {
            HDRType.HDR10 -> "HDR10"
            HDRType.DOLBY_VISION -> "Dolby Vision"
            HDRType.HLG -> "HLG"
            HDRType.SDR -> null
        }
    }

    fun reset() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CRITICAL FIX: Safe null checks for window and attributes
            val currentWindow = window ?: return
            val currentAttributes = currentWindow.attributes ?: return
            
            try {
                val field = WindowManager.LayoutParams::class.java.getField("COLOR_MODE_DEFAULT")
                currentAttributes.colorMode = field.getInt(null)
            } catch (e: Exception) {
                currentAttributes.colorMode = 0 // Default color mode
            }
            currentAttributes.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
            currentWindow.attributes = currentAttributes
        }
    }
}

enum class HDRType {
    SDR, HDR10, DOLBY_VISION, HLG
}

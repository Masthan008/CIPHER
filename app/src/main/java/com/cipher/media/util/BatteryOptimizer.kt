package com.cipher.media.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.PowerManager

/**
 * Battery-aware and network-aware optimizations.
 * Reduces resource usage on low battery or metered connections.
 */
object BatteryOptimizer {

    /**
     * Returns true if device is in power save (Doze) mode.
     */
    fun isPowerSaveMode(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isPowerSaveMode
    }

    /**
     * Returns battery percentage (0-100).
     */
    fun getBatteryLevel(context: Context): Int {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    /**
     * Returns true when device is charging.
     */
    fun isCharging(context: Context): Boolean {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return bm.isCharging
    }

    /**
     * Returns true if the device is connected via Wi-Fi (unmetered).
     */
    fun isOnWifi(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    /**
     * Returns true if we should preload video (only on Wi-Fi + charging + not power-save).
     */
    fun shouldPreloadVideo(context: Context): Boolean {
        return isOnWifi(context) && !isPowerSaveMode(context)
    }

    /**
     * Returns true if we should limit background work (low battery or power-save).
     */
    fun shouldLimitBackgroundWork(context: Context): Boolean {
        return isPowerSaveMode(context) || getBatteryLevel(context) < 15
    }

    /**
     * Returns recommended thumbnail quality based on memory.
     * Lower quality on low-RAM devices.
     */
    fun getThumbnailQuality(): Int {
        val totalMemMb = Runtime.getRuntime().maxMemory() / (1024 * 1024)
        return when {
            totalMemMb < 128 -> 50  // Low-end (2GB RAM phones)
            totalMemMb < 256 -> 75  // Mid-range
            else -> 90              // High-end
        }
    }
}

package com.cipher.media.util

import android.content.Context
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Indian locale-aware formatting utilities.
 * Handles Indian number system (lakh/crore) and date formats.
 */
object IndianLocaleHelper {

    /**
     * Format number using Indian numbering system (1,00,000 instead of 100,000).
     */
    fun formatNumber(number: Long, locale: Locale = Locale("en", "IN")): String {
        return NumberFormat.getNumberInstance(locale).format(number)
    }

    /**
     * Format file size for Indian users.
     */
    fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            bytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024))
            else -> String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024))
        }
    }

    /**
     * Format duration (mm:ss or hh:mm:ss).
     */
    fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }

    /**
     * Format date for Indian locale (dd MMM yyyy).
     */
    fun formatDate(date: Date, locale: Locale = Locale("en", "IN")): String {
        return SimpleDateFormat("dd MMM yyyy", locale).format(date)
    }

    /**
     * Format price in INR.
     */
    fun formatPrice(amountInPaise: Long): String {
        val rupees = amountInPaise / 100.0
        return "₹${NumberFormat.getNumberInstance(Locale("en", "IN")).format(rupees)}"
    }

    /**
     * Get supported locales for the app.
     */
    val supportedLocales = listOf(
        Locale("en", "IN"),
        Locale("hi", "IN"),
        Locale("ta", "IN"),
        Locale("te", "IN"),
        Locale("mr", "IN"),
        Locale("bn", "IN"),
        Locale("gu", "IN"),
        Locale("kn", "IN"),
        Locale("ml", "IN"),
        Locale("pa", "IN"),
        Locale("ur", "IN")
    )
}

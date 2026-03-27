package com.cipher.media.billing.model

/**
 * Subscription tiers with feature flags and pricing.
 */
enum class SubscriptionTier(
    val displayName: String,
    val monthlyPrice: String,
    val yearlyPrice: String,
    val maxVaultStorageMb: Long,
    val maxEqBands: Int,
    val maxVideoQuality: String,
    val adsEnabled: Boolean,
    val cloudSyncEnabled: Boolean,
    val hdrEnabled: Boolean,
    val familySharing: Int
) {
    FREE(
        displayName = "Free",
        monthlyPrice = "₹0",
        yearlyPrice = "₹0",
        maxVaultStorageMb = 1024, // 1GB
        maxEqBands = 3,
        maxVideoQuality = "720p",
        adsEnabled = true,
        cloudSyncEnabled = false,
        hdrEnabled = false,
        familySharing = 0
    ),

    PRO(
        displayName = "Pro",
        monthlyPrice = "₹99",
        yearlyPrice = "₹499",
        maxVaultStorageMb = 10240, // 10GB
        maxEqBands = 10,
        maxVideoQuality = "1080p",
        adsEnabled = false,
        cloudSyncEnabled = true,
        hdrEnabled = false,
        familySharing = 0
    ),

    POWER(
        displayName = "Power",
        monthlyPrice = "₹149",
        yearlyPrice = "₹999",
        maxVaultStorageMb = Long.MAX_VALUE, // Unlimited
        maxEqBands = 10,
        maxVideoQuality = "4K",
        adsEnabled = false,
        cloudSyncEnabled = true,
        hdrEnabled = true,
        familySharing = 5
    );

    val isUnlimitedVault: Boolean get() = maxVaultStorageMb == Long.MAX_VALUE
}

package com.cipher.media.billing.model

/**
 * In-app product definitions matching Google Play Console SKUs.
 */
object Products {

    // One-time purchases
    const val REMOVE_ADS = "cipher_remove_ads"       // ₹199 one-time

    // Subscriptions
    const val PRO_MONTHLY = "cipher_pro_monthly"      // ₹99/month
    const val PRO_YEARLY = "cipher_pro_yearly"        // ₹499/year
    const val POWER_YEARLY = "cipher_power_yearly"    // ₹999/year
    const val LIFETIME = "cipher_lifetime"            // ₹1999 one-time

    val ONE_TIME_PRODUCTS = listOf(REMOVE_ADS, LIFETIME)
    val SUBSCRIPTION_PRODUCTS = listOf(PRO_MONTHLY, PRO_YEARLY, POWER_YEARLY)
    val ALL_PRODUCTS = ONE_TIME_PRODUCTS + SUBSCRIPTION_PRODUCTS
}

/**
 * Resolved product info from Play Billing query.
 */
data class ProductInfo(
    val productId: String,
    val title: String,
    val description: String,
    val formattedPrice: String,
    val priceAmountMicros: Long,
    val isSubscription: Boolean,
    val billingPeriod: String = "" // P1M, P1Y, etc.
)

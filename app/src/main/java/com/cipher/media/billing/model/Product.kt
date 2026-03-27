package com.cipher.media.billing.model

/**
 * Product definitions for Razorpay payments.
 *
 * HOW TO SET UP:
 * 1. Go to https://dashboard.razorpay.com → Subscriptions → Plans
 * 2. Create plans matching these IDs with correct pricing
 * 3. Replace RAZORPAY_PLAN_* IDs below with your plan IDs (plan_XXXXXX)
 */
object Products {

    // One-time purchases (use standard Razorpay payment, not subscription)
    const val REMOVE_ADS = "cipher_remove_ads"       // ₹199 one-time
    const val LIFETIME = "cipher_lifetime"            // ₹1999 one-time

    // Amounts in paise (₹1 = 100 paise)
    const val REMOVE_ADS_AMOUNT = 19900L              // ₹199
    const val LIFETIME_AMOUNT = 199900L               // ₹1999

    // Razorpay Subscription Plan IDs (replace with yours from dashboard)
    const val PRO_MONTHLY = "plan_PRO_MONTHLY"        // ₹99/month
    const val PRO_YEARLY = "plan_PRO_YEARLY"          // ₹499/year  
    const val POWER_YEARLY = "plan_POWER_YEARLY"      // ₹999/year

    val ONE_TIME_PRODUCTS = listOf(REMOVE_ADS, LIFETIME)
    val SUBSCRIPTION_PRODUCTS = listOf(PRO_MONTHLY, PRO_YEARLY, POWER_YEARLY)
    val ALL_PRODUCTS = ONE_TIME_PRODUCTS + SUBSCRIPTION_PRODUCTS
}

/**
 * Resolved product info.
 */
data class ProductInfo(
    val productId: String,
    val title: String,
    val description: String,
    val formattedPrice: String,
    val priceAmountPaise: Long,
    val isSubscription: Boolean,
    val billingPeriod: String = "" // "monthly", "yearly", etc.
)

package com.cipher.media.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Razorpay payment manager for CIPHER.
 * Handles one-time purchases (remove ads) and subscription plans.
 *
 * SETUP:
 * 1. Get your Razorpay Key ID from https://dashboard.razorpay.com/app/keys
 * 2. Replace RAZORPAY_KEY_ID below with your live/test key
 * 3. Create subscription plans in Razorpay Dashboard → Subscriptions → Plans
 * 4. Update plan IDs in Products.kt
 */
@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "CIPHERBilling"

        // ⚠️ REPLACE with your Razorpay Key ID from dashboard.razorpay.com
        const val RAZORPAY_KEY_ID = "rzp_live_SKlWcTovIQ1j3G"
    }

    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState.asStateFlow()

    private val _activePurchases = MutableStateFlow<Set<String>>(emptySet())
    val activePurchases: StateFlow<Set<String>> = _activePurchases.asStateFlow()

    private val _lastPaymentId = MutableStateFlow<String?>(null)
    val lastPaymentId: StateFlow<String?> = _lastPaymentId.asStateFlow()

    private val prefs by lazy {
        context.getSharedPreferences("cipher_billing", Context.MODE_PRIVATE)
    }

    fun initialize() {
        Checkout.preload(context)
        _connectionState.value = true
        // Restore saved purchases
        val saved = prefs.getStringSet("active_purchases", emptySet()) ?: emptySet()
        _activePurchases.value = saved
        Log.d(TAG, "Razorpay initialized. Active purchases: $saved")
    }

    /**
     * Launch one-time payment (e.g., remove ads ₹199).
     */
    fun launchOneTimePayment(
        activity: Activity,
        amountInPaise: Long,
        productId: String,
        description: String,
        listener: PaymentResultListener
    ) {
        val checkout = Checkout()
        checkout.setKeyID(RAZORPAY_KEY_ID)

        val options = JSONObject().apply {
            put("name", "CIPHER")
            put("description", description)
            put("currency", "INR")
            put("amount", amountInPaise) // Amount in paise (₹199 = 19900)
            put("prefill", JSONObject().apply {
                put("email", "")
                put("contact", "")
            })
            put("theme", JSONObject().apply {
                put("color", "#FF9933") // Saffron primary
            })
            put("notes", JSONObject().apply {
                put("product_id", productId)
                put("type", "one_time")
            })
        }

        try {
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e(TAG, "Payment launch failed: ${e.message}")
        }
    }

    /**
     * Launch Razorpay subscription.
     * Requires pre-created subscription on Razorpay Dashboard.
     * @param subscriptionId - Razorpay subscription ID (sub_XXXXXX)
     */
    fun launchSubscription(
        activity: Activity,
        subscriptionId: String,
        planName: String,
        listener: PaymentResultListener
    ) {
        val checkout = Checkout()
        checkout.setKeyID(RAZORPAY_KEY_ID)

        val options = JSONObject().apply {
            put("name", "CIPHER")
            put("description", "CIPHER $planName")
            put("subscription_id", subscriptionId)
            put("currency", "INR")
            put("theme", JSONObject().apply {
                put("color", "#FF9933")
            })
            put("notes", JSONObject().apply {
                put("plan_name", planName)
                put("type", "subscription")
            })
        }

        try {
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e(TAG, "Subscription launch failed: ${e.message}")
        }
    }

    /**
     * Called on successful payment — save purchase locally.
     */
    fun onPaymentSuccess(paymentId: String, productId: String) {
        _lastPaymentId.value = paymentId
        val updated = _activePurchases.value.toMutableSet()
        updated.add(productId)
        _activePurchases.value = updated
        prefs.edit().putStringSet("active_purchases", updated).apply()
        Log.d(TAG, "Payment success: $paymentId for $productId")
    }

    /**
     * Called on payment failure.
     */
    fun onPaymentError(code: Int, message: String?) {
        Log.w(TAG, "Payment failed ($code): $message")
    }

    /**
     * Check if a product has been purchased.
     */
    fun isPurchased(productId: String): Boolean {
        return _activePurchases.value.contains(productId)
    }

    /**
     * Restore purchases (check with your backend in production).
     */
    fun restorePurchases() {
        val saved = prefs.getStringSet("active_purchases", emptySet()) ?: emptySet()
        _activePurchases.value = saved
        Log.d(TAG, "Restored purchases: $saved")
    }

    fun destroy() {
        _connectionState.value = false
    }
}

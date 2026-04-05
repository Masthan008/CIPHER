package com.cipher.media.ui.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.cipher.media.billing.BillingManager
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.Products
import com.cipher.media.billing.model.SubscriptionTier
import com.razorpay.PaymentResultListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val billingRepository: BillingRepository
) : ViewModel() {

    val activeTier: StateFlow<SubscriptionTier> = billingRepository.activeTier
    val shouldShowAds: StateFlow<Boolean> = billingRepository.shouldShowAds

    private val _selectedPlan = MutableStateFlow(1) // Default: yearly
    val selectedPlan: StateFlow<Int> = _selectedPlan.asStateFlow()

    fun selectPlan(index: Int) { _selectedPlan.value = index }

    fun subscribe(activity: Activity) {
        val (planId, planName) = when (_selectedPlan.value) {
            0 -> Products.PRO_MONTHLY to "Pro Monthly"
            1 -> Products.PRO_YEARLY to "Pro Yearly"
            2 -> Products.POWER_YEARLY to "Power Yearly"
            else -> return
        }
        
        val amountInPaise = when (_selectedPlan.value) {
            0 -> 9900L  // ₹99
            1 -> 49900L // ₹499
            2 -> 99900L // ₹999
            else -> return
        }

        billingManager.launchOneTimePayment(
            activity = activity,
            amountInPaise = amountInPaise,
            productId = planId,
            description = "CIPHER $planName Access",
            listener = object : PaymentResultListener {
                override fun onPaymentSuccess(paymentId: String?) {
                    paymentId?.let { billingManager.onPaymentSuccess(it, planId) }
                }
                override fun onPaymentError(code: Int, message: String?) {
                    billingManager.onPaymentError(code, message)
                }
            }
        )
    }

    /**
     * Launch one-time remove-ads purchase.
     */
    fun removeAds(activity: Activity) {
        billingManager.launchOneTimePayment(
            activity = activity,
            amountInPaise = Products.REMOVE_ADS_AMOUNT,
            productId = Products.REMOVE_ADS,
            description = "Remove all ads from CIPHER",
            listener = object : PaymentResultListener {
                override fun onPaymentSuccess(paymentId: String?) {
                    paymentId?.let { billingManager.onPaymentSuccess(it, Products.REMOVE_ADS) }
                }
                override fun onPaymentError(code: Int, message: String?) {
                    billingManager.onPaymentError(code, message)
                }
            }
        )
    }

    fun restorePurchases() {
        billingManager.restorePurchases()
    }

    // No-activity versions for compose preview
    fun subscribe() {}
    fun removeAds() {}
}

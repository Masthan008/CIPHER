package com.cipher.media.ui.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.cipher.media.billing.BillingManager
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val billingRepository: BillingRepository
) : ViewModel() {

    val activeTier: StateFlow<SubscriptionTier> = billingRepository.activeTier
    val shouldShowAds: StateFlow<Boolean> = billingRepository.shouldShowAds

    fun purchase(activity: Activity, productId: String) {
        billingManager.launchPurchaseFlow(activity, productId)
    }

    fun restorePurchases() {
        billingManager.queryExistingPurchases()
    }
}

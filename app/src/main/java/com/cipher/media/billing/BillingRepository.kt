package com.cipher.media.billing

import com.cipher.media.billing.model.Products
import com.cipher.media.billing.model.SubscriptionTier
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resolves the user's active subscription tier from Razorpay purchase state.
 */
@Singleton
class BillingRepository @Inject constructor(
    private val billingManager: BillingManager
) {
    /**
     * The user's current active tier based on purchases.
     */
    val activeTier: StateFlow<SubscriptionTier> = billingManager.activePurchases
        .map { purchases -> resolveTier(purchases) }
        .stateIn(
            scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SubscriptionTier.FREE
        )

    /**
     * Whether ads should be shown (free tier + no ad-removal purchase).
     */
    val shouldShowAds: StateFlow<Boolean> = billingManager.activePurchases
        .map { purchases ->
            val hasAdRemoval = Products.REMOVE_ADS in purchases
            val tier = resolveTier(purchases)
            tier == SubscriptionTier.FREE && !hasAdRemoval
        }
        .stateIn(
            scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    val isPro: Flow<Boolean> = activeTier.map { it == SubscriptionTier.PRO || it == SubscriptionTier.POWER }
    val isPower: Flow<Boolean> = activeTier.map { it == SubscriptionTier.POWER }

    private fun resolveTier(purchases: Set<String>): SubscriptionTier {
        return when {
            Products.LIFETIME in purchases -> SubscriptionTier.POWER
            Products.POWER_YEARLY in purchases -> SubscriptionTier.POWER
            Products.PRO_YEARLY in purchases -> SubscriptionTier.PRO
            Products.PRO_MONTHLY in purchases -> SubscriptionTier.PRO
            else -> SubscriptionTier.FREE
        }
    }
}

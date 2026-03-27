package com.cipher.media.billing

import com.android.billingclient.api.Purchase
import com.cipher.media.billing.model.Products
import com.cipher.media.billing.model.SubscriptionTier
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resolves the user's active subscription tier from purchase state.
 */
@Singleton
class BillingRepository @Inject constructor(
    private val billingManager: BillingManager
) {
    /**
     * The user's current active tier based on purchases.
     */
    val activeTier: StateFlow<SubscriptionTier> = billingManager.purchases
        .map { purchases -> resolveTier(purchases) }
        .stateIn(
            scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SubscriptionTier.FREE
        )

    /**
     * Whether ads should be shown (free tier + no ad-removal purchase).
     */
    val shouldShowAds: StateFlow<Boolean> = billingManager.purchases
        .map { purchases ->
            val hasAdRemoval = purchases.any {
                it.purchaseState == Purchase.PurchaseState.PURCHASED &&
                it.products.contains(Products.REMOVE_ADS)
            }
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

    private fun resolveTier(purchases: List<Purchase>): SubscriptionTier {
        val activeProducts = purchases
            .filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }
            .flatMap { it.products }
            .toSet()

        return when {
            Products.LIFETIME in activeProducts -> SubscriptionTier.POWER
            Products.POWER_YEARLY in activeProducts -> SubscriptionTier.POWER
            Products.PRO_YEARLY in activeProducts -> SubscriptionTier.PRO
            Products.PRO_MONTHLY in activeProducts -> SubscriptionTier.PRO
            else -> SubscriptionTier.FREE
        }
    }
}

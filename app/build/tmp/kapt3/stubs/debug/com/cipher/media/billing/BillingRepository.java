package com.cipher.media.billing;

import com.cipher.media.billing.model.Products;
import com.cipher.media.billing.model.SubscriptionTier;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Resolves the user's active subscription tier from Razorpay purchase state.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0011\u001a\u00020\u00072\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\t\u00a8\u0006\u0015"}, d2 = {"Lcom/cipher/media/billing/BillingRepository;", "", "billingManager", "Lcom/cipher/media/billing/BillingManager;", "(Lcom/cipher/media/billing/BillingManager;)V", "activeTier", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/cipher/media/billing/model/SubscriptionTier;", "getActiveTier", "()Lkotlinx/coroutines/flow/StateFlow;", "isPower", "Lkotlinx/coroutines/flow/Flow;", "", "()Lkotlinx/coroutines/flow/Flow;", "isPro", "shouldShowAds", "getShouldShowAds", "resolveTier", "purchases", "", "", "app_debug"})
public final class BillingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingManager billingManager = null;
    
    /**
     * The user's current active tier based on purchases.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.billing.model.SubscriptionTier> activeTier = null;
    
    /**
     * Whether ads should be shown (free tier + no ad-removal purchase).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> shouldShowAds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isPro = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isPower = null;
    
    @javax.inject.Inject()
    public BillingRepository(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingManager billingManager) {
        super();
    }
    
    /**
     * The user's current active tier based on purchases.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.billing.model.SubscriptionTier> getActiveTier() {
        return null;
    }
    
    /**
     * Whether ads should be shown (free tier + no ad-removal purchase).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShouldShowAds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isPro() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isPower() {
        return null;
    }
    
    private final com.cipher.media.billing.model.SubscriptionTier resolveTier(java.util.Set<java.lang.String> purchases) {
        return null;
    }
}
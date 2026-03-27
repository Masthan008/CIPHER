package com.cipher.media.ui.premium;

import android.app.Activity;
import androidx.lifecycle.ViewModel;
import com.cipher.media.billing.BillingManager;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.Products;
import com.cipher.media.billing.model.SubscriptionTier;
import com.razorpay.PaymentResultListener;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0015J\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\tJ\u0006\u0010\u001b\u001a\u00020\u0015J\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000e\u00a8\u0006\u001c"}, d2 = {"Lcom/cipher/media/ui/premium/PremiumViewModel;", "Landroidx/lifecycle/ViewModel;", "billingManager", "Lcom/cipher/media/billing/BillingManager;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "(Lcom/cipher/media/billing/BillingManager;Lcom/cipher/media/billing/BillingRepository;)V", "_selectedPlan", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "activeTier", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/cipher/media/billing/model/SubscriptionTier;", "getActiveTier", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedPlan", "getSelectedPlan", "shouldShowAds", "", "getShouldShowAds", "removeAds", "", "activity", "Landroid/app/Activity;", "restorePurchases", "selectPlan", "index", "subscribe", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PremiumViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingManager billingManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.billing.model.SubscriptionTier> activeTier = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> shouldShowAds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _selectedPlan = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> selectedPlan = null;
    
    @javax.inject.Inject()
    public PremiumViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingManager billingManager, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.billing.model.SubscriptionTier> getActiveTier() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShouldShowAds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getSelectedPlan() {
        return null;
    }
    
    public final void selectPlan(int index) {
    }
    
    /**
     * Launch Razorpay subscription for the selected plan.
     */
    public final void subscribe(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * Launch one-time remove-ads purchase.
     */
    public final void removeAds(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void restorePurchases() {
    }
    
    public final void subscribe() {
    }
    
    public final void removeAds() {
    }
}
package com.cipher.media.billing;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import org.json.JSONObject;
import javax.inject.Inject;
import javax.inject.Singleton;

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
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\b\u0007\u0018\u0000 22\u00020\u0001:\u00012B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cJ\u000e\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\bJ.\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\'J&\u0010(\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\b2\u0006\u0010&\u001a\u00020\'J\u0018\u0010+\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\bJ\u0016\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\bJ\u0006\u00101\u001a\u00020\u001cR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR#\u0010\u0014\u001a\n \u0016*\u0004\u0018\u00010\u00150\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018\u00a8\u00063"}, d2 = {"Lcom/cipher/media/billing/BillingManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_activePurchases", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "_connectionState", "", "_lastPaymentId", "activePurchases", "Lkotlinx/coroutines/flow/StateFlow;", "getActivePurchases", "()Lkotlinx/coroutines/flow/StateFlow;", "connectionState", "getConnectionState", "lastPaymentId", "getLastPaymentId", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", "destroy", "", "initialize", "isPurchased", "productId", "launchOneTimePayment", "activity", "Landroid/app/Activity;", "amountInPaise", "", "description", "listener", "Lcom/razorpay/PaymentResultListener;", "launchSubscription", "subscriptionId", "planName", "onPaymentError", "code", "", "message", "onPaymentSuccess", "paymentId", "restorePurchases", "Companion", "app_debug"})
public final class BillingManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CIPHERBilling";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RAZORPAY_KEY_ID = "rzp_live_SKlWcTovIQ1j3G";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _activePurchases = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> activePurchases = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _lastPaymentId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> lastPaymentId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy prefs$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.billing.BillingManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public BillingManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getActivePurchases() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLastPaymentId() {
        return null;
    }
    
    private final android.content.SharedPreferences getPrefs() {
        return null;
    }
    
    public final void initialize() {
    }
    
    /**
     * Launch one-time payment (e.g., remove ads ₹199).
     */
    public final void launchOneTimePayment(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, long amountInPaise, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    com.razorpay.PaymentResultListener listener) {
    }
    
    /**
     * Launch Razorpay subscription.
     * Requires pre-created subscription on Razorpay Dashboard.
     * @param subscriptionId - Razorpay subscription ID (sub_XXXXXX)
     */
    public final void launchSubscription(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    java.lang.String subscriptionId, @org.jetbrains.annotations.NotNull()
    java.lang.String planName, @org.jetbrains.annotations.NotNull()
    com.razorpay.PaymentResultListener listener) {
    }
    
    /**
     * Called on successful payment — save purchase locally.
     */
    public final void onPaymentSuccess(@org.jetbrains.annotations.NotNull()
    java.lang.String paymentId, @org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
    }
    
    /**
     * Called on payment failure.
     */
    public final void onPaymentError(int code, @org.jetbrains.annotations.Nullable()
    java.lang.String message) {
    }
    
    /**
     * Check if a product has been purchased.
     */
    public final boolean isPurchased(@org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
        return false;
    }
    
    /**
     * Restore purchases (check with your backend in production).
     */
    public final void restorePurchases() {
    }
    
    public final void destroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/cipher/media/billing/BillingManager$Companion;", "", "()V", "RAZORPAY_KEY_ID", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
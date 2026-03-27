package com.cipher.media.billing.model;

/**
 * Product definitions for Razorpay payments.
 *
 * HOW TO SET UP:
 * 1. Go to https://dashboard.razorpay.com → Subscriptions → Plans
 * 2. Create plans matching these IDs with correct pricing
 * 3. Replace RAZORPAY_PLAN_* IDs below with your plan IDs (plan_XXXXXX)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u000e\u0010\r\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/cipher/media/billing/model/Products;", "", "()V", "ALL_PRODUCTS", "", "", "getALL_PRODUCTS", "()Ljava/util/List;", "LIFETIME", "LIFETIME_AMOUNT", "", "ONE_TIME_PRODUCTS", "getONE_TIME_PRODUCTS", "POWER_YEARLY", "PRO_MONTHLY", "PRO_YEARLY", "REMOVE_ADS", "REMOVE_ADS_AMOUNT", "SUBSCRIPTION_PRODUCTS", "getSUBSCRIPTION_PRODUCTS", "app_debug"})
public final class Products {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REMOVE_ADS = "cipher_remove_ads";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LIFETIME = "cipher_lifetime";
    public static final long REMOVE_ADS_AMOUNT = 19900L;
    public static final long LIFETIME_AMOUNT = 199900L;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRO_MONTHLY = "plan_PRO_MONTHLY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRO_YEARLY = "plan_PRO_YEARLY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String POWER_YEARLY = "plan_POWER_YEARLY";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> ONE_TIME_PRODUCTS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> SUBSCRIPTION_PRODUCTS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> ALL_PRODUCTS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.billing.model.Products INSTANCE = null;
    
    private Products() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getONE_TIME_PRODUCTS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSUBSCRIPTION_PRODUCTS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getALL_PRODUCTS() {
        return null;
    }
}
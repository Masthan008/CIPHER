package com.cipher.media.billing.model;

/**
 * Subscription tiers with feature flags and pricing.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0017\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001BW\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\t\u00a2\u0006\u0002\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u0019\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015j\u0002\b j\u0002\b!j\u0002\b\"\u00a8\u0006#"}, d2 = {"Lcom/cipher/media/billing/model/SubscriptionTier;", "", "displayName", "", "monthlyPrice", "yearlyPrice", "maxVaultStorageMb", "", "maxEqBands", "", "maxVideoQuality", "adsEnabled", "", "cloudSyncEnabled", "hdrEnabled", "familySharing", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;JILjava/lang/String;ZZZI)V", "getAdsEnabled", "()Z", "getCloudSyncEnabled", "getDisplayName", "()Ljava/lang/String;", "getFamilySharing", "()I", "getHdrEnabled", "isUnlimitedVault", "getMaxEqBands", "getMaxVaultStorageMb", "()J", "getMaxVideoQuality", "getMonthlyPrice", "getYearlyPrice", "FREE", "PRO", "POWER", "app_debug"})
public enum SubscriptionTier {
    /*public static final*/ FREE /* = new FREE(null, null, null, 0L, 0, null, false, false, false, 0) */,
    /*public static final*/ PRO /* = new PRO(null, null, null, 0L, 0, null, false, false, false, 0) */,
    /*public static final*/ POWER /* = new POWER(null, null, null, 0L, 0, null, false, false, false, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String monthlyPrice = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String yearlyPrice = null;
    private final long maxVaultStorageMb = 0L;
    private final int maxEqBands = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String maxVideoQuality = null;
    private final boolean adsEnabled = false;
    private final boolean cloudSyncEnabled = false;
    private final boolean hdrEnabled = false;
    private final int familySharing = 0;
    
    SubscriptionTier(java.lang.String displayName, java.lang.String monthlyPrice, java.lang.String yearlyPrice, long maxVaultStorageMb, int maxEqBands, java.lang.String maxVideoQuality, boolean adsEnabled, boolean cloudSyncEnabled, boolean hdrEnabled, int familySharing) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMonthlyPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYearlyPrice() {
        return null;
    }
    
    public final long getMaxVaultStorageMb() {
        return 0L;
    }
    
    public final int getMaxEqBands() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaxVideoQuality() {
        return null;
    }
    
    public final boolean getAdsEnabled() {
        return false;
    }
    
    public final boolean getCloudSyncEnabled() {
        return false;
    }
    
    public final boolean getHdrEnabled() {
        return false;
    }
    
    public final int getFamilySharing() {
        return 0;
    }
    
    public final boolean isUnlimitedVault() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.billing.model.SubscriptionTier> getEntries() {
        return null;
    }
}
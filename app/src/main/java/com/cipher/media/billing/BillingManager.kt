package com.cipher.media.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.cipher.media.billing.model.ProductInfo
import com.cipher.media.billing.model.Products
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wraps Google Play Billing Library 6.0.
 * Handles connection, product queries, purchases, and acknowledgments.
 */
@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context
) : PurchasesUpdatedListener {

    companion object {
        private const val TAG = "CIPHERBilling"
    }

    private var billingClient: BillingClient? = null

    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState.asStateFlow()

    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    val purchases: StateFlow<List<Purchase>> = _purchases.asStateFlow()

    private val _productDetails = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
    val productDetails: StateFlow<Map<String, ProductDetails>> = _productDetails.asStateFlow()

    fun initialize() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        startConnection()
    }

    private fun startConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing connected")
                    _connectionState.value = true
                    queryProducts()
                    queryExistingPurchases()
                } else {
                    Log.w(TAG, "Billing setup failed: ${result.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "Billing disconnected")
                _connectionState.value = false
            }
        })
    }

    private fun queryProducts() {
        // Query one-time products
        val inAppParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                Products.ONE_TIME_PRODUCTS.map { productId ->
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                }
            ).build()

        billingClient?.queryProductDetailsAsync(inAppParams) { result, details ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val map = _productDetails.value.toMutableMap()
                details.forEach { map[it.productId] = it }
                _productDetails.value = map
            }
        }

        // Query subscriptions
        val subParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                Products.SUBSCRIPTION_PRODUCTS.map { productId ->
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                }
            ).build()

        billingClient?.queryProductDetailsAsync(subParams) { result, details ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val map = _productDetails.value.toMutableMap()
                details.forEach { map[it.productId] = it }
                _productDetails.value = map
            }
        }
    }

    fun queryExistingPurchases() {
        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val allPurchases = _purchases.value.toMutableList()
                allPurchases.addAll(purchases)
                _purchases.value = allPurchases
                purchases.forEach { acknowledgePurchaseIfNeeded(it) }
            }
        }

        billingClient?.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        ) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                val allPurchases = _purchases.value.toMutableList()
                allPurchases.addAll(purchases)
                _purchases.value = allPurchases
                purchases.forEach { acknowledgePurchaseIfNeeded(it) }
            }
        }
    }

    fun launchPurchaseFlow(activity: Activity, productId: String) {
        val productDetails = _productDetails.value[productId] ?: return

        val flowParams = BillingFlowParams.newBuilder()

        if (productDetails.productType == BillingClient.ProductType.SUBS) {
            val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: return
            flowParams.setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(offerToken)
                        .build()
                )
            )
        } else {
            flowParams.setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
            )
        }

        billingClient?.launchBillingFlow(activity, flowParams.build())
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            _purchases.value = purchases
            purchases.forEach { acknowledgePurchaseIfNeeded(it) }
        } else if (result.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "Purchase canceled")
        } else {
            Log.w(TAG, "Purchase error: ${result.debugMessage}")
        }
    }

    private fun acknowledgePurchaseIfNeeded(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
            val params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
            billingClient?.acknowledgePurchase(params) { result ->
                Log.d(TAG, "Acknowledge result: ${result.responseCode}")
            }
        }
    }

    fun destroy() {
        billingClient?.endConnection()
        billingClient = null
    }
}

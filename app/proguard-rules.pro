# ProGuard rules for CIPHER
-keepattributes *Annotation*
-keep class com.cipher.media.data.local.entity.** { *; }
-keep class com.cipher.media.data.model.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Media3
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# SQLCipher
-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**

# Google Play Billing
-keep class com.android.vending.billing.** { *; }
-keep class com.android.billingclient.** { *; }

# AdMob
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# Glance Widget
-keep class com.cipher.media.widget.** { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Compose (release stability)
-dontwarn androidx.compose.**

# Billing model
-keep class com.cipher.media.billing.model.** { *; }

# Room entities (encrypted DB)
-keep class com.cipher.media.data.local.** { *; }

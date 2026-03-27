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

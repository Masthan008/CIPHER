package com.cipher.media.billing

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.cipher.media.R
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.CIPHEROnPrimary
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature access control for Pro tier (₹99/month)
 */
@Singleton
class ProFeatureGate @Inject constructor() {

    fun checkAccess(userTier: Tier): Boolean {
        return when (userTier) {
            Tier.PRO, Tier.POWER, Tier.LIFETIME -> true
            else -> false
        }
    }

    fun getTierDisplayName(tier: Tier): String = when (tier) {
        Tier.FREE -> "Free"
        Tier.PRO -> "Pro"
        Tier.POWER -> "Power"
        Tier.LIFETIME -> "Lifetime"
    }
}

enum class Tier {
    FREE, PRO, POWER, LIFETIME
}

enum class ProVideoFeature(val displayName: String, val description: String) {
    VIDEO_4K("4K Playback", "Watch videos in stunning 2160p resolution"),
    HDR("HDR10/Dolby Vision", "High dynamic range for vivid colors"),
    TEN_BAND_EQ("10-Band Equalizer", "Customize audio with precision"),
    HI_RES_AUDIO("Hi-Res Audio", "192kHz/24-bit audio quality"),
    AUTO_SUBTITLES("Auto-download Subtitles", "Fetch subtitles automatically"),
    SUBTITLE_TRANSLATE("Subtitle Translation", "Translate to Hindi/English"),
    THUMBNAIL_PREVIEW("Thumbnail Preview", "Scrub to see previews"),
    AB_REPEAT("A-B Repeat", "Loop any video section"),
    VIDEO_CROP("Video Crop/Zoom", "Custom aspect ratios and zoom"),
    SCREENSHOT_4K("4K Screenshot", "Capture frames in 4K")
}

@Composable
fun PaywallDialog(
    feature: ProVideoFeature,
    onUpgrade: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pro Feature", color = Color.White) },
        text = {
            Column {
                Text(
                    "${feature.displayName} requires Pro subscription",
                    color = Color.White
                )
                Text(
                    feature.description,
                    color = CIPHERPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "₹99/month or ₹999/year",
                    color = CIPHERPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onUpgrade,
                colors = ButtonDefaults.buttonColors(containerColor = CIPHERPrimary)
            ) {
                Text("Upgrade to Pro", color = CIPHEROnPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Not Now") }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}

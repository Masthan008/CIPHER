package com.cipher.media.ui.video.quality

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cipher.media.R
import com.cipher.media.billing.ProVideoFeature
import com.cipher.media.billing.Tier
import com.cipher.media.ui.theme.CIPHEROnSurface
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.CIPHERSurface

/**
 * Quality Selector Dialog for 4K Video Playback feature
 */
@Composable
fun QualitySelectorDialog(
    currentQuality: VideoQuality,
    onQualitySelected: (VideoQuality) -> Unit,
    onDismiss: () -> Unit,
    userTier: Tier,
    onShowPaywall: () -> Unit,
    proFeatureGate: com.cipher.media.billing.ProFeatureGate = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val qualityManager = remember {
        VideoQualityManager(
            androidx.media3.exoplayer.trackselection.DefaultTrackSelector(context),
            proFeatureGate,
            context
        ).apply { setUserTier(userTier) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.video_quality), color = CIPHEROnSurface) },
        text = {
            Column {
                qualityManager.supportedQualities.forEach { quality: VideoQuality ->
                    val isLocked = quality.is4K && userTier == Tier.FREE
                    val isSelected = quality == currentQuality

                    ListItem(
                        headlineContent = {
                            Text(
                                text = quality.displayName,
                                color = if (isLocked) CIPHEROnSurfaceVariant else CIPHEROnSurface
                            )
                        },
                        supportingContent = { Text(quality.resolution) },
                        trailingContent = {
                            when {
                                isLocked -> Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = CIPHERPrimary
                                )
                                isSelected -> Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = CIPHERPrimary
                                )
                            }
                        },
                        modifier = Modifier.clickable {
                            if (isLocked) {
                                onShowPaywall()
                            } else {
                                onQualitySelected(quality)
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.close)) }
        },
        containerColor = CIPHERSurface
    )
}

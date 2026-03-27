package com.cipher.media.ui.premium

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.CIPHERButton
import com.cipher.media.ui.components.CIPHEROutlinedButton
import com.cipher.media.ui.components.PulseGlow
import com.cipher.media.ui.theme.*

/**
 * Non-intrusive paywall dialog for feature gating.
 */
@Composable
fun PaywallDialog(
    featureName: String,
    onUpgrade: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        shape = RoundedCornerShape(Corners.large),
        icon = {
            PulseGlow { mod ->
                Icon(
                    Icons.Default.Lock, null,
                    tint = CIPHERPrimary,
                    modifier = mod.size(48.dp)
                )
            }
        },
        title = {
            Text(
                "Premium Feature",
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "$featureName is available with CIPHER Pro.",
                    color = CIPHEROnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    "Starting at just ₹99/month",
                    color = CIPHERPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            CIPHERButton(text = "Upgrade Now", onClick = onUpgrade, modifier = Modifier.fillMaxWidth())
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Maybe Later", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

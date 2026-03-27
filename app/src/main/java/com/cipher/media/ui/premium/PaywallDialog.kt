package com.cipher.media.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Non-intrusive paywall dialog shown when a free user tries to access a gated feature.
 */
@Composable
fun PaywallDialog(
    featureName: String,
    onDismiss: () -> Unit,
    onUpgrade: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        icon = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(listOf(CIPHERPrimary, CIPHERSecondary))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.WorkspacePremium, null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        title = {
            Text(
                "Premium Feature",
                color = CIPHEROnSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "$featureName is available with CIPHER Pro.",
                    color = CIPHEROnSurfaceVariant,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Starting at just ₹99/month",
                    color = CIPHERPrimary,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onUpgrade,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CIPHERPrimary,
                    contentColor = CIPHEROnPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upgrade Now", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Maybe Later", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

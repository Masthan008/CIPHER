package com.cipher.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Glassmorphic error dialog with pulsing error icon.
 */
@Composable
fun ErrorDialog(
    title: String = "Something went wrong",
    message: String,
    onRetry: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        shape = RoundedCornerShape(Corners.large),
        icon = {
            PulseGlow { mod ->
                Icon(
                    Icons.Default.ErrorOutline, null,
                    tint = CIPHERError,
                    modifier = mod.size(48.dp)
                )
            }
        },
        title = {
            Text(
                title,
                color = CIPHEROnSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                message,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            if (onRetry != null) {
                CIPHERButton(text = "Retry", onClick = onRetry, modifier = Modifier.fillMaxWidth())
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Dismiss", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

package com.cipher.media.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.cipher.media.ui.theme.*

/**
 * Error dialog with optional retry action.
 */
@Composable
fun ErrorDialog(
    title: String = "Something went wrong",
    message: String,
    onDismiss: () -> Unit,
    onRetry: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        icon = { Icon(Icons.Default.ErrorOutline, null, tint = CIPHERError) },
        title = {
            Text(title, color = CIPHEROnSurface, fontWeight = FontWeight.Bold)
        },
        text = {
            Text(message, color = CIPHEROnSurfaceVariant)
        },
        confirmButton = {
            if (onRetry != null) {
                TextButton(onClick = onRetry) {
                    Text("RETRY", color = CIPHERPrimary, fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("DISMISS", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

package com.cipher.media.ui.vault.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Dialog for importing files into the vault.
 * Offers Move (encrypt + delete original) or Copy (encrypt only).
 */
@Composable
fun ImportDialog(
    onDismiss: () -> Unit,
    onImport: (uri: Uri, deleteOriginal: Boolean) -> Unit
) {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        selectedUri = uri
    }

    // Auto-launch picker on first composition
    LaunchedEffect(Unit) {
        filePicker.launch(arrayOf("image/*", "video/*"))
    }

    if (selectedUri != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = CIPHERSurface,
            titleContentColor = CIPHEROnSurface,
            title = {
                Text(
                    text = "Import to Vault",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = "How would you like to import this file?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CIPHEROnSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedUri?.let { onImport(it, true) }
                        onDismiss()
                    }
                ) {
                    Text("MOVE", color = CIPHERPrimary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedUri?.let { onImport(it, false) }
                        onDismiss()
                    }
                ) {
                    Text("COPY", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }
}

package com.cipher.media.ui.audio.library.components

import android.provider.MediaStore
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Bottom sheet for batch operations on selected audio files.
 * Pro-only feature with multiple editing actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchEditBottomSheet(
    selectedCount: Int,
    onDismiss: () -> Unit,
    onBatchSetField: (field: String, value: String) -> Unit,
    onAutoNumber: () -> Unit,
    onSelectAll: () -> Unit,
    onClearSelection: () -> Unit
) {
    var showFieldDialog by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            Text(
                "$selectedCount selected",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface
            )
            Spacer(Modifier.height(Spacing.md))

            // Selection controls
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = onSelectAll, label = { Text("Select All") })
                AssistChip(onClick = onClearSelection, label = { Text("Clear") })
            }

            Spacer(Modifier.height(Spacing.md))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.md))

            Text("Batch Edit", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))

            BatchAction(Icons.Default.Person, "Set Artist") {
                showFieldDialog = MediaStore.Audio.Media.ARTIST
            }
            BatchAction(Icons.Default.Album, "Set Album") {
                showFieldDialog = MediaStore.Audio.Media.ALBUM
            }
            BatchAction(Icons.Default.Category, "Set Genre") {
                showFieldDialog = "genre"
            }
            BatchAction(Icons.Default.FormatListNumbered, "Auto-Number Tracks") {
                onAutoNumber()
                onDismiss()
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }

    // Field input dialog
    showFieldDialog?.let { field ->
        var inputValue by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showFieldDialog = null },
            title = { Text("Set Value") },
            text = {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onBatchSetField(field, inputValue)
                    showFieldDialog = null
                    onDismiss()
                }) { Text("Apply", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showFieldDialog = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun BatchAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = CIPHEROnSurface, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Text(label, color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

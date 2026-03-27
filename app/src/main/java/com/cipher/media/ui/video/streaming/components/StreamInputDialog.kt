package com.cipher.media.ui.video.streaming.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Dialog for entering a network stream URL with live validation
 * and stream type detection preview.
 */
@Composable
fun StreamInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (url: String) -> Unit,
    validateUrl: (String) -> String?,
    getStreamTypeLabel: (String) -> String
) {
    var url by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var streamType by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Language, null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text("Network Stream", fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }
        },
        text = {
            Column(modifier = Modifier.animateContentSize()) {
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                        errorMsg = validateUrl(it)
                        if (errorMsg == null && it.isNotBlank()) {
                            streamType = getStreamTypeLabel(it)
                        } else {
                            streamType = ""
                        }
                    },
                    label = { Text("Stream URL") },
                    placeholder = { Text("https://example.com/video.m3u8") },
                    isError = errorMsg != null,
                    supportingText = {
                        errorMsg?.let { Text(it, color = CIPHERError) }
                    },
                    leadingIcon = { Icon(Icons.Default.Link, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {
                            if (validateUrl(url) == null) onConfirm(url)
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CIPHERPrimary,
                        cursorColor = CIPHERPrimary,
                        focusedLabelColor = CIPHERPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Stream type preview chip
                if (streamType.isNotEmpty()) {
                    Spacer(Modifier.height(Spacing.sm))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Stream, null, tint = CIPHERPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(streamType, color = CIPHERPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(Modifier.height(Spacing.md))

                // Quick URL examples
                Text("Examples:", color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                listOf(
                    "HLS" to "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
                    "HTTP" to "https://example.com/video.mp4"
                ).forEach { (label, example) ->
                    TextButton(
                        onClick = { url = example; errorMsg = null; streamType = getStreamTypeLabel(example) },
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                    ) {
                        Text("$label: ", color = CIPHERPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(example, color = CIPHEROnSurfaceVariant, fontSize = 11.sp, maxLines = 1)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(url) },
                enabled = errorMsg == null && url.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = CIPHERPrimary)
            ) {
                Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Play")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

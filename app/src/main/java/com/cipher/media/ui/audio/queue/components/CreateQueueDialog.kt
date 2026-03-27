package com.cipher.media.ui.audio.queue.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Dialog for creating a new queue with name + icon selection.
 */
@Composable
fun CreateQueueDialog(
    canCreate: Boolean,
    isPro: Boolean,
    queueCount: Int,
    queueLimit: Int,
    onCreate: (name: String, iconName: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("queue_music") }

    val iconOptions = listOf(
        "queue_music" to Icons.Default.QueueMusic,
        "headphones" to Icons.Default.Headphones,
        "fitness" to Icons.Default.FitnessCenter,
        "bedtime" to Icons.Default.Bedtime,
        "school" to Icons.Default.School,
        "work" to Icons.Default.Work,
        "celebration" to Icons.Default.Celebration,
        "favorite" to Icons.Default.Favorite,
        "music_note" to Icons.Default.MusicNote,
        "directions_car" to Icons.Default.DirectionsCar
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        title = {
            Text("New Queue", color = CIPHEROnSurface, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                // Queue count indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "$queueCount / $queueLimit queues",
                        color = CIPHEROnSurfaceVariant,
                        fontSize = 12.sp
                    )
                    if (!isPro && queueCount >= 3) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "PRO: ${ com.cipher.media.ui.audio.queue.QueueRepository.PRO_QUEUE_LIMIT } queues",
                            color = CIPHERPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Name input
                OutlinedTextField(
                    value = name,
                    onValueChange = { if (it.length <= 30) name = it },
                    label = { Text("Queue Name") },
                    placeholder = { Text("e.g. Study, Gym, Party…") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = CIPHEROnSurface,
                        unfocusedTextColor = CIPHEROnSurface,
                        focusedBorderColor = CIPHERPrimary,
                        unfocusedBorderColor = CIPHEROnSurfaceVariant.copy(alpha = 0.3f),
                        cursorColor = CIPHERPrimary,
                        focusedLabelColor = CIPHERPrimary,
                        unfocusedLabelColor = CIPHEROnSurfaceVariant
                    )
                )

                Spacer(Modifier.height(12.dp))

                // Icon picker
                Text("Icon", color = CIPHEROnSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))

                // Grid of icons (2 rows of 5)
                Column {
                    for (row in iconOptions.chunked(5)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for ((iconName, icon) in row) {
                                val isSelected = selectedIcon == iconName
                                IconButton(
                                    onClick = { selectedIcon = iconName }
                                ) {
                                    Icon(
                                        icon, null,
                                        tint = if (isSelected) CIPHERPrimary else CIPHEROnSurfaceVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.size(if (isSelected) 28.dp else 22.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                if (!canCreate) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (isPro) "Maximum queue limit reached"
                        else "Upgrade to Pro to create more queues!",
                        color = CIPHERError,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank()) onCreate(name.trim(), selectedIcon) },
                enabled = name.isNotBlank() && canCreate,
                colors = ButtonDefaults.buttonColors(containerColor = CIPHERPrimary)
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = CIPHEROnSurfaceVariant)
            }
        }
    )
}

package com.cipher.media.ui.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

@Composable
fun StorageInfo(
    vaultSize: Long,
    cacheSize: Long,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StorageRow(
                icon = Icons.Default.Folder,
                label = "Vault Storage",
                size = formatSize(vaultSize),
                color = CIPHERPrimary
            )
            StorageRow(
                icon = Icons.Default.Storage,
                label = "Cache",
                size = formatSize(cacheSize),
                color = CIPHEROnSurfaceVariant
            )
        }
    }
}

@Composable
private fun StorageRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    size: String,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = CIPHEROnSurface, modifier = Modifier.weight(1f))
        Text(size, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
    }
}

private fun formatSize(bytes: Long): String = when {
    bytes >= 1_073_741_824 -> "%.1f GB".format(bytes / 1_073_741_824.0)
    bytes >= 1_048_576 -> "%.1f MB".format(bytes / 1_048_576.0)
    bytes >= 1024 -> "%.0f KB".format(bytes / 1024.0)
    else -> "$bytes B"
}

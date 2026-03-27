package com.cipher.media.ui.vault.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Intruder log: timeline with photo cards showing time, location, device info.
 */
@Composable
fun IntruderLogScreen(
    logs: List<IntruderLogEntry> = emptyList(),
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.ArrowBack, onClick = onBack)
                Text(
                    "Break-in Alerts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CIPHEROnSurface
                )
            }
        }
    ) { padding ->
        if (logs.isEmpty()) {
            EmptyState(
                icon = Icons.Default.VerifiedUser,
                title = "No break-in attempts",
                subtitle = "Your vault is secure",
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                modifier = Modifier.padding(padding)
            ) {
                itemsIndexed(logs) { index, log ->
                    StaggeredEntrance(index = index) {
                        IntruderLogCard(log)
                    }
                }
            }
        }
    }
}

@Composable
private fun IntruderLogCard(log: IntruderLogEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        shape = RoundedCornerShape(Corners.large)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timeline dot
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(CIPHERError, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(CIPHERDivider)
                )
            }

            Spacer(Modifier.width(Spacing.md))

            // Photo placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(Corners.medium))
                    .background(CIPHERSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CameraFront, null, tint = CIPHERError.copy(alpha = 0.5f))
            }

            Spacer(Modifier.width(Spacing.md))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    log.timestamp,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = CIPHEROnSurface
                )
                Text(
                    log.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant
                )
                Text(
                    "${log.attempts} attempt(s)",
                    style = MaterialTheme.typography.labelSmall,
                    color = CIPHERError
                )
            }
        }
    }
}

data class IntruderLogEntry(
    val timestamp: String,
    val location: String,
    val attempts: Int,
    val photoUri: String? = null
)

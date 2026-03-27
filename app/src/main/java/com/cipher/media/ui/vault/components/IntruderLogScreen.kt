package com.cipher.media.ui.vault.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.local.IntruderLogEntity
import com.cipher.media.ui.stealth.StealthViewModel
import com.cipher.media.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Displays a log of break-in attempts with timestamp, GPS coordinates, and attempt count.
 */
@Composable
fun IntruderLogScreen(
    onBack: () -> Unit,
    viewModel: StealthViewModel = hiltViewModel()
) {
    val logs by viewModel.intruderLogs.collectAsState()
    val logCount by viewModel.logCount.collectAsState()
    var showClearDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Break-in Alerts",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = CIPHEROnSurface
                    )
                    Text(
                        text = "$logCount events logged",
                        style = MaterialTheme.typography.bodySmall,
                        color = CIPHEROnSurfaceVariant
                    )
                }
                if (logs.isNotEmpty()) {
                    IconButton(onClick = { showClearDialog = true }) {
                        Icon(Icons.Default.DeleteSweep, "Clear all", tint = CIPHERError)
                    }
                }
            }
        }
    ) { innerPadding ->
        if (logs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Shield, null,
                        modifier = Modifier.size(80.dp),
                        tint = CIPHERTertiary.copy(alpha = 0.4f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text("No break-in attempts", style = MaterialTheme.typography.titleMedium, color = CIPHEROnSurfaceVariant)
                    Text("Your vault is secure", style = MaterialTheme.typography.bodySmall, color = CIPHERTertiary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(logs, key = { it.id }) { log ->
                    IntruderLogCard(log)
                }
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            containerColor = CIPHERSurface,
            title = { Text("Clear All Logs?", color = CIPHEROnSurface, fontWeight = FontWeight.Bold) },
            text = { Text("This will delete all break-in records.", color = CIPHEROnSurfaceVariant) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearLogs()
                    showClearDialog = false
                }) { Text("CLEAR", color = CIPHERError) }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("CANCEL", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
private fun IntruderLogCard(log: IntruderLogEntity) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning,
                        null,
                        tint = CIPHERError,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Attempt #${log.attemptCount}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = CIPHERError
                    )
                }
                Text(
                    text = dateFormat.format(Date(log.timestamp)),
                    style = MaterialTheme.typography.labelSmall,
                    color = CIPHEROnSurfaceVariant
                )
            }

            if (log.latitude != null && log.longitude != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn, null,
                        tint = CIPHEROnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "%.4f, %.4f".format(log.latitude, log.longitude),
                        style = MaterialTheme.typography.bodySmall,
                        color = CIPHEROnSurfaceVariant
                    )
                }
            }

            if (log.photoPath != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CameraAlt, null,
                        tint = CIPHEROnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Photo captured",
                        style = MaterialTheme.typography.bodySmall,
                        color = CIPHEROnSurfaceVariant
                    )
                }
            }
        }
    }
}

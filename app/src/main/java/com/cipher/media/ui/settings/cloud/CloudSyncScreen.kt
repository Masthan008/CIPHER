package com.cipher.media.ui.settings.cloud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.cloud.SyncStatus
import com.cipher.media.cloud.VaultSyncEngine
import com.cipher.media.ui.theme.ProGold
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloudSyncScreen(
    onNavigateBack: () -> Unit,
    viewModel: CloudSyncViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cloud Vault Sync") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF0D0D0D)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFFF9933),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                StorageCard(
                    tier = state.activeTier,
                    usedBytes = state.usedStorageBytes,
                    totalBytes = state.totalQuotaBytes
                )

                SyncStatusCard(status = state.syncStatus)

                Button(
                    onClick = { viewModel.startSync() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9933)),
                    enabled = state.syncStatus !is SyncStatus.InProgress
                ) {
                    Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sync Now to Cloudinary")
                }
            }
        }
    }
}

@Composable
fun StorageCard(tier: SubscriptionTier, usedBytes: Long, totalBytes: Long) {
    val progress = if (totalBytes == VaultSyncEngine.QUOTA_POWER) 0f else (usedBytes.toFloat() / totalBytes.toFloat()).coerceIn(0f, 1f)
    val usedFormatted = formatBytes(usedBytes)
    val totalFormatted = if (totalBytes == VaultSyncEngine.QUOTA_POWER) "Unlimited" else formatBytes(totalBytes)

    val tierColor = when (tier) {
        SubscriptionTier.FREE -> Color.White
        SubscriptionTier.PRO -> ProGold
        SubscriptionTier.POWER -> Color(0xFFE0E0E0)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Storage Used",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${tier.name} TIER",
                    color = tierColor,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$usedFormatted / $totalFormatted",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (totalBytes != VaultSyncEngine.QUOTA_POWER) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = if (progress > 0.9f) Color.Red else Color(0xFFFF9933),
                    trackColor = Color.DarkGray
                )
            } else {
                Text("Unlimited Cloud Storage enabled.", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun SyncStatusCard(status: SyncStatus) {
    val bgColor = when (status) {
        is SyncStatus.Success -> Color(0xFF1B5E20).copy(alpha = 0.3f)
        is SyncStatus.Error, is SyncStatus.QuotaExceeded -> Color(0xFFB71C1C).copy(alpha = 0.3f)
        is SyncStatus.InProgress -> Color(0xFF0D47A1).copy(alpha = 0.3f)
        else -> Color.Transparent
    }

    val icon = when (status) {
        is SyncStatus.Success -> Icons.Default.CloudDone
        is SyncStatus.Error -> Icons.Default.ErrorOutline
        is SyncStatus.QuotaExceeded -> Icons.Default.Warning
        is SyncStatus.InProgress -> Icons.Default.CloudUpload
        else -> null
    }

    val message = when (status) {
        is SyncStatus.Success -> status.message
        is SyncStatus.Error -> "Error: ${status.reason}"
        is SyncStatus.QuotaExceeded -> "Quota Exceeded: Please upgrade your plan. Limit is ${status.limitStr}."
        is SyncStatus.InProgress -> "${status.progressText} (${(status.percent * 100).roundToInt()}%)"
        else -> "Ready to sync."
    }

    if (status != SyncStatus.Idle) {
        Card(
            colors = CardDefaults.cardColors(containerColor = bgColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(imageVector = it, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(text = message, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private fun formatBytes(bytes: Long): String {
    if (bytes < 1024) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(1024.0)).toInt()
    val pre = "KMGTPE"[exp - 1]
    return String.format("%.1f %cB", bytes / Math.pow(1024.0, exp.toDouble()), pre)
}

package com.cipher.media.ui.auth

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.CIPHERButton
import com.cipher.media.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Permission request screen with gradient CTA.
 * Requests both READ_MEDIA_VIDEO and READ_MEDIA_AUDIO on API 33+,
 * else READ_EXTERNAL_STORAGE. This ensures the app can discover
 * both video and audio files from the device.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AuthScreen(onAuthSuccess: () -> Unit) {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
    } else {
        listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionsState = rememberMultiplePermissionsState(permissions) { results ->
        // Navigate once ALL permissions are granted
        if (results.values.all { it }) {
            onAuthSuccess()
        }
    }

    // Auto-advance if already granted
    LaunchedEffect(permissionsState.allPermissionsGranted) {
        if (permissionsState.allPermissionsGranted) onAuthSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(CIPHERPrimary.copy(alpha = 0.15f), CIPHERBackground)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Folder, null,
                    modifier = Modifier.size(56.dp),
                    tint = CIPHERPrimary
                )
            }

            Spacer(Modifier.height(Spacing.xl))

            Text(
                "Access Your Media",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(Spacing.sm))

            Text(
                "CIPHER needs permission to access your videos and music files on this device.",
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(Spacing.xl))

            CIPHERButton(
                text = "Grant Permission",
                onClick = { permissionsState.launchMultiplePermissionRequest() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

package com.cipher.media.ui.vault.viewer

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.cipher.media.data.model.VaultItem
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.VaultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Encrypted video player: decrypts to temp file in app-private cache,
 * plays via ExoPlayer, and deletes the temp file on exit.
 * FLAG_SECURE is set to prevent screen recording.
 */
@Composable
fun EncryptedVideoPlayer(
    item: VaultItem,
    viewModel: VaultViewModel,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    var tempFile by remember { mutableStateOf<File?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var player by remember { mutableStateOf<ExoPlayer?>(null) }

    // FLAG_SECURE
    DisposableEffect(Unit) {
        (context as? Activity)?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            player?.release()
            tempFile?.delete()
        }
    }

    // Decrypt to temp file
    LaunchedEffect(item) {
        withContext(Dispatchers.IO) {
            try {
                tempFile = viewModel.decryptToTempFile(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading = false
        }
    }

    // Create player when temp file is ready
    LaunchedEffect(tempFile) {
        tempFile?.let { file ->
            val exoPlayer = ExoPlayer.Builder(context).build()
            exoPlayer.setMediaItem(MediaItem.fromUri(file.toURI().toString()))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            player = exoPlayer
        }
    }

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
                IconButton(onClick = {
                    player?.release()
                    tempFile?.delete()
                    onBack()
                }) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                }
                Text(
                    text = item.originalName,
                    style = MaterialTheme.typography.titleMedium,
                    color = CIPHEROnSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete", tint = CIPHERError)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CIPHERBackground),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = CIPHERPrimary)
            } else if (player != null) {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            this.player = player
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("Failed to decrypt video", color = CIPHERError)
            }
        }
    }
}

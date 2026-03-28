package com.cipher.media.ui.settings.theme.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Backup/Restore section for settings export/import.
 */
@Composable
fun BackupRestoreSection(
    userTier: Tier,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var lastBackupDate by remember { mutableStateOf<String?>(null) }
    var isExporting by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val file = File(context.filesDir, "cipher_backup.json")
        if (file.exists()) {
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            lastBackupDate = sdf.format(Date(file.lastModified()))
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "Backup & Restore",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface
        )
        Spacer(Modifier.height(Spacing.sm))

        if (lastBackupDate != null) {
            Text(
                "Last backup: $lastBackupDate",
                style = MaterialTheme.typography.bodySmall,
                color = CIPHEROnSurfaceVariant
            )
            Spacer(Modifier.height(Spacing.sm))
        }

        // Export
        OutlinedButton(
            onClick = {
                scope.launch {
                    isExporting = true
                    val success = exportSettings(context)
                    isExporting = false
                    Toast.makeText(
                        context,
                        if (success) "Settings exported" else "Export failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isExporting
        ) {
            if (isExporting) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
            } else {
                Icon(Icons.Default.Upload, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            Text("Export Settings")
        }

        Spacer(Modifier.height(Spacing.sm))

        // Import
        OutlinedButton(
            onClick = {
                scope.launch {
                    val success = importSettings(context)
                    Toast.makeText(
                        context,
                        if (success) "Settings restored" else "Restore failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Download, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Import Settings")
        }

        Spacer(Modifier.height(Spacing.sm))

        // Cloud backup (Pro)
        Button(
            onClick = { /* Cloud backup via Firebase - future */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = userTier != Tier.FREE,
            colors = ButtonDefaults.buttonColors(
                containerColor = CIPHERPrimary,
                disabledContainerColor = CIPHERSurface
            )
        ) {
            Icon(
                if (userTier == Tier.FREE) Icons.Default.Lock else Icons.Default.CloudUpload,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(if (userTier == Tier.FREE) "Cloud Backup (Pro)" else "Cloud Backup")
        }
    }
}

private suspend fun exportSettings(context: Context): Boolean = withContext(Dispatchers.IO) {
    try {
        val prefs = context.getSharedPreferences("cipher_settings", Context.MODE_PRIVATE)
        val themePrefs = context.getSharedPreferences("cipher_theme", Context.MODE_PRIVATE)

        val json = JSONObject().apply {
            // Settings
            put("theme", prefs.getString("theme", "DARK"))
            put("default_tab", prefs.getInt("default_tab", 0))
            put("auto_lock", prefs.getString("auto_lock", "FIVE_MIN"))
            put("language", prefs.getString("language", "English"))
            put("auto_rotate", prefs.getBoolean("auto_rotate", true))
            put("playback_speed", prefs.getString("playback_speed", "1x"))
            // Theme
            put("selected_theme", themePrefs.getString("selected_theme", "dark"))
            put("accent_color", themePrefs.getString("accent_color", "SAFFRON"))
            // Metadata
            put("backup_version", 1)
            put("backup_date", System.currentTimeMillis())
        }

        val file = File(context.filesDir, "cipher_backup.json")
        file.writeText(json.toString(2))
        true
    } catch (_: Exception) {
        false
    }
}

private suspend fun importSettings(context: Context): Boolean = withContext(Dispatchers.IO) {
    try {
        val file = File(context.filesDir, "cipher_backup.json")
        if (!file.exists()) return@withContext false

        val json = JSONObject(file.readText())
        val prefs = context.getSharedPreferences("cipher_settings", Context.MODE_PRIVATE).edit()
        val themePrefs = context.getSharedPreferences("cipher_theme", Context.MODE_PRIVATE).edit()

        if (json.has("theme")) prefs.putString("theme", json.getString("theme"))
        if (json.has("default_tab")) prefs.putInt("default_tab", json.getInt("default_tab"))
        if (json.has("auto_lock")) prefs.putString("auto_lock", json.getString("auto_lock"))
        if (json.has("language")) prefs.putString("language", json.getString("language"))
        if (json.has("auto_rotate")) prefs.putBoolean("auto_rotate", json.getBoolean("auto_rotate"))
        if (json.has("selected_theme")) themePrefs.putString("selected_theme", json.getString("selected_theme"))
        if (json.has("accent_color")) themePrefs.putString("accent_color", json.getString("accent_color"))

        prefs.apply()
        themePrefs.apply()
        true
    } catch (_: Exception) {
        false
    }
}

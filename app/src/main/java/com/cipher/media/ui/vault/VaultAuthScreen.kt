package com.cipher.media.ui.vault

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.PinPad
import kotlinx.coroutines.delay

/**
 * Vault authentication screen with PIN entry and biometric support.
 * Sets FLAG_SECURE to prevent screenshots.
 * Implements lockout after failed attempts.
 */
@Composable
fun VaultAuthScreen(
    onAuthSuccess: () -> Unit,
    storedPinHash: String,
    onBiometricRequest: () -> Unit
) {
    val context = LocalContext.current
    var pin by remember { mutableStateOf("") }
    var failedAttempts by remember { mutableIntStateOf(0) }
    var isLockedOut by remember { mutableStateOf(false) }
    var lockoutSeconds by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val maxPinLength = 6

    // Set FLAG_SECURE
    DisposableEffect(Unit) {
        (context as? Activity)?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    // Lockout timer
    LaunchedEffect(isLockedOut) {
        if (isLockedOut) {
            lockoutSeconds = 30
            while (lockoutSeconds > 0) {
                delay(1000)
                lockoutSeconds--
            }
            isLockedOut = false
            errorMessage = null
        }
    }

    // Auto-verify when PIN is complete
    LaunchedEffect(pin) {
        if (pin.length == maxPinLength) {
            val pinHash = pin.hashCode().toString() // Simple hash for Phase 3
            if (pinHash == storedPinHash) {
                failedAttempts = 0
                onAuthSuccess()
            } else {
                failedAttempts++
                pin = ""
                if (failedAttempts >= 3) {
                    isLockedOut = true
                    errorMessage = "Too many attempts. Locked for 30s."
                } else {
                    errorMessage = "Wrong PIN. ${3 - failedAttempts} attempts remaining."
                }
            }
        }
    }

    Scaffold(containerColor = CIPHERBackground) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Shield icon
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = CIPHERPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Unlock Vault",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = CIPHEROnSurface
            )
            Text(
                text = "ENTER YOUR 6-DIGIT PIN",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = CIPHEROnSurfaceVariant
            )

            // Error message
            errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isLockedOut) "Locked: ${lockoutSeconds}s" else msg,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHERError
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PinPad(
                pinLength = pin.length,
                maxLength = maxPinLength,
                onDigit = { digit ->
                    if (!isLockedOut && pin.length < maxPinLength) {
                        pin += digit
                    }
                },
                onBackspace = {
                    if (pin.isNotEmpty()) pin = pin.dropLast(1)
                },
                onBiometric = onBiometricRequest
            )
        }
    }
}

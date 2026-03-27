package com.cipher.media.ui.vault

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.PinPad

/**
 * First-time vault setup: create a 6-digit PIN with visible dot indicators
 * and optional show/hide PIN toggle.
 */
@Composable
fun VaultSetupScreen(
    onSetupComplete: (pinHash: String) -> Unit
) {
    var step by remember { mutableIntStateOf(0) } // 0 = create, 1 = confirm
    var firstPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showPin by remember { mutableStateOf(false) }
    val maxLength = 6

    val currentPin = if (step == 0) firstPin else confirmPin

    // Auto-advance when PIN is full
    LaunchedEffect(firstPin) {
        if (firstPin.length == maxLength && step == 0) step = 1
    }

    LaunchedEffect(confirmPin) {
        if (confirmPin.length == maxLength && step == 1) {
            if (confirmPin == firstPin) {
                // BUG FIX: Use identical SHA-256 algorithm as Auth screen!
                val digest = java.security.MessageDigest.getInstance("SHA-256")
                val hashBytes = digest.digest(confirmPin.toByteArray())
                val shaHash = hashBytes.joinToString("") { "%02x".format(it) }
                
                onSetupComplete(shaHash)
            } else {
                errorMessage = "PINs don't match. Try again."
                confirmPin = ""; firstPin = ""; step = 0
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
            Icon(Icons.Default.Lock, null, modifier = Modifier.size(56.dp), tint = CIPHERPrimary)
            Spacer(Modifier.height(24.dp))

            Text(
                text = if (step == 0) "Create Vault PIN" else "Confirm PIN",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = CIPHEROnSurface
            )
            Text(
                text = if (step == 0) "CHOOSE A 6-DIGIT ENCRYPTION KEY" else "RE-ENTER YOUR PIN",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = CIPHEROnSurfaceVariant
            )

            errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, style = MaterialTheme.typography.bodySmall, color = CIPHERError)
            }

            Spacer(Modifier.height(24.dp))

            // PIN dot/digit indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(maxLength) { index ->
                    if (index < currentPin.length) {
                        if (showPin) {
                            // Show the actual digit
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(CIPHERPrimary.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentPin[index].toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = CIPHERPrimary
                                )
                            }
                        } else {
                            // Filled dot
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .background(CIPHERPrimary, CircleShape)
                            )
                        }
                    } else {
                        // Empty dot
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(CIPHERDivider, CircleShape)
                        )
                    }
                }
            }

            Spacer(Modifier.height(Spacing.sm))

            // Show/hide PIN toggle
            TextButton(onClick = { showPin = !showPin }) {
                Icon(
                    if (showPin) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (showPin) "Hide PIN" else "Show PIN",
                    tint = CIPHERPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(Spacing.xs))
                Text(
                    if (showPin) "Hide PIN" else "Show PIN",
                    color = CIPHERPrimary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            PinPad(
                onDigit = { digit ->
                    if (step == 0 && firstPin.length < maxLength) firstPin += digit
                    else if (step == 1 && confirmPin.length < maxLength) confirmPin += digit
                },
                onBackspace = {
                    if (step == 0 && firstPin.isNotEmpty()) firstPin = firstPin.dropLast(1)
                    else if (step == 1 && confirmPin.isNotEmpty()) confirmPin = confirmPin.dropLast(1)
                },
                onSubmit = { }
            )

            Spacer(Modifier.height(32.dp))

            // Step indicator
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier.height(4.dp).width(if (step == 0) 32.dp else 16.dp)
                        .background(if (step == 0) CIPHERPrimary else CIPHERSurfaceBright, RoundedCornerShape(2.dp))
                )
                Box(
                    modifier = Modifier.height(4.dp).width(if (step == 1) 32.dp else 16.dp)
                        .background(if (step == 1) CIPHERPrimary else CIPHERSurfaceBright, RoundedCornerShape(2.dp))
                )
            }
        }
    }
}

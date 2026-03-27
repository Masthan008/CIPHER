package com.cipher.media.ui.vault

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.PinPad

/**
 * First-time vault setup: create a 6-digit PIN.
 */
@Composable
fun VaultSetupScreen(
    onSetupComplete: (pinHash: String) -> Unit
) {
    var step by remember { mutableIntStateOf(0) } // 0 = create, 1 = confirm
    var firstPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val maxLength = 6

    val currentPin = if (step == 0) firstPin else confirmPin

    // Auto-advance when PIN is full
    LaunchedEffect(firstPin) {
        if (firstPin.length == maxLength && step == 0) {
            step = 1
        }
    }

    LaunchedEffect(confirmPin) {
        if (confirmPin.length == maxLength && step == 1) {
            if (confirmPin == firstPin) {
                onSetupComplete(confirmPin.hashCode().toString())
            } else {
                errorMessage = "PINs don't match. Try again."
                confirmPin = ""
                firstPin = ""
                step = 0
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
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = CIPHERPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall, color = CIPHERError)
            }

            Spacer(modifier = Modifier.height(32.dp))

            PinPad(
                pinLength = currentPin.length,
                maxLength = maxLength,
                onDigit = { digit ->
                    if (step == 0 && firstPin.length < maxLength) {
                        firstPin += digit
                    } else if (step == 1 && confirmPin.length < maxLength) {
                        confirmPin += digit
                    }
                },
                onBackspace = {
                    if (step == 0 && firstPin.isNotEmpty()) firstPin = firstPin.dropLast(1)
                    else if (step == 1 && confirmPin.isNotEmpty()) confirmPin = confirmPin.dropLast(1)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Step indicator
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(if (step == 0) 32.dp else 16.dp)
                        .background(
                            if (step == 0) CIPHERPrimary else CIPHERSurfaceBright,
                            RoundedCornerShape(2.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(if (step == 1) 32.dp else 16.dp)
                        .background(
                            if (step == 1) CIPHERPrimary else CIPHERSurfaceBright,
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}

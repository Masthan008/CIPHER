package com.cipher.media.ui.stealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Fully functional calculator disguise.
 * Secret trigger: enter the vault PIN code then press "=" to open vault.
 */
@Composable
fun CalculatorScreen(
    secretCode: String,
    onVaultTrigger: () -> Unit
) {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var pendingOp by remember { mutableStateOf<String?>(null) }
    var resetOnNext by remember { mutableStateOf(false) }
    var inputBuffer by remember { mutableStateOf("") } // tracks raw digit input for secret code

    fun evaluate(): Double? {
        val a = operand1 ?: return null
        val b = display.toDoubleOrNull() ?: return null
        return when (pendingOp) {
            "+" -> a + b
            "−" -> a - b
            "×" -> a * b
            "÷" -> if (b != 0.0) a / b else null
            "%" -> a * (b / 100.0)
            else -> b
        }
    }

    fun onDigit(d: String) {
        inputBuffer += d
        if (resetOnNext) {
            display = d
            resetOnNext = false
        } else {
            display = if (display == "0") d else display + d
        }
    }

    fun onOperator(op: String) {
        inputBuffer = ""
        operand1 = display.toDoubleOrNull()
        pendingOp = op
        resetOnNext = true
    }

    fun onEquals() {
        // Secret code check: if buffer matches secret code, trigger vault
        if (inputBuffer == secretCode) {
            onVaultTrigger()
            return
        }

        val result = evaluate()
        display = if (result != null) {
            if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                "%.8g".format(result)
            }
        } else {
            "Error"
        }
        operand1 = null
        pendingOp = null
        resetOnNext = true
        inputBuffer = ""
    }

    fun onClear() {
        display = "0"
        operand1 = null
        pendingOp = null
        resetOnNext = false
        inputBuffer = ""
    }

    fun onToggleSign() {
        val value = display.toDoubleOrNull() ?: return
        display = if (-value == (-value).toLong().toDouble()) {
            (-value).toLong().toString()
        } else {
            (-value).toString()
        }
    }

    fun onDecimalPoint() {
        if ("." !in display) {
            display += "."
        }
    }

    val buttons = listOf(
        listOf("C", "±", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "−"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=")
    )

    Scaffold(containerColor = CIPHERBackground) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = display,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Light,
                        fontSize = 64.sp
                    ),
                    color = CIPHEROnSurface,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier.padding(end = 8.dp, bottom = 16.dp)
                )
            }

            // Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                buttons.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { label ->
                            val isOperator = label in listOf("+", "−", "×", "÷", "=")
                            val isFunction = label in listOf("C", "±", "%")
                            val isZero = label == "0"

                            Box(
                                modifier = Modifier
                                    .then(
                                        if (isZero) Modifier.weight(2f) else Modifier.weight(1f)
                                    )
                                    .aspectRatio(if (isZero) 2.2f else 1f)
                                    .clip(if (isZero) RoundedCornerShape(36.dp) else CircleShape)
                                    .background(
                                        when {
                                            isOperator -> CIPHERPrimary
                                            isFunction -> CIPHERSurfaceBright
                                            else -> CIPHERSurfaceVariant
                                        }
                                    )
                                    .clickable {
                                        when {
                                            label == "C" -> onClear()
                                            label == "±" -> onToggleSign()
                                            label == "." -> onDecimalPoint()
                                            label == "=" -> onEquals()
                                            isOperator -> onOperator(label)
                                            else -> onDigit(label)
                                        }
                                    },
                                contentAlignment = if (isZero) Alignment.CenterStart else Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = when {
                                        isOperator -> CIPHEROnPrimary
                                        isFunction -> CIPHEROnSurface
                                        else -> CIPHEROnSurface
                                    },
                                    modifier = if (isZero) Modifier.padding(start = 28.dp) else Modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

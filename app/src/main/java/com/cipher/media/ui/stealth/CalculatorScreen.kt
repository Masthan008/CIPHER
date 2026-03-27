package com.cipher.media.ui.stealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import kotlin.math.*

/**
 * Full calculator disguise. Secret code triggers vault.
 */
@Composable
fun CalculatorScreen(
    onSecretTriggered: () -> Unit,
    viewModel: StealthViewModel
) {
    var display by remember { mutableStateOf("0") }
    var expression by remember { mutableStateOf("") }
    val secretCode = viewModel.secretCode

    val buttons = listOf(
        "C", "(", ")", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "%", "0", ".", "="
    )

    fun onButtonClick(btn: String) {
        when (btn) {
            "C" -> { display = "0"; expression = "" }
            "=" -> {
                // Check secret code
                if (expression == secretCode) {
                    onSecretTriggered()
                    return
                }
                display = try {
                    evaluateExpression(expression)
                } catch (_: Exception) { "Error" }
                expression = ""
            }
            else -> {
                if (display == "0" && btn.first().isDigit()) {
                    display = btn; expression = btn
                } else {
                    display += btn; expression += btn
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Display
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(horizontal = Spacing.lg),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = expression,
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = display,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Light,
                color = CIPHEROnSurface,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(Spacing.md))
        }

        HorizontalDivider(color = CIPHERDivider)

        // Buttons grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(0.7f).padding(Spacing.sm),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            items(buttons) { btn ->
                val isOperator = btn in listOf("+", "-", "*", "/", "=")
                val isFunction = btn in listOf("C", "(", ")", "%")

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(
                            when {
                                isOperator -> CIPHERPrimary
                                isFunction -> CIPHERSurfaceVariant
                                else -> CIPHERSurface
                            }
                        )
                        .clickable { onButtonClick(btn) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = btn,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = when {
                            isOperator -> CIPHEROnPrimary
                            else -> CIPHEROnSurface
                        }
                    )
                }
            }
        }
    }
}

private fun evaluateExpression(expr: String): String {
    return try {
        val result = simpleEval(expr)
        if (result == result.toLong().toDouble()) result.toLong().toString()
        else "%.6f".format(result).trimEnd('0').trimEnd('.')
    } catch (_: Exception) { "Error" }
}

private fun simpleEval(expr: String): Double {
    return ExprParser(expr).parse()
}

private class ExprParser(private val expr: String) {
    private var pos = 0

    fun parse(): Double {
        val result = parseExpr()
        return result
    }

    private fun parseExpr(): Double {
        var left = parseTerm()
        while (pos < expr.length && expr[pos] in "+-") {
            val op = expr[pos++]
            val right = parseTerm()
            left = if (op == '+') left + right else left - right
        }
        return left
    }

    private fun parseTerm(): Double {
        var left = parseFactor()
        while (pos < expr.length && expr[pos] in "*/%") {
            val op = expr[pos++]
            val right = parseFactor()
            left = when (op) { '*' -> left * right; '/' -> left / right; '%' -> left % right; else -> left }
        }
        return left
    }

    private fun parseFactor(): Double {
        if (pos < expr.length && expr[pos] == '(') {
            pos++
            val r = parseExpr()
            if (pos < expr.length && expr[pos] == ')') pos++
            return r
        }
        return parseNumber()
    }

    private fun parseNumber(): Double {
        val start = pos
        if (pos < expr.length && (expr[pos] == '-' || expr[pos] == '+')) pos++
        while (pos < expr.length && (expr[pos].isDigit() || expr[pos] == '.')) pos++
        return expr.substring(start, pos).toDouble()
    }
}


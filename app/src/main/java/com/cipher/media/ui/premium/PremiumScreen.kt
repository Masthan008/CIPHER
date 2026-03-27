package com.cipher.media.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.billing.model.Products
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.ui.theme.*

@Composable
fun PremiumScreen(
    onBack: () -> Unit,
    viewModel: PremiumViewModel = hiltViewModel()
) {
    val activeTier by viewModel.activeTier.collectAsState()
    val context = LocalContext.current
    var selectedPlan by remember { mutableStateOf(1) } // 0=monthly, 1=yearly, 2=power

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
                Text(
                    "Go Premium",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = CIPHEROnSurface
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(CIPHERPrimary, CIPHERSecondary)
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.WorkspacePremium, null, tint = Color.White, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Unlock Full Power", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Remove ads • Premium features • Unlimited vault", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                }
            }

            if (activeTier != SubscriptionTier.FREE) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CIPHERTertiary.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, null, tint = CIPHERTertiary)
                        Spacer(Modifier.width(12.dp))
                        Text("You're on ${activeTier.displayName}", color = CIPHEROnSurface, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Plan Cards
            PlanCard(
                title = "Pro Monthly",
                price = "₹99",
                period = "/month",
                features = listOf("Remove all ads", "10-band EQ", "Hi-Res audio", "10GB vault", "1080p video"),
                selected = selectedPlan == 0,
                highlighted = false,
                onClick = { selectedPlan = 0 }
            )

            PlanCard(
                title = "Pro Yearly",
                price = "₹499",
                period = "/year",
                badge = "SAVE 58%",
                features = listOf("Everything in Pro Monthly", "Cloud sync", "Priority support"),
                selected = selectedPlan == 1,
                highlighted = true,
                onClick = { selectedPlan = 1 }
            )

            PlanCard(
                title = "Power Yearly",
                price = "₹999",
                period = "/year",
                badge = "BEST VALUE",
                features = listOf("Everything in Pro", "Unlimited vault", "4K + HDR", "Family sharing (5)", "Early access features"),
                selected = selectedPlan == 2,
                highlighted = false,
                onClick = { selectedPlan = 2 }
            )

            // Purchase Button
            Button(
                onClick = {
                    val productId = when (selectedPlan) {
                        0 -> Products.PRO_MONTHLY
                        1 -> Products.PRO_YEARLY
                        else -> Products.POWER_YEARLY
                    }
                    (context as? android.app.Activity)?.let { activity ->
                        viewModel.purchase(activity, productId)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CIPHERPrimary,
                    contentColor = CIPHEROnPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "Subscribe Now",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Remove ads one-time
            OutlinedButton(
                onClick = {
                    (context as? android.app.Activity)?.let { activity ->
                        viewModel.purchase(activity, Products.REMOVE_ADS)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Just Remove Ads — ₹199 one-time", color = CIPHEROnSurfaceVariant)
            }

            // Restore
            TextButton(onClick = { viewModel.restorePurchases() }) {
                Text("Restore Purchases", color = CIPHERPrimary)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PlanCard(
    title: String,
    price: String,
    period: String,
    badge: String? = null,
    features: List<String>,
    selected: Boolean,
    highlighted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (selected) Modifier.border(2.dp, CIPHERPrimary, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) CIPHERPrimary.copy(alpha = 0.08f) else CIPHERSurface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(title, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(price, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = CIPHERPrimary)
                        Text(period, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                    }
                }
                if (badge != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CIPHERPrimary
                    ) {
                        Text(
                            badge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = CIPHEROnPrimary
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, null, tint = CIPHERTertiary, modifier = Modifier.size(16.dp))
                    Text(feature, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                }
            }
        }
    }
}

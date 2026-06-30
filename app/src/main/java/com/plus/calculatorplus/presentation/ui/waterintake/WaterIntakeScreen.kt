package com.plus.calculatorplus.presentation.ui.waterintake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plus.calculatorplus.presentation.components.CustomCard2
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.water_full
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import kotlinx.coroutines.launch

private enum class ActivityLevel(val label: String, val multiplier: Double) {
    SEDENTARY("Sedentary", 30.0),
    LIGHT("Light", 35.0),
    MODERATE("Moderate", 40.0),
    ACTIVE("Active", 45.0),
    VERY_ACTIVE("Very Active", 50.0)
}

@Composable
fun WaterIntakeScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    WaterIntakeScreen(
        onBack = { navigator.goBack() },
        modifier = modifier
    )
}

@Composable
fun WaterIntakeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var weight by rememberSaveable { mutableStateOf("70") }
    var selectedActivity by remember { mutableStateOf(ActivityLevel.MODERATE) }
    var result by remember { mutableStateOf("") }

    ScreenScaffold(
        title = "Water Intake",
        subtitle = "Calculate your daily hydration needs",
        icon = water_full,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 14.dp,
                    end = 14.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Your Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )

            CustomCard2(
                modifier = Modifier.fillMaxWidth(),
                text = "Weight",
                endText = "Kg",
                isError = weight.toDoubleOrNull() == null || weight.toDouble() <= 0,
                value = weight,
                onValueChange = { weight = it }
            )

            Text(
                text = "Activity Level",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )

            ActivityLevel.entries.forEach { level ->
                FilterChip(
                    selected = selectedActivity == level,
                    onClick = { selectedActivity = level },
                    label = {
                        Text(
                            text = level.label,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (selectedActivity == level) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val wt = weight.toDoubleOrNull()
                    if (wt != null && wt > 0) {
                        val ml = wt * selectedActivity.multiplier
                        result = if (ml >= 1000) {
                            "%.1f Liters".format(ml / 1000)
                        } else {
                            "%.0f ml".format(ml)
                        }
                        coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                    }
                }
            ) {
                Text(
                    text = "Calculate",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (result.isNotEmpty()) {
                Card(
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Daily Water Intake",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = result,
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Based on ${selectedActivity.label.lowercase()} activity level (${selectedActivity.multiplier}ml per kg)",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WaterIntakeScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            WaterIntakeScreen(
                onBack = {}
            )
        }
    }
}

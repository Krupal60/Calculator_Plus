package com.plus.calculatorplus.presentation.ui.cagr

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plus.calculatorplus.domain.validation.cagrValidation
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.icons.trending_up
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Suppress("MultipleContentEmitters")
@Composable
fun CagrScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: CagrViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CagrScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is CagrEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun CagrScreen(
    state: State<CagrState>,
    onAction: (CagrAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "CAGR Calculator",
        subtitle = "Compound Annual Growth Rate of your investment",
        icon = trending_up,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current
        var initialValue by rememberSaveable { mutableStateOf("10000") }
        var finalValue by rememberSaveable { mutableStateOf("20000") }
        var years by rememberSaveable { mutableStateOf("5") }

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
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Investment Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SliderWithText(
                "Initial Value",
                100, 1000000,
                onValueChange = { initialValue = it },
                actionType = ImeAction.Next,
                prefix = "₹",
                suffix = "",
                isError = !cagrValidation(initialValue, finalValue, years).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            SliderWithText(
                "Final Value",
                100, 5000000,
                onValueChange = { finalValue = it },
                actionType = ImeAction.Next,
                prefix = "₹",
                suffix = "",
                isError = !cagrValidation(initialValue, finalValue, years).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            SliderWithText(
                "Number of Years",
                1, 40,
                onValueChange = { years = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "Yr",
                isError = !cagrValidation(initialValue, finalValue, years).first,
                decimalPlaces = 1
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val validationResult = cagrValidation(initialValue, finalValue, years)
                    if (validationResult.first) {
                        onAction(
                            CagrAction.CalculateCagr(
                                initialValue = initialValue,
                                finalValue = finalValue,
                                years = years
                            )
                        )
                        coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                    } else {
                        Toast.makeText(context, validationResult.second, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(
                    text = "Calculate Results",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            AnimatedVisibility(state.value.cagrPercentage != "0") {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
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
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Growth Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "${state.value.cagrPercentage}%",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            CagrSummaryRow("Initial Value", state.value.initialValue)
                            CagrSummaryRow("Final Value", state.value.finalValue)

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total Gain",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = getMoneyInWords(state.value.totalGain.toDouble()),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CagrSummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = getMoneyInWords(value.toDouble()),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CagrScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    CagrState(
                        cagrPercentage = "14.87",
                        totalGain = "10000",
                        initialValue = "10000",
                        finalValue = "20000"
                    )
                )
            }
            CagrScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

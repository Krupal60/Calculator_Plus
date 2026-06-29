package com.plus.calculatorplus.presentation.ui.retirement

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
import androidx.compose.foundation.shape.CircleShape
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
import com.plus.calculatorplus.domain.validation.retirementValidation
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.icons.retirement
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Suppress("MultipleContentEmitters")
@Composable
fun RetirementScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: RetirementViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    RetirementScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is RetirementEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun RetirementScreen(
    state: State<RetirementState>,
    onAction: (RetirementAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "Retirement Calculator",
        subtitle = "Calculate corpus needed for your future",
        icon = retirement,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current
        var currentAge by rememberSaveable { mutableStateOf("22") }
        var retirementAge by rememberSaveable { mutableStateOf("50") }
        var monthlySavings by rememberSaveable { mutableStateOf("10000") }
        var expectedReturnRate by rememberSaveable { mutableStateOf("10") }
        var currentSavings by rememberSaveable { mutableStateOf("0") }

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
                "Current Age",
                22, 100,
                onValueChange = { currentAge = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "Yr",
                isError = !retirementValidation(
                    currentAge,
                    retirementAge,
                    monthlySavings,
                    expectedReturnRate
                ).first
            )

            SliderWithText(
                "Retirement Age",
                50, 100,
                onValueChange = { retirementAge = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "Yr",
                isError = !retirementValidation(
                    currentAge,
                    retirementAge,
                    monthlySavings,
                    expectedReturnRate
                ).first
            )

            SliderWithText(
                "Monthly Investment",
                10000, 1000000,
                onValueChange = { monthlySavings = it },
                actionType = ImeAction.Done,
                prefix = "₹",
                suffix = "",
                isError = !retirementValidation(
                    currentAge,
                    retirementAge,
                    monthlySavings,
                    expectedReturnRate
                ).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            SliderWithText(
                "Expected Return Rate (p.a)",
                10, 30,
                onValueChange = { expectedReturnRate = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "%",
                isError = !retirementValidation(
                    currentAge,
                    retirementAge,
                    monthlySavings,
                    expectedReturnRate
                ).first
            )

            SliderWithText(
                "Current Savings",
                0, 10000000,
                onValueChange = { currentSavings = it },
                actionType = ImeAction.Done,
                prefix = "₹",
                suffix = "",
                isError = !retirementValidation(
                    currentAge,
                    retirementAge,
                    monthlySavings,
                    expectedReturnRate
                ).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val validationResult = retirementValidation(
                        currentAge,
                        retirementAge,
                        monthlySavings,
                        expectedReturnRate
                    )
                    if (validationResult.first) {
                        onAction(
                            RetirementAction.CalculateRetirement(
                                currentAge = currentAge,
                                retirementAge = retirementAge,
                                monthlySavings = monthlySavings,
                                expectedReturnRate = expectedReturnRate,
                                currentSavings = currentSavings
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

            AnimatedVisibility(state.value.retirementCorpus != "0") {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Plan Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            RetirementSummaryRow("Total Invested", state.value.totalInvested)
                            RetirementSummaryRow("Estimated Returns", state.value.estimatedReturns)

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Retirement Corpus",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = getMoneyInWords(state.value.retirementCorpus.toDouble()),
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
private fun RetirementSummaryRow(label: String, value: String) {
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
private fun RetirementScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    RetirementState(
                        retirementCorpus = "5000000",
                        totalInvested = "2000000",
                        estimatedReturns = "3000000"
                    )
                )
            }
            RetirementScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

package com.plus.calculatorplus.presentation.ui.retirement

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.retirementValidation
import kotlinx.coroutines.launch

@Suppress("MultipleContentEmitters")
@Composable
fun RetirementScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: RetirementViewModel = viewModel()
) {
    ScreenScaffold(
        title = "Retirement Calculator",
        showBack = true,
        onBack = { navigator.goBack() },
        modifier = modifier
    ) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        RetirementScreen(paddingValues, state, viewModel::onAction)
    }
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is RetirementEffect.ShowToast -> {
                Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun RetirementScreen(
    paddingValues: PaddingValues,
    state: State<RetirementState>,
    onAction: (RetirementAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    var currentAge by rememberSaveable { mutableStateOf("22") }
    var retirementAge by rememberSaveable { mutableStateOf("50") }
    var monthlySavings by rememberSaveable { mutableStateOf("10000") }
    var expectedReturnRate by rememberSaveable { mutableStateOf("10") }
    var currentSavings by rememberSaveable { mutableStateOf("0") }

    Column(
        modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 14.dp,
                end = 14.dp
            ), verticalArrangement = Arrangement.Top
    ) {

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

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val validationResult =
                retirementValidation(currentAge, retirementAge, monthlySavings, expectedReturnRate)
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
                Toast.makeText(context, validationResult.second, Toast.LENGTH_SHORT)
                    .show()
            }
        }) {
            CustomText(
                modifier = Modifier.padding(vertical = 2.dp),
                title = "Calculate",
                size = 14.sp
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.LightGray
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Retirement Corpus")
                    Text(text = getMoneyInWords(state.value.retirementCorpus.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Invested")
                    Text(text = getMoneyInWords(state.value.totalInvested.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Estimated Returns")
                    Text(text = getMoneyInWords(state.value.estimatedReturns.toDouble()))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RetirementScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(RetirementState()) }
            RetirementScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

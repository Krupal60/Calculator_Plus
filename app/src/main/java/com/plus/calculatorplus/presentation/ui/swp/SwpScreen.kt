package com.plus.calculatorplus.presentation.ui.swp

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
import com.plus.calculatorplus.presentation.validation.swpValidation
import kotlinx.coroutines.launch

@Suppress("MultipleContentEmitters")
@Composable
fun SwpScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SwpViewModel = viewModel()
) {
    ScreenScaffold(
        title = "SWP Calculator",
        showBack = true,
        onBack = { navigator.goBack() }) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        SwpScreen(paddingValues, state, viewModel::onAction, modifier)
    }
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is SwpEffect.ShowToast -> {
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
fun SwpScreen(
    paddingValues: PaddingValues,
    state: State<SwpState>,
    onAction: (SwpAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    var totalInvestment by rememberSaveable { mutableStateOf("100000") }
    var withdrawalPerMonth by rememberSaveable { mutableStateOf("1000") }
    var expectedReturnRate by rememberSaveable { mutableStateOf("10") }
    var timePeriodYears by rememberSaveable { mutableStateOf("5") }

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
            "Total Investment",
            100000, 10000000,
            onValueChange = { totalInvestment = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !swpValidation(
                totalInvestment,
                withdrawalPerMonth,
                expectedReturnRate,
                timePeriodYears
            ).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Withdrawal Per Month",
            1000, 500000,
            onValueChange = { withdrawalPerMonth = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !swpValidation(
                totalInvestment,
                withdrawalPerMonth,
                expectedReturnRate,
                timePeriodYears
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
            isError = !swpValidation(
                totalInvestment,
                withdrawalPerMonth,
                expectedReturnRate,
                timePeriodYears
            ).first
        )

        SliderWithText(
            "Time Period (Years)",
            5, 50,
            onValueChange = { timePeriodYears = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "Yr",
            isError = !swpValidation(
                totalInvestment,
                withdrawalPerMonth,
                expectedReturnRate,
                timePeriodYears
            ).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val validationResult = swpValidation(
                totalInvestment,
                withdrawalPerMonth,
                expectedReturnRate,
                timePeriodYears
            )
            if (validationResult.first) {
                onAction(
                    SwpAction.CalculateSwp(
                        totalInvestment = totalInvestment,
                        withdrawalPerMonth = withdrawalPerMonth,
                        expectedReturnRate = expectedReturnRate,
                        timePeriodYears = timePeriodYears
                    )
                )
                coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
            } else {
                Toast.makeText(context, validationResult.second, Toast.LENGTH_SHORT)
                    .show()
            }
        }) {
            CustomText(
                modifier = Modifier.padding(vertical = 2.dp), title = "Calculate",
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
                    Text(text = "Total Investment")
                    Text(text = getMoneyInWords(state.value.totalInvestment.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Withdrawal")
                    Text(text = getMoneyInWords(state.value.totalWithdrawal.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Final Value")
                    Text(text = getMoneyInWords(state.value.finalValue.toDouble()))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwpScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(SwpState()) }
            SwpScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

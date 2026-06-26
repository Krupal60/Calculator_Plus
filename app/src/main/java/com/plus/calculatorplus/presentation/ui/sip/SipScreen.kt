package com.plus.calculatorplus.presentation.ui.sip

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.plus.calculatorplus.presentation.components.CustomSelectionCard
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.PieChart
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.validation.inflationValidation
import com.plus.calculatorplus.presentation.validation.interestRateValidation
import com.plus.calculatorplus.presentation.validation.lumsumValidation
import com.plus.calculatorplus.presentation.validation.monthlyValidation
import com.plus.calculatorplus.presentation.validation.sipValidation
import com.plus.calculatorplus.presentation.validation.stepUpValidation
import com.plus.calculatorplus.presentation.validation.yearsValidation
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import kotlinx.coroutines.launch


@Composable
fun SipScreenMain(navigator: Navigator, viewModel: SipViewModel = viewModel()) {
    ScreenScaffold(
        title = "SIP Calculator",
        showBack = true,
        onBack = { navigator.goBack() }) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        SipScreen(paddingValues, state, viewModel::onAction)
    }
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is SipEffect.ShowToast -> {
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
fun SipScreen(
    paddingValues: PaddingValues,
    state: State<SipState>,
    onAction: (SipAction) -> Unit
) {
    var monthlyInvestment by rememberSaveable { mutableStateOf("100") }
    var lumsumInvestment by rememberSaveable { mutableStateOf("500") }
    var interestRate by rememberSaveable { mutableStateOf("12") }
    var investmentYears by rememberSaveable { mutableStateOf("5") }
    var lumSum by rememberSaveable { mutableStateOf(false) }
    var withInflation by rememberSaveable { mutableStateOf(false) }
    var inflationRate by rememberSaveable { mutableStateOf("6") }
    var withStepUp by rememberSaveable { mutableStateOf(false) }
    var stepUpPercentage by rememberSaveable { mutableStateOf("5") }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = 10.dp,
                start = 14.dp,
                end = 14.dp
            ), verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            CustomSelectionCard(
                onClick = { lumSum = false },
                "Sip Mf fund",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                backgroundColor = if (!lumSum) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
            CustomSelectionCard(
                onClick = {
                    lumSum = true
                    withStepUp = false
                },
                "Lumsum fund",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                backgroundColor = if (lumSum) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            CustomSelectionCard(
                onClick = { withInflation = !withInflation },
                "With Inflation",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                backgroundColor = if (withInflation) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
            CustomSelectionCard(
                onClick = {
                    if (!lumSum) {
                        withStepUp = !withStepUp
                    } else {
                        Toast.makeText(
                            context,
                            "Step Up is Not Available For Lumsum",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                "Step up SIP",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                backgroundColor = if (withStepUp) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
        }

        AnimatedVisibility(visible = !lumSum) {
            SliderWithText(
                "Monthly Amount  (in Rs.)",
                100, 800000,
                onValueChange = { monthlyInvestment = it },
                actionType = ImeAction.Done,
                prefix = "₹",
                suffix = "",
                isError = !monthlyValidation(monthlyAmount = monthlyInvestment).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )
        }
        AnimatedVisibility(visible = lumSum) {
            SliderWithText(
                "Lumsum Amount (in Rs.)",
                500, 1000000,
                onValueChange = { lumsumInvestment = it },
                actionType = ImeAction.Done,
                prefix = "₹",
                suffix = "",
                isError = !lumsumValidation(lumSum, lumsumInvestment).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )
        }

        AnimatedVisibility(visible = withInflation) {
            SliderWithText(
                "Inflation Rate (Annual)",
                1, 20,
                onValueChange = { inflationRate = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "%",
                isError = !inflationValidation(inflationRate, withInflation).first
            )
        }

        AnimatedVisibility(visible = withStepUp && !lumSum) {
            SliderWithText(
                "Step up Percentage",
                1, 50,
                onValueChange = { stepUpPercentage = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "%",
                isError = !stepUpValidation(stepUpPercentage, withStepUp).first
            )
        }

        SliderWithText(
            "Interest Rate  (Annual)",
            12, 45,
            onValueChange = { interestRate = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "%",
            isError = !interestRateValidation(interestRate).first
        )
        SliderWithText(
            "Investment Years",
            5, 40,
            onValueChange = { investmentYears = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "Yr",
            isError = !yearsValidation(investmentYears).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            when (sipValidation(
                lumSum, monthlyInvestment, lumsumInvestment, interestRate,
                investmentYears, withInflation, inflationRate, withStepUp, stepUpPercentage
            ).first) {
                true -> {
                    onAction(
                        SipAction.CalculateSip(
                            isLumsum = lumSum,
                            monthlyAmount = monthlyInvestment,
                            lumsumAmount = lumsumInvestment,
                            interest = interestRate,
                            investedYears = investmentYears,
                            withInflation = withInflation,
                            inflationRate = inflationRate,
                            withStepUp = withStepUp,
                            stepUpPercentage = stepUpPercentage
                        )
                    )
                    coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                }

                false -> {
                    Toast.makeText(
                        context,
                        sipValidation(
                            lumSum,
                            monthlyInvestment,
                            lumsumInvestment,
                            interestRate,
                            investmentYears,
                            withInflation,
                            inflationRate,
                            withStepUp,
                            stepUpPercentage
                        ).second,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }) {
            CustomText(modifier = Modifier.padding(vertical = 2.dp), "Calculate", 14.sp)
        }
        AnimatedVisibility(visible = state.value.estimateReturnsAmount.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    PieChart(
                        data = mapOf(
                            Pair("Invested Amount", state.value.investedAmount.toLong()),
                            Pair("Estimate Returns", state.value.estimateReturnsAmount.toLong()),
                            Pair("Total Amount", state.value.totalAmount.toLong())
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SipScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(SipState()) }
            SipScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

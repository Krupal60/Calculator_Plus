package com.plus.calculatorplus.presentation.ui

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.Calculator
import com.plus.calculatorplus.data.model.swp.OnSwpAction
import com.plus.calculatorplus.data.model.swp.SwpDetailState
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.swpValidation
import com.plus.calculatorplus.viewmodel.SwpViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun SwpScreenMain(paddingValues: PaddingValues, viewModel: SwpViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    SwpScreen(paddingValues, state, viewModel::onAction)
}

@Composable
fun SwpScreen(
    paddingValues: PaddingValues,
    state: State<SwpDetailState>,
    onAction: (OnSwpAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    var totalInvestment by rememberSaveable { mutableStateOf("100000") }
    var withdrawalPerMonth by rememberSaveable { mutableStateOf("1000") }
    var expectedReturnRate by rememberSaveable { mutableStateOf("10") }
    var timePeriodYears by rememberSaveable { mutableStateOf("5") }

    Column(
        Modifier
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
            totalInvestment.toInt(), 10000000,
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
            withdrawalPerMonth.toInt(), 500000,
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
            expectedReturnRate.toInt(), 30,
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
            timePeriodYears.toInt(), 50,
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
                    OnSwpAction.CalculateSwp(
                        totalInvestment = totalInvestment,
                        withdrawalPerMonth = withdrawalPerMonth,
                        expectedReturnRate = expectedReturnRate,
                        timePeriodYears = timePeriodYears
                    )
                )
                coroutineScope.launch {
                    scrollState.animateScrollTo(Int.MAX_VALUE)
                }
            } else {
                Toast.makeText(
                    Calculator.calculator,
                    validationResult.second,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            CustomText(
                modifier = Modifier.padding(vertical = 2.dp),
                "Calculate",
                14.ssp
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

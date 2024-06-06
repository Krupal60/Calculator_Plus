package com.plus.calculatorplus.presentation.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.Calculator
import com.plus.calculatorplus.data.model.sip.OnSipAction
import com.plus.calculatorplus.data.model.sip.SipDetailState
import com.plus.calculatorplus.presentation.components.CustomSelectionCard
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.PieChart
import com.plus.calculatorplus.presentation.components.sliderWithText
import com.plus.calculatorplus.presentation.validation.interestRateValidation
import com.plus.calculatorplus.presentation.validation.lumsumValidation
import com.plus.calculatorplus.presentation.validation.monthlyValidation
import com.plus.calculatorplus.presentation.validation.sipValidation
import com.plus.calculatorplus.presentation.validation.yearsValidation
import com.plus.calculatorplus.viewmodel.SipViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


@Composable
fun SipScreenMain(paddingValues: PaddingValues,viewModel : SipViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    SipScreen(paddingValues,state,viewModel::onAction)
}


@Composable
fun SipScreen(
    paddingValues: PaddingValues,
    state: State<SipDetailState>,
    onAction: (OnSipAction) -> Unit
) {
    val monthlyInvestment = remember { mutableStateOf("100") }
    val lumsumInvestment = remember { mutableStateOf("6") }
    val interestRate = remember { mutableStateOf("12") }
    val investmentYears = remember { mutableStateOf("5") }
    var lumSum by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    Column(
        modifier = Modifier
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
                onClick = {
                    lumSum = false
                },
                "Sip Mf fund",
                modifier = Modifier.weight(1f).padding(end = 10.dp),
                backgroundColor = if (!lumSum) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
            CustomSelectionCard(
                onClick = {
                    lumSum = true
                },
                "Lumsum fund",
                modifier = Modifier.weight(1f).padding(start = 10.dp),
                backgroundColor = if (lumSum) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface
            )
        }
        AnimatedVisibility(visible = !lumSum) {
            monthlyInvestment.value = sliderWithText(
                "Monthly Amount  (in Rs.)",
                100, 800000,
                actionType = ImeAction.Done,
                preffix = "₹",
                suffix = "",
                isError = !monthlyValidation(monthlyAmount = monthlyInvestment.value).first
            )
        }
        AnimatedVisibility(visible = lumSum) {
            lumsumInvestment.value = sliderWithText(
                "Lumsum Amount (in Rs.)",
                500, 1000000,
                actionType = ImeAction.Done,
                preffix = "₹",
                suffix = "",
                isError = !lumsumValidation(lumSum, lumsumInvestment.value).first
            )
        }

        interestRate.value = sliderWithText(
            "Interest Rate  (Annual)",
            12, 45,
            actionType = ImeAction.Done,
            preffix = "",
            suffix = "%",
            isError = !interestRateValidation(interestRate.value).first
        )
        investmentYears.value = sliderWithText(
            "Investment Years",
            5,
            40,
            actionType = ImeAction.Done,
            preffix = "",
            suffix = "Yr",
            isError = !yearsValidation(investmentYears.value).first
        )


        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            when (sipValidation(
                lumSum,
                monthlyInvestment.value,
                lumsumInvestment.value,
                interestRate.value,
                investmentYears.value
            ).first

            ) {
                true -> {
                    onAction(
                        OnSipAction.CalculateSip(
                            isLumsum = lumSum,
                            monthlyAmount = monthlyInvestment.value,
                            lumsumAmount = lumsumInvestment.value,
                            interest = interestRate.value,
                            investedYears = investmentYears.value
                        )
                    )
                    coroutineScope.launch {
                        scrollState.animateScrollTo(Int.MAX_VALUE)
                    }

                }

                false -> {
                    Toast.makeText(
                        Calculator.calculator, sipValidation(
                            lumSum,
                            monthlyInvestment.value,
                            lumsumInvestment.value,
                            interestRate.value,
                            investmentYears.value
                        ).second, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }) {
            CustomText(
                modifier = Modifier.padding(vertical = 2.dp),
                "Calculate",
               14.ssp
            )
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
                            Pair("Total Amount", state.value.totalAmount.toLong()),                    ))

                    }

            }

        }

    }
}

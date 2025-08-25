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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.data.model.emi.EmiDetailState
import com.plus.calculatorplus.data.model.emi.OnEmiAction
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.sliderWithText
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.emiValidation
import com.plus.calculatorplus.presentation.validation.loanAmountValidation
import com.plus.calculatorplus.presentation.validation.loanInterestRateValidation
import com.plus.calculatorplus.presentation.validation.yearsValidation
import com.plus.calculatorplus.viewmodel.EmiViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun EmiScreenMain(paddingValues: PaddingValues, viewModel: EmiViewModel = viewModel()) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    EmiScreen(paddingValues, state,viewModel::onAction)
}

@Composable
fun EmiScreen(
    paddingValues: PaddingValues, state: State<EmiDetailState>,
    onAction: (OnEmiAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    val loanAmount = remember { mutableStateOf("10000") }
    val interestRate = remember { mutableStateOf("2") }
    val loanYears = remember { mutableStateOf("1") }

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

        loanAmount.value = sliderWithText(
            "loan Amount",
            10000, 10000000,
            actionType = ImeAction.Done,
            preffix = "₹",
            suffix = "",
            isError = !loanAmountValidation(loanAmount.value).first
        )

        interestRate.value = sliderWithText(
            "Interest Rate (p.a)",
            4, 35,
            actionType = ImeAction.Done,
            preffix = "",
            suffix = "%",
            isError = !loanInterestRateValidation(interestRate.value).first
        )

        loanYears.value = sliderWithText(
            "loan Years",
            1,
            35,
            actionType = ImeAction.Done,
            preffix = "",
            suffix = "Yr",
            isError = !yearsValidation(loanYears.value).first
        )


        Button(modifier = Modifier.fillMaxWidth(), onClick = {

            when (
                emiValidation(
                    loanAmount.value,
                    interestRate.value,
                    loanYears.value
                ).first

            ) {
                true -> {
                    onAction(
                        OnEmiAction.CalculateEmi(
                            loanAmount = loanAmount.value,
                            loanInterest = interestRate.value,
                            loanYear = loanYears.value
                        )
                    )
                    coroutineScope.launch {
                        scrollState.animateScrollTo(Int.MAX_VALUE)
                    }

                }

                false -> {
                    Toast.makeText(
                        com.plus.calculatorplus.Calculator.calculator, emiValidation(
                            loanAmount.value,
                            interestRate.value,
                            loanYears.value
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

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.LightGray
            )
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Monthly EMI")
                    Text(text = getMoneyInWords(state.value.monthlyEmi.toDouble()))
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Loan Amount")
                    Text(text = getMoneyInWords(state.value.loanAmount.toDouble()))
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Interest")
                    Text(text = getMoneyInWords(state.value.totalInterest.toDouble()))
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Amount")
                    Text(text = getMoneyInWords(state.value.totalAmount.toDouble()))
                }
            }
        }


    }
}
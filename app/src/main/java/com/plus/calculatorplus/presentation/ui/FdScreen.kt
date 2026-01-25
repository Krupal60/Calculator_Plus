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
import com.plus.calculatorplus.data.model.fd.FdDetailState
import com.plus.calculatorplus.data.model.fd.OnFdAction
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.fdValidation
import com.plus.calculatorplus.viewmodel.FdViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun FdScreenMain(paddingValues: PaddingValues, viewModel: FdViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    FdScreen(paddingValues, state, viewModel::onAction)
}

@Composable
fun FdScreen(
    paddingValues: PaddingValues,
    state: State<FdDetailState>,
    onAction: (OnFdAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    var investmentAmount by rememberSaveable { mutableStateOf("10000") }
    var interestRate by rememberSaveable { mutableStateOf("7") }
    var years by rememberSaveable { mutableStateOf("5") }

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
            "Investment Amount",
            1000, 10000000,
            onValueChange = { investmentAmount = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !fdValidation(investmentAmount, interestRate, years).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Rate of Interest (p.a)",
            1, 20,
            onValueChange = { interestRate = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "%",
            isError = !fdValidation(investmentAmount, interestRate, years).first
        )

        SliderWithText(
            "Time Period (Years)",
            1, 30,
            onValueChange = { years = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "Yr",
            isError = !fdValidation(investmentAmount, interestRate, years).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val validationResult = fdValidation(investmentAmount, interestRate, years)
            if (validationResult.first) {
                onAction(
                    OnFdAction.CalculateFd(
                        investmentAmount = investmentAmount,
                        annualInterestRate = interestRate,
                        years = years
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
                    Text(text = "Invested Amount")
                    Text(text = getMoneyInWords(state.value.totalInvestment.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Estimated Returns")
                    Text(text = getMoneyInWords(state.value.estimatedReturns.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Value")
                    Text(text = getMoneyInWords(state.value.totalValue.toDouble()))
                }
            }
        }
    }
}

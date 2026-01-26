package com.plus.calculatorplus.presentation.ui

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
import com.plus.calculatorplus.data.model.dividend.DividendDetailState
import com.plus.calculatorplus.data.model.dividend.OnDividendAction
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.dividendValidation
import com.plus.calculatorplus.viewmodel.DividendViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun DividendScreenMain(paddingValues: PaddingValues, viewModel: DividendViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    DividendScreen(paddingValues, state, viewModel::onAction)
}

@Composable
fun DividendScreen(
    paddingValues: PaddingValues,
    state: State<DividendDetailState>,
    onAction: (OnDividendAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    var sharePrice by rememberSaveable { mutableStateOf("10000") }
    var dividendPerShare by rememberSaveable { mutableStateOf("200") }
    var numberOfShares by rememberSaveable { mutableStateOf("10") }

    Column(
        Modifier
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
            "Share Price",
            10000, 100000,
            onValueChange = { sharePrice = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !dividendValidation(sharePrice, dividendPerShare, numberOfShares).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Dividend Per Share",
            200, 10000,
            onValueChange = { dividendPerShare = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !dividendValidation(sharePrice, dividendPerShare, numberOfShares).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Number of Shares",
            10, 10000,
            onValueChange = { numberOfShares = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "",
            isError = !dividendValidation(sharePrice, dividendPerShare, numberOfShares).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val validationResult = dividendValidation(sharePrice, dividendPerShare, numberOfShares)
            if (validationResult.first) {
                onAction(
                    OnDividendAction.CalculateDividend(
                        sharePrice = sharePrice,
                        dividendPerShare = dividendPerShare,
                        numberOfShares = numberOfShares
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
                    Text(text = "Total Dividend")
                    Text(text = getMoneyInWords(state.value.totalDividend.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Dividend Yield")
                    Text(text = "${state.value.dividendYield}%")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Annual Dividend")
                    Text(text = getMoneyInWords(state.value.annualDividend.toDouble()))
                }
            }
        }
    }
}

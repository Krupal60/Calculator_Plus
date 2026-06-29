package com.plus.calculatorplus.presentation.ui.emi

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
import com.plus.calculatorplus.presentation.validation.emiValidation
import com.plus.calculatorplus.presentation.validation.loanAmountValidation
import com.plus.calculatorplus.presentation.validation.loanInterestRateValidation
import com.plus.calculatorplus.presentation.validation.yearsValidation
import kotlinx.coroutines.launch

@Suppress("MultipleContentEmitters")
@Composable
fun EmiScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: EmiViewModel = viewModel()
) {
    ScreenScaffold(
        title = "EMI Calculator",
        showBack = true,
        onBack = { navigator.goBack() },
        modifier = modifier
    ) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        EmiScreen(paddingValues, state, viewModel::onAction)
    }
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is EmiEffect.ShowToast -> {
                Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmiScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(EmiState()) }
            EmiScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

@Composable
fun EmiScreen(
    paddingValues: PaddingValues, state: State<EmiState>,
    onAction: (EmiAction) -> Unit, modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    var loanAmount by rememberSaveable { mutableStateOf("10000") }
    var interestRate by rememberSaveable { mutableStateOf("2") }
    var loanYears by rememberSaveable { mutableStateOf("1") }

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
            "loan Amount",
            10000, 10000000,
            onValueChange = { loanAmount = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !loanAmountValidation(loanAmount).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Interest Rate (p.a)",
            4, 35,
            onValueChange = { interestRate = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "%",
            isError = !loanInterestRateValidation(interestRate).first
        )

        SliderWithText(
            "loan Years",
            1, 35,
            onValueChange = { loanYears = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "Yr",
            isError = !yearsValidation(loanYears).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            when (emiValidation(loanAmount, interestRate, loanYears).first) {
                true -> {
                    onAction(
                        EmiAction.CalculateEmi(
                            loanAmount = loanAmount,
                            loanInterest = interestRate,
                            loanYear = loanYears
                        )
                    )
                    coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                }

                false -> {
                    Toast.makeText(
                        context,
                        emiValidation(loanAmount, interestRate, loanYears).second,
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                    Text(text = "Monthly EMI")
                    Text(text = getMoneyInWords(state.value.monthlyEmi.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Loan Amount")
                    Text(text = getMoneyInWords(state.value.loanAmount.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Interest")
                    Text(text = getMoneyInWords(state.value.totalInterest.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Amount")
                    Text(text = getMoneyInWords(state.value.totalAmount.toDouble()))
                }
            }
        }
    }
}

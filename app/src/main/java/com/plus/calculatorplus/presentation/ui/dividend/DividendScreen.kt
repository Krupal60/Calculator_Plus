package com.plus.calculatorplus.presentation.ui.dividend

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
import com.plus.calculatorplus.presentation.validation.dividendValidation
import kotlinx.coroutines.launch

@Suppress("MultipleContentEmitters")
@Composable
fun DividendScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: DividendViewModel = viewModel()
) {
    ScreenScaffold(
        title = "Dividend Calculator",
        showBack = true,
        onBack = { navigator.goBack() },
        modifier = modifier
    ) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        DividendScreen(paddingValues, state, viewModel::onAction)
    }
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is DividendEffect.ShowToast -> {
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
fun DividendScreen(
    paddingValues: PaddingValues,
    state: State<DividendState>,
    onAction: (DividendAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    var sharePrice by rememberSaveable { mutableStateOf("10000") }
    var dividendPerShare by rememberSaveable { mutableStateOf("200") }
    var numberOfShares by rememberSaveable { mutableStateOf("10") }

    Column(
        modifier = modifier
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
                    DividendAction.CalculateDividend(
                        sharePrice = sharePrice,
                        dividendPerShare = dividendPerShare,
                        numberOfShares = numberOfShares
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

@Preview(showBackground = true)
@Composable
private fun DividendScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(DividendState()) }
            DividendScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

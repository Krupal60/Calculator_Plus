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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.Calculator
import com.plus.calculatorplus.data.model.discount.DiscountDetailState
import com.plus.calculatorplus.data.model.discount.OnDiscountAction
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.presentation.validation.discountValidation
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import com.plus.calculatorplus.viewmodel.DiscountViewModel
import kotlinx.coroutines.launch

@Composable
fun DiscountScreenMain(navigator: Navigator, viewModel: DiscountViewModel = viewModel()) {
    ScreenScaffold(
        title = "Discount Calculator",
        showBack = true,
        onBack = { navigator.goBack() }) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        DiscountScreen(paddingValues, state, viewModel::onAction)
    }
}

@Composable
fun DiscountScreen(
    paddingValues: PaddingValues,
    state: State<DiscountDetailState>,
    onAction: (OnDiscountAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    var originalPrice by rememberSaveable { mutableStateOf("1000") }
    var discountPercentage by rememberSaveable { mutableStateOf("10") }

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
            "Original Price",
            100, 1000000,
            onValueChange = { originalPrice = it },
            actionType = ImeAction.Done,
            prefix = "₹",
            suffix = "",
            isError = !discountValidation(originalPrice, discountPercentage).first,
            visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
        )

        SliderWithText(
            "Discount (%)",
            1, 100,
            onValueChange = { discountPercentage = it },
            actionType = ImeAction.Done,
            prefix = "",
            suffix = "%",
            isError = !discountValidation(originalPrice, discountPercentage).first
        )

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val validationResult = discountValidation(originalPrice, discountPercentage)
            if (validationResult.first) {
                onAction(
                    OnDiscountAction.CalculateDiscount(
                        originalPrice = originalPrice,
                        discountPercentage = discountPercentage
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
                14.sp
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
                    Text(text = "Final Price")
                    Text(text = getMoneyInWords(state.value.finalPrice.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Savings")
                    Text(text = getMoneyInWords(state.value.savings.toDouble()))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Original Price")
                    Text(text = getMoneyInWords(state.value.originalPrice.toDouble()))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscountScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(DiscountDetailState()) }
            DiscountScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}

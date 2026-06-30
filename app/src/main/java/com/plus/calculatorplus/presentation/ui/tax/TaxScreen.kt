@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.plus.calculatorplus.presentation.ui.tax

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plus.calculatorplus.domain.validation.taxValidation
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.icons.request_quote
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Suppress("MultipleContentEmitters")
@Composable
fun TaxScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: TaxViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    TaxScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is TaxEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun TaxScreen(
    state: State<TaxState>,
    onAction: (TaxAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "Tax / GST Calculator",
        subtitle = "Add or remove GST and estimate tax",
        icon = request_quote,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current
        var amount by rememberSaveable { mutableStateOf("1000") }
        var rate by rememberSaveable { mutableStateOf("18") }
        var removeGst by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 14.dp,
                    end = 14.dp
                ),
            verticalArrangement = Arrangement.Top
        ) {

            ButtonGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                overflowIndicator = { ButtonGroupDefaults.OverflowIndicator(it) }
            ) {
                toggleableItem(
                    checked = !removeGst,
                    onCheckedChange = { removeGst = false },
                    label = "Add GST",
                    weight = 1f
                )
                toggleableItem(
                    checked = removeGst,
                    onCheckedChange = { removeGst = true },
                    label = "Remove GST",
                    weight = 1f
                )
            }

            Text(
                text = "Amount Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            SliderWithText(
                if (removeGst) "Gross Amount (incl. GST)" else "Base Amount",
                1, 1000000,
                onValueChange = { amount = it },
                actionType = ImeAction.Next,
                prefix = "₹",
                suffix = "",
                isError = !taxValidation(amount, rate).first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            SliderWithText(
                "Tax / GST Rate",
                1, 100,
                onValueChange = { rate = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "%",
                isError = !taxValidation(amount, rate).first,
                decimalPlaces = 1
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val validationResult = taxValidation(amount, rate)
                    if (validationResult.first) {
                        onAction(
                            TaxAction.CalculateTax(
                                amount = amount,
                                rate = rate,
                                removeGst = removeGst
                            )
                        )
                        coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                    } else {
                        Toast.makeText(context, validationResult.second, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(
                    text = "Calculate Results",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            AnimatedVisibility(state.value.totalAmount != "0") {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tax Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            TaxSummaryRow("Base Amount", state.value.baseAmount)
                            TaxSummaryRow("Tax / GST", state.value.taxAmount)

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total Amount",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = getMoneyInWords(state.value.totalAmount.toDouble()),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaxSummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = getMoneyInWords(value.toDouble()),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaxScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    TaxState(
                        baseAmount = "1000",
                        taxAmount = "180",
                        totalAmount = "1180"
                    )
                )
            }
            TaxScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

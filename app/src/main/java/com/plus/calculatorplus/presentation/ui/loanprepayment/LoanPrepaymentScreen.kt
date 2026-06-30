package com.plus.calculatorplus.presentation.ui.loanprepayment

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.plus.calculatorplus.domain.validation.loanPrepaymentValidation
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.icons.acute
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Suppress("MultipleContentEmitters")
@Composable
fun LoanPrepaymentScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: LoanPrepaymentViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LoanPrepaymentScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is LoanPrepaymentEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun LoanPrepaymentScreen(
    state: State<LoanPrepaymentState>,
    onAction: (LoanPrepaymentAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "Loan Prepayment",
        subtitle = "Interest saved with extra payments",
        icon = acute,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current
        var principal by rememberSaveable { mutableStateOf("1000000") }
        var interestRate by rememberSaveable { mutableStateOf("9") }
        var years by rememberSaveable { mutableStateOf("20") }
        var extraMonthly by rememberSaveable { mutableStateOf("5000") }

        fun validate() = loanPrepaymentValidation(principal, interestRate, years, extraMonthly)

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

            Text(
                text = "Loan Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SliderWithText(
                "Loan Amount",
                10000, 10000000,
                onValueChange = { principal = it },
                actionType = ImeAction.Next,
                prefix = "₹",
                suffix = "",
                isError = !validate().first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
            )

            SliderWithText(
                "Interest Rate (Annual)",
                1, 30,
                onValueChange = { interestRate = it },
                actionType = ImeAction.Next,
                prefix = "",
                suffix = "%",
                isError = !validate().first,
                decimalPlaces = 1
            )

            SliderWithText(
                "Loan Tenure",
                1, 40,
                onValueChange = { years = it },
                actionType = ImeAction.Next,
                prefix = "",
                suffix = "Yr",
                isError = !validate().first
            )

            SliderWithText(
                "Extra Monthly Payment",
                0, 500000,
                onValueChange = { extraMonthly = it },
                actionType = ImeAction.Done,
                prefix = "₹",
                suffix = "",
                isError = !validate().first,
                visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
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
                    val validationResult = validate()
                    if (validationResult.first) {
                        onAction(
                            LoanPrepaymentAction.CalculatePrepayment(
                                principal = principal,
                                interestRate = interestRate,
                                years = years,
                                extraMonthly = extraMonthly
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

            AnimatedVisibility(state.value.interestSaved != "0") {
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
                            text = "Prepayment Benefit",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            MoneyRow("Interest (Without Prepay)", state.value.originalInterest)
                            MoneyRow("Interest (With Prepay)", state.value.newInterest)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Tenure Reduced",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${state.value.monthsReduced} months",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Interest Saved",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = getMoneyInWords(state.value.interestSaved.toDouble()),
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
private fun MoneyRow(label: String, value: String) {
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
private fun LoanPrepaymentScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    LoanPrepaymentState(
                        interestSaved = "850000",
                        monthsReduced = "78",
                        originalInterest = "1158000",
                        newInterest = "308000"
                    )
                )
            }
            LoanPrepaymentScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

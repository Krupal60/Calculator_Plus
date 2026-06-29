@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.plus.calculatorplus.presentation.ui.sip

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plus.calculatorplus.domain.validation.inflationValidation
import com.plus.calculatorplus.domain.validation.interestRateValidation
import com.plus.calculatorplus.domain.validation.lumsumValidation
import com.plus.calculatorplus.domain.validation.monthlyValidation
import com.plus.calculatorplus.domain.validation.sipValidation
import com.plus.calculatorplus.domain.validation.stepUpValidation
import com.plus.calculatorplus.domain.validation.yearsValidation
import com.plus.calculatorplus.presentation.components.PieChart
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.components.SliderWithText
import com.plus.calculatorplus.presentation.icons.sip
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import com.plus.calculatorplus.presentation.util.IndianCurrencyVisualTransformation
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Suppress("MultipleContentEmitters")
@Composable
fun SipScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SipViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    SipScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is SipEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun SipScreen(
    state: State<SipState>,
    onAction: (SipAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "SIP Calculator",
        subtitle = "Plan your financial future with smart investments",
        icon = sip,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        var monthlyInvestment by rememberSaveable { mutableStateOf("100") }
        var lumsumInvestment by rememberSaveable { mutableStateOf("500") }
        var interestRate by rememberSaveable { mutableStateOf("12") }
        var investmentYears by rememberSaveable { mutableStateOf("5") }
        var lumSum by rememberSaveable { mutableStateOf(false) }
        var withInflation by rememberSaveable { mutableStateOf(false) }
        var inflationRate by rememberSaveable { mutableStateOf("6") }
        var withStepUp by rememberSaveable { mutableStateOf(false) }
        var stepUpPercentage by rememberSaveable { mutableStateOf("5") }
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current

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
                    checked = !lumSum,
                    onCheckedChange = { lumSum = false },
                    label = "SIP",
                    weight = 1f
                )
                toggleableItem(
                    checked = lumSum,
                    onCheckedChange = {
                        lumSum = true
                        withStepUp = false
                    },
                    label = "Lumpsum",
                    weight = 1f
                )
            }

            Text(
                text = "Investment Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val inflationBorderWidth by animateDpAsState(
                    if (withInflation) 2.dp else 1.dp,
                    label = ""
                )
                val inflationBorderColor by animateColorAsState(
                    if (withInflation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    label = ""
                )
                FilterChip(
                    selected = withInflation,
                    onClick = { withInflation = !withInflation },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(visible = withInflation) {
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CircleShape
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                            }
                            Text(
                                text = "Inflation",
                                fontWeight = if (withInflation) FontWeight.Bold else FontWeight.Normal,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = CircleShape,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color.Transparent,
                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = withInflation,
                        borderColor = inflationBorderColor,
                        selectedBorderColor = inflationBorderColor,
                        borderWidth = inflationBorderWidth,
                        selectedBorderWidth = inflationBorderWidth
                    )
                )

                val stepUpBorderWidth by animateDpAsState(
                    if (withStepUp) 2.dp else 1.dp,
                    label = ""
                )
                val stepUpBorderColor by animateColorAsState(
                    if (withStepUp) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    label = ""
                )
                FilterChip(
                    selected = withStepUp,
                    onClick = {
                        if (!lumSum) {
                            withStepUp = !withStepUp
                        } else {
                            Toast.makeText(context, "Step Up not for Lumpsum", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(visible = withStepUp) {
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CircleShape
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                            }
                            Text(
                                text = "Step-up",
                                fontWeight = if (withStepUp) FontWeight.Bold else FontWeight.Normal,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = CircleShape,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color.Transparent,
                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = withStepUp,
                        borderColor = stepUpBorderColor,
                        selectedBorderColor = stepUpBorderColor,
                        borderWidth = stepUpBorderWidth,
                        selectedBorderWidth = stepUpBorderWidth
                    )
                )
            }

            AnimatedVisibility(visible = !lumSum) {
                SliderWithText(
                    "Monthly Amount  (in Rs.)",
                    100, 800000,
                    onValueChange = { monthlyInvestment = it },
                    actionType = ImeAction.Done,
                    prefix = "₹",
                    suffix = "",
                    isError = !monthlyValidation(monthlyAmount = monthlyInvestment).first,
                    visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
                )
            }
            AnimatedVisibility(visible = lumSum) {
                SliderWithText(
                    "Lumsum Amount (in Rs.)",
                    500, 1000000,
                    onValueChange = { lumsumInvestment = it },
                    actionType = ImeAction.Done,
                    prefix = "₹",
                    suffix = "",
                    isError = !lumsumValidation(lumSum, lumsumInvestment).first,
                    visualTransformation = IndianCurrencyVisualTransformation(showSymbol = false)
                )
            }

            AnimatedVisibility(visible = withInflation) {
                SliderWithText(
                    "Inflation Rate (Annual)",
                    1, 20,
                    onValueChange = { inflationRate = it },
                    actionType = ImeAction.Done,
                    prefix = "",
                    suffix = "%",
                    isError = !inflationValidation(inflationRate, withInflation).first
                )
            }

            AnimatedVisibility(visible = withStepUp && !lumSum) {
                SliderWithText(
                    "Step up Percentage",
                    1, 50,
                    onValueChange = { stepUpPercentage = it },
                    actionType = ImeAction.Done,
                    prefix = "",
                    suffix = "%",
                    isError = !stepUpValidation(stepUpPercentage, withStepUp).first
                )
            }

            SliderWithText(
                "Interest Rate  (Annual)",
                1, 45,
                onValueChange = { interestRate = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "%",
                isError = !interestRateValidation(interestRate).first,
                decimalPlaces = 1
            )
            SliderWithText(
                "Investment Years",
                1, 40,
                onValueChange = { investmentYears = it },
                actionType = ImeAction.Done,
                prefix = "",
                suffix = "Yr",
                isError = !yearsValidation(investmentYears).first,
                decimalPlaces = 1
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    when (sipValidation(
                        lumSum, monthlyInvestment, lumsumInvestment, interestRate,
                        investmentYears, withInflation, inflationRate, withStepUp, stepUpPercentage
                    ).first) {
                        true -> {
                            onAction(
                                SipAction.CalculateSip(
                                    isLumsum = lumSum,
                                    monthlyAmount = monthlyInvestment,
                                    lumsumAmount = lumsumInvestment,
                                    interest = interestRate,
                                    investedYears = investmentYears,
                                    withInflation = withInflation,
                                    inflationRate = inflationRate,
                                    withStepUp = withStepUp,
                                    stepUpPercentage = stepUpPercentage
                                )
                            )
                            coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                        }

                        false -> {
                            Toast.makeText(
                                context,
                                sipValidation(
                                    lumSum,
                                    monthlyInvestment,
                                    lumsumInvestment,
                                    interestRate,
                                    investmentYears,
                                    withInflation,
                                    inflationRate,
                                    withStepUp,
                                    stepUpPercentage
                                ).second,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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
            AnimatedVisibility(visible = state.value.estimateReturnsAmount != "0") {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Investment Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        PieChart(
                            data = persistentMapOf(
                                Pair("Invested Amount", state.value.investedAmount.toLong()),
                                Pair(
                                    "Estimated Returns",
                                    state.value.estimateReturnsAmount.toLong()
                                )
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Wealth",
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

@Preview(showBackground = true)
@Composable
private fun SipScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    SipState(
                        totalAmount = "500000",
                        estimateReturnsAmount = "200000",
                        investedAmount = "300000"
                    )
                )
            }
            SipScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

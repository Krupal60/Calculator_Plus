@file:OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)

package com.plus.calculatorplus.presentation.ui.unitconverter

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plus.calculatorplus.domain.unitconverter.UnitConverterCalculationUseCase.Category
import com.plus.calculatorplus.domain.validation.unitConverterValidation
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.swap_horizontal_circle
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@Suppress("MultipleContentEmitters")
@Composable
fun UnitConverterScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: UnitConverterViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    UnitConverterScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is UnitConverterEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun UnitConverterScreen(
    state: State<UnitConverterState>,
    onAction: (UnitConverterAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "Unit Converter",
        subtitle = "Convert length, weight and area",
        icon = swap_horizontal_circle,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val scrollState = rememberScrollState(0)

        var category by rememberSaveable { mutableStateOf(Category.LENGTH) }
        var fromUnit by rememberSaveable { mutableStateOf(Category.LENGTH.units.first()) }
        var toUnit by rememberSaveable { mutableStateOf(Category.LENGTH.units[1]) }
        var value by rememberSaveable { mutableStateOf("1") }

        fun recompute() {
            if (unitConverterValidation(value).first) {
                onAction(UnitConverterAction.Convert(category, fromUnit, toUnit, value))
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .animateContentSize()
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
                    .padding(top = 8.dp, bottom = 12.dp),
                overflowIndicator = { ButtonGroupDefaults.OverflowIndicator(it) }
            ) {
                Category.entries.forEach { cat ->
                    toggleableItem(
                        checked = category == cat,
                        onCheckedChange = {
                            category = cat
                            fromUnit = cat.units.first()
                            toUnit = cat.units[1]
                            recompute()
                        },
                        label = cat.label,
                        weight = 1f
                    )
                }
            }

            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Input",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            value = it
                            recompute()
                        },
                        label = { Text("Enter value") },
                        isError = !unitConverterValidation(value).first,
                        singleLine = true,
                        shape = MaterialTheme.shapes.extraLarge,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    UnitChips(
                        title = "From Unit",
                        units = category.units,
                        selected = fromUnit,
                        onSelect = { fromUnit = it; recompute() }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        val temp = fromUnit
                        fromUnit = toUnit
                        toUnit = temp
                        recompute()
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.graphicsLayer(rotationZ = 90f)
                ) {
                    Icon(
                        imageVector = swap_horizontal_circle,
                        contentDescription = "Swap Units"
                    )
                }
            }

            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    UnitChips(
                        title = "To Unit",
                        units = category.units,
                        selected = toUnit,
                        onSelect = { toUnit = it; recompute() }
                    )
                }
            }

            AnimatedVisibility(state.value.convertedValue.isNotEmpty()) {
                Card(
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$value $fromUnit =",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        AnimatedContent(
                            targetState = state.value.convertedValue,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "ConvertedValueAnimation"
                        ) { targetValue ->
                            Text(
                                text = "$targetValue $toUnit",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = 16.sp,
                                    maxFontSize = 36.sp,
                                    stepSize = 2.sp
                                ),
                                maxLines = 1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UnitChips(
    title: String,
    units: ImmutableList<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            units.forEach { unit ->
                FilterChip(
                    selected = selected == unit,
                    onClick = { onSelect(unit) },
                    label = { Text(unit) },
                    shape = MaterialTheme.shapes.medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UnitConverterScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(UnitConverterState(convertedValue = "100")) }
            UnitConverterScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

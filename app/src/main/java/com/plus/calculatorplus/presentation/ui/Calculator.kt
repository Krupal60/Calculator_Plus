package com.plus.calculatorplus.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.plus.calculatorplus.data.model.calculator.CalculatorAction
import com.plus.calculatorplus.data.model.calculator.CalculatorOperation
import com.plus.calculatorplus.data.model.calculator.CalculatorState
import com.plus.calculatorplus.presentation.components.CalculatorButton
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import com.plus.calculatorplus.ui.theme.orange
import com.plus.calculatorplus.viewmodel.CalculationViewModel


@Composable
fun CalculatorMain(paddingValues: PaddingValues, viewModel: CalculationViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Calculator(state, viewModel::onAction, paddingValues)
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Calculator(
    state: State<CalculatorState>,
    onAction: (CalculatorAction) -> Unit,
    paddingValues: PaddingValues
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    // Landscape is identified by a compact height
    val isLandscape =
        !windowAdaptiveInfo.windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
    // For wider screens (Foldables, Tablets), we use a two-pane layout
    val isMediumOrWider =
        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isLandscape || isMediumOrWider) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DisplayCard(
                state = state,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                isLandscape = true
            )

            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                ButtonsGrid(
                    onAction = onAction,
                    isExpanded = isMediumOrWider
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DisplayCard(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                contentAlignment = Alignment.Center
            ) {
                ButtonsGrid(
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun DisplayCard(
    state: State<CalculatorState>,
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(12.dp)
    ) {
        val displayText = if (state.value.number1.isEmpty()) {
            "0${state.value.operation?.symbol ?: ""}${state.value.number2}"
        } else {
            "${state.value.number1}${state.value.operation?.symbol ?: ""}${state.value.number2}"
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = displayText,
                style = MaterialTheme.typography.displayLarge.copy(
                    textAlign = TextAlign.End,
                    lineHeight = 1.1.em,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 22.sp,
                    maxFontSize = if (isLandscape) 60.sp else 80.sp,
                    stepSize = 1.sp
                ),
                overflow = TextOverflow.MiddleEllipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
            )
        }
    }
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun ButtonsGrid(
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    val buttons = listOf(
        "AC", "⌫", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "00", "0", ".", "="
    )

    Grid(
        modifier = modifier,
        config = {
            val cols = 4
            val rows = 5
            val gapSize = 12.dp
            val maxWidthDp = constraints.maxWidth.toDp()
            val maxHeightDp = constraints.maxHeight.toDp()

            val cellWidth = (maxWidthDp - gapSize * (cols - 1)) / cols
            val cellHeight = (maxHeightDp - gapSize * (rows - 1)) / rows

            // To make buttons "even" (square), we take the smaller of the two.
            // But we also don't want them to be "too big".
            val cellSize =
                minOf(cellWidth, cellHeight).coerceAtMost(if (isExpanded) 160.dp else 80.dp)

            repeat(cols) { column(cellSize) }
            repeat(rows) { row(cellSize) }
            gap(gapSize)
        }
    ) {
        val buttonAutoSize = TextAutoSize.StepBased(
            minFontSize = 14.sp,
            maxFontSize = 32.sp,
            stepSize = 1.sp
        )

        buttons.forEach { text ->
            CalculatorButton(
                modifier = Modifier.fillMaxSize(),
                text = text,
                autoSize = buttonAutoSize,
                color = when (text) {
                    "÷", "×", "-", "+", "%", "⌫", "AC", "=" -> Color(orange.value)
                    else -> MaterialTheme.colorScheme.onSurface
                }
            ) {
                when (text) {
                    "AC" -> onAction(CalculatorAction.Clear)
                    "." -> onAction(CalculatorAction.Decimal)
                    "⌫" -> onAction(CalculatorAction.Delete)
                    "÷" -> onAction(CalculatorAction.Operation(CalculatorOperation.Division))
                    "%" -> onAction(CalculatorAction.Operation(CalculatorOperation.Percentage))
                    "×" -> onAction(CalculatorAction.Operation(CalculatorOperation.Multiplication))
                    "-" -> onAction(CalculatorAction.Operation(CalculatorOperation.Subtraction))
                    "+" -> onAction(CalculatorAction.Operation(CalculatorOperation.Addition))
                    "=" -> onAction(CalculatorAction.Calculate)
                    else -> onAction(CalculatorAction.Number(number = text))
                }
            }
        }
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
@Preview(name = "Phone Wide", device = "spec:width=511dp,height=891dp", showBackground = true)
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showBackground = true)
@Preview(
    name = "Desktop",
    device = "spec:width=1920dp,height=1080dp,dpi=160",
    showBackground = true
)
annotation class FormFactorPreviews

@FormFactorPreviews
@Composable
private fun CalculatorPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(CalculatorState()) }
            Calculator(
                state = state,
                onAction = {},
                paddingValues = PaddingValues(0.dp)
            )
        }
    }
}
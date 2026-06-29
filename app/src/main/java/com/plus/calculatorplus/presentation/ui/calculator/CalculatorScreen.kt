package com.plus.calculatorplus.presentation.ui.calculator

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.key
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
import androidx.window.core.layout.WindowSizeClass
import com.plus.calculatorplus.domain.calculator.CalculatorUseCase
import com.plus.calculatorplus.presentation.components.CalculatorButton
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.theme.orange
import org.koin.androidx.compose.koinViewModel


@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculationViewModel = koinViewModel()
) {
    Scaffold(modifier = modifier) { innerPadding ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        Calculator(state, viewModel::onAction, innerPadding)
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Calculator(
    state: State<CalculatorState>,
    onAction: (CalculatorAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val isLandscape =
        !windowAdaptiveInfo.windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
    val isMediumOrWider =
        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (isLandscape || isMediumOrWider) {
        Row(
            modifier = modifier
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
                ButtonsGrid(onAction = onAction, isExpanded = isMediumOrWider)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DisplayCard(
                state = state,
                modifier = Modifier
                    .padding(bottom = 18.dp, top = 10.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f, false),
                contentAlignment = Alignment.BottomCenter
            ) {
                ButtonsGrid(onAction = onAction)
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
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.inverseOnSurface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 12.dp
    ) {
        val displayText = if (state.value.number1.isEmpty()) {
            "0${state.value.operation?.symbol ?: ""}${state.value.number2}"
        } else {
            "${state.value.number1}${state.value.operation?.symbol ?: ""}${state.value.number2}"
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
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

            @Suppress("DerivedStateOfCandidate")
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
            key(text) {
                CalculatorButton(
                    modifier = Modifier.fillMaxSize(),
                    text = text,
                    autoSize = buttonAutoSize,
                    color = when (text) {
                        "÷", "×", "-", "+", "%", "⌫", "AC", "=" -> Color(orange.value)
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    onClick = {
                        when (text) {
                            "AC" -> onAction(CalculatorAction.Clear)
                            "." -> onAction(CalculatorAction.Decimal)
                            "⌫" -> onAction(CalculatorAction.Delete)
                            "÷" -> onAction(CalculatorAction.Operation(CalculatorUseCase.Operation.Division))
                            "%" -> onAction(CalculatorAction.Operation(CalculatorUseCase.Operation.Percentage))
                            "×" -> onAction(CalculatorAction.Operation(CalculatorUseCase.Operation.Multiplication))
                            "-" -> onAction(CalculatorAction.Operation(CalculatorUseCase.Operation.Subtraction))
                            "+" -> onAction(CalculatorAction.Operation(CalculatorUseCase.Operation.Addition))
                            "=" -> onAction(CalculatorAction.Calculate)
                            else -> onAction(CalculatorAction.Number(number = text))
                        }
                    }
                )
            }
        }
    }
}

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

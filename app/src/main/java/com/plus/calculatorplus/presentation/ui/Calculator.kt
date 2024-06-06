package com.plus.calculatorplus.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.data.model.calculator.CalculatorAction
import com.plus.calculatorplus.data.model.calculator.CalculatorOperation
import com.plus.calculatorplus.data.model.calculator.CalculatorState
import com.plus.calculatorplus.presentation.components.CalculatorButton
import com.plus.calculatorplus.ui.theme.orange
import com.plus.calculatorplus.viewmodel.CalculationViewModel


@Composable
fun CalculatorMain(paddingValues: PaddingValues,viewModel : CalculationViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Calculator(state, viewModel::onAction, paddingValues)
}


@Composable
fun Calculator(
    state: State<CalculatorState>,
    onAction: (CalculatorAction) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 14.dp,
                end = 14.dp

            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                disabledContentColor = MaterialTheme.colorScheme.surfaceDim,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceDim
            ),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {

            val displayText = if (state.value.number1.isEmpty()) {
                "0${state.value.operation?.symbol ?: ""}${state.value.number2}"
            } else {
                "${state.value.number1}${state.value.operation?.symbol ?: ""}${state.value.number2}"
            }

            Text(
                text = displayText,
                textAlign = TextAlign.End,
                fontSize = 25.sp,
                fontFamily = FontFamily.Serif,
                lineHeight = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 15.dp
                    ),
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 10.dp)
        ) {
            itemsIndexed(
                items = listOf(
                    "AC",
                    "⌫",
                    "%",
                    "÷",
                    "7",
                    "8",
                    "9",
                    "×",
                    "4",
                    "5",
                    "6",
                    "-",
                    "1",
                    "2",
                    "3",
                    "+",
                    "00",
                    "0",
                    ".",
                    "="
                )
            ) { _, text ->

                CalculatorButton(
                    Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .animateContentSize()
                    ,text = text, color = when (text) {
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
}
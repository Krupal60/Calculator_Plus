package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.calculator.CalculatorAction
import com.plus.calculatorplus.data.model.calculator.CalculatorOperation
import com.plus.calculatorplus.data.model.calculator.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow


class CalculationViewModel : ViewModel() {

    var state = MutableStateFlow(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Calculate -> calculateResult()
            is CalculatorAction.Clear -> clearState()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
        }
    }

    private fun enterNumber(number: String) {
        val current = state.value

        if (current.operation == null) {
            state.value = current.copy(
                number1 = current.number1 + number
            )
        } else {
            state.value = current.copy(
                number2 = current.number2 + number
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val current = state.value

        if (current.number1.isBlank()) return

        // If second number exists, calculate first (chaining)
        if (current.number2.isNotBlank()) {
            calculateResult()
        }

        state.value = state.value.copy(operation = operation)
    }

    private fun enterDecimal() {
        val current = state.value

        if (current.operation == null) {
            if (!current.number1.contains(".")) {
                val value =
                    if (current.number1.isBlank()) "0." else current.number1 + "."
                state.value = current.copy(number1 = value)
            }
        } else {
            if (!current.number2.contains(".")) {
                val value =
                    if (current.number2.isBlank()) "0." else current.number2 + "."
                state.value = current.copy(number2 = value)
            }
        }
    }

    private fun delete() {
        val current = state.value

        when {
            current.number2.isNotBlank() ->
                state.value = current.copy(
                    number2 = current.number2.dropLast(1)
                )

            current.operation != null ->
                state.value = current.copy(operation = null)

            current.number1.isNotBlank() ->
                state.value = current.copy(
                    number1 = current.number1.dropLast(1)
                )
        }
    }

    private fun calculateResult() {
        val current = state.value

        val n1 = current.number1.toDoubleOrNull() ?: return
        val n2 = current.number2.toDoubleOrNull() ?: return
        val op = current.operation ?: return

        val result = when (op) {
            CalculatorOperation.Addition -> n1 + n2
            CalculatorOperation.Subtraction -> n1 - n2
            CalculatorOperation.Multiplication -> n1 * n2
            CalculatorOperation.Division -> {
                if (n2 == 0.0) n1
                else
                    n1 / n2
            }

            CalculatorOperation.Percentage -> (n1 * n2) / 100
        }

        state.value = current.copy(
            number1 = formatResult(result),
            number2 = "",
            operation = null
        )
    }

    private fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }

    private fun clearState() {
        state.value = CalculatorState()
    }
}
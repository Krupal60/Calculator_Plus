package com.plus.calculatorplus.presentation.ui.calculator

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.calculator.CalculatorUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class CalculationViewModel : ViewModel() {

    private val calculatorUseCase = CalculatorUseCase()

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
            state.value = current.copy(number1 = current.number1 + number)
        } else {
            state.value = current.copy(number2 = current.number2 + number)
        }
    }

    private fun enterOperation(operation: CalculatorUseCase.Operation) {
        val current = state.value
        if (current.number1.isBlank()) return
        if (current.number2.isNotBlank()) {
            calculateResult()
        }
        state.value = state.value.copy(operation = operation)
    }

    private fun enterDecimal() {
        val current = state.value
        if (current.operation == null) {
            if (!current.number1.contains(".")) {
                val value = if (current.number1.isBlank()) "0." else current.number1 + "."
                state.value = current.copy(number1 = value)
            }
        } else {
            if (!current.number2.contains(".")) {
                val value = if (current.number2.isBlank()) "0." else current.number2 + "."
                state.value = current.copy(number2 = value)
            }
        }
    }

    private fun delete() {
        val current = state.value
        when {
            current.number2.isNotBlank() -> state.value =
                current.copy(number2 = current.number2.dropLast(1))

            current.operation != null -> state.value = current.copy(operation = null)
            current.number1.isNotBlank() -> state.value =
                current.copy(number1 = current.number1.dropLast(1))
        }
    }

    private fun calculateResult() {
        val current = state.value
        val result = calculatorUseCase.calculate(
            CalculatorUseCase.Input(
                number1 = current.number1,
                number2 = current.number2,
                operation = current.operation
            )
        )
        state.value = current.copy(
            number1 = result.number1,
            number2 = result.number2,
            operation = result.operation
        )
    }

    private fun clearState() {
        state.value = CalculatorState()
    }
}

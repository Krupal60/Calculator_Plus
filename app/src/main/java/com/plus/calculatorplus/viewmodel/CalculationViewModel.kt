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

    private fun enterOperation(operation: CalculatorOperation) {
        // Update operation
        if (state.value.number1.isNotBlank()){
            state.value = state.value.copy(operation = operation)
        }
        // Perform calculation if both numbers are provided
        if (state.value.number1.isNotBlank() && state.value.number2.isNotBlank()) {
            calculateResult()
            state.value = state.value.copy(operation = operation)
        }

    }

    private fun enterNumber(number: String) {
        if (number.contains("00")){
            if (state.value.operation == null) {
                state.value = state.value.copy(number1 = state.value.number1 + number.toInt() +  number.toInt())
            } else {
                state.value = state.value.copy(number2 = state.value.number2 + number.toInt() +  number.toInt())
            }
        }else {
            if (state.value.operation == null) {
                state.value = state.value.copy(number1 = state.value.number1 + number.toInt())
            } else {
                state.value = state.value.copy(number2 = state.value.number2 + number.toInt())
            }
        }
    }

    private fun delete() {
        if (state.value.number2.isNotBlank()) {
            state.value = state.value.copy(number2 = state.value.number2.dropLast(1))
            return
        }
        if (state.value.operation != null) {
            state.value = state.value.copy(operation = null)
            return
        }
        if (state.value.number1.isNotBlank()) {
            state.value = state.value.copy(number1 = state.value.number1.dropLast(1))
            return
        }
    }

    private fun enterDecimal() {
        if (state.value.operation == null && !state.value.number1.contains(".") && state.value.number1.isNotBlank()) {
            state.value = state.value.copy(number1 = state.value.number1 + ".")
            return
        }
        if (!state.value.number2.contains(".") && state.value.number2.isNotBlank()) {
            state.value = state.value.copy(number2 = state.value.number2 + ".")
            return
        }
    }

    private fun calculateResult() {
        val number1 = state.value.number1.toDoubleOrNull() ?: return
        val number2 = state.value.number2.toDoubleOrNull() ?: return
        val operation = state.value.operation ?: return

        val result = when (operation) {
            CalculatorOperation.Addition -> number1 + number2
            CalculatorOperation.Division -> number1 / number2
            CalculatorOperation.Multiplication -> number1 * number2
            CalculatorOperation.Percentage -> number1 % number2
            CalculatorOperation.Subtraction -> number1 - number2
        }

        // Update state with result
        val integerPart = result.toInt()
        state.value = state.value.copy(
            number1 = if (integerPart.toDouble() == result) integerPart.toString() else result.toString(),
            number2 = "",
            operation = null
        )
    }

    private fun clearState() {
        state.value = CalculatorState()
    }
}
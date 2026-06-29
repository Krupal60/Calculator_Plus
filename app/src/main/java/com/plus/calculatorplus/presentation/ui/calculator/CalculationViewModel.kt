package com.plus.calculatorplus.presentation.ui.calculator

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.calculator.CalculatorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class CalculationViewModel(
    private val calculatorUseCase: CalculatorUseCase
) : ViewModel() {

    val state: StateFlow<CalculatorState>
        field = MutableStateFlow(CalculatorState())

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
        state.update { current ->
            if (current.operation == null) {
                current.copy(number1 = current.number1 + number)
            } else {
                current.copy(number2 = current.number2 + number)
            }
        }
    }

    private fun enterOperation(operation: CalculatorUseCase.Operation) {
        state.update { current ->
            if (current.number1.isBlank()) return@update current
            if (current.number2.isNotBlank()) {
                val result = calculatorUseCase.calculate(
                    CalculatorUseCase.Input(
                        number1 = current.number1,
                        number2 = current.number2,
                        operation = current.operation
                    )
                )
                return@update current.copy(
                    number1 = result.number1,
                    number2 = result.number2,
                    operation = operation
                )
            }
            current.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        state.update { current ->
            if (current.operation == null) {
                if (!current.number1.contains(".")) {
                    val value = if (current.number1.isBlank()) "0." else current.number1 + "."
                    current.copy(number1 = value)
                } else {
                    current
                }
            } else {
                if (!current.number2.contains(".")) {
                    val value = if (current.number2.isBlank()) "0." else current.number2 + "."
                    current.copy(number2 = value)
                } else {
                    current
                }
            }
        }
    }

    private fun delete() {
        state.update { current ->
            when {
                current.number2.isNotBlank() -> current.copy(number2 = current.number2.dropLast(1))
                current.operation != null -> current.copy(operation = null)
                current.number1.isNotBlank() -> current.copy(number1 = current.number1.dropLast(1))
                else -> current
            }
        }
    }

    private fun calculateResult() {
        state.update { current ->
            val result = calculatorUseCase.calculate(
                CalculatorUseCase.Input(
                    number1 = current.number1,
                    number2 = current.number2,
                    operation = current.operation
                )
            )
            current.copy(
                number1 = result.number1,
                number2 = result.number2,
                operation = result.operation
            )
        }
    }

    private fun clearState() {
        state.update { CalculatorState() }
    }
}

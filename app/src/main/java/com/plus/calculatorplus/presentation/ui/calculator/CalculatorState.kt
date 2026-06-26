package com.plus.calculatorplus.presentation.ui.calculator

import com.plus.calculatorplus.domain.calculator.CalculatorUseCase

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorUseCase.Operation? = null
)

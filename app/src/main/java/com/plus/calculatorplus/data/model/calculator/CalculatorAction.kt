package com.plus.calculatorplus.data.model.calculator

sealed class CalculatorAction {
    data class Number ( val number : String) : CalculatorAction()
    data object Clear : CalculatorAction()
    data object Delete : CalculatorAction()
    data object Decimal : CalculatorAction()
    data object Calculate : CalculatorAction()
    data class Operation (val operation : CalculatorOperation): CalculatorAction()
}
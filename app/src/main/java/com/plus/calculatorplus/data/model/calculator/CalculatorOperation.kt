package com.plus.calculatorplus.data.model.calculator

sealed class CalculatorOperation(val symbol : String) {
    data object Addition : CalculatorOperation("+")
    data object Subtraction : CalculatorOperation("-")
    data  object Multiplication : CalculatorOperation("×")
    data object Division : CalculatorOperation("÷")
    data object Percentage : CalculatorOperation("%")

}
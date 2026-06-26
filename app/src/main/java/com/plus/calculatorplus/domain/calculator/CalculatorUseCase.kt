package com.plus.calculatorplus.domain.calculator

class CalculatorUseCase {

    sealed class Operation(val symbol: String) {
        data object Addition : Operation("+")
        data object Subtraction : Operation("-")
        data object Multiplication : Operation("×")
        data object Division : Operation("÷")
        data object Percentage : Operation("%")
    }

    data class Input(
        val number1: String,
        val number2: String,
        val operation: Operation?
    )

    data class Result(
        val number1: String,
        val number2: String,
        val operation: Operation?
    )

    fun calculate(input: Input): Result {
        val n1 = input.number1.toDoubleOrNull() ?: return Result(input.number1, "", null)
        val n2 = input.number2.toDoubleOrNull() ?: return Result(input.number1, "", null)
        val op = input.operation ?: return Result(input.number1, "", null)

        val result = when (op) {
            Operation.Addition -> n1 + n2
            Operation.Subtraction -> n1 - n2
            Operation.Multiplication -> n1 * n2
            Operation.Division -> if (n2 == 0.0) n1 else n1 / n2
            Operation.Percentage -> (n1 * n2) / 100
        }
        return Result(formatResult(result), "", null)
    }

    private fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) value.toLong().toString() else value.toString()
    }
}

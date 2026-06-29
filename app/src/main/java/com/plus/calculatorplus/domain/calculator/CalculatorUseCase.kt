package com.plus.calculatorplus.domain.calculator

import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
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
        val n1 = input.number1.toBigDecimalOrNull() ?: return Result(input.number1, "", null)
        val n2 = input.number2.toBigDecimalOrNull() ?: return Result(input.number1, "", null)
        val op = input.operation ?: return Result(input.number1, "", null)

        val result = when (op) {
            Operation.Addition -> n1.add(n2)
            Operation.Subtraction -> n1.subtract(n2)
            Operation.Multiplication -> n1.multiply(n2)
            Operation.Division -> if (n2.compareTo(BigDecimal.ZERO) == 0) n1 else n1.divide(
                n2,
                10,
                RoundingMode.HALF_EVEN
            )

            Operation.Percentage -> n1.multiply(n2)
                .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        }
        return Result(formatResult(result), "", null)
    }

    private fun formatResult(value: BigDecimal): String {
        return if (value.stripTrailingZeros().scale() <= 0) {
            value.toBigInteger().toString()
        } else {
            value.stripTrailingZeros().toPlainString()
        }
    }
}

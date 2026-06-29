package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun cagrValidation(
    initialValue: String,
    finalValue: String,
    years: String
): Pair<Boolean, String> {
    return when {
        initialValue.isEmpty() -> Pair(false, "Enter Initial Value")
        finalValue.isEmpty() -> Pair(false, "Enter Final Value")
        years.isEmpty() -> Pair(false, "Enter Number of Years")
        else -> {
            val initial =
                initialValue.toBigDecimalOrNull() ?: return Pair(false, "Invalid Initial Value")
            val final = finalValue.toBigDecimalOrNull() ?: return Pair(false, "Invalid Final Value")
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Years")

            when {
                initial.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Initial Value must be greater than 0"
                )

                final.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Final Value must be greater than 0"
                )

                period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "Years must be between 1 and 100")

                else -> Pair(true, "")
            }
        }
    }
}

package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun inflationScreenValidation(
    amount: String,
    inflationRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        amount.isEmpty() -> Pair(false, "Enter Amount")
        inflationRate.isEmpty() -> Pair(false, "Enter Inflation Rate")
        years.isEmpty() -> Pair(false, "Enter Number of Years")
        else -> {
            val amountValue = amount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Amount")
            val rate =
                inflationRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Inflation Rate")
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Years")

            when {
                amountValue.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Amount must be greater than 0"
                )

                rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("30")) > 0 ->
                    Pair(false, "Inflation Rate must be between 0 and 30")

                period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "Years must be between 1 and 100")

                else -> Pair(true, "")
            }
        }
    }
}

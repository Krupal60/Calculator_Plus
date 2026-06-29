package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun lumpsumScreenValidation(
    principal: String,
    annualRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        principal.isEmpty() -> Pair(false, "Enter Investment Amount")
        annualRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        years.isEmpty() -> Pair(false, "Enter Number of Years")
        else -> {
            val amount = principal.toBigDecimalOrNull() ?: return Pair(false, "Invalid Amount")
            val rate =
                annualRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Interest Rate")
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Years")

            when {
                amount.compareTo(BigDecimal("500")) < 0 -> Pair(
                    false,
                    "Amount must be at least 500"
                )

                rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("50")) > 0 ->
                    Pair(false, "Interest Rate must be between 0 and 50")

                period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "Years must be between 1 and 100")

                else -> Pair(true, "")
            }
        }
    }
}

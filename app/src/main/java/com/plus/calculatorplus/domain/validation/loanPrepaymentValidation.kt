package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun loanPrepaymentValidation(
    principal: String,
    interestRate: String,
    years: String,
    extraMonthly: String
): Pair<Boolean, String> {
    return when {
        principal.isEmpty() -> Pair(false, "Enter Loan Amount")
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        years.isEmpty() -> Pair(false, "Enter Loan Tenure")
        extraMonthly.isEmpty() -> Pair(false, "Enter Extra Monthly Payment")
        else -> {
            val loan = principal.toBigDecimalOrNull() ?: return Pair(false, "Invalid Loan Amount")
            val rate =
                interestRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Interest Rate")
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Tenure")
            val extra =
                extraMonthly.toBigDecimalOrNull() ?: return Pair(false, "Invalid Extra Payment")

            when {
                loan.compareTo(BigDecimal.ZERO) <= 0 -> Pair(false, "Loan must be greater than 0")
                rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("50")) > 0 ->
                    Pair(false, "Interest Rate must be between 0 and 50")

                period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("40")) > 0 ->
                    Pair(false, "Tenure must be between 1 and 40 years")

                extra.compareTo(BigDecimal.ZERO) < 0 -> Pair(
                    false,
                    "Extra payment cannot be negative"
                )

                else -> Pair(true, "")
            }
        }
    }
}

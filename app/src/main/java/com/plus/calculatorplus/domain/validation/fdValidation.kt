package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun fdValidation(
    investmentAmount: String,
    annualInterestRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        investmentAmount.isEmpty() -> Pair(false, "Enter Investment Amount")
        annualInterestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        years.isEmpty() -> Pair(false, "Enter Years")
        else -> {
            val amount =
                investmentAmount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Amount")
            val interest = annualInterestRate.toBigDecimalOrNull() ?: return Pair(
                false,
                "Invalid Interest Rate"
            )
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Years")

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                Pair(false, "Amount must be greater than 0")
            } else if (interest.compareTo(BigDecimal.ZERO) <= 0 || interest.compareTo(BigDecimal("50")) > 0) {
                Pair(false, "Interest Rate must be between 0 and 50")
            } else if (period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("50")) > 0) {
                Pair(false, "Period must be between 0 and 50 years")
            } else {
                Pair(true, "")
            }
        }
    }
}

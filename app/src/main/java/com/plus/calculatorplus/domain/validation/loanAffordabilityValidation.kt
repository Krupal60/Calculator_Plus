package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun loanAffordabilityValidation(
    monthlyIncome: String,
    existingEmi: String,
    interestRate: String,
    years: String,
    foirPercentage: String
): Pair<Boolean, String> {
    return when {
        monthlyIncome.isEmpty() -> Pair(false, "Enter Monthly Income")
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        years.isEmpty() -> Pair(false, "Enter Loan Tenure")
        foirPercentage.isEmpty() -> Pair(false, "Enter FOIR Percentage")
        else -> {
            val income = monthlyIncome.toBigDecimalOrNull() ?: return Pair(false, "Invalid Income")
            val emi = existingEmi.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val rate =
                interestRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Interest Rate")
            val period = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Tenure")
            val foir = foirPercentage.toBigDecimalOrNull() ?: return Pair(false, "Invalid FOIR")

            when {
                income.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Income must be greater than 0"
                )

                emi.compareTo(BigDecimal.ZERO) < 0 -> Pair(false, "Existing EMI cannot be negative")
                rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("50")) > 0 ->
                    Pair(false, "Interest Rate must be between 0 and 50")

                period.compareTo(BigDecimal.ZERO) <= 0 || period.compareTo(BigDecimal("40")) > 0 ->
                    Pair(false, "Tenure must be between 1 and 40 years")

                foir.compareTo(BigDecimal.ZERO) <= 0 || foir.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "FOIR must be between 1 and 100")

                else -> Pair(true, "")
            }
        }
    }
}

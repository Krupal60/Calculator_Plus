package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun emiValidation(
    loanAmount: String,
    interestRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        loanAmount.isEmpty() -> Pair(false, "Enter loan Amount")
        years.isEmpty() -> Pair(false, "Enter loan Years")
        else -> {
            val loanAmountResult = loanAmountValidation(loanAmount)
            if (!loanAmountResult.first) return loanAmountResult

            val yearsResult = loanYearsValidation(years)
            if (!yearsResult.first) return yearsResult

            val interestRateResult = loanInterestRateValidation(interestRate)
            if (!interestRateResult.first) return interestRateResult

            Pair(true, "")
        }
    }
}

fun loanYearsValidation(years: String): Pair<Boolean, String> {
    return when {
        years.isEmpty() -> Pair(false, "Enter loan years")
        else -> {
            val yearsValue = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid loan years")
            if (yearsValue.compareTo(BigDecimal("1")) >= 0 && yearsValue.compareTo(BigDecimal("50")) <= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Loan years must be between 1 and 50")
            }
        }
    }
}

fun loanInterestRateValidation(interestRate: String): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        else -> {
            val rate =
                interestRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Interest Rate")
            when {
                rate.compareTo(BigDecimal.ZERO) < 0 -> Pair(
                    false,
                    "Interest Rate cannot be negative"
                )

                rate.compareTo(BigDecimal("35")) > 0 -> Pair(
                    false,
                    "Interest Rate must not exceed 35%"
                )

                else -> Pair(true, "")
            }
        }
    }
}

fun loanAmountValidation(loanAmount: String): Pair<Boolean, String> {
    return when {
        loanAmount.isEmpty() -> Pair(false, "Enter Loan Amount")
        else -> {
            val amount =
                loanAmount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Loan Amount")
            if (amount.compareTo(BigDecimal("10000")) >= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Loan Amount must be at least 10000")
            }
        }
    }
}

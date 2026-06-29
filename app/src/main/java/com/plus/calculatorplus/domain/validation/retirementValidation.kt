package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun retirementValidation(
    currentAge: String,
    retirementAge: String,
    monthlySavings: String,
    expectedReturnRate: String
): Pair<Boolean, String> {
    return when {
        currentAge.isEmpty() -> Pair(false, "Enter Current Age")
        retirementAge.isEmpty() -> Pair(false, "Enter Retirement Age")
        monthlySavings.isEmpty() -> Pair(false, "Enter Monthly Savings")
        expectedReturnRate.isEmpty() -> Pair(false, "Enter Expected Return Rate")
        else -> {
            val cAge = currentAge.toBigDecimalOrNull() ?: return Pair(false, "Invalid Current Age")
            val rAge =
                retirementAge.toBigDecimalOrNull() ?: return Pair(false, "Invalid Retirement Age")
            val savings =
                monthlySavings.toBigDecimalOrNull() ?: return Pair(false, "Invalid Monthly Savings")
            val rate =
                expectedReturnRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Return Rate")

            when {
                cAge.compareTo(BigDecimal("1")) < 0 || cAge.compareTo(BigDecimal("100")) > 0 -> Pair(
                    false,
                    "Current Age must be between 1 and 100"
                )

                rAge.compareTo(BigDecimal("1")) < 0 || rAge.compareTo(BigDecimal("120")) > 0 -> Pair(
                    false,
                    "Retirement Age must be between 1 and 120"
                )

                rAge.compareTo(cAge) <= 0 -> Pair(
                    false,
                    "Retirement Age must be greater than Current Age"
                )

                savings.compareTo(BigDecimal.ZERO) < 0 -> Pair(false, "Savings cannot be negative")
                rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("50")) > 0 -> Pair(
                    false,
                    "Rate must be between 0 and 50"
                )

                else -> Pair(true, "")
            }
        }
    }
}

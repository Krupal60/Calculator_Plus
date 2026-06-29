package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun swpValidation(
    totalInvestment: String,
    withdrawalPerMonth: String,
    expectedReturnRate: String,
    timePeriodYears: String
): Pair<Boolean, String> {
    return when {
        totalInvestment.isEmpty() -> Pair(false, "Enter Total Investment")
        withdrawalPerMonth.isEmpty() -> Pair(false, "Enter Withdrawal per month")
        expectedReturnRate.isEmpty() -> Pair(false, "Enter Expected return rate")
        timePeriodYears.isEmpty() -> Pair(false, "Enter Time period")
        else -> {
            val investment =
                totalInvestment.toBigDecimalOrNull() ?: return Pair(false, "Invalid Investment")
            val withdrawal =
                withdrawalPerMonth.toBigDecimalOrNull() ?: return Pair(false, "Invalid Withdrawal")
            val rate =
                expectedReturnRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Return Rate")
            val years =
                timePeriodYears.toBigDecimalOrNull() ?: return Pair(false, "Invalid Time Period")

            if (investment.compareTo(BigDecimal.ZERO) <= 0) Pair(
                false,
                "Investment must be greater than 0"
            )
            else if (withdrawal.compareTo(BigDecimal.ZERO) <= 0) Pair(
                false,
                "Withdrawal must be greater than 0"
            )
            else if (rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal("50")) > 0) Pair(
                false,
                "Rate must be between 0 and 50"
            )
            else if (years.compareTo(BigDecimal.ZERO) <= 0 || years.compareTo(BigDecimal("50")) > 0) Pair(
                false,
                "Years must be between 0 and 50"
            )
            else Pair(true, "")
        }
    }
}

package com.plus.calculatorplus.presentation.validation

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
                totalInvestment.toDoubleOrNull() ?: return Pair(false, "Invalid Investment")
            val withdrawal =
                withdrawalPerMonth.toDoubleOrNull() ?: return Pair(false, "Invalid Withdrawal")
            val rate =
                expectedReturnRate.toDoubleOrNull() ?: return Pair(false, "Invalid Return Rate")
            val years =
                timePeriodYears.toDoubleOrNull() ?: return Pair(false, "Invalid Time Period")

            if (investment <= 0) Pair(false, "Investment must be greater than 0")
            else if (withdrawal <= 0) Pair(false, "Withdrawal must be greater than 0")
            else if (rate <= 0 || rate > 50) Pair(false, "Rate must be between 0 and 50")
            else if (years <= 0 || years > 50) Pair(false, "Years must be between 0 and 50")
            else Pair(true, "")
        }
    }
}

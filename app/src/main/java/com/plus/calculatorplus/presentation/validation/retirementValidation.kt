package com.plus.calculatorplus.presentation.validation

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
            val cAge = currentAge.toIntOrNull() ?: return Pair(false, "Invalid Current Age")
            val rAge = retirementAge.toIntOrNull() ?: return Pair(false, "Invalid Retirement Age")
            val savings =
                monthlySavings.toDoubleOrNull() ?: return Pair(false, "Invalid Monthly Savings")
            val rate =
                expectedReturnRate.toDoubleOrNull() ?: return Pair(false, "Invalid Return Rate")

            if (cAge !in 1..100) Pair(false, "Current Age must be between 1 and 100")
            else if (rAge <= cAge) Pair(false, "Retirement Age must be greater than Current Age")
            else if (savings < 0) Pair(false, "Savings cannot be negative")
            else if (rate <= 0 || rate > 50) Pair(false, "Rate must be between 0 and 50")
            else Pair(true, "")
        }
    }
}

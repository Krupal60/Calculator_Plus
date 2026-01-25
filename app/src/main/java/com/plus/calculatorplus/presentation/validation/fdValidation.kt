package com.plus.calculatorplus.presentation.validation

fun fdValidation(
    investmentAmount: String,
    annualInterestRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        investmentAmount.isEmpty() -> {
            Pair(false, "Enter Investment Amount")
        }

        annualInterestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        years.isEmpty() -> {
            Pair(false, "Enter Years")
        }

        else -> {
            val amount = investmentAmount.toDoubleOrNull() ?: return Pair(false, "Invalid Amount")
            val interest =
                annualInterestRate.toDoubleOrNull() ?: return Pair(false, "Invalid Interest Rate")
            val period = years.toDoubleOrNull() ?: return Pair(false, "Invalid Years")

            if (amount <= 0) {
                Pair(false, "Amount must be greater than 0")
            } else if (interest <= 0 || interest > 50) {
                Pair(false, "Interest Rate must be between 0 and 50")
            } else if (period <= 0 || period > 50) {
                Pair(false, "Period must be between 0 and 50 years")
            } else {
                Pair(true, "")
            }
        }
    }
}

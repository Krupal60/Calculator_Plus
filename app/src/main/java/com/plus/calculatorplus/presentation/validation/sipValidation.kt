package com.plus.calculatorplus.presentation.validation

fun sipValidation(
    lumSum: Boolean,
    monthlyAmount: String,
    lumsumAmount: String,
    interestRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        lumSum && lumsumAmount.isEmpty() -> {
            Pair(false, "Enter Lumsum Amount")
        }

        interestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        monthlyAmount.isEmpty() -> {
            Pair(false, "Enter Monthly Amount")
        }

        years.isEmpty() -> {
            Pair(false, "Enter Investment Years")
        }

        else -> {
            // Perform individual validations and short-circuit if any fails
            val monthlyAmountResult = monthlyValidation(monthlyAmount)
            if (!monthlyAmountResult.first) return monthlyAmountResult

            val lumsumResult = lumsumValidation(lumSum, lumsumAmount)
            if (!lumsumResult.first) return lumsumResult

            val yearsResult = yearsValidation(years)
            if (!yearsResult.first) return yearsResult

            val interestRateResult = interestRateValidation(interestRate)
            if (!interestRateResult.first) return interestRateResult

            // All validations passed
            Pair(true, "")
        }
    }
}

fun yearsValidation(
    years: String
): Pair<Boolean, String> {
    return when {
        years.isEmpty() -> {
            Pair(false, "Enter Investment years")
        }

        else -> {
            Pair(true, "")
        }
    }
}

fun interestRateValidation(
    interestRate: String
): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        else -> {
            val interestRateInt = interestRate.toInt()
            if (interestRateInt > 1) {
                Pair(true, "")
            } else {
                Pair(false, "Interest Rate Must Greater than 1 %")
            }
        }
    }
}

fun monthlyValidation(
    monthlyAmount: String
): Pair<Boolean, String> {
    return when {
        monthlyAmount.isEmpty() -> {
            Pair(false, "Enter Monthly Amount")
        }

        else -> {
            val monthlyAmountInt = monthlyAmount.toInt()
            if (monthlyAmountInt >= 100) {
                Pair(true, "")
            } else {
                Pair(false, "Monthly Amount not less than 100 RS.")
            }

        }
    }
}

fun lumsumValidation(
    lumSum: Boolean,
    lumsumAmount: String
): Pair<Boolean, String> {
    return when {
        lumSum && lumsumAmount.isEmpty() -> {
            Pair(false, "Enter Lumsum Amount")
        }

        !lumSum -> {
            Pair(true, "") // No need to validate lumsumAmount if lumSum is false
        }

        else -> {
            val lumsumAmountInt = lumsumAmount.toInt()
            if (lumsumAmountInt >= 500) {
                Pair(true, "")
            } else {
                Pair(false, "Lumsum Amount not less than 500 RS.")
            }
        }
    }
}
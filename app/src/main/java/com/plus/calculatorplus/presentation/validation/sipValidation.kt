package com.plus.calculatorplus.presentation.validation

fun sipValidation(
    lumSum: Boolean,
    monthlyAmount: String,
    lumsumAmount: String,
    interestRate: String,
    years: String,
    withInflation: Boolean,
    inflationRate: String,
    withStepUp: Boolean,
    stepUpPercentage: String
): Pair<Boolean, String> {
    return when {
        lumSum && lumsumAmount.isEmpty() -> {
            Pair(false, "Enter Lumsum Amount")
        }

        interestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        !lumSum && monthlyAmount.isEmpty() -> {
            Pair(false, "Enter Monthly Amount")
        }

        years.isEmpty() -> {
            Pair(false, "Enter Investment Years")
        }

        withInflation && inflationRate.isEmpty() -> {
            Pair(false, "Enter Inflation Rate")
        }

        withStepUp && stepUpPercentage.isEmpty() -> {
            Pair(false, "Enter Step-up Percentage")
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

            val inflationResult = inflationValidation(inflationRate, withInflation)
            if (!inflationResult.first) return inflationResult

            val stepUpResult = stepUpValidation(stepUpPercentage, withStepUp)
            if (!stepUpResult.first) return stepUpResult

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
            val interestRateInt = interestRate.toDouble()
            if (interestRateInt > 1.0) {
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
            val monthlyAmountInt = monthlyAmount.toDouble()
            if (monthlyAmountInt >= 100.0) {
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
            val lumsumAmountInt = lumsumAmount.toDouble()
            if (lumsumAmountInt >= 500.0) {
                Pair(true, "")
            } else {
                Pair(false, "Lumsum Amount not less than 500 RS.")
            }
        }
    }
}

fun inflationValidation(
    inflationRate: String,
    withInflation: Boolean
): Pair<Boolean, String> {
    if (!withInflation) return Pair(true, "")

    return when {
        inflationRate.isEmpty() -> {
            Pair(false, "Enter Inflation Rate")
        }

        else -> {
            val rate = inflationRate.toDouble()
            if (rate > 0.0) {
                Pair(true, "")
            } else {
                Pair(false, "Inflation Rate Must Be Greater Than 0 %")
            }
        }
    }
}

fun stepUpValidation(
    stepUpPercentage: String,
    withStepUp: Boolean
): Pair<Boolean, String> {
    if (!withStepUp) return Pair(true, "")

    return when {
        stepUpPercentage.isEmpty() -> {
            Pair(false, "Enter Step-up Percentage")
        }

        else -> {
            val percentage = stepUpPercentage.toDouble()
            if (percentage > 0.0) {
                Pair(true, "")
            } else {
                Pair(false, "Step-up Percentage Must Be Greater Than 0 %")
            }
        }
    }
}
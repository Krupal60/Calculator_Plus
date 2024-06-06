package com.plus.calculatorplus.presentation.validation


fun emiValidation(
    loanAmount: String,
    interestRate: String,
    years: String
): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        loanAmount.isEmpty() -> {
            Pair(false, "Enter loan Amount")
        }

        years.isEmpty() -> {
            Pair(false, "Enter loan Years")
        }

        else -> {
            // Perform individual validations and short-circuit if any fails
            val loanAmountResult = loanAmountValidation(loanAmount)
            if (!loanAmountResult.first) return loanAmountResult

            val yearsResult = loanYearsValidation(years)
            if (!yearsResult.first) return yearsResult

            val interestRateResult = loanInterestRateValidation(interestRate)
            if (!interestRateResult.first) return interestRateResult

            // All validations passed
            Pair(true, "")
        }
    }
}

fun loanYearsValidation(
    years: String
): Pair<Boolean, String> {
    return when {
        years.isEmpty() -> {
            Pair(false, "Enter loan years")
        }

        else -> {
            Pair(true, "")
        }
    }
}

fun loanInterestRateValidation(
    interestRate: String
): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> {
            Pair(false, "Enter Interest Rate")
        }

        else -> {
            val interestRateInt = interestRate.toInt()
               if (interestRateInt < 0)  {
                Pair(false, "Enter valid Interest Rate")
                }
            if (interestRateInt >= 35 ){
                    Pair(false, "Interest Rate not greater then 35 %")
                }

                else {
                    Pair(true, "")
                }
            }
        }
    }


fun loanAmountValidation(
    loanAmount: String
): Pair<Boolean, String> {
    return when {
        loanAmount.isEmpty() -> {
            Pair(false, "Enter Monthly Amount")
        }

        else -> {
            val monthlyAmountInt = loanAmount.toInt()
            if (monthlyAmountInt >= 10000) {
                Pair(true, "")
            } else {
                Pair(false, "Monthly Amount not less than 10000")
            }

        }
    }
}


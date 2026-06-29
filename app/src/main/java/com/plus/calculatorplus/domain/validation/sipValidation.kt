package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

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
        lumSum && lumsumAmount.isEmpty() -> Pair(false, "Enter Lumsum Amount")
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        !lumSum && monthlyAmount.isEmpty() -> Pair(false, "Enter Monthly Amount")
        years.isEmpty() -> Pair(false, "Enter Investment Years")
        withInflation && inflationRate.isEmpty() -> Pair(false, "Enter Inflation Rate")
        withStepUp && stepUpPercentage.isEmpty() -> Pair(false, "Enter Step-up Percentage")
        else -> {
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

            Pair(true, "")
        }
    }
}

fun yearsValidation(years: String): Pair<Boolean, String> {
    return when {
        years.isEmpty() -> Pair(false, "Enter Investment years")
        else -> {
            val yearsValue = years.toBigDecimalOrNull() ?: return Pair(false, "Invalid Years")
            if (yearsValue.compareTo(BigDecimal("1")) >= 0 && yearsValue.compareTo(BigDecimal("100")) <= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Years must be between 1 and 100")
            }
        }
    }
}

fun interestRateValidation(interestRate: String): Pair<Boolean, String> {
    return when {
        interestRate.isEmpty() -> Pair(false, "Enter Interest Rate")
        else -> {
            val rate =
                interestRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Interest Rate")
            when {
                rate.compareTo(BigDecimal("1")) <= 0 -> Pair(
                    false,
                    "Interest Rate must be greater than 1%"
                )

                rate.compareTo(BigDecimal("50")) > 0 -> Pair(
                    false,
                    "Interest Rate must not exceed 50%"
                )

                else -> Pair(true, "")
            }
        }
    }
}

fun monthlyValidation(monthlyAmount: String): Pair<Boolean, String> {
    return when {
        monthlyAmount.isEmpty() -> Pair(false, "Enter Monthly Amount")
        else -> {
            val amount =
                monthlyAmount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Monthly Amount")
            if (amount.compareTo(BigDecimal("100")) >= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Monthly Amount must be at least 100")
            }
        }
    }
}

fun lumsumValidation(lumSum: Boolean, lumsumAmount: String): Pair<Boolean, String> {
    return when {
        lumSum && lumsumAmount.isEmpty() -> Pair(false, "Enter Lumsum Amount")
        !lumSum -> Pair(true, "")
        else -> {
            val amount =
                lumsumAmount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Lumsum Amount")
            if (amount.compareTo(BigDecimal("500")) >= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Lumsum Amount must be at least 500")
            }
        }
    }
}

fun inflationValidation(inflationRate: String, withInflation: Boolean): Pair<Boolean, String> {
    if (!withInflation) return Pair(true, "")

    return when {
        inflationRate.isEmpty() -> Pair(false, "Enter Inflation Rate")
        else -> {
            val rate =
                inflationRate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Inflation Rate")
            when {
                rate.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Inflation Rate must be greater than 0%"
                )

                rate.compareTo(BigDecimal("30")) > 0 -> Pair(
                    false,
                    "Inflation Rate must not exceed 30%"
                )

                else -> Pair(true, "")
            }
        }
    }
}

fun stepUpValidation(stepUpPercentage: String, withStepUp: Boolean): Pair<Boolean, String> {
    if (!withStepUp) return Pair(true, "")

    return when {
        stepUpPercentage.isEmpty() -> Pair(false, "Enter Step-up Percentage")
        else -> {
            val percentage = stepUpPercentage.toBigDecimalOrNull() ?: return Pair(
                false,
                "Invalid Step-up Percentage"
            )
            when {
                percentage.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Step-up Percentage must be greater than 0%"
                )

                percentage.compareTo(BigDecimal("100")) > 0 -> Pair(
                    false,
                    "Step-up Percentage must not exceed 100%"
                )

                else -> Pair(true, "")
            }
        }
    }
}

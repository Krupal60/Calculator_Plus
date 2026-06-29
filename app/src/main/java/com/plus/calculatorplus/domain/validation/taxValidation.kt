package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun taxValidation(
    amount: String,
    rate: String
): Pair<Boolean, String> {
    return when {
        amount.isEmpty() -> Pair(false, "Enter Amount")
        rate.isEmpty() -> Pair(false, "Enter Tax / GST Rate")
        else -> {
            val amountValue = amount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Amount")
            val rateValue = rate.toBigDecimalOrNull() ?: return Pair(false, "Invalid Rate")

            when {
                amountValue.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Amount must be greater than 0"
                )

                rateValue.compareTo(BigDecimal.ZERO) <= 0 || rateValue.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "Rate must be between 0 and 100")

                else -> Pair(true, "")
            }
        }
    }
}

package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun dividendValidation(
    sharePrice: String,
    dividendPerShare: String,
    numberOfShares: String
): Pair<Boolean, String> {
    return when {
        sharePrice.isEmpty() -> Pair(false, "Enter Share Price")
        dividendPerShare.isEmpty() -> Pair(false, "Enter Dividend Per Share")
        numberOfShares.isEmpty() -> Pair(false, "Enter Number of Shares")
        else -> {
            val price = sharePrice.toBigDecimalOrNull() ?: return Pair(false, "Invalid Share Price")
            val div =
                dividendPerShare.toBigDecimalOrNull() ?: return Pair(false, "Invalid Dividend")
            val shares = numberOfShares.toBigDecimalOrNull() ?: return Pair(
                false,
                "Invalid Number of Shares"
            )

            when {
                price.compareTo(BigDecimal.ZERO) <= 0 -> Pair(false, "Price must be greater than 0")
                div.compareTo(BigDecimal.ZERO) < 0 -> Pair(false, "Dividend cannot be negative")
                shares.compareTo(BigDecimal.ZERO) <= 0 -> Pair(
                    false,
                    "Shares must be greater than 0"
                )

                shares.compareTo(BigDecimal("1000000000")) > 0 -> Pair(
                    false,
                    "Shares cannot exceed 1 billion"
                )

                else -> Pair(true, "")
            }
        }
    }
}

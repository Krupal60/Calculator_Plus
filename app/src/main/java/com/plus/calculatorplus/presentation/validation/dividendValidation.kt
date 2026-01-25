package com.plus.calculatorplus.presentation.validation

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
            val price = sharePrice.toDoubleOrNull() ?: return Pair(false, "Invalid Share Price")
            val div = dividendPerShare.toDoubleOrNull() ?: return Pair(false, "Invalid Dividend")
            val shares =
                numberOfShares.toDoubleOrNull() ?: return Pair(false, "Invalid Number of Shares")

            if (price <= 0) Pair(false, "Price must be greater than 0")
            else if (div < 0) Pair(false, "Dividend cannot be negative")
            else if (shares <= 0) Pair(false, "Shares must be greater than 0")
            else Pair(true, "")
        }
    }
}

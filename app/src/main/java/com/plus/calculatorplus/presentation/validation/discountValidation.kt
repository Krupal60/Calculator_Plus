package com.plus.calculatorplus.presentation.validation

fun discountValidation(
    originalPrice: String,
    discountPercentage: String
): Pair<Boolean, String> {
    return when {
        originalPrice.isEmpty() -> {
            Pair(false, "Enter Original Price")
        }

        discountPercentage.isEmpty() -> {
            Pair(false, "Enter Discount Percentage")
        }

        else -> {
            val price = originalPrice.toDoubleOrNull() ?: return Pair(false, "Invalid Price")
            val discount =
                discountPercentage.toDoubleOrNull() ?: return Pair(false, "Invalid Discount")

            if (price <= 0) {
                Pair(false, "Price must be greater than 0")
            } else if (discount !in 0.0..100.0) {
                Pair(false, "Discount must be between 0 and 100")
            } else {
                Pair(true, "")
            }
        }
    }
}

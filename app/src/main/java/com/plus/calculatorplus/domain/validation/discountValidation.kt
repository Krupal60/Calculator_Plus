package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun discountValidation(
    originalPrice: String,
    discountPercentage: String
): Pair<Boolean, String> {
    return when {
        originalPrice.isEmpty() -> Pair(false, "Enter Original Price")
        discountPercentage.isEmpty() -> Pair(false, "Enter Discount Percentage")
        else -> {
            val price = originalPrice.toBigDecimalOrNull() ?: return Pair(false, "Invalid Price")
            val discount =
                discountPercentage.toBigDecimalOrNull() ?: return Pair(false, "Invalid Discount")

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                Pair(false, "Price must be greater than 0")
            } else if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal("100")) > 0) {
                Pair(false, "Discount must be between 0 and 100")
            } else {
                Pair(true, "")
            }
        }
    }
}

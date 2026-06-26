package com.plus.calculatorplus.domain.discount

import kotlin.math.roundToInt

class DiscountCalculationUseCase {

    data class DiscountInput(
        val originalPrice: String,
        val discountPercentage: String
    )

    data class DiscountResult(
        val originalPrice: String,
        val discountPercentage: String,
        val finalPrice: String,
        val savings: String
    )

    fun calculate(input: DiscountInput): DiscountResult {
        val originalPrice = input.originalPrice.toDoubleOrNull() ?: 0.0
        val discountPercentage = input.discountPercentage.toDoubleOrNull() ?: 0.0
        val savings = originalPrice * (discountPercentage / 100.0)
        val finalPrice = originalPrice - savings

        return DiscountResult(
            originalPrice = originalPrice.roundToInt().toString(),
            discountPercentage = discountPercentage.roundToInt().toString(),
            finalPrice = finalPrice.roundToInt().toString(),
            savings = savings.roundToInt().toString()
        )
    }
}

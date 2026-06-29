package com.plus.calculatorplus.domain.discount

import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
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
        val originalPrice = input.originalPrice.toBigDecimalOrZero()
        val discountPercentage = input.discountPercentage.toBigDecimalOrZero()
        val savings = originalPrice.multiply(
            discountPercentage.divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        )
        val finalPrice = originalPrice.subtract(savings)

        return DiscountResult(
            originalPrice = originalPrice.toCurrencyString(),
            discountPercentage = discountPercentage.toCurrencyString(),
            finalPrice = finalPrice.toCurrencyString(),
            savings = savings.toCurrencyString()
        )
    }
}

package com.plus.calculatorplus.domain.lumpsum

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class LumpsumCalculationUseCase {

    data class LumpsumInput(
        val principal: String,
        val annualRate: String,
        val years: String
    )

    data class LumpsumResult(
        val totalAmount: String,
        val estimateReturnsAmount: String,
        val investedAmount: String
    )

    fun calculate(input: LumpsumInput): LumpsumResult {
        val principal = input.principal.toBigDecimalOrZero()
        val rate = input.annualRate.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
        val years = input.years.toBigDecimalOrZero()

        val totalValue = principal.multiply(
            BigDecimal.ONE.add(rate).powExponent(years.toDouble())
        )
        val estimatedReturn = totalValue.subtract(principal)

        return LumpsumResult(
            totalAmount = totalValue.toCurrencyString(),
            estimateReturnsAmount = estimatedReturn.toCurrencyString(),
            investedAmount = principal.toCurrencyString()
        )
    }
}

package com.plus.calculatorplus.domain.dividend

import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class DividendCalculationUseCase {

    data class DividendInput(
        val sharePrice: String,
        val dividendPerShare: String,
        val numberOfShares: String
    )

    data class DividendResult(
        val totalDividend: String,
        val dividendYield: String,
        val annualDividend: String
    )

    fun calculate(input: DividendInput): DividendResult {
        val sharePrice = input.sharePrice.toBigDecimalOrZero()
        val dividendPerShare = input.dividendPerShare.toBigDecimalOrZero()
        val numberOfShares = input.numberOfShares.toBigDecimalOrZero()

        val totalDividend = dividendPerShare.multiply(numberOfShares)
        val yield = if (sharePrice.isGreaterThan(BigDecimal.ZERO)) {
            dividendPerShare.divide(sharePrice, 10, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal("100"))
        } else {
            BigDecimal.ZERO
        }

        return DividendResult(
            totalDividend = totalDividend.toCurrencyString(),
            dividendYield = yield.setScale(2, RoundingMode.HALF_UP).toPlainString(),
            annualDividend = totalDividend.toCurrencyString()
        )
    }

    private fun BigDecimal.isGreaterThan(other: BigDecimal): Boolean =
        this.compareTo(other) > 0
}

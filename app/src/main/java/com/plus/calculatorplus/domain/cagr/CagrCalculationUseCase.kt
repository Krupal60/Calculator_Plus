package com.plus.calculatorplus.domain.cagr

import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class CagrCalculationUseCase {

    data class CagrInput(
        val initialValue: String,
        val finalValue: String,
        val years: String
    )

    data class CagrResult(
        val cagrPercentage: String,
        val totalGain: String,
        val initialValue: String,
        val finalValue: String
    )

    fun calculate(input: CagrInput): CagrResult {
        val initial = input.initialValue.toBigDecimalOrZero()
        val final = input.finalValue.toBigDecimalOrZero()
        val years = input.years.toBigDecimalOrZero()

        val cagr =
            if (initial.compareTo(BigDecimal.ZERO) > 0 && years.compareTo(BigDecimal.ZERO) > 0) {
                final.divide(initial, 10, RoundingMode.HALF_EVEN)
                    .powExponent(1.0 / years.toDouble())
                    .subtract(BigDecimal.ONE)
                    .multiply(BigDecimal("100"))
            } else {
                BigDecimal.ZERO
            }
        val totalGain = final.subtract(initial)

        return CagrResult(
            cagrPercentage = cagr.setScale(2, RoundingMode.HALF_UP).toPlainString(),
            totalGain = totalGain.toCurrencyString(),
            initialValue = initial.toCurrencyString(),
            finalValue = final.toCurrencyString()
        )
    }
}

package com.plus.calculatorplus.domain.inflation

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class InflationCalculationUseCase {

    data class InflationInput(
        val amount: String,
        val inflationRate: String,
        val years: String
    )

    data class InflationResult(
        val currentAmount: String,
        val futureCost: String,
        val purchasingPower: String
    )

    fun calculate(input: InflationInput): InflationResult {
        val amount = input.amount.toBigDecimalOrZero()
        val rate = input.inflationRate.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
        val years = input.years.toBigDecimalOrZero()

        val growthFactor = BigDecimal.ONE.add(rate).powExponent(years.toDouble())
        // Future cost of buying today's basket after N years of inflation.
        val futureCost = amount.multiply(growthFactor)
        // What today's amount will be worth in today's money after N years.
        val purchasingPower = amount.divide(growthFactor, 10, RoundingMode.HALF_EVEN)

        return InflationResult(
            currentAmount = amount.toCurrencyString(),
            futureCost = futureCost.toCurrencyString(),
            purchasingPower = purchasingPower.toCurrencyString()
        )
    }
}

package com.plus.calculatorplus.domain.swp

import com.plus.calculatorplus.domain.BD_TWELVE
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class SwpCalculationUseCase {

    data class SwpInput(
        val totalInvestment: String,
        val withdrawalPerMonth: String,
        val expectedReturnRate: String,
        val timePeriodYears: String
    )

    data class SwpResult(
        val totalInvestment: String,
        val totalWithdrawal: String,
        val finalValue: String
    )

    fun calculate(input: SwpInput): SwpResult {
        val principal = input.totalInvestment.toBigDecimalOrZero()
        val withdrawal = input.withdrawalPerMonth.toBigDecimalOrZero()
        val annualRate = input.expectedReturnRate.toBigDecimalOrZero()
        val years = input.timePeriodYears.toBigDecimalOrZero()

        if (principal == BigDecimal.ZERO || withdrawal == BigDecimal.ZERO) {
            return SwpResult("0", "0", "0")
        }

        val monthlyRate = annualRate.divide(BD_TWELVE, 10, RoundingMode.HALF_EVEN)
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val totalMonths = (years.toDouble() * 12).toInt()
        var balance = principal
        var totalWithdrawn = BigDecimal.ZERO

        repeat(totalMonths) {
            if (balance.isLessThanOrEqual(BigDecimal.ZERO)) return@repeat
            balance = balance.subtract(withdrawal)
            if (balance.isLessThan(BigDecimal.ZERO)) balance = BigDecimal.ZERO
            totalWithdrawn = totalWithdrawn.add(withdrawal)
            balance = balance.multiply(BigDecimal.ONE.add(monthlyRate))
        }

        return SwpResult(
            totalInvestment = principal.toCurrencyString(),
            totalWithdrawal = totalWithdrawn.toCurrencyString(),
            finalValue = balance.toCurrencyString()
        )
    }

    private fun BigDecimal.isLessThanOrEqual(other: BigDecimal): Boolean =
        this.compareTo(other) <= 0

    private fun BigDecimal.isLessThan(other: BigDecimal): Boolean =
        this.compareTo(other) < 0
}

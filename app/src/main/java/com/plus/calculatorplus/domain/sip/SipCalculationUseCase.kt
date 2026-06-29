package com.plus.calculatorplus.domain.sip

import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class SipCalculationUseCase {

    data class SipInput(
        val isLumsum: Boolean,
        val monthlyAmount: String,
        val lumsumAmount: String,
        val interest: String,
        val investedYears: String,
        val withInflation: Boolean = false,
        val inflationRate: String = "0",
        val withStepUp: Boolean = false,
        val stepUpPercentage: String = "0"
    )

    data class SipResult(
        val totalAmount: String,
        val estimateReturnsAmount: String,
        val investedAmount: String
    )

    fun calculate(input: SipInput): SipResult {
        return if (input.isLumsum) {
            calculateLumsum(input)
        } else {
            calculateSip(input)
        }
    }

    private fun calculateLumsum(input: SipInput): SipResult {
        val principal = input.lumsumAmount.toBigDecimalOrZero()
        val years = input.investedYears.toBigDecimalOrZero()
        val nominalAnnualReturn = input.interest.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val inflation = input.inflationRate.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val effectiveAnnualReturn = if (input.withInflation) {
            BigDecimal.ONE.add(nominalAnnualReturn)
                .divide(BigDecimal.ONE.add(inflation), 10, RoundingMode.HALF_EVEN)
                .subtract(BigDecimal.ONE)
        } else {
            nominalAnnualReturn
        }
        val totalValue = principal.multiply(
            BigDecimal.ONE.add(effectiveAnnualReturn).powExponent(years.toDouble())
        )
        val estimatedReturn = totalValue.subtract(principal)

        return SipResult(
            totalAmount = totalValue.toCurrencyString(),
            estimateReturnsAmount = estimatedReturn.toCurrencyString(),
            investedAmount = principal.toCurrencyString()
        )
    }

    private fun calculateSip(input: SipInput): SipResult {
        val baseMonthly = input.monthlyAmount.toBigDecimalOrZero()
        val years = input.investedYears.toBigDecimalOrZero()
        val nominalAnnualReturn = input.interest.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val inflation = input.inflationRate.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val stepUp = input.stepUpPercentage.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val effectiveAnnualReturn = if (input.withInflation) {
            BigDecimal.ONE.add(nominalAnnualReturn)
                .divide(BigDecimal.ONE.add(inflation), 10, RoundingMode.HALF_EVEN)
                .subtract(BigDecimal.ONE)
        } else {
            nominalAnnualReturn
        }
        val monthlyRate = BigDecimal.ONE.add(effectiveAnnualReturn)
            .powExponent(1.0 / 12)
            .subtract(BigDecimal.ONE)

        var totalValue = BigDecimal.ZERO
        var totalInvested = BigDecimal.ZERO
        var currentMonthlyAmount = baseMonthly

        val totalMonths = (years.toDouble() * 12).toInt()
        for (month in 1..totalMonths) {
            totalValue =
                totalValue.add(currentMonthlyAmount).multiply(BigDecimal.ONE.add(monthlyRate))
            totalInvested = totalInvested.add(currentMonthlyAmount)
            if (input.withStepUp && month % 12 == 0) {
                currentMonthlyAmount = currentMonthlyAmount.multiply(BigDecimal.ONE.add(stepUp))
            }
        }

        val estimatedReturn = totalValue.subtract(totalInvested)

        return SipResult(
            totalAmount = totalValue.toCurrencyString(),
            estimateReturnsAmount = estimatedReturn.toCurrencyString(),
            investedAmount = totalInvested.toCurrencyString()
        )
    }
}

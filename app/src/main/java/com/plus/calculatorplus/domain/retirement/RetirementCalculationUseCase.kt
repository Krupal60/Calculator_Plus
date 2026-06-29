package com.plus.calculatorplus.domain.retirement

import com.plus.calculatorplus.domain.BD_TWELVE
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class RetirementCalculationUseCase {

    data class RetirementInput(
        val currentAge: String,
        val retirementAge: String,
        val monthlySavings: String,
        val expectedReturnRate: String,
        val currentSavings: String
    )

    data class RetirementResult(
        val retirementCorpus: String,
        val totalInvested: String,
        val estimatedReturns: String
    )

    fun calculate(input: RetirementInput): RetirementResult {
        val currentAge = input.currentAge.toBigDecimalOrZero()
        val retirementAge = input.retirementAge.toBigDecimalOrZero()
        val monthlySavings = input.monthlySavings.toBigDecimalOrZero()
        val annualRate = input.expectedReturnRate.toBigDecimalOrZero()
        val currentSavings = input.currentSavings.toBigDecimalOrZero()

        val yearsToRetire = retirementAge.subtract(currentAge)
        if (yearsToRetire.isLessThanOrEqual(BigDecimal.ZERO)) return RetirementResult("0", "0", "0")

        val monthlyRate = annualRate.divide(BD_TWELVE, 10, RoundingMode.HALF_EVEN)
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val totalMonths = yearsToRetire.multiply(BD_TWELVE).toDouble().toInt()

        val onePlusRate = BigDecimal.ONE.add(monthlyRate)
        val fvCurrentSavings = currentSavings.multiply(
            onePlusRate.powExponent(totalMonths.toDouble())
        )
        val powFactor = onePlusRate.powExponent(totalMonths.toDouble())
        val fvMonthlyContributions = monthlySavings.multiply(
            powFactor.subtract(BigDecimal.ONE)
                .divide(monthlyRate, 10, RoundingMode.HALF_EVEN)
                .multiply(onePlusRate)
        )
        val totalCorpus = fvCurrentSavings.add(fvMonthlyContributions)
        val totalInvested = currentSavings.add(
            monthlySavings.multiply(BigDecimal.valueOf(totalMonths.toLong()))
        )

        return RetirementResult(
            retirementCorpus = totalCorpus.toCurrencyString(),
            totalInvested = totalInvested.toCurrencyString(),
            estimatedReturns = totalCorpus.subtract(totalInvested).toCurrencyString()
        )
    }

    private fun BigDecimal.isLessThanOrEqual(other: BigDecimal): Boolean =
        this.compareTo(other) <= 0
}

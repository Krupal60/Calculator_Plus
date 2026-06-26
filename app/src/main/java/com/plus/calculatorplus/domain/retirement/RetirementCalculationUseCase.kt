package com.plus.calculatorplus.domain.retirement

import kotlin.math.pow
import kotlin.math.roundToInt

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
        val currentAge = input.currentAge.toIntOrNull() ?: 0
        val retirementAge = input.retirementAge.toIntOrNull() ?: 0
        val monthlySavings = input.monthlySavings.toDoubleOrNull() ?: 0.0
        val annualRate = input.expectedReturnRate.toDoubleOrNull() ?: 0.0
        val currentSavings = input.currentSavings.toDoubleOrNull() ?: 0.0

        val yearsToRetire = retirementAge - currentAge
        if (yearsToRetire <= 0) return RetirementResult("0", "0", "0")

        val monthlyRate = annualRate / 12.0 / 100.0
        val totalMonths = yearsToRetire * 12

        val fvCurrentSavings = currentSavings * (1 + monthlyRate).pow(totalMonths.toDouble())
        val fvMonthlyContributions = monthlySavings *
                  ((1 + monthlyRate).pow(totalMonths.toDouble()) - 1) / monthlyRate * (1 + monthlyRate)
        val totalCorpus = fvCurrentSavings + fvMonthlyContributions
        val totalInvested = currentSavings + (monthlySavings * totalMonths)

        return RetirementResult(
            retirementCorpus = totalCorpus.roundToInt().toString(),
            totalInvested = totalInvested.roundToInt().toString(),
            estimatedReturns = (totalCorpus - totalInvested).roundToInt().toString()
        )
    }
}

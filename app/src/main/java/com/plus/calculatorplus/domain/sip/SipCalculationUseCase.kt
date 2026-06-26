package com.plus.calculatorplus.domain.sip

import kotlin.math.pow
import kotlin.math.roundToInt

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
        val principal = input.lumsumAmount.toDouble()
        val years = input.investedYears.toInt()
        val nominalAnnualReturn = input.interest.toDouble() / 100
        val inflation = input.inflationRate.toDouble() / 100
        val effectiveAnnualReturn = if (input.withInflation) {
            ((1 + nominalAnnualReturn) / (1 + inflation)) - 1
        } else {
            nominalAnnualReturn
        }
        val totalValue = principal * (1 + effectiveAnnualReturn).pow(years.toDouble())
        val estimatedReturn = totalValue - principal

        return SipResult(
            totalAmount = totalValue.roundToInt().toString(),
            estimateReturnsAmount = estimatedReturn.roundToInt().toString(),
            investedAmount = principal.roundToInt().toString()
        )
    }

    private fun calculateSip(input: SipInput): SipResult {
        val baseMonthly = input.monthlyAmount.toDouble()
        val years = input.investedYears.toInt()
        val nominalAnnualReturn = input.interest.toDouble() / 100
        val inflation = input.inflationRate.toDouble() / 100
        val stepUp = input.stepUpPercentage.toDouble() / 100
        val effectiveAnnualReturn = if (input.withInflation) {
            ((1 + nominalAnnualReturn) / (1 + inflation)) - 1
        } else {
            nominalAnnualReturn
        }
        val monthlyRate = (1 + effectiveAnnualReturn).pow(1.0 / 12) - 1

        var totalValue = 0.0
        var totalInvested = 0.0
        var currentMonthlyAmount = baseMonthly

        for (year in 1..years) {
            repeat(12) {
                totalValue = (totalValue + currentMonthlyAmount) * (1 + monthlyRate)
                totalInvested += currentMonthlyAmount
            }
            if (input.withStepUp) {
                currentMonthlyAmount *= (1 + stepUp)
            }
        }

        val estimatedReturn = totalValue - totalInvested

        return SipResult(
            totalAmount = totalValue.roundToInt().toString(),
            estimateReturnsAmount = estimatedReturn.roundToInt().toString(),
            investedAmount = totalInvested.roundToInt().toString()
        )
    }
}

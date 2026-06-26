package com.plus.calculatorplus.domain.fd

import kotlin.math.pow
import kotlin.math.roundToInt

class FdCalculationUseCase {

    data class FdInput(
        val investmentAmount: String,
        val annualInterestRate: String,
        val years: String
    )

    data class FdResult(
        val totalInvestment: String,
        val estimatedReturns: String,
        val totalValue: String
    )

    fun calculate(input: FdInput): FdResult {
        val p = input.investmentAmount.toDoubleOrNull() ?: 0.0
        val r = input.annualInterestRate.toDoubleOrNull() ?: 0.0
        val t = input.years.toDoubleOrNull() ?: 0.0
        val maturityValue = p * (1 + r / 400.0).pow(4 * t)
        val estReturns = maturityValue - p

        return FdResult(
            totalInvestment = p.roundToInt().toString(),
            estimatedReturns = estReturns.roundToInt().toString(),
            totalValue = maturityValue.roundToInt().toString()
        )
    }
}

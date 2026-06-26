package com.plus.calculatorplus.domain.dividend

import kotlin.math.roundToInt

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
        val sharePrice = input.sharePrice.toDoubleOrNull() ?: 0.0
        val dividendPerShare = input.dividendPerShare.toDoubleOrNull() ?: 0.0
        val numberOfShares = input.numberOfShares.toDoubleOrNull() ?: 0.0

        val totalDividend = dividendPerShare * numberOfShares
        val yield = if (sharePrice > 0) (dividendPerShare / sharePrice) * 100 else 0.0

        return DividendResult(
            totalDividend = totalDividend.roundToInt().toString(),
            dividendYield = String.format("%.2f", yield),
            annualDividend = totalDividend.roundToInt().toString()
        )
    }
}

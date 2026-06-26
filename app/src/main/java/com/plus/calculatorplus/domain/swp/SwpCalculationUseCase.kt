package com.plus.calculatorplus.domain.swp

import kotlin.math.roundToInt

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
        val principal = input.totalInvestment.toDoubleOrNull() ?: return SwpResult("0", "0", "0")
        val withdrawal =
            input.withdrawalPerMonth.toDoubleOrNull() ?: return SwpResult("0", "0", "0")
        val annualRate =
            input.expectedReturnRate.toDoubleOrNull() ?: return SwpResult("0", "0", "0")
        val years = input.timePeriodYears.toDoubleOrNull() ?: return SwpResult("0", "0", "0")

        val monthlyRate = annualRate / 12 / 100
        val totalMonths = (years * 12).toInt()
        var balance = principal
        var totalWithdrawn = 0.0

        repeat(totalMonths) {
            if (balance <= 0) return@repeat
            balance -= withdrawal
            if (balance < 0) balance = 0.0
            totalWithdrawn += withdrawal
            balance *= (1 + monthlyRate)
        }

        return SwpResult(
            totalInvestment = principal.roundToInt().toString(),
            totalWithdrawal = totalWithdrawn.roundToInt().toString(),
            finalValue = balance.roundToInt().toString()
        )
    }
}

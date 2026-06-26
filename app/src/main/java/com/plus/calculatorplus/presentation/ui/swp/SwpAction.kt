package com.plus.calculatorplus.presentation.ui.swp

sealed class SwpAction {
    data class CalculateSwp(
        val totalInvestment: String,
        val withdrawalPerMonth: String,
        val expectedReturnRate: String,
        val timePeriodYears: String
    ) : SwpAction()
}

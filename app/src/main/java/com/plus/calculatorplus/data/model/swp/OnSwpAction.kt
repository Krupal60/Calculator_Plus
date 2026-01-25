package com.plus.calculatorplus.data.model.swp

sealed class OnSwpAction {
    data class CalculateSwp(
        val totalInvestment: String,
        val withdrawalPerMonth: String,
        val expectedReturnRate: String,
        val timePeriodYears: String
    ) : OnSwpAction()
}

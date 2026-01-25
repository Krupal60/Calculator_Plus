package com.plus.calculatorplus.data.model.retirement

sealed class OnRetirementAction {
    data class CalculateRetirement(
        val currentAge: String,
        val retirementAge: String,
        val monthlySavings: String,
        val expectedReturnRate: String,
        val currentSavings: String
    ) : OnRetirementAction()
}

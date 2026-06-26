package com.plus.calculatorplus.presentation.ui.retirement

sealed class RetirementAction {
    data class CalculateRetirement(
        val currentAge: String,
        val retirementAge: String,
        val monthlySavings: String,
        val expectedReturnRate: String,
        val currentSavings: String
    ) : RetirementAction()
}

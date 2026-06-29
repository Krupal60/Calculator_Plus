package com.plus.calculatorplus.presentation.ui.loanaffordability

sealed class LoanAffordabilityAction {
    data class CalculateAffordability(
        val monthlyIncome: String,
        val existingEmi: String,
        val interestRate: String,
        val years: String,
        val foirPercentage: String
    ) : LoanAffordabilityAction()
}

package com.plus.calculatorplus.presentation.ui.loanaffordability

sealed class LoanAffordabilityEffect {
    data class ShowToast(val message: String) : LoanAffordabilityEffect()
}

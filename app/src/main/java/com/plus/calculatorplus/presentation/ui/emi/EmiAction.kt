package com.plus.calculatorplus.presentation.ui.emi

sealed class EmiAction {
    data class CalculateEmi(
        val loanAmount: String,
        val loanInterest: String,
        val loanYear: String
    ) : EmiAction()
}

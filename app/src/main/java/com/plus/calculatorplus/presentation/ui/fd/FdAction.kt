package com.plus.calculatorplus.presentation.ui.fd

sealed class FdAction {
    data class CalculateFd(
        val investmentAmount: String,
        val annualInterestRate: String,
        val years: String
    ) : FdAction()
}

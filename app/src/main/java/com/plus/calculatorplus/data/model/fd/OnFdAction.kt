package com.plus.calculatorplus.data.model.fd

sealed class OnFdAction {
    data class CalculateFd(
        val investmentAmount: String,
        val annualInterestRate: String,
        val years: String
    ) : OnFdAction()
}

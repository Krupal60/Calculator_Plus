package com.plus.calculatorplus.presentation.ui.loanprepayment

sealed class LoanPrepaymentAction {
    data class CalculatePrepayment(
        val principal: String,
        val interestRate: String,
        val years: String,
        val extraMonthly: String
    ) : LoanPrepaymentAction()
}

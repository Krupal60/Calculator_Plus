package com.plus.calculatorplus.presentation.ui.loanprepayment

sealed class LoanPrepaymentEffect {
    data class ShowToast(val message: String) : LoanPrepaymentEffect()
}

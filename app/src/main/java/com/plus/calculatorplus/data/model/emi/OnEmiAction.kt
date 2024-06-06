package com.plus.calculatorplus.data.model.emi

sealed class OnEmiAction {
    data class CalculateEmi(
        val loanAmount: String,
        val loanInterest: String,
        val loanYear: String
    ) : OnEmiAction()

}
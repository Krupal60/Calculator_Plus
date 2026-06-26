package com.plus.calculatorplus.presentation.ui.emi

data class EmiState(
    val monthlyEmi: String = "0",
    val loanAmount: String = "0",
    val totalInterest: String = "0",
    val totalAmount: String = "0"
)

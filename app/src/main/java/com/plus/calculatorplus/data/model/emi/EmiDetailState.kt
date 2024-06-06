package com.plus.calculatorplus.data.model.emi

data class EmiDetailState(
    val monthlyEmi: String = "0",
    val loanAmount: String = "0",
    val totalInterest: String = "0",
    val totalAmount: String = "0"
)

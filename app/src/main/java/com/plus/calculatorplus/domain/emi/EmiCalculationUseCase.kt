package com.plus.calculatorplus.domain.emi

import kotlin.math.pow
import kotlin.math.roundToInt

class EmiCalculationUseCase {

    data class EmiInput(
        val loanAmount: String,
        val loanInterest: String,
        val loanYear: String
    )

    data class EmiResult(
        val monthlyEmi: String,
        val totalAmount: String,
        val totalInterest: String,
        val loanAmount: String
    )

    fun calculate(input: EmiInput): EmiResult {
        val loanAmount = input.loanAmount.toDouble()
        val annualInterestRate = input.loanInterest.toDouble() / 100.0
        val loanYears = input.loanYear.toDouble()
        val monthlyInterestRate = annualInterestRate / 12.0
        val numberOfPayments = loanYears * 12
        val emi =
            (loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(numberOfPayments)) /
                      ((1 + monthlyInterestRate).pow(numberOfPayments) - 1)
        val totalPayment = emi * numberOfPayments
        val totalInterest = totalPayment - loanAmount

        return EmiResult(
            monthlyEmi = emi.roundToInt().toString(),
            totalAmount = (loanAmount + totalInterest).roundToInt().toString(),
            totalInterest = totalInterest.roundToInt().toString(),
            loanAmount = input.loanAmount
        )
    }
}

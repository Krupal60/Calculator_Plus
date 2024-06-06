package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.emi.EmiDetailState
import com.plus.calculatorplus.data.model.emi.OnEmiAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow

class EmiViewModel : ViewModel() {

    var state = MutableStateFlow(EmiDetailState())
        private set


    fun onAction(onEmiAction: OnEmiAction) {
        when (onEmiAction) {
            is OnEmiAction.CalculateEmi -> calculateEmi(
                onEmiAction.loanAmount,
                onEmiAction.loanInterest,
                onEmiAction.loanYear
            )
        }
    }

    //EMI = [P x R x (1+R) ^N]/ [(1+R) ^ (N-1)]
    private fun calculateEmi(loanAmountStr: String, loanInterestStr: String, loanYearsStr: String) {
        val loanAmount = loanAmountStr.toInt()
        val annualInterestRate = loanInterestStr.toDouble() / 100.0
        val loanYears = loanYearsStr.toInt()

        // Calculate monthly interest rate
        val monthlyInterestRate = annualInterestRate / 12.0

        // Calculate number of payments
        val numberOfPayments = loanYears * 12

        // Calculate EMI
        val emi = (loanAmount * monthlyInterestRate * (1 + monthlyInterestRate).pow(numberOfPayments)) /
                ((1 + monthlyInterestRate).pow(numberOfPayments) - 1)

        // Calculate total payment and total interest
        val totalPayment = (emi * numberOfPayments).toInt()
        val totalInterest = (totalPayment - loanAmount)

        // Calculate monthly EMI
        val monthlyEmi = emi.toInt()

        // Update state
        state.value = state.value.copy(
            monthlyEmi = monthlyEmi.toString(),
            totalAmount = (loanAmount + totalInterest).toString(),
            totalInterest = totalInterest.toString(),
            loanAmount = loanAmountStr
        )
    }

    override fun onCleared() {
        super.onCleared()
        state.value = state.value
    }

}

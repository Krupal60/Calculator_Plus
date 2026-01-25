package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.sip.OnSipAction
import com.plus.calculatorplus.data.model.sip.SipDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow

class SipViewModel : ViewModel() {

    var state = MutableStateFlow(SipDetailState())
        private set

    fun onAction(action: OnSipAction) {
        when (action) {
            is OnSipAction.CalculateSip -> calculate(action)
        }
    }

    private fun calculate(action: OnSipAction.CalculateSip) {
        val monthlyAmount = action.monthlyAmount
        val isLumsum = action.isLumsum
        val interest = action.interest
        val investedYears = action.investedYears
        when {
            isLumsum -> calculatLumsum(
                action.lumsumAmount,
                interest,
                investedYears,
            )
            else -> calculateSip(monthlyAmount, interest, investedYears)
        }
    }


    private fun calculatLumsum(
        lumsumAmount: String,
        interest: String,
        investedYears: String
    ) {
        val lumsumInvestmentAmount = lumsumAmount.toDouble()
        val initialInterestRate = interest.toDouble() / 100
        val investmentYears = investedYears.toDouble()


        // Calculating accumulated amount using compound interest formula
        val accumulatedAmount = lumsumInvestmentAmount * (1 + initialInterestRate).pow(
            investmentYears
        )

        // Calculating estimated returns (interest earned)
        val estimateReturn = accumulatedAmount - lumsumInvestmentAmount

        // Total invested amount
        val totalInvestedAmount = lumsumInvestmentAmount


        state.value = state.value.copy(
            totalAmount = accumulatedAmount.toInt().toString(),
            estimateReturnsAmount = estimateReturn.toInt().toString(),
            investedAmount = totalInvestedAmount.toInt().toString()
        )
    }





    private fun calculateSip(monthlyAmount: String, interest: String, investedYears: String) {
        val monthlyInvestmentAmount = monthlyAmount.toDouble()
        val expectedReturnRate = interest.toDouble()
        val investmentTimePeriod = investedYears.toDouble() * 12
        val periodicInterest = ((expectedReturnRate.toFloat() / 12) / 100)

        val totalInvestedAmount = monthlyInvestmentAmount * investmentTimePeriod

        val totalValue = monthlyInvestmentAmount * ((((1 + periodicInterest).toDouble()
            .pow(investmentTimePeriod) - 1) / periodicInterest) * (1 + periodicInterest)).toLong()
        val estimateReturn = totalValue - totalInvestedAmount

        state.value = state.value.copy(
            totalAmount = totalValue.toInt().toString(),
            estimateReturnsAmount = estimateReturn.toInt().toString(),
            investedAmount = totalInvestedAmount.toInt().toString()
        )
    }

    override fun onCleared() {
        super.onCleared()
        state.value = SipDetailState()
    }
}

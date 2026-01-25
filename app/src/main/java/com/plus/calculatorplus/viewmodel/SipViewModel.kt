package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.sip.OnSipAction
import com.plus.calculatorplus.data.model.sip.SipDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow
import kotlin.math.roundToInt

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
            isLumsum -> calculateLumsum(
                action.lumsumAmount,
                interest,
                investedYears,
                action.withInflation,
                action.inflationRate
            )

            else -> calculateSip(
                monthlyAmount,
                interest,
                investedYears,
                action.withInflation,
                action.inflationRate,
                action.withStepUp,
                action.stepUpPercentage
            )
        }
    }


    private fun calculateLumsum(
        lumsumAmount: String,
        interest: String,
        investedYears: String,
        withInflation: Boolean,
        inflationRate: String
    ) {
        val principal = lumsumAmount.toDouble()
        val years = investedYears.toInt()

        val nominalAnnualReturn = interest.toDouble() / 100
        val inflation = inflationRate.toDouble() / 100

        val effectiveAnnualReturn =
            if (withInflation) {
                ((1 + nominalAnnualReturn) / (1 + inflation)) - 1
            } else {
                nominalAnnualReturn
            }

        val totalValue =
            principal * (1 + effectiveAnnualReturn).pow(years.toDouble())

        val estimatedReturn = totalValue - principal

        state.value = state.value.copy(
            totalAmount = totalValue.roundToInt().toString(),
            estimateReturnsAmount = estimatedReturn.roundToInt().toString(),
            investedAmount = principal.roundToInt().toString()
        )
    }


    private fun calculateSip(
        monthlyAmount: String,
        interest: String,
        investedYears: String,
        withInflation: Boolean,
        inflationRate: String,
        withStepUp: Boolean,
        stepUpPercentage: String
    ) {
        val baseMonthly = monthlyAmount.toDouble()
        val years = investedYears.toInt()

        val nominalAnnualReturn = interest.toDouble() / 100
        val inflation = inflationRate.toDouble() / 100
        val stepUp = stepUpPercentage.toDouble() / 100

        // Inflation-adjusted (real) annual return
        val effectiveAnnualReturn =
            if (withInflation) {
                ((1 + nominalAnnualReturn) / (1 + inflation)) - 1
            } else {
                nominalAnnualReturn
            }


        val monthlyRate =
            (1 + effectiveAnnualReturn).pow(1.0 / 12) - 1

        var totalValue = 0.0
        var totalInvested = 0.0
        var currentMonthlyAmount = baseMonthly

        for (year in 1..years) {

            repeat(12) {
                totalValue =
                    (totalValue + currentMonthlyAmount) * (1 + monthlyRate)
                totalInvested += currentMonthlyAmount
            }

            // Annual step-up
            if (withStepUp) {
                currentMonthlyAmount *= (1 + stepUp)
            }
        }

        val estimatedReturn = totalValue - totalInvested

        state.value = state.value.copy(
            totalAmount = totalValue.roundToInt().toString(),
            estimateReturnsAmount = estimatedReturn.roundToInt().toString(),
            investedAmount = totalInvested.roundToInt().toString()
        )
    }

    override fun onCleared() {
        super.onCleared()
        state.value = SipDetailState()
    }
}

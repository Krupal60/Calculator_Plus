package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.retirement.OnRetirementAction
import com.plus.calculatorplus.data.model.retirement.RetirementDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow
import kotlin.math.roundToInt

class RetirementViewModel : ViewModel() {

    private val _state = MutableStateFlow(RetirementDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: OnRetirementAction) {
        when (action) {
            is OnRetirementAction.CalculateRetirement -> calculateRetirement(
                action.currentAge,
                action.retirementAge,
                action.monthlySavings,
                action.expectedReturnRate,
                action.currentSavings
            )
        }
    }

    private fun calculateRetirement(
        currentAgeStr: String,
        retirementAgeStr: String,
        monthlySavingsStr: String,
        rateStr: String,
        currentSavingsStr: String
    ) {
        val currentAge = currentAgeStr.toIntOrNull() ?: 0
        val retirementAge = retirementAgeStr.toIntOrNull() ?: 0
        val monthlySavings = monthlySavingsStr.toDoubleOrNull() ?: 0.0
        val annualRate = rateStr.toDoubleOrNull() ?: 0.0
        val currentSavings = currentSavingsStr.toDoubleOrNull() ?: 0.0

        val yearsToRetire = retirementAge - currentAge
        if (yearsToRetire <= 0) return

        val monthlyRate = annualRate / 12.0 / 100.0
        val totalMonths = yearsToRetire * 12

        // FV of current savings: PV * (1 + r)^n
        val fvCurrentSavings = currentSavings * (1 + monthlyRate).pow(totalMonths.toDouble())

        // FV of monthly contributions: P * [((1 + r)^n - 1) / r] * (1 + r)
        val fvMonthlyContributions = monthlySavings *
                  ((1 + monthlyRate).pow(totalMonths.toDouble()) - 1) / monthlyRate * (1 + monthlyRate)

        val totalCorpus = fvCurrentSavings + fvMonthlyContributions
        val totalInvested = currentSavings + (monthlySavings * totalMonths)

        _state.value = _state.value.copy(
            retirementCorpus = totalCorpus.roundToInt().toString(),
            totalInvested = totalInvested.roundToInt().toString(),
            estimatedReturns = (totalCorpus - totalInvested).roundToInt().toString()
        )
    }
}

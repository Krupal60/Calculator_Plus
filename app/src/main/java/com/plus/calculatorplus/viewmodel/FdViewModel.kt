package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.fd.FdDetailState
import com.plus.calculatorplus.data.model.fd.OnFdAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow
import kotlin.math.roundToInt

class FdViewModel : ViewModel() {

    private val _state = MutableStateFlow(FdDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: OnFdAction) {
        when (action) {
            is OnFdAction.CalculateFd -> calculateFd(
                action.investmentAmount,
                action.annualInterestRate,
                action.years
            )
        }
    }

    private fun calculateFd(amountStr: String, interestStr: String, yearsStr: String) {
        val p = amountStr.toDoubleOrNull() ?: 0.0
        val r = interestStr.toDoubleOrNull() ?: 0.0
        val t = yearsStr.toDoubleOrNull() ?: 0.0

        // Quarterly compounding: A = P(1 + r/400)^(4t)
        val maturityValue = p * (1 + r / 400.0).pow(4 * t)
        val estReturns = maturityValue - p

        _state.value = _state.value.copy(
            totalInvestment = p.roundToInt().toString(),
            estimatedReturns = estReturns.roundToInt().toString(),
            totalValue = maturityValue.roundToInt().toString()
        )
    }
}

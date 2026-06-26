package com.plus.calculatorplus.presentation.ui.sip

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.sip.SipCalculationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SipViewModel : ViewModel() {

    private val calculationUseCase = SipCalculationUseCase()

    var state = MutableStateFlow(SipState())
        private set

    private val _effect = MutableSharedFlow<SipEffect>()
    val effect: SharedFlow<SipEffect> = _effect.asSharedFlow()

    fun onAction(action: SipAction) {
        when (action) {
            is SipAction.CalculateSip -> calculate(action)
        }
    }

    private fun calculate(action: SipAction.CalculateSip) {
        val input = SipCalculationUseCase.SipInput(
            isLumsum = action.isLumsum,
            monthlyAmount = action.monthlyAmount,
            lumsumAmount = action.lumsumAmount,
            interest = action.interest,
            investedYears = action.investedYears,
            withInflation = action.withInflation,
            inflationRate = action.inflationRate,
            withStepUp = action.withStepUp,
            stepUpPercentage = action.stepUpPercentage
        )
        val result = calculationUseCase.calculate(input)
        state.value = SipState(
            totalAmount = result.totalAmount,
            estimateReturnsAmount = result.estimateReturnsAmount,
            investedAmount = result.investedAmount
        )
    }

    override fun onCleared() {
        super.onCleared()
        state.value = SipState()
    }
}

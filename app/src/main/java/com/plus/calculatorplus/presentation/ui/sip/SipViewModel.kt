package com.plus.calculatorplus.presentation.ui.sip

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.sip.SipCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SipViewModel : ViewModel() {

    private val calculationUseCase = SipCalculationUseCase()

    var state = MutableStateFlow(SipState())
        private set

    private val _effect = Channel<SipEffect>(Channel.BUFFERED)
    val effect: Flow<SipEffect> = _effect.receiveAsFlow()

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

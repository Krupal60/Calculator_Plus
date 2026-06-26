package com.plus.calculatorplus.presentation.ui.fd

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.fd.FdCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FdViewModel : ViewModel() {

    private val calculationUseCase = FdCalculationUseCase()

    private val _state = MutableStateFlow(FdState())
    val state: StateFlow<FdState> = _state.asStateFlow()

    private val _effect = Channel<FdEffect>(Channel.BUFFERED)
    val effect: Flow<FdEffect> = _effect.receiveAsFlow()

    fun onAction(action: FdAction) {
        when (action) {
            is FdAction.CalculateFd -> calculateFd(action)
        }
    }

    private fun calculateFd(action: FdAction.CalculateFd) {
        val input = FdCalculationUseCase.FdInput(
            investmentAmount = action.investmentAmount,
            annualInterestRate = action.annualInterestRate,
            years = action.years
        )
        val result = calculationUseCase.calculate(input)
        _state.value = FdState(
            totalInvestment = result.totalInvestment,
            estimatedReturns = result.estimatedReturns,
            totalValue = result.totalValue
        )
    }
}

package com.plus.calculatorplus.presentation.ui.fd

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.fd.FdCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class FdViewModel(
    private val calculationUseCase: FdCalculationUseCase
) : ViewModel() {

    val state: StateFlow<FdState>
        field = MutableStateFlow(FdState())

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
        state.update {
            FdState(
                totalInvestment = result.totalInvestment,
                estimatedReturns = result.estimatedReturns,
                totalValue = result.totalValue
            )
        }
    }
}

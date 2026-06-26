package com.plus.calculatorplus.presentation.ui.swp

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.swp.SwpCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SwpViewModel : ViewModel() {

    private val calculationUseCase = SwpCalculationUseCase()

    private val _state = MutableStateFlow(SwpState())
    val state: StateFlow<SwpState> = _state.asStateFlow()

    private val _effect = Channel<SwpEffect>(Channel.BUFFERED)
    val effect: Flow<SwpEffect> = _effect.receiveAsFlow()

    fun onAction(action: SwpAction) {
        when (action) {
            is SwpAction.CalculateSwp -> calculateSwp(action)
        }
    }

    private fun calculateSwp(action: SwpAction.CalculateSwp) {
        val input = SwpCalculationUseCase.SwpInput(
            totalInvestment = action.totalInvestment,
            withdrawalPerMonth = action.withdrawalPerMonth,
            expectedReturnRate = action.expectedReturnRate,
            timePeriodYears = action.timePeriodYears
        )
        val result = calculationUseCase.calculate(input)
        _state.value = SwpState(
            totalInvestment = result.totalInvestment,
            totalWithdrawal = result.totalWithdrawal,
            finalValue = result.finalValue
        )
    }
}

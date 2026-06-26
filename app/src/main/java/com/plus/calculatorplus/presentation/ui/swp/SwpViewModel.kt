package com.plus.calculatorplus.presentation.ui.swp

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.swp.SwpCalculationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class SwpViewModel : ViewModel() {

    private val calculationUseCase = SwpCalculationUseCase()

    private val _state = MutableStateFlow(SwpState())
    val state: StateFlow<SwpState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SwpEffect>()
    val effect: SharedFlow<SwpEffect> = _effect.asSharedFlow()

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

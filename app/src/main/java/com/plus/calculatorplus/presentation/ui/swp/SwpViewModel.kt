package com.plus.calculatorplus.presentation.ui.swp

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.swp.SwpCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class SwpViewModel(
    private val calculationUseCase: SwpCalculationUseCase
) : ViewModel() {

    val state: StateFlow<SwpState>
        field = MutableStateFlow(SwpState())

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
        state.update {
            SwpState(
                totalInvestment = result.totalInvestment,
                totalWithdrawal = result.totalWithdrawal,
                finalValue = result.finalValue
            )
        }
    }
}

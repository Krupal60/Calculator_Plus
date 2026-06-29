package com.plus.calculatorplus.presentation.ui.inflation

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.inflation.InflationCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class InflationViewModel(
    private val calculationUseCase: InflationCalculationUseCase
) : ViewModel() {

    val state: StateFlow<InflationState>
        field = MutableStateFlow(InflationState())

    private val _effect = Channel<InflationEffect>(Channel.BUFFERED)
    val effect: Flow<InflationEffect> = _effect.receiveAsFlow()

    fun onAction(action: InflationAction) {
        when (action) {
            is InflationAction.CalculateInflation -> calculate(action)
        }
    }

    private fun calculate(action: InflationAction.CalculateInflation) {
        val input = InflationCalculationUseCase.InflationInput(
            amount = action.amount,
            inflationRate = action.inflationRate,
            years = action.years
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            InflationState(
                currentAmount = result.currentAmount,
                futureCost = result.futureCost,
                purchasingPower = result.purchasingPower
            )
        }
    }
}

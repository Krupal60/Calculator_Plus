package com.plus.calculatorplus.presentation.ui.cagr

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.cagr.CagrCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class CagrViewModel(
    private val calculationUseCase: CagrCalculationUseCase
) : ViewModel() {

    val state: StateFlow<CagrState>
        field = MutableStateFlow(CagrState())

    private val _effect = Channel<CagrEffect>(Channel.BUFFERED)
    val effect: Flow<CagrEffect> = _effect.receiveAsFlow()

    fun onAction(action: CagrAction) {
        when (action) {
            is CagrAction.CalculateCagr -> calculate(action)
        }
    }

    private fun calculate(action: CagrAction.CalculateCagr) {
        val input = CagrCalculationUseCase.CagrInput(
            initialValue = action.initialValue,
            finalValue = action.finalValue,
            years = action.years
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            CagrState(
                cagrPercentage = result.cagrPercentage,
                totalGain = result.totalGain,
                initialValue = result.initialValue,
                finalValue = result.finalValue
            )
        }
    }
}

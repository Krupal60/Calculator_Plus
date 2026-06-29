package com.plus.calculatorplus.presentation.ui.lumpsum

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.lumpsum.LumpsumCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class LumpsumViewModel(
    private val calculationUseCase: LumpsumCalculationUseCase
) : ViewModel() {

    val state: StateFlow<LumpsumState>
        field = MutableStateFlow(LumpsumState())

    private val _effect = Channel<LumpsumEffect>(Channel.BUFFERED)
    val effect: Flow<LumpsumEffect> = _effect.receiveAsFlow()

    fun onAction(action: LumpsumAction) {
        when (action) {
            is LumpsumAction.CalculateLumpsum -> calculate(action)
        }
    }

    private fun calculate(action: LumpsumAction.CalculateLumpsum) {
        val input = LumpsumCalculationUseCase.LumpsumInput(
            principal = action.principal,
            annualRate = action.annualRate,
            years = action.years
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            LumpsumState(
                totalAmount = result.totalAmount,
                estimateReturnsAmount = result.estimateReturnsAmount,
                investedAmount = result.investedAmount
            )
        }
    }
}

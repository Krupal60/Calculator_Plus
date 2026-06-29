package com.plus.calculatorplus.presentation.ui.emi

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.emi.EmiCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class EmiViewModel(
    private val calculationUseCase: EmiCalculationUseCase
) : ViewModel() {

    val state: StateFlow<EmiState>
        field = MutableStateFlow(EmiState())

    private val _effect = Channel<EmiEffect>(Channel.BUFFERED)
    val effect: Flow<EmiEffect> = _effect.receiveAsFlow()

    fun onAction(action: EmiAction) {
        when (action) {
            is EmiAction.CalculateEmi -> calculateEmi(action)
        }
    }

    private fun calculateEmi(action: EmiAction.CalculateEmi) {
        val input = EmiCalculationUseCase.EmiInput(
            loanAmount = action.loanAmount,
            loanInterest = action.loanInterest,
            loanYear = action.loanYear
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            EmiState(
                monthlyEmi = result.monthlyEmi,
                totalAmount = result.totalAmount,
                totalInterest = result.totalInterest,
                loanAmount = result.loanAmount
            )
        }
    }
}

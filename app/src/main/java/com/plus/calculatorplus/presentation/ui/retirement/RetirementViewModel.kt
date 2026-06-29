package com.plus.calculatorplus.presentation.ui.retirement

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.retirement.RetirementCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RetirementViewModel(
    private val calculationUseCase: RetirementCalculationUseCase
) : ViewModel() {

    val state: StateFlow<RetirementState>
        field = MutableStateFlow(RetirementState())

    private val _effect = Channel<RetirementEffect>(Channel.BUFFERED)
    val effect: Flow<RetirementEffect> = _effect.receiveAsFlow()

    fun onAction(action: RetirementAction) {
        when (action) {
            is RetirementAction.CalculateRetirement -> calculateRetirement(action)
        }
    }

    private fun calculateRetirement(action: RetirementAction.CalculateRetirement) {
        val input = RetirementCalculationUseCase.RetirementInput(
            currentAge = action.currentAge,
            retirementAge = action.retirementAge,
            monthlySavings = action.monthlySavings,
            expectedReturnRate = action.expectedReturnRate,
            currentSavings = action.currentSavings
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            RetirementState(
                retirementCorpus = result.retirementCorpus,
                totalInvested = result.totalInvested,
                estimatedReturns = result.estimatedReturns
            )
        }
    }
}

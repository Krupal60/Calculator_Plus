package com.plus.calculatorplus.presentation.ui.retirement

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.retirement.RetirementCalculationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class RetirementViewModel : ViewModel() {

    private val calculationUseCase = RetirementCalculationUseCase()

    private val _state = MutableStateFlow(RetirementState())
    val state: StateFlow<RetirementState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RetirementEffect>()
    val effect: SharedFlow<RetirementEffect> = _effect.asSharedFlow()

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
        _state.value = RetirementState(
            retirementCorpus = result.retirementCorpus,
            totalInvested = result.totalInvested,
            estimatedReturns = result.estimatedReturns
        )
    }
}

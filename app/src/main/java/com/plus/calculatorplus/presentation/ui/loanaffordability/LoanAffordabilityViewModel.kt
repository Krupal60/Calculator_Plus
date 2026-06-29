package com.plus.calculatorplus.presentation.ui.loanaffordability

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.loanaffordability.LoanAffordabilityCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class LoanAffordabilityViewModel(
    private val calculationUseCase: LoanAffordabilityCalculationUseCase
) : ViewModel() {

    val state: StateFlow<LoanAffordabilityState>
        field = MutableStateFlow(LoanAffordabilityState())

    private val _effect = Channel<LoanAffordabilityEffect>(Channel.BUFFERED)
    val effect: Flow<LoanAffordabilityEffect> = _effect.receiveAsFlow()

    fun onAction(action: LoanAffordabilityAction) {
        when (action) {
            is LoanAffordabilityAction.CalculateAffordability -> calculate(action)
        }
    }

    private fun calculate(action: LoanAffordabilityAction.CalculateAffordability) {
        val input = LoanAffordabilityCalculationUseCase.LoanAffordabilityInput(
            monthlyIncome = action.monthlyIncome,
            existingEmi = action.existingEmi,
            interestRate = action.interestRate,
            years = action.years,
            foirPercentage = action.foirPercentage
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            LoanAffordabilityState(
                maxEmi = result.maxEmi,
                maxLoanAmount = result.maxLoanAmount,
                totalPayable = result.totalPayable
            )
        }
    }
}

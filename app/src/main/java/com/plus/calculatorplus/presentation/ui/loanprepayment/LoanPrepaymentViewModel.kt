package com.plus.calculatorplus.presentation.ui.loanprepayment

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.loanprepayment.LoanPrepaymentCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class LoanPrepaymentViewModel(
    private val calculationUseCase: LoanPrepaymentCalculationUseCase
) : ViewModel() {

    val state: StateFlow<LoanPrepaymentState>
        field = MutableStateFlow(LoanPrepaymentState())

    private val _effect = Channel<LoanPrepaymentEffect>(Channel.BUFFERED)
    val effect: Flow<LoanPrepaymentEffect> = _effect.receiveAsFlow()

    fun onAction(action: LoanPrepaymentAction) {
        when (action) {
            is LoanPrepaymentAction.CalculatePrepayment -> calculate(action)
        }
    }

    private fun calculate(action: LoanPrepaymentAction.CalculatePrepayment) {
        val input = LoanPrepaymentCalculationUseCase.LoanPrepaymentInput(
            principal = action.principal,
            interestRate = action.interestRate,
            years = action.years,
            extraMonthly = action.extraMonthly
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            LoanPrepaymentState(
                interestSaved = result.interestSaved,
                monthsReduced = result.monthsReduced,
                originalInterest = result.originalInterest,
                newInterest = result.newInterest
            )
        }
    }
}

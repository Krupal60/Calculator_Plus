package com.plus.calculatorplus.presentation.ui.tax

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.tax.TaxCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class TaxViewModel(
    private val calculationUseCase: TaxCalculationUseCase
) : ViewModel() {

    val state: StateFlow<TaxState>
        field = MutableStateFlow(TaxState())

    private val _effect = Channel<TaxEffect>(Channel.BUFFERED)
    val effect: Flow<TaxEffect> = _effect.receiveAsFlow()

    fun onAction(action: TaxAction) {
        when (action) {
            is TaxAction.CalculateTax -> calculate(action)
        }
    }

    private fun calculate(action: TaxAction.CalculateTax) {
        val input = TaxCalculationUseCase.TaxInput(
            amount = action.amount,
            rate = action.rate,
            removeGst = action.removeGst
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            TaxState(
                baseAmount = result.baseAmount,
                taxAmount = result.taxAmount,
                totalAmount = result.totalAmount
            )
        }
    }
}

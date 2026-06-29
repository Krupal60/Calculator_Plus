package com.plus.calculatorplus.presentation.ui.billsplitter

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.billsplitter.BillSplitterCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class BillSplitterViewModel(
    private val calculationUseCase: BillSplitterCalculationUseCase
) : ViewModel() {

    val state: StateFlow<BillSplitterState>
        field = MutableStateFlow(BillSplitterState())

    private val _effect = Channel<BillSplitterEffect>(Channel.BUFFERED)
    val effect: Flow<BillSplitterEffect> = _effect.receiveAsFlow()

    fun onAction(action: BillSplitterAction) {
        when (action) {
            is BillSplitterAction.CalculateBillSplit -> calculate(action)
        }
    }

    private fun calculate(action: BillSplitterAction.CalculateBillSplit) {
        val input = BillSplitterCalculationUseCase.BillSplitterInput(
            billAmount = action.billAmount,
            tipPercentage = action.tipPercentage,
            people = action.people
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            BillSplitterState(
                tipAmount = result.tipAmount,
                grandTotal = result.grandTotal,
                perPerson = result.perPerson
            )
        }
    }
}

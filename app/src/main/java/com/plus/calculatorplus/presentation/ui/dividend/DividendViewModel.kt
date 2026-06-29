package com.plus.calculatorplus.presentation.ui.dividend

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.dividend.DividendCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DividendViewModel(
    private val calculationUseCase: DividendCalculationUseCase
) : ViewModel() {

    val state: StateFlow<DividendState>
        field = MutableStateFlow(DividendState())

    private val _effect = Channel<DividendEffect>(Channel.BUFFERED)
    val effect: Flow<DividendEffect> = _effect.receiveAsFlow()

    fun onAction(action: DividendAction) {
        when (action) {
            is DividendAction.CalculateDividend -> calculateDividend(action)
        }
    }

    private fun calculateDividend(action: DividendAction.CalculateDividend) {
        val input = DividendCalculationUseCase.DividendInput(
            sharePrice = action.sharePrice,
            dividendPerShare = action.dividendPerShare,
            numberOfShares = action.numberOfShares
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            DividendState(
                totalDividend = result.totalDividend,
                dividendYield = result.dividendYield,
                annualDividend = result.annualDividend
            )
        }
    }
}

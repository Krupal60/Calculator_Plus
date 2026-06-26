package com.plus.calculatorplus.presentation.ui.dividend

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.dividend.DividendCalculationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class DividendViewModel : ViewModel() {

    private val calculationUseCase = DividendCalculationUseCase()

    private val _state = MutableStateFlow(DividendState())
    val state: StateFlow<DividendState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DividendEffect>()
    val effect: SharedFlow<DividendEffect> = _effect.asSharedFlow()

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
        _state.value = DividendState(
            totalDividend = result.totalDividend,
            dividendYield = result.dividendYield,
            annualDividend = result.annualDividend
        )
    }
}

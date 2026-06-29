package com.plus.calculatorplus.presentation.ui.agedate

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.agedate.AgeDateCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class AgeDateViewModel(
    private val calculationUseCase: AgeDateCalculationUseCase
) : ViewModel() {

    val state: StateFlow<AgeDateState>
        field = MutableStateFlow(AgeDateState())

    private val _effect = Channel<AgeDateEffect>(Channel.BUFFERED)
    val effect: Flow<AgeDateEffect> = _effect.receiveAsFlow()

    fun onAction(action: AgeDateAction) {
        when (action) {
            is AgeDateAction.CalculateAge -> calculate(action)
        }
    }

    private fun calculate(action: AgeDateAction.CalculateAge) {
        val input = AgeDateCalculationUseCase.AgeDateInput(
            fromMillis = action.fromMillis,
            toMillis = action.toMillis
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            AgeDateState(
                years = result.years,
                months = result.months,
                days = result.days,
                totalDays = result.totalDays
            )
        }
    }
}

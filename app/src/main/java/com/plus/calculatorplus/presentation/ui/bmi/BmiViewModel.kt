package com.plus.calculatorplus.presentation.ui.bmi

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.bmi.BmiCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class BmiViewModel(
    private val calculationUseCase: BmiCalculationUseCase
) : ViewModel() {

    val state: StateFlow<BmiState>
        field = MutableStateFlow(BmiState())

    private val _effect = Channel<BmiEffect>(Channel.BUFFERED)
    val effect: Flow<BmiEffect> = _effect.receiveAsFlow()

    fun onAction(action: BmiAction) {
        when (action) {
            is BmiAction.CalculateBmi -> calculateBmi(action)
        }
    }

    private fun calculateBmi(action: BmiAction.CalculateBmi) {
        val input = BmiCalculationUseCase.BmiInput(
            age = action.age,
            cm = action.cm,
            weight = action.weight,
            isMale = action.isMale
        )
        val result = calculationUseCase.calculate(input)
        state.update { BmiState(bmi = result.bmi, interpretation = result.interpretation) }
    }
}

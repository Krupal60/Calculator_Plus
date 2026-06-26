package com.plus.calculatorplus.presentation.ui.bmi

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.bmi.BmiCalculationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BmiViewModel : ViewModel() {

    private val calculationUseCase = BmiCalculationUseCase()

    var state = MutableStateFlow(BmiState())
        private set

    private val _effect = MutableSharedFlow<BmiEffect>()
    val effect: SharedFlow<BmiEffect> = _effect.asSharedFlow()

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
        state.value = BmiState(bmi = result.bmi, interpretation = result.interpretation)
    }
}

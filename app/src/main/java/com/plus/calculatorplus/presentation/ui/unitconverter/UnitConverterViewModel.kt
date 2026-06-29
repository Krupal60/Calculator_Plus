package com.plus.calculatorplus.presentation.ui.unitconverter

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.unitconverter.UnitConverterCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class UnitConverterViewModel(
    private val calculationUseCase: UnitConverterCalculationUseCase
) : ViewModel() {

    val state: StateFlow<UnitConverterState>
        field = MutableStateFlow(UnitConverterState())

    private val _effect = Channel<UnitConverterEffect>(Channel.BUFFERED)
    val effect: Flow<UnitConverterEffect> = _effect.receiveAsFlow()

    fun onAction(action: UnitConverterAction) {
        when (action) {
            is UnitConverterAction.Convert -> convert(action)
        }
    }

    private fun convert(action: UnitConverterAction.Convert) {
        val input = UnitConverterCalculationUseCase.UnitConverterInput(
            category = action.category,
            fromUnit = action.fromUnit,
            toUnit = action.toUnit,
            value = action.value
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            UnitConverterState(convertedValue = result.convertedValue)
        }
    }
}

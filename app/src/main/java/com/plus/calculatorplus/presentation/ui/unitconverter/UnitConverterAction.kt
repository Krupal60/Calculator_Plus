package com.plus.calculatorplus.presentation.ui.unitconverter

import com.plus.calculatorplus.domain.unitconverter.UnitConverterCalculationUseCase

sealed class UnitConverterAction {
    data class Convert(
        val category: UnitConverterCalculationUseCase.Category,
        val fromUnit: String,
        val toUnit: String,
        val value: String
    ) : UnitConverterAction()
}

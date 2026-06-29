package com.plus.calculatorplus.presentation.ui.unitconverter

sealed class UnitConverterEffect {
    data class ShowToast(val message: String) : UnitConverterEffect()
}

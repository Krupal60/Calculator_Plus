package com.plus.calculatorplus.presentation.ui.inflation

sealed class InflationEffect {
    data class ShowToast(val message: String) : InflationEffect()
}

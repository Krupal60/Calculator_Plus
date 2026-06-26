package com.plus.calculatorplus.presentation.ui.dividend

sealed class DividendEffect {
    data class ShowToast(val message: String) : DividendEffect()
}

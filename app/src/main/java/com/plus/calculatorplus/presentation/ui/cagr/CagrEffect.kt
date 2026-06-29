package com.plus.calculatorplus.presentation.ui.cagr

sealed class CagrEffect {
    data class ShowToast(val message: String) : CagrEffect()
}

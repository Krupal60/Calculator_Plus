package com.plus.calculatorplus.presentation.ui.tax

sealed class TaxEffect {
    data class ShowToast(val message: String) : TaxEffect()
}

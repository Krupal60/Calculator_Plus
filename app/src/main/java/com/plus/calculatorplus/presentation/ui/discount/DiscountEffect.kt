package com.plus.calculatorplus.presentation.ui.discount

sealed class DiscountEffect {
    data class ShowToast(val message: String) : DiscountEffect()
}

package com.plus.calculatorplus.presentation.ui.emi

sealed class EmiEffect {
    data class ShowToast(val message: String) : EmiEffect()
}

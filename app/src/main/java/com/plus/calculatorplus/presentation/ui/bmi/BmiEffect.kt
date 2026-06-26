package com.plus.calculatorplus.presentation.ui.bmi

sealed class BmiEffect {
    data class ShowToast(val message: String) : BmiEffect()
}

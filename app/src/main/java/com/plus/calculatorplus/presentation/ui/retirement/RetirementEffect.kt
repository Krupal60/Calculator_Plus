package com.plus.calculatorplus.presentation.ui.retirement

sealed class RetirementEffect {
    data class ShowToast(val message: String) : RetirementEffect()
}

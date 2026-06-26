package com.plus.calculatorplus.presentation.ui.swp

sealed class SwpEffect {
    data class ShowToast(val message: String) : SwpEffect()
}

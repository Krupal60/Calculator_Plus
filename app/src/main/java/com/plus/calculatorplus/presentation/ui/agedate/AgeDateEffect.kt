package com.plus.calculatorplus.presentation.ui.agedate

sealed class AgeDateEffect {
    data class ShowToast(val message: String) : AgeDateEffect()
}

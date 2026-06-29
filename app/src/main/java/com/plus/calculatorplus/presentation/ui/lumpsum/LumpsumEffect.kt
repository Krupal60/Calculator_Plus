package com.plus.calculatorplus.presentation.ui.lumpsum

sealed class LumpsumEffect {
    data class ShowToast(val message: String) : LumpsumEffect()
}

package com.plus.calculatorplus.presentation.ui.lumpsum

sealed class LumpsumAction {
    data class CalculateLumpsum(
        val principal: String,
        val annualRate: String,
        val years: String
    ) : LumpsumAction()
}

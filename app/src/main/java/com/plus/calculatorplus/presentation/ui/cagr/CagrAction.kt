package com.plus.calculatorplus.presentation.ui.cagr

sealed class CagrAction {
    data class CalculateCagr(
        val initialValue: String,
        val finalValue: String,
        val years: String
    ) : CagrAction()
}

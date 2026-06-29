package com.plus.calculatorplus.presentation.ui.tax

sealed class TaxAction {
    data class CalculateTax(
        val amount: String,
        val rate: String,
        val removeGst: Boolean
    ) : TaxAction()
}

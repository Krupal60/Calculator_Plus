package com.plus.calculatorplus.presentation.ui.dividend

sealed class DividendAction {
    data class CalculateDividend(
        val sharePrice: String,
        val dividendPerShare: String,
        val numberOfShares: String
    ) : DividendAction()
}

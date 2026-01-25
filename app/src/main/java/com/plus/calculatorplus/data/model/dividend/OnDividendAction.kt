package com.plus.calculatorplus.data.model.dividend

sealed class OnDividendAction {
    data class CalculateDividend(
        val sharePrice: String,
        val dividendPerShare: String,
        val numberOfShares: String
    ) : OnDividendAction()
}

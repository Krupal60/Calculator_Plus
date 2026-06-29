package com.plus.calculatorplus.presentation.ui.inflation

sealed class InflationAction {
    data class CalculateInflation(
        val amount: String,
        val inflationRate: String,
        val years: String
    ) : InflationAction()
}

package com.plus.calculatorplus.presentation.ui.sip

sealed class SipAction {
    data class CalculateSip(
        val isLumsum: Boolean,
        val monthlyAmount: String,
        val lumsumAmount: String,
        val interest: String,
        val investedYears: String,
        val withInflation: Boolean = false,
        val inflationRate: String = "0",
        val withStepUp: Boolean = false,
        val stepUpPercentage: String = "0"
    ) : SipAction()
}

package com.plus.calculatorplus.data.model.sip

sealed class OnSipAction {
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
    ) : OnSipAction()

}
package com.plus.calculatorplus.presentation.ui.bmi

sealed class BmiAction {
    data class CalculateBmi(
        val age: String,
        val cm: String,
        val weight: String,
        val isMale: Boolean
    ) : BmiAction()
}

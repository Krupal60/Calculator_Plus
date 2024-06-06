package com.plus.calculatorplus.data.model.bmi

sealed class OnBmiAction {
    data class CalculateBmi(
        val age: String,
        val cm: String,
        val weight: String,
        val isMale:Boolean
    ) : OnBmiAction()

}
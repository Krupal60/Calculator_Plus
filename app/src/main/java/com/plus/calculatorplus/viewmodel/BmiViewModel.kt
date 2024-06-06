package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.bmi.BmiDetailState
import com.plus.calculatorplus.data.model.bmi.OnBmiAction
import kotlinx.coroutines.flow.MutableStateFlow

class BmiViewModel : ViewModel() {

    var state = MutableStateFlow(BmiDetailState())
        private set

    fun onAction(onBmiAction: OnBmiAction) {
        when (onBmiAction) {
            is OnBmiAction.CalculateBmi -> calculateBmi(onBmiAction)
        }
    }

    private fun calculateBmi(onBmiAction: OnBmiAction.CalculateBmi) {

        val cm = onBmiAction.cm.toDouble()
        val weight = onBmiAction.weight.toDouble()
        val age = onBmiAction.age.toInt()
        val isMale = onBmiAction.isMale
        val heightInMeters = cm / 100.0

        val bmi = weight / (heightInMeters * heightInMeters)

        val interpretation: String = if (age >= 18) {
            when (isMale) {
                true -> { // Male interpretation
                    when {
                        bmi < 18.5 -> "Underweight"
                        bmi < 25 -> "Normal weight"
                        bmi < 30 -> "Overweight"
                        else -> "Obese"
                    }
                }
                false -> { // Female interpretation
                    when {
                        bmi < 21.5 -> "Underweight"
                        bmi < 25 -> "Normal weight"
                        bmi < 30 -> "Overweight"
                        else -> "Obese"
                    }
                }
            }
        } else {
            "BMI for children requires growth charts and consultation with a healthcare professional."
        }
            val formatBmi = String.format("%.1f",bmi)
        state.value =  state.value.copy(bmi = formatBmi, interpretation = interpretation)

    }

}

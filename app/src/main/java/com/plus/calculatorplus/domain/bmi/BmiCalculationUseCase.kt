package com.plus.calculatorplus.domain.bmi

class BmiCalculationUseCase {

    data class BmiInput(
        val age: String,
        val cm: String,
        val weight: String,
        val isMale: Boolean
    )

    data class BmiResult(
        val bmi: String,
        val interpretation: String
    )

    fun calculate(input: BmiInput): BmiResult {
        val cm = input.cm.toDouble()
        val weight = input.weight.toDouble()
        val age = input.age.toInt()
        val heightInMeters = cm / 100.0
        val bmi = weight / (heightInMeters * heightInMeters)

        val interpretation = if (age >= 18) {
            if (input.isMale) {
                when {
                    bmi < 18.5 -> "Underweight"
                    bmi < 25 -> "Normal weight"
                    bmi < 30 -> "Overweight"
                    else -> "Obese"
                }
            } else {
                when {
                    bmi < 21.5 -> "Underweight"
                    bmi < 25 -> "Normal weight"
                    bmi < 30 -> "Overweight"
                    else -> "Obese"
                }
            }
        } else {
            "BMI for children requires growth charts and consultation with a healthcare professional."
        }
        val formatBmi = String.format(java.util.Locale.ENGLISH, "%.1f", bmi)
        return BmiResult(bmi = formatBmi, interpretation = interpretation)
    }
}

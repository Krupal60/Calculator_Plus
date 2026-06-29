package com.plus.calculatorplus.domain.bmi

import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toBmiString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
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
        val cm = input.cm.toBigDecimalOrZero()
        val weight = input.weight.toBigDecimalOrZero()
        val age = input.age.toBigDecimalOrZero()
        val heightInMeters = cm.divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val bmi = weight.divide(
            heightInMeters.multiply(heightInMeters), 10, RoundingMode.HALF_EVEN
        )

        val interpretation = if (age.isGreaterThanOrEqual(BigDecimal("18"))) {
            if (input.isMale) {
                when {
                    bmi.isLessThan(BigDecimal("18.5")) -> "Underweight"
                    bmi.isLessThan(BigDecimal("25")) -> "Normal weight"
                    bmi.isLessThan(BigDecimal("30")) -> "Overweight"
                    else -> "Obese"
                }
            } else {
                when {
                    bmi.isLessThan(BigDecimal("21.5")) -> "Underweight"
                    bmi.isLessThan(BigDecimal("25")) -> "Normal weight"
                    bmi.isLessThan(BigDecimal("30")) -> "Overweight"
                    else -> "Obese"
                }
            }
        } else {
            "BMI for children requires growth charts and consultation with a healthcare professional."
        }
        return BmiResult(bmi = bmi.toBmiString(), interpretation = interpretation)
    }

    private fun BigDecimal.isGreaterThanOrEqual(other: BigDecimal): Boolean =
        this.compareTo(other) >= 0

    private fun BigDecimal.isLessThan(other: BigDecimal): Boolean =
        this.compareTo(other) < 0
}

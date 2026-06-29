package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun bmiValidation(
    age: String,
    cm: String,
    weight: String,
): Pair<Boolean, String> {
    return when {
        age.isEmpty() -> Pair(false, "Enter Age")
        cm.isEmpty() -> Pair(false, "Enter cm or ft")
        weight.isEmpty() -> Pair(false, "Enter weight")
        else -> {
            val ageValidation = ageValidate(age)
            if (!ageValidation.first) return ageValidation

            val cmValidation = cmValidation(cm)
            if (!cmValidation.first) return cmValidation

            val yearsResult = weightValidation(weight)
            if (!yearsResult.first) return yearsResult

            Pair(true, "")
        }
    }
}

fun weightValidation(weight: String): Pair<Boolean, String> {
    return when {
        weight.isEmpty() -> Pair(false, "Enter Weight")
        else -> {
            val weightValue = weight.toBigDecimalOrNull() ?: return Pair(false, "Invalid Weight")
            if (weightValue.compareTo(BigDecimal("2")) >= 0 && weightValue.compareTo(BigDecimal("500")) <= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Weight must be between 2 and 500 Kg")
            }
        }
    }
}

fun ageValidate(age: String): Pair<Boolean, String> {
    return when {
        age.isEmpty() -> Pair(false, "Enter Age")
        else -> {
            val ageValue = age.toBigDecimalOrNull() ?: return Pair(false, "Invalid Age")
            if (ageValue.compareTo(BigDecimal("18")) >= 0 && ageValue.compareTo(BigDecimal("120")) <= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Age must be between 18 and 120")
            }
        }
    }
}

fun cmValidation(cm: String): Pair<Boolean, String> {
    return when {
        cm.isEmpty() -> Pair(false, "Enter Cm or Ft")
        else -> {
            val cmValue = cm.toBigDecimalOrNull() ?: return Pair(false, "Invalid Height")
            if (cmValue.compareTo(BigDecimal("50")) >= 0 && cmValue.compareTo(BigDecimal("280")) <= 0) {
                Pair(true, "")
            } else {
                Pair(false, "Height must be between 50 and 280 cm")
            }
        }
    }
}

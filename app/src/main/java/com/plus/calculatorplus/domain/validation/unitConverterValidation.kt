package com.plus.calculatorplus.domain.validation

fun unitConverterValidation(value: String): Pair<Boolean, String> {
    return when {
        value.isEmpty() -> Pair(false, "Enter a value to convert")
        value.toBigDecimalOrNull() == null -> Pair(false, "Invalid value")
        else -> Pair(true, "")
    }
}

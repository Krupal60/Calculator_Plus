package com.plus.calculatorplus.domain

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

val BD_ONE_HUNDRED: BigDecimal = BigDecimal("100")
val BD_FOUR_HUNDRED: BigDecimal = BigDecimal("400")
val BD_TWELVE: BigDecimal = BigDecimal("12")

fun String.toBigDecimalOrZero(): BigDecimal = this.toBigDecimalOrNull() ?: BigDecimal.ZERO

fun BigDecimal.toCurrencyString(): String = this.setScale(0, RoundingMode.HALF_UP).toPlainString()

fun BigDecimal.toBmiString(): String = this.setScale(1, RoundingMode.HALF_UP).toPlainString()

fun BigDecimal.powExponent(exp: Double): BigDecimal {
    return if (exp == exp.toInt().toDouble()) {
        this.pow(exp.toInt())
    } else {
        this.toDouble().pow(exp).toBigDecimal()
    }
}

fun BigDecimal.powExponent(exp: Int): BigDecimal = this.pow(exp)

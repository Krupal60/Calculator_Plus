package com.plus.calculatorplus.domain.validation

import java.math.BigDecimal

fun billSplitterValidation(
    billAmount: String,
    tipPercentage: String,
    people: String
): Pair<Boolean, String> {
    return when {
        billAmount.isEmpty() -> Pair(false, "Enter Bill Amount")
        tipPercentage.isEmpty() -> Pair(false, "Enter Tip Percentage")
        people.isEmpty() -> Pair(false, "Enter Number of People")
        else -> {
            val bill = billAmount.toBigDecimalOrNull() ?: return Pair(false, "Invalid Bill Amount")
            val tip =
                tipPercentage.toBigDecimalOrNull() ?: return Pair(false, "Invalid Tip Percentage")
            val peopleCount =
                people.toBigDecimalOrNull() ?: return Pair(false, "Invalid Number of People")

            when {
                bill.compareTo(BigDecimal.ZERO) <= 0 -> Pair(false, "Bill must be greater than 0")
                tip.compareTo(BigDecimal.ZERO) < 0 || tip.compareTo(BigDecimal("100")) > 0 ->
                    Pair(false, "Tip must be between 0 and 100")

                peopleCount.compareTo(BigDecimal.ONE) < 0 -> Pair(
                    false,
                    "At least 1 person required"
                )

                else -> Pair(true, "")
            }
        }
    }
}

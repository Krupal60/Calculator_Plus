package com.plus.calculatorplus.presentation.util

import java.text.NumberFormat
import java.util.Locale

object Utils {


    fun getMoneyInWords(number: Double): String {
        when {
            number >= 10000000 -> {
                return "₹ " + String.format("%.2f", number / 10000000) + " Cr"
            }

            number >= 100000 -> {
                return "₹ " + String.format("%.2f", number / 100000) + " L"
            }

            number >= 10000 -> {
                return "₹ " + String.format("%.2f", number / 1000) + " K"
            }
        }
        val format = NumberFormat.getCurrencyInstance(Locale("en", "in"))
        format.maximumFractionDigits = 0
        return format.format(number)
    }
}
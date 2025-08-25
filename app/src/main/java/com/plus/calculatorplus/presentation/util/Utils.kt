package com.plus.calculatorplus.presentation.util

import java.text.NumberFormat
import java.util.Locale

object Utils {


    fun getMoneyInWords(number: Double): String {
        when {
            number >= 10000000 -> {
                return buildString {
                    append("₹ ")
                    append(String.format(buildString {
                        append("%.2f")
                    }, number / 10000000))
                    append(" Cr")
                }
            }

            number >= 100000 -> {
                return buildString {
                    append("₹ ")
                    append(String.format(buildString {
                        append("%.2f")
                    }, number / 100000))
                    append(" L")
                }
            }

            number >= 10000 -> {
                return buildString {
                    append("₹ ")
                    append(String.format(buildString {
                        append("%.2f")
                    }, number / 1000))
                    append(" K")
                }
            }
        }
        val format = NumberFormat.getCurrencyInstance(
            Locale.Builder().setLanguage("en").setRegion("IN").build()
        )
        format.maximumFractionDigits = 0
        return format.format(number)
    }
}
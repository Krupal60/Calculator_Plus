package com.plus.calculatorplus.presentation.util

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale

class IndianCurrencyVisualTransformation(
    private val showSymbol: Boolean = true
) : VisualTransformation {

    private val decimalFormat: DecimalFormat = DecimalFormat(
        "#,##,##0",
        DecimalFormatSymbols(Locale.Builder().setLanguage("en").setRegion("IN").build())
    )

    override fun filter(text: AnnotatedString): TransformedText {
        if (text.text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val rawText = text.text.filter { it.isDigit() }
        if (rawText.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val number = rawText.toLongOrNull() ?: return TransformedText(
            AnnotatedString(text.text),
            OffsetMapping.Identity
        )

        val formattedNumber = decimalFormat.format(number)
        val finalText = if (showSymbol) "₹ $formattedNumber" else formattedNumber

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val digitsBeforeCursor = rawText.take(offset).length
                var transformedOffset = if (showSymbol) 2 else 0

                var digitsSeen = 0
                formattedNumber.forEachIndexed { index, c ->
                    if (c.isDigit()) digitsSeen++
                    if (digitsSeen == digitsBeforeCursor) {
                        transformedOffset += index + 1
                        return transformedOffset
                    }
                }
                return finalText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                val adjustedOffset = offset - if (showSymbol) 2 else 0
                if (adjustedOffset <= 0) return 0

                var digitsCount = 0
                formattedNumber.take(adjustedOffset).forEach {
                    if (it.isDigit()) digitsCount++
                }
                return digitsCount
            }
        }

        return TransformedText(
            AnnotatedString(finalText),
            offsetMapping
        )
    }
}
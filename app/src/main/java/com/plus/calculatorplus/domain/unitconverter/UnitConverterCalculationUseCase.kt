package com.plus.calculatorplus.domain.unitconverter

import com.plus.calculatorplus.domain.toBigDecimalOrZero
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class UnitConverterCalculationUseCase {

    /** Conversion categories with their units mapped to a common base unit factor. */
    enum class Category(val label: String, val factors: Map<String, BigDecimal>) {
        LENGTH(
            "Length",
            linkedMapOf(
                "mm" to BigDecimal("0.001"),
                "cm" to BigDecimal("0.01"),
                "m" to BigDecimal("1"),
                "km" to BigDecimal("1000"),
                "in" to BigDecimal("0.0254"),
                "ft" to BigDecimal("0.3048"),
                "yd" to BigDecimal("0.9144"),
                "mi" to BigDecimal("1609.344")
            )
        ),
        WEIGHT(
            "Weight",
            linkedMapOf(
                "mg" to BigDecimal("0.001"),
                "g" to BigDecimal("1"),
                "kg" to BigDecimal("1000"),
                "t" to BigDecimal("1000000"),
                "oz" to BigDecimal("28.349523125"),
                "lb" to BigDecimal("453.59237")
            )
        ),
        AREA(
            "Area",
            linkedMapOf(
                "mm²" to BigDecimal("0.000001"),
                "cm²" to BigDecimal("0.0001"),
                "m²" to BigDecimal("1"),
                "km²" to BigDecimal("1000000"),
                "ft²" to BigDecimal("0.09290304"),
                "ac" to BigDecimal("4046.8564224"),
                "ha" to BigDecimal("10000")
            )
        );

        val units: ImmutableList<String> get() = factors.keys.toPersistentList()
    }

    data class UnitConverterInput(
        val category: Category,
        val fromUnit: String,
        val toUnit: String,
        val value: String
    )

    data class UnitConverterResult(
        val convertedValue: String
    )

    fun calculate(input: UnitConverterInput): UnitConverterResult {
        val value = input.value.toBigDecimalOrZero()
        val fromFactor = input.category.factors[input.fromUnit] ?: BigDecimal.ONE
        val toFactor = input.category.factors[input.toUnit] ?: BigDecimal.ONE

        val converted = value.multiply(fromFactor)
            .divide(toFactor, 10, RoundingMode.HALF_EVEN)
            .stripTrailingZeros()

        return UnitConverterResult(
            convertedValue = converted.toPlainString()
        )
    }
}

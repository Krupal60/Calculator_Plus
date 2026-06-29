package com.plus.calculatorplus.domain.tax

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class TaxCalculationUseCase {

    data class TaxInput(
        val amount: String,
        val rate: String,
        val removeGst: Boolean
    )

    data class TaxResult(
        val baseAmount: String,
        val taxAmount: String,
        val totalAmount: String
    )

    fun calculate(input: TaxInput): TaxResult {
        val amount = input.amount.toBigDecimalOrZero()
        val rate = input.rate.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)

        return if (input.removeGst) {
            // Amount is gross (tax-inclusive); extract the base and tax.
            val base = amount.divide(BigDecimal.ONE.add(rate), 10, RoundingMode.HALF_EVEN)
            val tax = amount.subtract(base)
            TaxResult(
                baseAmount = base.toCurrencyString(),
                taxAmount = tax.toCurrencyString(),
                totalAmount = amount.toCurrencyString()
            )
        } else {
            // Amount is the base; add tax on top.
            val tax = amount.multiply(rate)
            val total = amount.add(tax)
            TaxResult(
                baseAmount = amount.toCurrencyString(),
                taxAmount = tax.toCurrencyString(),
                totalAmount = total.toCurrencyString()
            )
        }
    }
}

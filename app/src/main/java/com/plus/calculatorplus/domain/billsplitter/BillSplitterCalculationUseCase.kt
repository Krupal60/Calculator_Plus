package com.plus.calculatorplus.domain.billsplitter

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class BillSplitterCalculationUseCase {

    data class BillSplitterInput(
        val billAmount: String,
        val tipPercentage: String,
        val people: String
    )

    data class BillSplitterResult(
        val tipAmount: String,
        val grandTotal: String,
        val perPerson: String
    )

    fun calculate(input: BillSplitterInput): BillSplitterResult {
        val bill = input.billAmount.toBigDecimalOrZero()
        val tipRate = input.tipPercentage.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
        val people = input.people.toBigDecimalOrZero()

        val tip = bill.multiply(tipRate)
        val grandTotal = bill.add(tip)
        val perPerson = if (people.compareTo(BigDecimal.ZERO) > 0) {
            grandTotal.divide(people, 10, RoundingMode.HALF_EVEN)
        } else {
            grandTotal
        }

        return BillSplitterResult(
            tipAmount = tip.toCurrencyString(),
            grandTotal = grandTotal.toCurrencyString(),
            perPerson = perPerson.toCurrencyString()
        )
    }
}

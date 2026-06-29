package com.plus.calculatorplus.domain.emi

import com.plus.calculatorplus.domain.BD_TWELVE
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class EmiCalculationUseCase {

    data class EmiInput(
        val loanAmount: String,
        val loanInterest: String,
        val loanYear: String
    )

    data class EmiResult(
        val monthlyEmi: String,
        val totalAmount: String,
        val totalInterest: String,
        val loanAmount: String
    )

    fun calculate(input: EmiInput): EmiResult {
        val loanAmount = input.loanAmount.toBigDecimalOrZero()
        val annualInterestRate = input.loanInterest.toBigDecimalOrZero()
            .divide(BigDecimal("100"), 10, RoundingMode.HALF_EVEN)
        val loanYears = input.loanYear.toBigDecimalOrZero()
        val monthlyInterestRate = annualInterestRate.divide(BD_TWELVE, 10, RoundingMode.HALF_EVEN)
        val numberOfPayments = loanYears.multiply(BD_TWELVE)
        val onePlusRate = BigDecimal.ONE.add(monthlyInterestRate)
        val powFactor = onePlusRate.powExponent(numberOfPayments.toDouble())
        val numerator = loanAmount.multiply(monthlyInterestRate).multiply(powFactor)
        val denominator = powFactor.subtract(BigDecimal.ONE)
        val emi = numerator.divide(denominator, 10, RoundingMode.HALF_EVEN)
        val totalPayment = emi.multiply(numberOfPayments)
        val totalInterest = totalPayment.subtract(loanAmount)

        return EmiResult(
            monthlyEmi = emi.toCurrencyString(),
            totalAmount = loanAmount.add(totalInterest).toCurrencyString(),
            totalInterest = totalInterest.toCurrencyString(),
            loanAmount = input.loanAmount
        )
    }
}

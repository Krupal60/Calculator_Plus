package com.plus.calculatorplus.domain.loanaffordability

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.BD_TWELVE
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class LoanAffordabilityCalculationUseCase {

    data class LoanAffordabilityInput(
        val monthlyIncome: String,
        val existingEmi: String,
        val interestRate: String,
        val years: String,
        val foirPercentage: String
    )

    data class LoanAffordabilityResult(
        val maxEmi: String,
        val maxLoanAmount: String,
        val totalPayable: String
    )

    fun calculate(input: LoanAffordabilityInput): LoanAffordabilityResult {
        val income = input.monthlyIncome.toBigDecimalOrZero()
        val existingEmi = input.existingEmi.toBigDecimalOrZero()
        val foir = input.foirPercentage.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
        val monthlyRate = input.interestRate.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
            .divide(BD_TWELVE, 10, RoundingMode.HALF_EVEN)
        val months = input.years.toBigDecimalOrZero().multiply(BD_TWELVE)

        // Maximum EMI the applicant can service after existing obligations.
        val maxEmi = income.multiply(foir).subtract(existingEmi)
            .max(BigDecimal.ZERO)

        // Back-solve principal from the EMI formula: P = EMI * (pow - 1) / (rate * pow).
        val maxLoan =
            if (monthlyRate.compareTo(BigDecimal.ZERO) > 0 && maxEmi.compareTo(BigDecimal.ZERO) > 0) {
                val pow = BigDecimal.ONE.add(monthlyRate).powExponent(months.toDouble())
                val numerator = maxEmi.multiply(pow.subtract(BigDecimal.ONE))
                val denominator = monthlyRate.multiply(pow)
                numerator.divide(denominator, 10, RoundingMode.HALF_EVEN)
            } else {
                maxEmi.multiply(months)
            }

        val totalPayable = maxEmi.multiply(months)

        return LoanAffordabilityResult(
            maxEmi = maxEmi.toCurrencyString(),
            maxLoanAmount = maxLoan.toCurrencyString(),
            totalPayable = totalPayable.toCurrencyString()
        )
    }
}

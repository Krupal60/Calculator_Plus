package com.plus.calculatorplus.domain.loanprepayment

import com.plus.calculatorplus.domain.BD_ONE_HUNDRED
import com.plus.calculatorplus.domain.BD_TWELVE
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class LoanPrepaymentCalculationUseCase {

    data class LoanPrepaymentInput(
        val principal: String,
        val interestRate: String,
        val years: String,
        val extraMonthly: String
    )

    data class LoanPrepaymentResult(
        val interestSaved: String,
        val monthsReduced: String,
        val originalInterest: String,
        val newInterest: String
    )

    fun calculate(input: LoanPrepaymentInput): LoanPrepaymentResult {
        val principal = input.principal.toBigDecimalOrZero()
        val monthlyRate = input.interestRate.toBigDecimalOrZero()
            .divide(BD_ONE_HUNDRED, 10, RoundingMode.HALF_EVEN)
            .divide(BD_TWELVE, 10, RoundingMode.HALF_EVEN)
        val totalMonths = input.years.toBigDecimalOrZero().multiply(BD_TWELVE).toInt()
        val extra = input.extraMonthly.toBigDecimalOrZero()

        if (monthlyRate.compareTo(BigDecimal.ZERO) <= 0 || totalMonths <= 0) {
            return LoanPrepaymentResult("0", "0", "0", "0")
        }

        // Standard EMI for the scheduled tenure.
        val pow = BigDecimal.ONE.add(monthlyRate).powExponent(totalMonths)
        val emi = principal.multiply(monthlyRate).multiply(pow)
            .divide(pow.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_EVEN)

        val originalInterest = emi.multiply(BigDecimal(totalMonths)).subtract(principal)

        // Amortize with the extra monthly payment until the balance clears.
        val newInterest = amortize(principal, monthlyRate, emi.add(extra))
        val newMonths = newInterest.second

        val interestSaved = originalInterest.subtract(newInterest.first).max(BigDecimal.ZERO)
        val monthsReduced = (totalMonths - newMonths).coerceAtLeast(0)

        return LoanPrepaymentResult(
            interestSaved = interestSaved.toCurrencyString(),
            monthsReduced = monthsReduced.toString(),
            originalInterest = originalInterest.toCurrencyString(),
            newInterest = newInterest.first.toCurrencyString()
        )
    }

    /** Returns (total interest paid, months taken) for a fixed monthly payment. */
    private fun amortize(
        principal: BigDecimal,
        monthlyRate: BigDecimal,
        payment: BigDecimal
    ): Pair<BigDecimal, Int> {
        var balance = principal
        var totalInterest = BigDecimal.ZERO
        var months = 0
        // Cap iterations to avoid runaway loops if payment never reduces the balance.
        while (balance.compareTo(BigDecimal.ZERO) > 0 && months < 100 * 12) {
            val interest = balance.multiply(monthlyRate)
            val principalPaid = payment.subtract(interest)
            if (principalPaid.compareTo(BigDecimal.ZERO) <= 0) {
                // Payment can't cover interest; schedule never ends.
                return Pair(totalInterest, months)
            }
            totalInterest = totalInterest.add(interest)
            balance = balance.subtract(principalPaid)
            months++
        }
        return Pair(totalInterest, months)
    }
}

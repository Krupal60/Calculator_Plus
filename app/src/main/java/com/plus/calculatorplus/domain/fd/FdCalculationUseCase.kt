package com.plus.calculatorplus.domain.fd

import com.plus.calculatorplus.domain.BD_FOUR_HUNDRED
import com.plus.calculatorplus.domain.powExponent
import com.plus.calculatorplus.domain.toBigDecimalOrZero
import com.plus.calculatorplus.domain.toCurrencyString
import org.koin.core.annotation.Singleton
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class FdCalculationUseCase {

    data class FdInput(
        val investmentAmount: String,
        val annualInterestRate: String,
        val years: String
    )

    data class FdResult(
        val totalInvestment: String,
        val estimatedReturns: String,
        val totalValue: String
    )

    fun calculate(input: FdInput): FdResult {
        val p = input.investmentAmount.toBigDecimalOrZero()
        val r = input.annualInterestRate.toBigDecimalOrZero()
        val t = input.years.toBigDecimalOrZero()
        val base = BigDecimal.ONE.add(
            r.divide(BD_FOUR_HUNDRED, 10, RoundingMode.HALF_EVEN)
        )
        val exponent = BigDecimal("4").multiply(t).toDouble()
        val maturityValue = base.powExponent(exponent).multiply(p)
        val estReturns = maturityValue.subtract(p)

        return FdResult(
            totalInvestment = p.toCurrencyString(),
            estimatedReturns = estReturns.toCurrencyString(),
            totalValue = maturityValue.toCurrencyString()
        )
    }
}

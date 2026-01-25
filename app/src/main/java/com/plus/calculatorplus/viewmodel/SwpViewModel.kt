package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.swp.OnSwpAction
import com.plus.calculatorplus.data.model.swp.SwpDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class SwpViewModel : ViewModel() {

    private val _state = MutableStateFlow(SwpDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: OnSwpAction) {
        when (action) {
            is OnSwpAction.CalculateSwp -> calculateSwp(
                action.totalInvestment,
                action.withdrawalPerMonth,
                action.expectedReturnRate,
                action.timePeriodYears
            )
        }
    }

    private fun calculateSwp(
        investmentStr: String,
        withdrawalStr: String,
        rateStr: String,
        yearsStr: String
    ) {
        val principal = investmentStr.toDoubleOrNull() ?: return
        val withdrawal = withdrawalStr.toDoubleOrNull() ?: return
        val annualRate = rateStr.toDoubleOrNull() ?: return
        val years = yearsStr.toDoubleOrNull() ?: return

        val monthlyRate = annualRate / 12 / 100
        val totalMonths = (years * 12).toInt()

        var balance = principal
        var totalWithdrawn = 0.0

        repeat(totalMonths) {
            if (balance <= 0) return@repeat

            balance -= withdrawal
            if (balance < 0) balance = 0.0

            totalWithdrawn += withdrawal
            balance *= (1 + monthlyRate)
        }

        _state.value = _state.value.copy(
            totalInvestment = principal.roundToInt().toString(),
            totalWithdrawal = totalWithdrawn.roundToInt().toString(),
            finalValue = balance.roundToInt().toString()
        )
    }

}

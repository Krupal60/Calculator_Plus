package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.dividend.DividendDetailState
import com.plus.calculatorplus.data.model.dividend.OnDividendAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class DividendViewModel : ViewModel() {

    private val _state = MutableStateFlow(DividendDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: OnDividendAction) {
        when (action) {
            is OnDividendAction.CalculateDividend -> calculateDividend(
                action.sharePrice,
                action.dividendPerShare,
                action.numberOfShares
            )
        }
    }

    private fun calculateDividend(priceStr: String, divStr: String, sharesStr: String) {
        val sharePrice = priceStr.toDoubleOrNull() ?: 0.0
        val dividendPerShare = divStr.toDoubleOrNull() ?: 0.0
        val numberOfShares = sharesStr.toDoubleOrNull() ?: 0.0

        val totalDividend = dividendPerShare * numberOfShares
        val yield = if (sharePrice > 0) (dividendPerShare / sharePrice) * 100 else 0.0

        _state.value = _state.value.copy(
            totalDividend = totalDividend.roundToInt().toString(),
            dividendYield = String.format("%.2f", yield),
            annualDividend = totalDividend.roundToInt().toString()
        )
    }
}

package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.data.model.discount.DiscountDetailState
import com.plus.calculatorplus.data.model.discount.OnDiscountAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class DiscountViewModel : ViewModel() {

    private val _state = MutableStateFlow(DiscountDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: OnDiscountAction) {
        when (action) {
            is OnDiscountAction.CalculateDiscount -> calculateDiscount(
                action.originalPrice,
                action.discountPercentage
            )
        }
    }

    private fun calculateDiscount(originalPriceStr: String, discountPercentageStr: String) {
        val originalPrice = originalPriceStr.toDoubleOrNull() ?: 0.0
        val discountPercentage = discountPercentageStr.toDoubleOrNull() ?: 0.0

        val savings = originalPrice * (discountPercentage / 100.0)
        val finalPrice = originalPrice - savings

        _state.value = _state.value.copy(
            originalPrice = originalPrice.roundToInt().toString(),
            discountPercentage = discountPercentage.roundToInt().toString(),
            finalPrice = finalPrice.roundToInt().toString(),
            savings = savings.roundToInt().toString()
        )
    }
}

package com.plus.calculatorplus.presentation.ui.discount

sealed class DiscountAction {
    data class CalculateDiscount(
        val originalPrice: String,
        val discountPercentage: String
    ) : DiscountAction()
}

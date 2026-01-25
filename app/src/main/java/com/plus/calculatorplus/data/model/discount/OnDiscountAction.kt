package com.plus.calculatorplus.data.model.discount

sealed class OnDiscountAction {
    data class CalculateDiscount(
        val originalPrice: String,
        val discountPercentage: String
    ) : OnDiscountAction()
}

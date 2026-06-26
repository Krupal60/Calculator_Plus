package com.plus.calculatorplus.presentation.ui.discount

data class DiscountState(
    val originalPrice: String = "0",
    val discountPercentage: String = "0",
    val finalPrice: String = "0",
    val savings: String = "0"
)

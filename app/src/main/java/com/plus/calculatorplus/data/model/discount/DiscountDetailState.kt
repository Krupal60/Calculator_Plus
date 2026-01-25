package com.plus.calculatorplus.data.model.discount

data class DiscountDetailState(
    val originalPrice: String = "0",
    val discountPercentage: String = "0",
    val finalPrice: String = "0",
    val savings: String = "0"
)

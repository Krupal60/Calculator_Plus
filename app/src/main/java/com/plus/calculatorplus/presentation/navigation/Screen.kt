package com.plus.calculatorplus.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object CalculatorScreen : Screen

    @Serializable
    data object MoreScreen : Screen

    @Serializable
    data object SipScreen : Screen

    @Serializable
    data object BmiScreen : Screen

    @Serializable
    data object EmiScreen : Screen

    @Serializable
    data object ConverterScreen : Screen

    @Serializable
    data object DiscountScreen : Screen

    @Serializable
    data object FdScreen : Screen

    @Serializable
    data object SwpScreen : Screen
}
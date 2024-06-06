package com.plus.calculatorplus.presentation.navigation

sealed class Screen (val route : String){
    data object CalculatorScreen : Screen("calculator")
    data object MoreScreen : Screen("more")
    data object SipScreen : Screen("sip")
    data object BmiScreen : Screen("bmi")
    data object EmiScreen : Screen("emi")
    data object ConverterScreen : Screen("converter")
}
package com.plus.calculatorplus.presentation.ui.agedate

sealed class AgeDateAction {
    data class CalculateAge(
        val fromMillis: Long,
        val toMillis: Long
    ) : AgeDateAction()
}

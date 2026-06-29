package com.plus.calculatorplus.presentation.ui.billsplitter

sealed class BillSplitterAction {
    data class CalculateBillSplit(
        val billAmount: String,
        val tipPercentage: String,
        val people: String
    ) : BillSplitterAction()
}

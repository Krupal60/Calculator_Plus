package com.plus.calculatorplus.presentation.ui.billsplitter

sealed class BillSplitterEffect {
    data class ShowToast(val message: String) : BillSplitterEffect()
}

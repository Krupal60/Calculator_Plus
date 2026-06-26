package com.plus.calculatorplus.presentation.ui.fd

sealed class FdEffect {
    data class ShowToast(val message: String) : FdEffect()
}

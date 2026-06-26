package com.plus.calculatorplus.presentation.ui.sip

sealed class SipEffect {
    data class ShowToast(val message: String) : SipEffect()
}

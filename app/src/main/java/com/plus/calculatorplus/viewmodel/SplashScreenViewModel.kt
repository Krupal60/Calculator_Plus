package com.plus.calculatorplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private val _State = MutableStateFlow(true)
    val State: StateFlow<Boolean> = _State.asStateFlow()

    init {
        viewModelScope.launch {
            delay(900L)
            _State.value = false
        }
    }
}
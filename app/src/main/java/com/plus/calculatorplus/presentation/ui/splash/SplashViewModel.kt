package com.plus.calculatorplus.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import kotlin.time.Duration.Companion.milliseconds

@KoinViewModel
class SplashViewModel : ViewModel() {

    private val _state = Channel<SplashState>()
    val state: Flow<SplashState> = _state.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(900L.milliseconds)
            _state.send(SplashState(isLoading = false))
        }
    }
}

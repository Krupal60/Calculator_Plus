package com.plus.calculatorplus.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Collects values from a [Flow] within the [Lifecycle.State.STARTED] state using [kotlinx.coroutines.flow.collect].
 * The composable will automatically re-collect when the lifecycle resumes.
 *
 * @param E The type of events emitted by the flow.
 * @param effect The flow of effects to collect.
 * @param onEffect The callback invoked for each emitted effect.
 */
@Composable
fun <E> CollectEffect(effect: Flow<E>, onEffect: suspend (E) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnEffect by rememberUpdatedState(onEffect)
    LaunchedEffect(effect, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effect.collect { currentOnEffect(it) }
        }
    }
}


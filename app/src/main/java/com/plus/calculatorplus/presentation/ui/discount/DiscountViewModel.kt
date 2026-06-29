package com.plus.calculatorplus.presentation.ui.discount

import androidx.lifecycle.ViewModel
import com.plus.calculatorplus.domain.discount.DiscountCalculationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DiscountViewModel(
    private val calculationUseCase: DiscountCalculationUseCase
) : ViewModel() {

    val state: StateFlow<DiscountState>
        field = MutableStateFlow(DiscountState())

    private val _effect = Channel<DiscountEffect>(Channel.BUFFERED)
    val effect: Flow<DiscountEffect> = _effect.receiveAsFlow()

    fun onAction(action: DiscountAction) {
        when (action) {
            is DiscountAction.CalculateDiscount -> calculateDiscount(action)
        }
    }

    private fun calculateDiscount(action: DiscountAction.CalculateDiscount) {
        val input = DiscountCalculationUseCase.DiscountInput(
            originalPrice = action.originalPrice,
            discountPercentage = action.discountPercentage
        )
        val result = calculationUseCase.calculate(input)
        state.update {
            DiscountState(
                originalPrice = result.originalPrice,
                discountPercentage = result.discountPercentage,
                finalPrice = result.finalPrice,
                savings = result.savings
            )
        }
    }
}

package com.plus.calculatorplus.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.plus.calculatorplus.presentation.ui.BmiScreenMain
import com.plus.calculatorplus.presentation.ui.CalculatorMain
import com.plus.calculatorplus.presentation.ui.ConvertersScreenMain
import com.plus.calculatorplus.presentation.ui.DiscountScreenMain
import com.plus.calculatorplus.presentation.ui.DividendScreenMain
import com.plus.calculatorplus.presentation.ui.EmiScreenMain
import com.plus.calculatorplus.presentation.ui.FdScreenMain
import com.plus.calculatorplus.presentation.ui.MoreServices
import com.plus.calculatorplus.presentation.ui.RetirementScreenMain
import com.plus.calculatorplus.presentation.ui.SipScreenMain
import com.plus.calculatorplus.presentation.ui.SwpScreenMain

@Composable
fun NavHost3(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    NavDisplay(
        entries = navigator.state.toEntries(entryProvider {

            entry<Screen.CalculatorScreen> {
                CalculatorMain(paddingValues)
            }

            entry<Screen.MoreScreen> {
                MoreServices(navigator, paddingValues)
            }

            entry<Screen.SipScreen>(metadata = verticalSlideTransition) {
                SipScreenMain(paddingValues)
            }

            entry<Screen.BmiScreen>(metadata = verticalSlideTransition) {
                BmiScreenMain(paddingValues)
            }

            entry<Screen.ConverterScreen>(metadata = verticalSlideTransition) {
                ConvertersScreenMain(paddingValues)
            }

            entry<Screen.EmiScreen>(metadata = verticalSlideTransition) {
                EmiScreenMain(paddingValues)
            }

            entry<Screen.DiscountScreen>(metadata = verticalSlideTransition) {
                DiscountScreenMain(paddingValues)
            }

            entry<Screen.FdScreen>(metadata = verticalSlideTransition) {
                FdScreenMain(paddingValues)
            }

            entry<Screen.SwpScreen>(metadata = verticalSlideTransition) {
                SwpScreenMain(paddingValues)
            }

            entry<Screen.RetirementScreen>(metadata = verticalSlideTransition) {
                RetirementScreenMain(paddingValues)
            }

            entry<Screen.DividendScreen>(metadata = verticalSlideTransition) {
                DividendScreenMain(paddingValues)
            }

        }),
        onBack = { navigator.goBack() },

        // Global default (used for screens without metadata)
        transitionSpec = {
            slideInHorizontally { it } togetherWith
                      slideOutHorizontally { -it }
        },
        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith
                      slideOutHorizontally { it }
        },
        predictivePopTransitionSpec = { _ ->
            slideInHorizontally { -it } togetherWith
                      slideOutHorizontally { it }
        }
    )
}

// Keeping a version for compatibility or to satisfy grep if needed,
// but we should eventually replace its usage in MainActivity.
@Composable
fun NavHost(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    NavHost3(navigator, paddingValues)
}
private val verticalSlideTransition =
    NavDisplay.transitionSpec {
        slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(400)
        ) togetherWith ExitTransition.KeepUntilTransitionsFinished
    } +
              NavDisplay.popTransitionSpec {
                  EnterTransition.None togetherWith
                            slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = tween(400)
                            )
              } +
              NavDisplay.predictivePopTransitionSpec {
                  EnterTransition.None togetherWith
                            slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = tween(400)
                            )
              }

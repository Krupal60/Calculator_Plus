package com.plus.calculatorplus.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavHost(
    navigator: Navigator
) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        entries = navigator.state.toEntries(entryProvider {

            entry<Screen.CalculatorScreen> {
                CalculatorMain()
            }

            entry<Screen.MoreScreen>(metadata = ListDetailSceneStrategy.listPane()) {
                MoreServices(navigator)
            }

            entry<Screen.SipScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                SipScreenMain(navigator)
            }

            entry<Screen.BmiScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                BmiScreenMain(navigator)
            }

            entry<Screen.ConverterScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                ConvertersScreenMain(navigator)
            }

            entry<Screen.EmiScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                EmiScreenMain(navigator)
            }

            entry<Screen.DiscountScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                DiscountScreenMain(navigator)
            }

            entry<Screen.FdScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                FdScreenMain(navigator)
            }

            entry<Screen.SwpScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                SwpScreenMain(navigator)
            }

            entry<Screen.RetirementScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                RetirementScreenMain(navigator)
            }

            entry<Screen.DividendScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                DividendScreenMain(navigator)
            }

        }),
        onBack = { navigator.goBack() },
        sceneStrategies = listOf(listDetailStrategy),
        transitionSpec = {
            slideInHorizontally { it } togetherWith
                      slideOutHorizontally { -it }
        },
        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith
                      slideOutHorizontally { it }
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } togetherWith
                      slideOutHorizontally { it }
        }
    )
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

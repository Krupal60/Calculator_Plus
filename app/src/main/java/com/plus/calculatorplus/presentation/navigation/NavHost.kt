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
fun NavHost3(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        entries = navigator.state.toEntries(entryProvider {

            // Main Calculator: No metadata, stays as a single panel
            entry<Screen.CalculatorScreen> {
                CalculatorMain(paddingValues)
            }

            // MoreServices: Acts as the List Pane for the tools section
            entry<Screen.MoreScreen>(metadata = ListDetailSceneStrategy.listPane()) {
                MoreServices(navigator, paddingValues)
            }

            // All specific tools: Act as Detail Panes
            entry<Screen.SipScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                SipScreenMain(paddingValues)
            }

            entry<Screen.BmiScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                BmiScreenMain(paddingValues)
            }

            entry<Screen.ConverterScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                ConvertersScreenMain(paddingValues)
            }

            entry<Screen.EmiScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                EmiScreenMain(paddingValues)
            }

            entry<Screen.DiscountScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                DiscountScreenMain(paddingValues)
            }

            entry<Screen.FdScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                FdScreenMain(paddingValues)
            }

            entry<Screen.SwpScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                SwpScreenMain(paddingValues)
            }

            entry<Screen.RetirementScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                RetirementScreenMain(paddingValues)
            }

            entry<Screen.DividendScreen>(metadata = ListDetailSceneStrategy.detailPane() + verticalSlideTransition) {
                DividendScreenMain(paddingValues)
            }

        }),
        onBack = { navigator.goBack() },
        sceneStrategies = listOf(listDetailStrategy),
        // Global default transitions
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

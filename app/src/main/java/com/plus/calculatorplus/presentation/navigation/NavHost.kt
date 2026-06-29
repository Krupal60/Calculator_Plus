package com.plus.calculatorplus.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.plus.calculatorplus.presentation.ui.bmi.BmiScreenMain
import com.plus.calculatorplus.presentation.ui.calculator.CalculatorScreen
import com.plus.calculatorplus.presentation.ui.converters.ConvertersScreenMain
import com.plus.calculatorplus.presentation.ui.discount.DiscountScreenMain
import com.plus.calculatorplus.presentation.ui.dividend.DividendScreenMain
import com.plus.calculatorplus.presentation.ui.emi.EmiScreenMain
import com.plus.calculatorplus.presentation.ui.fd.FdScreenMain
import com.plus.calculatorplus.presentation.ui.more.MoreServicesScreen
import com.plus.calculatorplus.presentation.ui.retirement.RetirementScreenMain
import com.plus.calculatorplus.presentation.ui.sip.SipScreenMain
import com.plus.calculatorplus.presentation.ui.swp.SwpScreenMain

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavHost(
    navigator: Navigator, modifier: Modifier = Modifier
) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        modifier = modifier,
        entries = navigator.state.toEntries(entryProvider {
            entry<Screen.CalculatorScreen>(metadata = defaultTransition) {
                CalculatorScreen()
            }

            entry<Screen.MoreScreen>(metadata = ListDetailSceneStrategy.listPane() + defaultTransition) {
                MoreServicesScreen(navigator)
            }

            entry<Screen.SipScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                SipScreenMain(navigator)
            }

            entry<Screen.BmiScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                BmiScreenMain(navigator)
            }

            entry<Screen.ConverterScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                ConvertersScreenMain(navigator)
            }

            entry<Screen.EmiScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                EmiScreenMain(navigator)
            }

            entry<Screen.DiscountScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                DiscountScreenMain(navigator)
            }

            entry<Screen.FdScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                FdScreenMain(navigator)
            }

            entry<Screen.SwpScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                SwpScreenMain(navigator)
            }

            entry<Screen.RetirementScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                RetirementScreenMain(navigator)
            }

            entry<Screen.DividendScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
                DividendScreenMain(navigator)
            }
        }),
        onBack = { navigator.goBack() },
        sceneStrategies = listOf(listDetailStrategy),
        transitionSpec = iosTransitionSpec(),
        popTransitionSpec = iosPopTransitionSpec(),
        predictivePopTransitionSpec = iosPredictivePopTransitionSpec()
    )
}

private val defaultTransition =
    NavDisplay.transitionSpec {
        ContentTransform(
            fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
            fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
        )
    } + NavDisplay.popTransitionSpec {
        ContentTransform(
            fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
            fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
        )
    } + NavDisplay.predictivePopTransitionSpec {
        ContentTransform(
            fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
            fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
        )
    }

package com.plus.calculatorplus.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.plus.calculatorplus.presentation.ui.BmiScreenMain
import com.plus.calculatorplus.presentation.ui.CalculatorMain
import com.plus.calculatorplus.presentation.ui.ConvertersScreenMain
import com.plus.calculatorplus.presentation.ui.EmiScreenMain
import com.plus.calculatorplus.presentation.ui.MoreServices
import com.plus.calculatorplus.presentation.ui.SipScreenMain

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
            entry<Screen.SipScreen> {
                SipScreenMain(paddingValues)
            }
            entry<Screen.BmiScreen> {
                BmiScreenMain(paddingValues)
            }
            entry<Screen.ConverterScreen> {
                ConvertersScreenMain()
            }
            entry<Screen.EmiScreen> {
                EmiScreenMain(paddingValues)
            }
        }),
        onBack = { navigator.goBack() }
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

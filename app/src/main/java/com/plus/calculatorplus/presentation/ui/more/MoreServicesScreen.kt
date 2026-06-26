package com.plus.calculatorplus.presentation.ui.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen

private val calculatorRoutes = setOf<NavKey>(
    Screen.SipScreen, Screen.BmiScreen, Screen.EmiScreen, Screen.ConverterScreen,
    Screen.DiscountScreen, Screen.FdScreen, Screen.SwpScreen,
    Screen.RetirementScreen, Screen.DividendScreen
)

private fun Navigator.navigateReplacing(route: NavKey) {
    state.backStacks.values.forEach { stack ->
        calculatorRoutes.forEach { stack.remove(it) }
    }
    navigate(route)
}

@Composable
fun MoreServicesScreen(navigator: Navigator) {
    ScreenScaffold(title = "More Calculators") { paddingValues ->
        val services = listOf(
            ServiceItem("EMI", R.drawable.loan) { navigator.navigateReplacing(Screen.EmiScreen) },
            ServiceItem("Discount", R.drawable.percent_discount) {
                navigator.navigateReplacing(
                    Screen.DiscountScreen
                )
            },
            ServiceItem("SIP", R.drawable.sip) { navigator.navigateReplacing(Screen.SipScreen) },
            ServiceItem("BMI", R.drawable.bmr) { navigator.navigateReplacing(Screen.BmiScreen) },
            ServiceItem("FD", R.drawable.more) { navigator.navigateReplacing(Screen.FdScreen) },
            ServiceItem("SWP", R.drawable.sip) { navigator.navigateReplacing(Screen.SwpScreen) },
            ServiceItem(
                "Retirement",
                R.drawable.retirement
            ) { navigator.navigateReplacing(Screen.RetirementScreen) },
            ServiceItem(
                "Dividend",
                R.drawable.dividend
            ) { navigator.navigateReplacing(Screen.DividendScreen) }
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(services, key = { it.title }) { service ->
                CustomCard(
                    backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier,
                    icon = service.icon,
                    title = service.title,
                    onClick = service.onClick
                )
            }
        }
    }
}

data class ServiceItem(
    val title: String,
    val icon: Int,
    val onClick: () -> Unit
)

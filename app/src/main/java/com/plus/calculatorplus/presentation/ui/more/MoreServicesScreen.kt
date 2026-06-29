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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.bmr
import com.plus.calculatorplus.presentation.icons.dividend
import com.plus.calculatorplus.presentation.icons.loan
import com.plus.calculatorplus.presentation.icons.more
import com.plus.calculatorplus.presentation.icons.percent_discount
import com.plus.calculatorplus.presentation.icons.retirement
import com.plus.calculatorplus.presentation.icons.sip
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen

data class ServiceItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

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
fun MoreServicesScreen(navigator: Navigator, modifier: Modifier = Modifier) {
    ScreenScaffold(title = "More Calculators", modifier = modifier) { paddingValues ->
        val services = listOf(
            ServiceItem("EMI", loan) { navigator.navigateReplacing(Screen.EmiScreen) },
            ServiceItem("Discount", percent_discount) {
                navigator.navigateReplacing(
                    Screen.DiscountScreen
                )
            },
            ServiceItem("SIP", sip) { navigator.navigateReplacing(Screen.SipScreen) },
            ServiceItem("BMI", bmr) { navigator.navigateReplacing(Screen.BmiScreen) },
            ServiceItem("FD", more) { navigator.navigateReplacing(Screen.FdScreen) },
            ServiceItem("SWP", sip) { navigator.navigateReplacing(Screen.SwpScreen) },
            ServiceItem(
                "Retirement",
                retirement
            ) { navigator.navigateReplacing(Screen.RetirementScreen) },
            ServiceItem(
                "Dividend",
                dividend
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


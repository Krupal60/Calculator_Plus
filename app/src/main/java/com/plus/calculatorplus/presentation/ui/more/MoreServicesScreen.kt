package com.plus.calculatorplus.presentation.ui.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import kotlinx.collections.immutable.persistentListOf

data class ServiceItem(
    val title: String, val icon: ImageVector, val onClick: () -> Unit
)

private val calculatorRoutes = setOf<NavKey>(
    Screen.SipScreen,
    Screen.BmiScreen,
    Screen.EmiScreen,
    Screen.ConverterScreen,
    Screen.DiscountScreen,
    Screen.FdScreen,
    Screen.SwpScreen,
    Screen.RetirementScreen,
    Screen.DividendScreen
)

private fun Navigator.navigateReplacing(route: NavKey) {
    state.backStacks.values.forEach { stack ->
        calculatorRoutes.forEach { stack.remove(it) }
    }
    navigate(route)
}

@Composable
fun MoreServicesScreen(navigator: Navigator, modifier: Modifier = Modifier) {
    MoreServices(
        onServiceClick = { navigator.navigateReplacing(it) }, modifier = modifier
    )
}

@Composable
fun MoreServices(
    onServiceClick: (NavKey) -> Unit, modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "More Tools",
        subtitle = "Specialized calculators for all your needs",
        icon = more,
        modifier = modifier
    ) { paddingValues ->
        val services = persistentListOf(
            ServiceItem("EMI", loan) { onServiceClick(Screen.EmiScreen) },
            ServiceItem("FD", more) { onServiceClick(Screen.FdScreen) },
            ServiceItem("SIP", sip) { onServiceClick(Screen.SipScreen) },
            ServiceItem("SWP", sip) { onServiceClick(Screen.SwpScreen) },
            ServiceItem("Discount", percent_discount) { onServiceClick(Screen.DiscountScreen) },
            ServiceItem("Retirement", retirement) { onServiceClick(Screen.RetirementScreen) },
            ServiceItem("Dividend", dividend) { onServiceClick(Screen.DividendScreen) },
            ServiceItem("BMI", bmr) { onServiceClick(Screen.BmiScreen) })

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }, contentType = "Spacer") {
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }

            item(span = { GridItemSpan(maxLineSpan) }, contentType = "Header") {
                Text(
                    text = "Available Calculators",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(
                items = services,
                key = { it.title },
                contentType = { _ -> "ServiceItem" }) { service ->
                CustomCard(
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = Modifier,
                    icon = service.icon,
                    title = service.title,
                    onClick = service.onClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreServicesScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            MoreServices(
                onServiceClick = {})
        }
    }
}


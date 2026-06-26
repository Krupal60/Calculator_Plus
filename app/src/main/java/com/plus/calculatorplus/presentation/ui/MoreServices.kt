package com.plus.calculatorplus.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen


@Composable
fun MoreServices(navigator: Navigator, paddingValues: PaddingValues) {
    val services = listOf(
        ServiceItem("EMI", R.drawable.loan) { navigator.navigate(Screen.EmiScreen) },
        ServiceItem(
            "Discount",
            R.drawable.percent_discount
        ) { navigator.navigate(Screen.DiscountScreen) },
        ServiceItem("SIP", R.drawable.sip) { navigator.navigate(Screen.SipScreen) },
        ServiceItem("BMI", R.drawable.bmr) { navigator.navigate(Screen.BmiScreen) },
        ServiceItem("FD", R.drawable.more) { navigator.navigate(Screen.FdScreen) },
        ServiceItem("SWP", R.drawable.sip) { navigator.navigate(Screen.SwpScreen) },
        ServiceItem(
            "Retirement",
            R.drawable.retirement
        ) { navigator.navigate(Screen.RetirementScreen) },
        ServiceItem("Dividend", R.drawable.dividend) { navigator.navigate(Screen.DividendScreen) }
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
        items(services, key = {
            it.title
        }) { service ->
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

data class ServiceItem(
    val title: String,
    val icon: Int,
    val onClick: () -> Unit
)

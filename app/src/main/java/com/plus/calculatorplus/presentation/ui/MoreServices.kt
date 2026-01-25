package com.plus.calculatorplus.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MoreServices(navigator: Navigator, paddingValues: PaddingValues) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 15.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 4.dp,
                end = 4.dp
            ), verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.sdp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            Alignment.CenterVertically
        ) {
            CustomCard(
                backgroundColor =  MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.loan,
                title = "EMI",
                onClick = {
                    navigator.navigate(Screen.EmiScreen)
                })

            CustomCard(
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.percent_discount,
                title = "Discount",
                onClick = {
                    navigator.navigate(Screen.DiscountScreen)
                })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.sdp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            Alignment.CenterVertically
        ) {
            CustomCard(
                backgroundColor =  MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.sip,
                title = "SIP",
                onClick = {
                    navigator.navigate(Screen.SipScreen)
                })

            CustomCard(
                backgroundColor =  MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.bmr,
                title = "BMI",
                onClick = {
                    navigator.navigate(Screen.BmiScreen)
                })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.sdp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            Alignment.CenterVertically
        ) {
            CustomCard(
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.more,
                title = "FD",
                onClick = {
                    navigator.navigate(Screen.FdScreen)
                })

            CustomCard(
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.sip,
                title = "SWP",
                onClick = {
                    navigator.navigate(Screen.SwpScreen)
                })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.sdp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            Alignment.CenterVertically
        ) {
            CustomCard(
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.retirement,
                title = "Retirement",
                onClick = {
                    navigator.navigate(Screen.RetirementScreen)
                })

            CustomCard(
                backgroundColor = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                icon = R.drawable.dividend,
                title = "Dividend",
                onClick = {
                    navigator.navigate(Screen.DividendScreen)
                })
        }

    }

}

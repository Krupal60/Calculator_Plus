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
import androidx.navigation.NavHostController
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.navigation.Screen
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MoreServices(navController: NavHostController, paddingValues: PaddingValues) {
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
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                icon = R.drawable.scale,
                title = "Converter",
                onClick = {
                    navController.navigate(Screen.ConverterScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.MoreScreen.route)
                    }
                })

            CustomCard(
                backgroundColor =  MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                icon = R.drawable.loan,
                title = "EMI",
                onClick = {
                    navController.navigate(Screen.EmiScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.MoreScreen.route)
                    }
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
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                icon = R.drawable.sip,
                title = "SIP",
                onClick = {
                    navController.navigate(Screen.SipScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.MoreScreen.route)
                    }
                })

            CustomCard(
                backgroundColor =  MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                icon = R.drawable.bmr,
                title = "BMI",
                onClick = {
                    navController.navigate(Screen.BmiScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.MoreScreen.route)
                    }
                })
        }

    }

}
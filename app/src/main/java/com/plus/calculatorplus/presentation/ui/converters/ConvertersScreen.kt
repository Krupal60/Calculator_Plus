package com.plus.calculatorplus.presentation.ui.converters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.navigation.Navigator


@Composable
fun ConvertersScreenMain(navigator: Navigator) {
    ScreenScaffold(
        title = "Converter tools",
        showBack = true,
        onBack = { navigator.goBack() }) { paddingValues ->
        Converters(paddingValues)
    }
}

@Composable
fun Converters(paddingValues: PaddingValues) {
    var selectedConverter by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 14.dp,
                end = 14.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedConverter == null) {
            Button(onClick = { selectedConverter = "Length" }) {
                Text("Length Converter")
            }
        } else if (selectedConverter == "Length") {
            LengthConverterScreen()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { selectedConverter = null }) {
                Text("Back to Converter List")
            }
        }
    }
}

@Composable
fun LengthConverterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Length Converter Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Implementation coming soon!")
    }
}

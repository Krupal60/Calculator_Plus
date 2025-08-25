package com.plus.calculatorplus.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertersScreenMain() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Converter Tools") })
        }
    ) { paddingValues ->
        Converters(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun Converters(modifier: Modifier = Modifier) {
    var selectedConverter by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedConverter == null) {
            Button(onClick = { selectedConverter = "Length" }) {
                Text("Length Converter")
            }
        } else if (selectedConverter == "Length") {
            // Placeholder for LengthConverter - replace with actual implementation
            LengthConverterScreen()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { selectedConverter = null }) {
                Text("Back to Converter List")
            }
        }
    }
}

// Placeholder for LengthConverter - replace with actual implementation
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

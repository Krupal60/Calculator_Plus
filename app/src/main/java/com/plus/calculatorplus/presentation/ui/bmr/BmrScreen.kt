package com.plus.calculatorplus.presentation.ui.bmr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plus.calculatorplus.domain.validation.ageValidate
import com.plus.calculatorplus.domain.validation.cmValidation
import com.plus.calculatorplus.domain.validation.weightValidation
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.CustomCard2
import com.plus.calculatorplus.presentation.components.HeightSliderWithText
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.bmr
import com.plus.calculatorplus.presentation.icons.man
import com.plus.calculatorplus.presentation.icons.woman
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Suppress("MultipleContentEmitters")
@Composable
fun BmrScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    BmrScreen(
        onBack = { navigator.goBack() },
        modifier = modifier
    )
}

@Suppress("MultipleContentEmitters")
@Composable
fun BmrScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var isMale by remember { mutableStateOf(true) }
    var weight by rememberSaveable { mutableStateOf("50") }
    var cm by rememberSaveable { mutableStateOf("170") }
    var age by rememberSaveable { mutableStateOf("18") }
    var result by remember { mutableStateOf("") }

    ScreenScaffold(
        title = "BMR Calculator",
        subtitle = "Basal Metabolic Rate estimate",
        icon = bmr,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 14.dp,
                    end = 14.dp
                ),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Your Profile",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomCard(
                    backgroundColor = if (isMale) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                    onClick = { isMale = true },
                    icon = man,
                    title = "Male",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .wrapContentHeight()
                )
                CustomCard(
                    backgroundColor = if (!isMale) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                    onClick = { isMale = false },
                    icon = woman,
                    title = "Female",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .wrapContentHeight()
                )
            }

            Text(
                text = "Body Measurements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
            )

            HeightSliderWithText(
                onValueChange = { cm = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp),
                startNumber = 50,
                endNumber = 280,
                actionType = androidx.compose.ui.text.input.ImeAction.Done,
                isError = !cmValidation(cm).first
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomCard2(
                    modifier = Modifier.weight(1f),
                    text = "Weight",
                    endText = "Kg",
                    isError = !weightValidation(weight).first,
                    value = weight,
                    onValueChange = { weight = it })
                CustomCard2(
                    modifier = Modifier.weight(1f),
                    text = "Age",
                    endText = "Years",
                    isError = !ageValidate(age).first,
                    value = age,
                    onValueChange = { age = it })
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    val w = weight.toDoubleOrNull()
                    val h = cm.toDoubleOrNull()
                    val a = age.toDoubleOrNull()
                    if (w != null && h != null && a != null && w > 0 && h > 0 && a > 0) {
                        val bmrValue = if (isMale) {
                            (10 * w) + (6.25 * h) - (5 * a) + 5
                        } else {
                            (10 * w) + (6.25 * h) - (5 * a) - 161
                        }
                        result = "${bmrValue.roundToInt()} kcal/day"
                        coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                    }
                }
            ) {
                Text(
                    text = "Calculate BMR",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (result.isNotEmpty()) {
                Card(
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "BMR Result",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = result,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Your body needs this many calories per day at complete rest.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BmrScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            BmrScreen(
                onBack = {}
            )
        }
    }
}

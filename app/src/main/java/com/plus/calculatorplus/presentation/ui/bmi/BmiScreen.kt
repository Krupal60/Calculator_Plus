package com.plus.calculatorplus.presentation.ui.bmi

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plus.calculatorplus.domain.validation.ageValidate
import com.plus.calculatorplus.domain.validation.bmiValidation
import com.plus.calculatorplus.domain.validation.cmValidation
import com.plus.calculatorplus.domain.validation.weightValidation
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.CustomCard2
import com.plus.calculatorplus.presentation.components.HeightSliderWithText
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.man
import com.plus.calculatorplus.presentation.icons.monitor_weight
import com.plus.calculatorplus.presentation.icons.woman
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.util.CollectEffect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Suppress("MultipleContentEmitters")
@Composable
fun BmiScreenMain(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: BmiViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    BmiScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = { navigator.goBack() },
        modifier = modifier
    )
    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is BmiEffect.ShowToast -> {
                Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}


@Composable
fun BmiScreen(
    state: State<BmiState>,
    onAction: (BmiAction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenScaffold(
        title = "BMI Calculator",
        subtitle = "Track your health and body composition",
        icon = monitor_weight,
        showBack = true,
        onBack = onBack,
        modifier = modifier
    ) { paddingValues ->
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState(0)
        val context = LocalContext.current
        var age by rememberSaveable { mutableStateOf("18") }
        var weight by rememberSaveable { mutableStateOf("50") }
        var cm by rememberSaveable { mutableStateOf("50") }

        Column(
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 14.dp,
                    end = 14.dp
                ), verticalArrangement = Arrangement.Top
        ) {

            var isMale by remember { mutableStateOf(true) }

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
                Alignment.CenterVertically
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
                actionType = ImeAction.Done,
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
                    val validation = bmiValidation(age, cm, weight)
                    if (validation.first) {
                        onAction(BmiAction.CalculateBmi(age, cm, weight, isMale))
                        coroutineScope.launch { scrollState.animateScrollTo(Int.MAX_VALUE) }
                    } else {
                        Toast.makeText(context, validation.second, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(
                    text = "Calculate BMI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }

            AnimatedVisibility(state.value.bmi != "0.0") {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
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
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "BMI Result",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = state.value.bmi,
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = state.value.interpretation,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Your BMI indicates that you are in the ${state.value.interpretation.lowercase()} category.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun BmiScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember {
                mutableStateOf(
                    BmiState(
                        bmi = "24.5",
                        interpretation = "Normal weight"
                    )
                )
            }
            BmiScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
private fun BmiScreenTabletPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(BmiState()) }
            BmiScreen(
                state = state,
                onAction = {},
                onBack = {}
            )
        }
    }
}

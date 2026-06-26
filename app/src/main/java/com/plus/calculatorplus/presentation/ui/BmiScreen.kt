package com.plus.calculatorplus.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.Calculator
import com.plus.calculatorplus.R
import com.plus.calculatorplus.data.model.bmi.BmiDetailState
import com.plus.calculatorplus.data.model.bmi.OnBmiAction
import com.plus.calculatorplus.presentation.components.BmiResultCard
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.CustomCard2
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.HeightSliderWithText
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.validation.ageValidate
import com.plus.calculatorplus.presentation.validation.bmiValidation
import com.plus.calculatorplus.presentation.validation.cmValidation
import com.plus.calculatorplus.presentation.validation.weightValidation
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import com.plus.calculatorplus.viewmodel.BmiViewModel
import kotlinx.coroutines.launch


@Composable
fun BmiScreenMain(navigator: Navigator, viewModel: BmiViewModel = viewModel()) {
    ScreenScaffold(
        title = "BMI Calculator",
        showBack = true,
        onBack = { navigator.goBack() }) { paddingValues ->
        val state = viewModel.state.collectAsStateWithLifecycle()
        BmiScreen(paddingValues, state, viewModel::onAction)
    }
}


@Composable
fun BmiScreen(
    paddingValues: PaddingValues,
    state: State<BmiDetailState>,
    onAction: (OnBmiAction) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

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
        var isMale by remember {
            mutableStateOf(true)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            CustomCard(
                backgroundColor = if (isMale) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface,
                onClick = { isMale = true },
                icon = R.drawable.male,
                title = "Male",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .wrapContentHeight()

            )
            CustomCard(
                backgroundColor = if (!isMale) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.inverseOnSurface,
                onClick = { isMale = false },
                icon = R.drawable.female,
                title = "Female",
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .wrapContentHeight()

            )
        }
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
                Modifier.weight(1f),
                "Weight", "Kg", !weightValidation(weight).first, "50",
                onValueChange = { weight = it }
            )
            CustomCard2(
                Modifier.weight(1f),
                "Age", "Years", !ageValidate(age).first, "18",
                onValueChange = { age = it }
            )
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
            onClick = {
                when (
                    bmiValidation(
                        age = age,
                        weight = weight,
                        cm = cm
                    ).first
                ) {
                    true -> {
                        onAction(
                            OnBmiAction.CalculateBmi(
                                age,
                                cm,
                                weight,
                                isMale
                            )
                        )
                        coroutineScope.launch {
                            scrollState.animateScrollTo(Int.MAX_VALUE)
                        }
                    }

                    else -> {
                        Toast.makeText(
                            Calculator.calculator,
                            bmiValidation(
                                age = age,
                                weight = weight,
                                cm = cm
                            ).second,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }) {
            CustomText(
                title = "Calculate BMI", size = 14.sp,
                modifier = Modifier.padding(vertical = 2.dp)
            )

        }
        BmiResultCard(modifier = Modifier.fillMaxWidth(), state.value)
    }
}

@Preview(showBackground = true)
@Composable
private fun BmiScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            val state = remember { mutableStateOf(BmiDetailState()) }
            BmiScreen(
                paddingValues = PaddingValues(0.dp),
                state = state,
                onAction = {}
            )
        }
    }
}
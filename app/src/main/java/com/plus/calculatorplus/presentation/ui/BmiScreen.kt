package com.plus.calculatorplus.presentation.ui

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plus.calculatorplus.Calculator
import com.plus.calculatorplus.R
import com.plus.calculatorplus.data.model.bmi.BmiDetailState
import com.plus.calculatorplus.data.model.bmi.OnBmiAction
import com.plus.calculatorplus.presentation.components.BmiResultCard
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.CustomText
import com.plus.calculatorplus.presentation.components.customCard2
import com.plus.calculatorplus.presentation.components.heightSliderWithText
import com.plus.calculatorplus.presentation.validation.ageValidate
import com.plus.calculatorplus.presentation.validation.bmiValidation
import com.plus.calculatorplus.presentation.validation.cmValidation
import com.plus.calculatorplus.presentation.validation.weightValidation
import com.plus.calculatorplus.viewmodel.BmiViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch


@Composable
fun BmiScreenMain(paddingValues: PaddingValues,viewModel : BmiViewModel = viewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    BmiScreen(paddingValues,state,viewModel::onAction)
}


@Composable
fun BmiScreen(
    paddingValues: PaddingValues,
    state: State<BmiDetailState>,
    onAction: (OnBmiAction) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)

    val age = remember { mutableStateOf("18") }
    val weight = remember { mutableStateOf("50") }
    val cm = remember { mutableStateOf("50") }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                top = paddingValues.calculateTopPadding() ,
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
        cm.value = heightSliderWithText(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp),
            startNumber = 50,
            endNumber = 280,
            actionType = ImeAction.Done,
            isError = !cmValidation(cm.value).first
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            weight.value = customCard2(
                Modifier.weight(1f),
                "Weight", "Kg", !weightValidation(weight.value).first,"50"
            )
            age.value = customCard2(
                Modifier.weight(1f),
                "Age", "Years", !ageValidate(age.value).first,"18"
            )
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
            onClick = {
                when (
                    bmiValidation(
                        age = age.value,
                        weight = weight.value,
                        cm = cm.value
                    ).first
                ) {
                    true -> {
                        onAction(
                            OnBmiAction.CalculateBmi(
                                age.value,
                                cm.value,
                                weight.value,
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
                                age = age.value,
                                weight = weight.value,
                                cm = cm.value
                            ).second,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }) {
            CustomText(
                title = "Calculate BMI", size = 14.ssp,
                modifier = Modifier.padding(vertical = 2.dp)
            )

        }
        BmiResultCard(modifier = Modifier.fillMaxWidth(), state.value)
    }
}

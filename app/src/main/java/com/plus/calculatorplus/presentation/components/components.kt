@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.plus.calculatorplus.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plus.calculatorplus.presentation.ui.bmi.BmiState
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import com.plus.calculatorplus.ui.theme.md_theme_dark_inversePrimary
import com.plus.calculatorplus.ui.theme.md_theme_dark_onSurface
import com.plus.calculatorplus.ui.theme.md_theme_dark_primary
import com.plus.calculatorplus.ui.theme.md_theme_dark_secondaryContainer
import kotlin.math.roundToInt

@Composable
fun CalculatorButton(
    modifier: Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 18.sp,
    autoSize: TextAutoSize? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .clickable {
                onClick()
            },
        shapes = ButtonShapes(
            CircleShape,
            RoundedCornerShape(24.dp)
        ),
        colors = ButtonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            autoSize = autoSize,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            maxLines = 1
        )
    }
}

@Composable
fun SliderWithText(
    title: String,
    startNumber: Int,
    endNumber: Int,
    actionType: ImeAction,
    prefix: String,
    suffix: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var value by rememberSaveable(startNumber) { mutableStateOf("$startNumber") }
    var text by rememberSaveable(startNumber) { mutableStateOf("$startNumber") }
    val focusManager = LocalFocusManager.current
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    lineHeight = 18.sp,
                    modifier = Modifier.fillMaxWidth(0.41f)
                )
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        if (text.isNotBlank()) {
                            value = text
                        }
                        onValueChange(text)
                    },
                    prefix = {
                        if (prefix.isNotEmpty()) {
                            Text(text = prefix)
                        }
                    },
                    suffix = {
                        if (suffix.isNotEmpty()) {
                            Text(text = suffix)
                        }
                    },
                    isError = isError,
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = actionType
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.63f)
                        .wrapContentHeight()
                )
            }
            Slider(
                value = value.toFloat(), onValueChange = {
                    value = it.roundToInt().toString()
                    text = value
                    onValueChange(text)
                }, valueRange = startNumber.toFloat()..endNumber.toFloat(), colors = SliderColors(
                    inactiveTrackColor = Color.Gray,
                    inactiveTickColor = Color.Gray,
                    disabledInactiveTickColor = Color.Gray,
                    disabledActiveTrackColor = Color.Gray,
                    disabledActiveTickColor = Color.Gray,
                    disabledInactiveTrackColor = Color.Gray,
                    disabledThumbColor = Color.Gray,
                    activeTickColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    thumbColor = MaterialTheme.colorScheme.primary
                ), modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomCard2(
    modifier: Modifier,
    text: String,
    endText: String,
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {

    var value by rememberSaveable(value) {
        mutableStateOf(value)
    }
    val focusManager = LocalFocusManager.current
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ), modifier = modifier

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text, fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        value = (value.toInt() - 1).toString()
                        onValueChange(value)
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = "Minus Number"
                    )
                }
                OutlinedTextField(
                    modifier = modifier.weight(1f),
                    value = value,
                    onValueChange = {
                        value = it
                        onValueChange(it)
                    },
                    isError = isError, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLargeEmphasized.copy(textAlign = TextAlign.Center)
                )

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        value = (value.toInt() + 1).toString()
                        onValueChange(value)
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = "Plus Number"
                    )
                }
            }

            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = endText,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal
                )
            }

        }
    }
}


@Composable
fun HeightSliderWithText(
    modifier: Modifier,
    title: String = "Height",
    startNumber: Int,
    endNumber: Int,
    actionType: ImeAction,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf("150") }
    var valueFt by rememberSaveable { mutableStateOf("5") }
    var valueIn by rememberSaveable { mutableStateOf("0") }
    var text by rememberSaveable { mutableStateOf("150") }
    var textFt by rememberSaveable { mutableStateOf("5") }
    var textIn by rememberSaveable { mutableStateOf("0") }
    var isCm by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Logic moved from the bottom of the function to a LaunchedEffect
    LaunchedEffect(isCm, text, textFt, textIn) {
        val finalValue = if (isCm) {
            text
        } else {
            val textFtToCm = when {
                textFt.isNotEmpty() && textIn.isNotEmpty() && textIn != "0" && textFt != "0" -> {
                    ((textFt.toDouble() * 30.48) + (textIn.toDouble() * 2.54)).roundToInt()
                        .toString()
                }

                textFt.isNotEmpty() && textFt != "0" -> {
                    (textFt.toDouble() * 30.48).roundToInt().toString()
                }

                else -> {
                    30.48.roundToInt().toString()
                }
            }
            onValueChange(textFtToCm)
        }
        if (isCm) {
            onValueChange(text)
        }
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ),
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    lineHeight = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    modifier = Modifier.weight(0.5f),
                    shape = CircleShape,
                    onClick = { isCm = false },
                    colors = ButtonColors(
                        containerColor = if (!isCm) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surfaceBright,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledContentColor = LightGray,
                        disabledContainerColor = LightGray
                    )
                ) {
                    Text(text = "Ft", color = MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    modifier = Modifier.weight(0.5f),
                    onClick = { isCm = true },
                    shape = CircleShape,
                    colors = ButtonColors(
                        containerColor = if (isCm) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surfaceBright,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledContentColor = LightGray,
                        disabledContainerColor = LightGray
                    )
                ) {
                    Text(text = "Cm", color = MaterialTheme.colorScheme.onSurface)
                }

            }
            AnimatedVisibility(
                visible = isCm,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        if (text.isNotBlank() && text != "150") {
                            value = text
                        }
                    },

                    suffix = {
                        Text(text = "Cm")
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = actionType
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                )

            }
            AnimatedVisibility(
                visible = !isCm,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textFt,
                        onValueChange = {
                            textFt = it
                            if (textFt.isNotBlank()) {
                                valueFt = textFt
                            }
                        },
                        suffix = {
                            Text(text = "Ft")

                        },
                        isError = isError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = actionType
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .align(Alignment.CenterVertically)
                    )

                    OutlinedTextField(
                        value = textIn,
                        onValueChange = {
                            textIn = it
                            if (textIn.isNotBlank()) {
                                valueIn = textIn
                            }
                        },
                        suffix = {
                            Text(text = "In")

                        },
                        isError = isError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = actionType
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .align(Alignment.CenterVertically)
                    )
                }


            }
            AnimatedVisibility(visible = isCm) {
                Slider(
                    value = value.toFloat(),
                    onValueChange = {
                        value = it.roundToInt().toString()
                        text = value
                    },
                    valueRange = startNumber.toFloat()..endNumber.toFloat(),
                    colors = SliderColors(
                        inactiveTrackColor = Color.Gray,
                        inactiveTickColor = Color.Gray,
                        disabledInactiveTickColor = Color.Gray,
                        disabledActiveTrackColor = Color.Gray,
                        disabledActiveTickColor = Color.Gray,
                        disabledInactiveTrackColor = Color.Gray,
                        disabledThumbColor = Color.Gray,
                        activeTickColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        thumbColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AnimatedVisibility(visible = !isCm) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Slider(
                        value = valueFt.toFloat(), onValueChange = {
                            valueFt = it.roundToInt().toString()
                            textFt = valueFt
                        }, valueRange = 1f..10f, colors = SliderColors(
                            inactiveTrackColor = Color.Gray,
                            inactiveTickColor = Color.Gray,
                            disabledInactiveTickColor = Color.Gray,
                            disabledActiveTrackColor = Color.Gray,
                            disabledActiveTickColor = Color.Gray,
                            disabledInactiveTrackColor = Color.Gray,
                            disabledThumbColor = Color.Gray,
                            activeTickColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            thumbColor = MaterialTheme.colorScheme.primary
                        ), modifier = Modifier.weight(1f)
                    )

                    Slider(
                        value = valueIn.toFloat(), onValueChange = {
                            valueIn = it.roundToInt().toString()
                            textIn = valueIn
                        }, valueRange = 0f..12f, colors = SliderColors(
                            inactiveTrackColor = Color.Gray,
                            inactiveTickColor = Color.Gray,
                            disabledInactiveTickColor = Color.Gray,
                            disabledActiveTrackColor = Color.Gray,
                            disabledActiveTickColor = Color.Gray,
                            disabledInactiveTrackColor = Color.Gray,
                            disabledThumbColor = Color.Gray,
                            activeTickColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            thumbColor = MaterialTheme.colorScheme.primary
                        ), modifier = Modifier.weight(1f)
                    )
                }

            }
        }
    }
}


@Composable
fun CustomCard(
    onClick: () -> Unit,
    icon: Int,
    title: String,
    modifier: Modifier,
    backgroundColor: Color
) {
    Card(
        modifier = modifier, onClick = {
            onClick()
        }, elevation = CardDefaults.cardElevation(10.dp), colors = CardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier
                    .size(55.dp)
                    .padding(top = 8.dp),
                contentDescription = title
            )
            Text(
                text = title,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = FontFamily.Serif
            )
        }

    }
}

@Composable
fun CustomSelectionCard(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .wrapContentHeight(), onClick = {
            onClick()
        }, elevation = CardDefaults.cardElevation(10.dp), colors = CardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        )
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        )
    }

}


@Composable
fun PieChart(
    data: Map<String, Long>,
    radiusOuter: Dp = 60.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        md_theme_dark_primary,
        md_theme_dark_secondaryContainer,
        md_theme_dark_inversePrimary,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DetailsPieChart(
            data = data,
            colors = colors
        )
        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            // To see the data in more structured way
            // Compose Function in which Items are showing data
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }


    }

}

@Composable
fun DetailsPieChart(
    data: Map<String, Long>,
    colors: List<Color>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // create the data items
        data.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index]
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Long>,
    height: Dp = 18.dp,
    color: Color
) {
    Surface(
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp, start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .size(height)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = getMoneyInWords(data.second.toDouble()),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }

    }

}

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    title: String,
    size: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal

) {
    Text(
        modifier = modifier,
        text = title,
        fontWeight = fontWeight,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Normal,
        fontSize = size,
        textAlign = TextAlign.Start,
        lineHeight = 18.sp
    )
}


@Composable
fun BmiResultCard(modifier: Modifier, state: BmiState) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ),
        modifier = modifier
            .padding(bottom = 10.dp, top = 12.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = "BMI Results",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(
                    title = "BMI: ",
                    size = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                CustomText(
                    title = state.bmi,
                    size = 14.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Weight Status:",
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
                Text(
                    text = state.interpretation,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = getColorBasedOnInterpretation(state.interpretation)
                )
            }
        }
    }
}

// Function to determine color based on interpretation (example)
fun getColorBasedOnInterpretation(interpretation: String): Color {
    return when (interpretation) {
        "Underweight" -> Color.Yellow
        "Overweight" -> Color.Yellow
        "Obese" -> Color.Red
        else -> md_theme_dark_onSurface
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScaffold(
    title: String,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        content = content
    )
}
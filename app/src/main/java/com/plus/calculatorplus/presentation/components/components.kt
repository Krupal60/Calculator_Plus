@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plus.calculatorplus.presentation.icons.arrow_back
import com.plus.calculatorplus.presentation.icons.calculator
import com.plus.calculatorplus.presentation.icons.keyboard_arrow_left
import com.plus.calculatorplus.presentation.icons.keyboard_arrow_right
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.theme.md_theme_dark_inversePrimary
import com.plus.calculatorplus.presentation.theme.md_theme_dark_onSurface
import com.plus.calculatorplus.presentation.theme.md_theme_dark_primary
import com.plus.calculatorplus.presentation.theme.md_theme_dark_secondaryContainer
import com.plus.calculatorplus.presentation.ui.bmi.BmiState
import com.plus.calculatorplus.presentation.util.Utils.getMoneyInWords
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlin.math.roundToInt

@Composable
fun CalculatorButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    autoSize: TextAutoSize? = null
) {
    Button(
        modifier = modifier.clickable { onClick() },
        shapes = ButtonShapes(CircleShape, RoundedCornerShape(24.dp)),
        colors = ButtonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface,
            disabledContainerColor = LightGray,
            disabledContentColor = LightGray
        ),
        onClick = { onClick() }
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
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    decimalPlaces: Int = 0
) {
    val initialValue =
        if (decimalPlaces > 0) "${startNumber}.${"0".repeat(decimalPlaces)}" else "$startNumber"
    var value by rememberSaveable(startNumber) { mutableStateOf(initialValue) }
    var text by rememberSaveable(startNumber) { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 4.dp)
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
                        if (prefix.isNotEmpty()) Text(
                            text = prefix,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    suffix = {
                        if (suffix.isNotEmpty()) Text(
                            text = suffix,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    isError = isError,
                    visualTransformation = visualTransformation,
                    shape = MaterialTheme.shapes.large,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (decimalPlaces > 0) KeyboardType.Decimal else KeyboardType.Number,
                        imeAction = actionType
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    singleLine = true,
                    modifier = Modifier
                        .weight(0.5f)
                        .wrapContentHeight()
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Slider(
                value = value.toFloatOrNull() ?: startNumber.toFloat(),
                onValueChange = {
                    value = if (decimalPlaces > 0) {
                        "%.${decimalPlaces}f".format(it)
                    } else {
                        it.roundToInt().toString()
                    }
                    text = value
                    onValueChange(text)
                },
                valueRange = startNumber.toFloat()..endNumber.toFloat(),
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant,
                    thumbColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomCard2(
    text: String,
    endText: String,
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentValue by rememberSaveable(value) { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        currentValue = (currentValue.toInt() - 1).toString()
                        onValueChange(currentValue)
                    }) {
                    Icon(
                        imageVector = keyboard_arrow_left,
                        contentDescription = "Minus Number"
                    )
                }
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = currentValue,
                    onValueChange = {
                        currentValue = it
                        onValueChange(it)
                    },
                    isError = isError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center,
                    ),
                    shape = MaterialTheme.shapes.large
                )
                IconButton(
                    onClick = {
                        currentValue = (currentValue.toInt() + 1).toString()
                        onValueChange(currentValue)
                    }) {
                    Icon(
                        imageVector = keyboard_arrow_right,
                        contentDescription = "Plus Number"
                    )
                }
            }
            Text(
                text = endText,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Suppress("MultipleContentEmitters")
@Composable
fun HeightSliderWithText(
    startNumber: Int,
    endNumber: Int,
    actionType: ImeAction,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Height"
) {
    var value by rememberSaveable { mutableStateOf("150") }
    var valueFt by rememberSaveable { mutableStateOf("5") }
    var valueIn by rememberSaveable { mutableStateOf("0") }
    var text by rememberSaveable { mutableStateOf("150") }
    var textFt by rememberSaveable { mutableStateOf("5") }
    var textIn by rememberSaveable { mutableStateOf("0") }
    var isCm by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val currentOnValueChange by rememberUpdatedState(onValueChange)
    LaunchedEffect(isCm, text, textFt, textIn) {
        if (isCm) {
            currentOnValueChange(text)
        } else {
            val textFtToCm = when {
                textFt.isNotEmpty() && textIn.isNotEmpty() && textIn != "0" && textFt != "0" ->
                    ((textFt.toDouble() * 30.48) + (textIn.toDouble() * 2.54)).roundToInt()
                        .toString()

                textFt.isNotEmpty() && textFt != "0" ->
                    (textFt.toDouble() * 30.48).roundToInt().toString()

                else -> "30"
            }
            currentOnValueChange(textFtToCm)
        }
    }

    Card(
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                ButtonGroup(
                    modifier = Modifier.widthIn(max = 150.dp),
                    overflowIndicator = { ButtonGroupDefaults.OverflowIndicator(it) }
                ) {
                    toggleableItem(
                        checked = !isCm,
                        onCheckedChange = { isCm = false },
                        label = "Ft",
                        weight = 1f
                    )
                    toggleableItem(
                        checked = isCm,
                        onCheckedChange = { isCm = true },
                        label = "Cm",
                        weight = 1f
                    )
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
                    suffix = { Text(text = "Cm") },
                    isError = isError,
                    shape = MaterialTheme.shapes.large,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = actionType
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
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
                            if (textFt.isNotBlank()) valueFt = textFt
                        },
                        suffix = { Text(text = "Ft") },
                        isError = isError,
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = actionType
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
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
                            if (textIn.isNotBlank()) valueIn = textIn
                        },
                        suffix = { Text(text = "In") },
                        isError = isError,
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = actionType
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
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
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
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
                        value = valueFt.toFloat(),
                        onValueChange = {
                            valueFt = it.roundToInt().toString()
                            textFt = valueFt
                        },
                        valueRange = 1f..10f,
                        colors = SliderDefaults.colors(
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                            thumbColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Slider(
                        value = valueIn.toFloat(),
                        onValueChange = {
                            valueIn = it.roundToInt().toString()
                            textIn = valueIn
                        },
                        valueRange = 0f..12f,
                        colors = SliderDefaults.colors(
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                            thumbColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCard(
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface
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
                imageVector = icon,
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

@Suppress("MultipleContentEmitters", "DeferStateReads")
@Composable
fun PieChart(
    data: ImmutableMap<String, Long>,
    modifier: Modifier = Modifier,
    radiusOuter: Dp = 60.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = persistentListOf(
        md_theme_dark_primary,
        md_theme_dark_secondaryContainer,
        md_theme_dark_inversePrimary,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        DetailsPieChart(data = data, colors = colors)
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .graphicsLayer { rotationZ = animateRotation }
            ) {
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
    data: ImmutableMap<String, Long>,
    colors: ImmutableList<Color>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
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
    color: Color,
    modifier: Modifier = Modifier,
    height: Dp = 18.dp
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(bottom = 18.dp, start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = color, shape = RoundedCornerShape(5.dp))
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
    title: String,
    size: TextUnit,
    modifier: Modifier = Modifier,
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
fun BmiResultCard(state: BmiState, modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.padding(bottom = 10.dp, top = 12.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = "BMI Results",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomText(title = "BMI: ", size = 14.sp, fontWeight = FontWeight.Bold)
                CustomText(title = state.bmi, size = 14.sp, fontWeight = FontWeight.Bold)
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

fun getColorBasedOnInterpretation(interpretation: String): Color {
    return when (interpretation) {
        "Underweight" -> Color.Yellow
        "Overweight" -> Color.Yellow
        "Obese" -> Color.Red
        else -> md_theme_dark_onSurface
    }
}

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenScaffold(
    title: String,
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    subtitle: String? = null,
    icon: ImageVector? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                subtitle = {
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (showBack) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .size(40.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainerHigh,
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = arrow_back,
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                actions = {
                    if (icon != null) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
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

@Preview
@Composable
private fun CalculatorButtonPreview() {
    CalculatorPlusTheme {
        CalculatorButton(
            text = "1",
            color = MaterialTheme.colorScheme.primary,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun SliderWithTextPreview() {
    CalculatorPlusTheme {
        SliderWithText(
            title = "Investment",
            startNumber = 1000,
            endNumber = 100000,
            actionType = ImeAction.Done,
            prefix = "$",
            suffix = "",
            isError = false,
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun CustomCard2Preview() {
    CalculatorPlusTheme {
        CustomCard2(
            text = "Age",
            endText = "Years",
            isError = false,
            value = "25",
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun HeightSliderWithTextPreview() {
    CalculatorPlusTheme {
        HeightSliderWithText(
            startNumber = 100,
            endNumber = 250,
            actionType = ImeAction.Done,
            isError = false,
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun CustomCardPreview() {
    CalculatorPlusTheme {
        CustomCard(
            onClick = {},
            icon = calculator,
            title = "Calculator",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Preview
@Composable
private fun PieChartPreview() {
    CalculatorPlusTheme {
        Surface {
            PieChart(
                data = persistentMapOf(
                    "Principal" to 10000L,
                    "Interest" to 5000L
                )
            )
        }
    }
}

@Preview
@Composable
private fun CustomTextPreview() {
    CalculatorPlusTheme {
        Surface {
            CustomText(title = "Hello World", size = 16.sp)
        }
    }
}

@Preview
@Composable
private fun BmiResultCardPreview() {
    CalculatorPlusTheme {
        BmiResultCard(
            state = BmiState(
                bmi = "22.5",
                interpretation = "Normal"
            )
        )
    }
}

@Preview
@Composable
private fun ScreenScaffoldPreview() {
    CalculatorPlusTheme {
        ScreenScaffold(
            title = "Preview Title",
            showBack = true
        ) { paddingValues ->
            Box(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Content")
            }
        }
    }
}

package com.plus.calculatorplus.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val credit_card_clock: ImageVector
    get() {
        if (_credit_card_clock != null) {
            return _credit_card_clock!!
        }
        _credit_card_clock =
            ImageVector.Builder(
                name = "credit_card_clock",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(18.5f, 16.8f)
                        verticalLineTo(14.5f)
                        quadToRelative(0f, -0.2f, -0.15f, -0.35f)
                        reflectiveQuadTo(18f, 14f)
                        reflectiveQuadToRelative(-0.35f, 0.15f)
                        reflectiveQuadTo(17.5f, 14.5f)
                        verticalLineToRelative(2.27f)
                        quadToRelative(0f, 0.2f, 0.07f, 0.39f)
                        reflectiveQuadTo(17.8f, 17.5f)
                        lineToRelative(1.53f, 1.52f)
                        quadToRelative(0.15f, 0.15f, 0.35f, 0.15f)
                        reflectiveQuadToRelative(0.35f, -0.15f)
                        quadToRelative(0.15f, -0.15f, 0.15f, -0.35f)
                        reflectiveQuadTo(20.03f, 18.33f)
                        lineTo(18.5f, 16.8f)
                        close()
                        moveTo(18.03f, 22f)
                        quadToRelative(-2.1f, 0f, -3.56f, -1.45f)
                        reflectiveQuadTo(13f, 17f)
                        reflectiveQuadToRelative(1.46f, -3.55f)
                        reflectiveQuadTo(18.03f, 12f)
                        quadToRelative(2.07f, 0f, 3.52f, 1.46f)
                        quadTo(23f, 14.93f, 23f, 17f)
                        reflectiveQuadToRelative(-1.45f, 3.54f)
                        reflectiveQuadTo(18.03f, 22f)
                        close()
                        moveTo(4f, 20f)
                        quadTo(3.18f, 20f, 2.59f, 19.41f)
                        reflectiveQuadTo(2f, 18f)
                        verticalLineTo(6f)
                        quadTo(2f, 5.18f, 2.59f, 4.59f)
                        reflectiveQuadTo(4f, 4f)
                        horizontalLineTo(20f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        quadTo(22f, 5.18f, 22f, 6f)
                        verticalLineTo(9.82f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(21f, 10.83f)
                        reflectiveQuadTo(20.29f, 10.54f)
                        reflectiveQuadTo(20f, 9.82f)
                        verticalLineTo(8f)
                        horizontalLineTo(4f)
                        verticalLineToRelative(4f)
                        horizontalLineToRelative(7.5f)
                        quadToRelative(0.43f, 0f, 0.63f, 0.4f)
                        reflectiveQuadToRelative(-0.07f, 0.78f)
                        quadTo(11.5f, 14f, 11.2f, 14.98f)
                        reflectiveQuadTo(10.9f, 17f)
                        quadToRelative(0f, 0.48f, 0.06f, 0.94f)
                        reflectiveQuadToRelative(0.19f, 0.89f)
                        reflectiveQuadToRelative(-0.14f, 0.8f)
                        reflectiveQuadTo(10.33f, 20f)
                        horizontalLineTo(4f)
                        close()
                    }
                }
                .build()
        return _credit_card_clock!!
    }

private var _credit_card_clock: ImageVector? = null

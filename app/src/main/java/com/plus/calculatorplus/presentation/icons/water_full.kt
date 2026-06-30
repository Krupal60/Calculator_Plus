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
public val water_full: ImageVector
    get() {
        if (_water_full != null) {
            return _water_full!!
        }
        _water_full =
            ImageVector.Builder(
                name = "water_full",
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
                        moveTo(5.7f, 8.38f)
                        quadTo(6.98f, 7.7f, 8.34f, 7.35f)
                        reflectiveQuadTo(11.13f, 7f)
                        quadToRelative(0.75f, 0f, 1.49f, 0.1f)
                        reflectiveQuadToRelative(1.46f, 0.3f)
                        quadToRelative(1.25f, 0.35f, 1.91f, 0.48f)
                        reflectiveQuadTo(17.4f, 8f)
                        horizontalLineToRelative(0.92f)
                        lineTo(18.75f, 4f)
                        horizontalLineTo(5.25f)
                        lineTo(5.7f, 8.38f)
                        close()
                        moveTo(6.98f, 22f)
                        quadTo(6.2f, 22f, 5.64f, 21.5f)
                        reflectiveQuadTo(5f, 20.23f)
                        lineTo(3.28f, 4.47f)
                        quadTo(3.15f, 3.47f, 3.83f, 2.74f)
                        reflectiveQuadTo(5.5f, 2f)
                        horizontalLineToRelative(13f)
                        quadToRelative(1f, 0f, 1.68f, 0.74f)
                        reflectiveQuadToRelative(0.55f, 1.74f)
                        lineTo(19f, 20.23f)
                        quadTo(18.93f, 21f, 18.36f, 21.5f)
                        reflectiveQuadTo(17.03f, 22f)
                        horizontalLineTo(6.98f)
                        close()
                    }
                }
                .build()
        return _water_full!!
    }

private var _water_full: ImageVector? = null

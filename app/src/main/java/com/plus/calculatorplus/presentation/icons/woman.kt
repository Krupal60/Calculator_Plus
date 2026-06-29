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
val woman: ImageVector
    get() {
        if (_woman != null) {
            return _woman!!
        }
        _woman =
            ImageVector.Builder(
                name = "woman",
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
                        moveTo(10f, 21f)
                        verticalLineTo(16f)
                        horizontalLineTo(8.48f)
                        quadTo(7.95f, 16f, 7.65f, 15.56f)
                        reflectiveQuadTo(7.55f, 14.63f)
                        lineTo(10.05f, 8.3f)
                        quadTo(10.3f, 7.7f, 10.83f, 7.35f)
                        reflectiveQuadTo(12f, 7f)
                        reflectiveQuadToRelative(1.18f, 0.35f)
                        reflectiveQuadTo(13.95f, 8.3f)
                        lineToRelative(2.5f, 6.33f)
                        quadToRelative(0.2f, 0.5f, -0.1f, 0.94f)
                        reflectiveQuadTo(15.53f, 16f)
                        horizontalLineTo(14f)
                        verticalLineToRelative(5f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(13f, 22f)
                        horizontalLineTo(11f)
                        quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
                        quadTo(10f, 21.43f, 10f, 21f)
                        close()
                        moveTo(10.59f, 5.41f)
                        quadTo(10f, 4.82f, 10f, 4f)
                        quadTo(10f, 3.17f, 10.59f, 2.59f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(1.41f, 0.59f)
                        reflectiveQuadTo(14f, 4f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(10.59f, 5.41f)
                        close()
                    }
                }
                .build()
        return _woman!!
    }

private var _woman: ImageVector? = null

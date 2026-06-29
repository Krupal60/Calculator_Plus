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
val man: ImageVector
    get() {
        if (_man != null) {
            return _man!!
        }
        _man =
            ImageVector.Builder(
                name = "man",
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
                        verticalLineTo(15f)
                        horizontalLineTo(9f)
                        quadTo(8.58f, 15f, 8.29f, 14.71f)
                        reflectiveQuadTo(8f, 14f)
                        verticalLineTo(9f)
                        quadTo(8f, 8.17f, 8.59f, 7.59f)
                        reflectiveQuadTo(10f, 7f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(16f, 9f)
                        verticalLineToRelative(5f)
                        quadToRelative(0f, 0.42f, -0.29f, 0.71f)
                        reflectiveQuadTo(15f, 15f)
                        horizontalLineTo(14f)
                        verticalLineToRelative(6f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(13f, 22f)
                        horizontalLineTo(11f)
                        quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
                        quadTo(10f, 21.43f, 10f, 21f)
                        close()
                        moveTo(12f, 6f)
                        quadTo(11.18f, 6f, 10.59f, 5.41f)
                        reflectiveQuadTo(10f, 4f)
                        quadTo(10f, 3.17f, 10.59f, 2.59f)
                        reflectiveQuadTo(12f, 2f)
                        reflectiveQuadToRelative(1.41f, 0.59f)
                        reflectiveQuadTo(14f, 4f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(12f, 6f)
                        close()
                    }
                }
                .build()
        return _man!!
    }

private var _man: ImageVector? = null

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
public val trending_down: ImageVector
    get() {
        if (_trending_down != null) {
            return _trending_down!!
        }
        _trending_down =
            ImageVector.Builder(
                name = "trending_down",
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
                        moveTo(18.6f, 16f)
                        lineTo(13.4f, 10.85f)
                        lineToRelative(-2.57f, 2.57f)
                        quadTo(10.25f, 14f, 9.4f, 14f)
                        reflectiveQuadTo(7.98f, 13.43f)
                        lineTo(2.7f, 8.1f)
                        quadTo(2.43f, 7.82f, 2.41f, 7.41f)
                        reflectiveQuadTo(2.7f, 6.7f)
                        quadTo(2.98f, 6.43f, 3.4f, 6.43f)
                        reflectiveQuadTo(4.1f, 6.7f)
                        lineTo(9.4f, 12f)
                        lineTo(11.98f, 9.42f)
                        quadTo(12.55f, 8.85f, 13.4f, 8.85f)
                        reflectiveQuadToRelative(1.43f, 0.57f)
                        lineTo(20f, 14.6f)
                        verticalLineTo(13f)
                        quadToRelative(0f, -0.43f, 0.29f, -0.71f)
                        reflectiveQuadTo(21f, 12f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(22f, 13f)
                        verticalLineToRelative(4f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(21f, 18f)
                        horizontalLineTo(17f)
                        quadToRelative(-0.43f, 0f, -0.71f, -0.29f)
                        quadTo(16f, 17.43f, 16f, 17f)
                        reflectiveQuadToRelative(0.29f, -0.71f)
                        reflectiveQuadTo(17f, 16f)
                        horizontalLineToRelative(1.6f)
                        close()
                    }
                }
                .build()
        return _trending_down!!
    }

private var _trending_down: ImageVector? = null

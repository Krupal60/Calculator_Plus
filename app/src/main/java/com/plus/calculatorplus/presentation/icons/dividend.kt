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
val dividend: ImageVector
    get() {
        if (_dividend != null) {
            return _dividend!!
        }
        _dividend =
            ImageVector.Builder(
                name = "dividend",
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
                        moveTo(12.38f, 21.51f)
                        quadTo(12.55f, 21.45f, 12.7f, 21.3f)
                        lineToRelative(2.6f, -2.6f)
                        quadTo(15.58f, 18.43f, 15.58f, 18f)
                        reflectiveQuadTo(15.3f, 17.3f)
                        quadTo(15.03f, 17.02f, 14.6f, 17.02f)
                        reflectiveQuadTo(13.9f, 17.3f)
                        lineTo(13f, 18.2f)
                        verticalLineTo(16f)
                        quadToRelative(0f, -0.43f, -0.29f, -0.71f)
                        reflectiveQuadTo(12f, 15f)
                        reflectiveQuadToRelative(-0.71f, 0.29f)
                        reflectiveQuadTo(11f, 16f)
                        verticalLineToRelative(2.2f)
                        lineTo(10.1f, 17.3f)
                        quadTo(9.83f, 17.02f, 9.4f, 17.02f)
                        reflectiveQuadTo(8.7f, 17.3f)
                        reflectiveQuadTo(8.43f, 18f)
                        reflectiveQuadTo(8.7f, 18.7f)
                        lineToRelative(2.6f, 2.6f)
                        quadToRelative(0.15f, 0.15f, 0.32f, 0.21f)
                        reflectiveQuadTo(12f, 21.58f)
                        reflectiveQuadToRelative(0.38f, -0.06f)
                        close()
                        moveTo(12f, 11f)
                        quadToRelative(1.25f, 0f, 2.13f, -0.88f)
                        reflectiveQuadTo(15f, 8f)
                        reflectiveQuadTo(14.13f, 5.88f)
                        reflectiveQuadTo(12f, 5f)
                        reflectiveQuadTo(9.88f, 5.88f)
                        reflectiveQuadTo(9f, 8f)
                        reflectiveQuadToRelative(0.88f, 2.13f)
                        reflectiveQuadTo(12f, 11f)
                        close()
                        moveTo(5f, 14f)
                        quadTo(4.18f, 14f, 3.59f, 13.41f)
                        reflectiveQuadTo(3f, 12f)
                        verticalLineTo(4f)
                        quadTo(3f, 3.17f, 3.59f, 2.59f)
                        reflectiveQuadTo(5f, 2f)
                        horizontalLineTo(19f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 4f)
                        verticalLineToRelative(8f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 14f)
                        horizontalLineTo(5f)
                        close()
                        moveTo(5f, 12f)
                        horizontalLineTo(7f)
                        quadTo(7f, 11.18f, 6.41f, 10.59f)
                        reflectiveQuadTo(5f, 10f)
                        verticalLineToRelative(2f)
                        close()
                        moveToRelative(12f, 0f)
                        horizontalLineToRelative(2f)
                        verticalLineTo(10f)
                        quadToRelative(-0.82f, 0f, -1.41f, 0.59f)
                        quadTo(17f, 11.18f, 17f, 12f)
                        close()
                        moveTo(19f, 6f)
                        verticalLineTo(4f)
                        horizontalLineTo(17f)
                        quadToRelative(0f, 0.82f, 0.59f, 1.41f)
                        reflectiveQuadTo(19f, 6f)
                        close()
                        moveTo(5f, 6f)
                        quadTo(5.83f, 6f, 6.41f, 5.41f)
                        reflectiveQuadTo(7f, 4f)
                        horizontalLineTo(5f)
                        verticalLineTo(6f)
                        close()
                    }
                }
                .build()
        return _dividend!!
    }

private var _dividend: ImageVector? = null

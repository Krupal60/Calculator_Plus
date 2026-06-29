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
public val swap_horizontal_circle: ImageVector
    get() {
        if (_swap_horizontal_circle != null) {
            return _swap_horizontal_circle!!
        }
        _swap_horizontal_circle =
            ImageVector.Builder(
                name = "swap_horizontal_circle",
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
                        pathFillType = PathFillType.Companion.NonZero,
                    ) {
                        moveTo(8.85f, 15f)
                        horizontalLineTo(12f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        reflectiveQuadTo(13f, 14f)
                        reflectiveQuadTo(12.71f, 13.29f)
                        reflectiveQuadTo(12f, 13f)
                        horizontalLineTo(8.85f)
                        lineTo(9.73f, 12.1f)
                        quadTo(10f, 11.83f, 10f, 11.41f)
                        reflectiveQuadTo(9.7f, 10.7f)
                        quadTo(9.43f, 10.43f, 9f, 10.43f)
                        quadToRelative(-0.42f, 0f, -0.7f, 0.28f)
                        lineTo(5.7f, 13.3f)
                        quadTo(5.4f, 13.6f, 5.4f, 14f)
                        reflectiveQuadToRelative(0.3f, 0.7f)
                        lineToRelative(2.6f, 2.6f)
                        quadToRelative(0.28f, 0.27f, 0.69f, 0.29f)
                        reflectiveQuadTo(9.7f, 17.3f)
                        quadTo(9.98f, 17.02f, 9.99f, 16.61f)
                        reflectiveQuadTo(9.73f, 15.9f)
                        lineTo(8.85f, 15f)
                        close()
                        moveToRelative(6.3f, -4f)
                        lineToRelative(-0.88f, 0.9f)
                        quadTo(14f, 12.18f, 14f, 12.59f)
                        reflectiveQuadToRelative(0.3f, 0.71f)
                        quadToRelative(0.28f, 0.28f, 0.7f, 0.28f)
                        reflectiveQuadTo(15.7f, 13.3f)
                        lineToRelative(2.6f, -2.6f)
                        quadTo(18.6f, 10.4f, 18.6f, 10f)
                        reflectiveQuadTo(18.3f, 9.3f)
                        lineTo(15.7f, 6.7f)
                        quadTo(15.43f, 6.43f, 15.01f, 6.41f)
                        reflectiveQuadTo(14.3f, 6.7f)
                        quadTo(14.03f, 6.97f, 14.01f, 7.39f)
                        reflectiveQuadTo(14.28f, 8.1f)
                        lineTo(15.15f, 9f)
                        horizontalLineTo(12f)
                        quadTo(11.58f, 9f, 11.29f, 9.29f)
                        reflectiveQuadTo(11f, 10f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(12f, 11f)
                        horizontalLineToRelative(3.15f)
                        close()
                        moveTo(12f, 22f)
                        quadTo(9.93f, 22f, 8.1f, 21.21f)
                        quadTo(6.28f, 20.43f, 4.93f, 19.08f)
                        quadTo(3.58f, 17.73f, 2.79f, 15.9f)
                        reflectiveQuadTo(2f, 12f)
                        quadTo(2f, 9.92f, 2.79f, 8.1f)
                        quadTo(3.58f, 6.27f, 4.93f, 4.93f)
                        quadTo(6.28f, 3.57f, 8.1f, 2.79f)
                        quadTo(9.93f, 2f, 12f, 2f)
                        reflectiveQuadToRelative(3.9f, 0.79f)
                        reflectiveQuadToRelative(3.17f, 2.14f)
                        quadToRelative(1.35f, 1.35f, 2.14f, 3.17f)
                        quadTo(22f, 9.92f, 22f, 12f)
                        reflectiveQuadToRelative(-0.79f, 3.9f)
                        reflectiveQuadToRelative(-2.14f, 3.17f)
                        quadToRelative(-1.35f, 1.35f, -3.17f, 2.14f)
                        reflectiveQuadTo(12f, 22f)
                        close()
                    }
                }
                .build()
        return _swap_horizontal_circle!!
    }

private var _swap_horizontal_circle: ImageVector? = null

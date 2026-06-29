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
val bmr: ImageVector
    get() {
        if (_bmr != null) {
            return _bmr!!
        }
        _bmr =
            ImageVector.Builder(
                name = "bmr",
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
                        moveTo(7f, 14f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 0.43f, 0.29f, 0.71f)
                        reflectiveQuadTo(8f, 18f)
                        horizontalLineTo(9f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(10f, 17.43f, 10f, 17f)
                        verticalLineTo(14f)
                        quadToRelative(0.28f, -0.28f, 0.64f, -0.44f)
                        reflectiveQuadTo(11f, 13f)
                        verticalLineTo(11.5f)
                        quadToRelative(0f, -0.83f, -0.59f, -1.41f)
                        reflectiveQuadTo(9f, 9.5f)
                        horizontalLineTo(8f)
                        quadToRelative(-0.82f, 0f, -1.41f, 0.59f)
                        quadTo(6f, 10.68f, 6f, 11.5f)
                        verticalLineTo(13f)
                        quadToRelative(0f, 0.4f, 0.36f, 0.56f)
                        reflectiveQuadTo(7f, 14f)
                        close()
                        moveTo(9.39f, 8.14f)
                        quadTo(9.75f, 7.77f, 9.75f, 7.25f)
                        quadTo(9.75f, 6.72f, 9.39f, 6.36f)
                        reflectiveQuadTo(8.5f, 6f)
                        reflectiveQuadTo(7.61f, 6.36f)
                        quadTo(7.25f, 6.72f, 7.25f, 7.25f)
                        quadToRelative(0f, 0.52f, 0.36f, 0.89f)
                        reflectiveQuadTo(8.5f, 8.5f)
                        quadToRelative(0.53f, 0f, 0.89f, -0.36f)
                        close()
                        moveTo(13.9f, 11f)
                        horizontalLineToRelative(3.2f)
                        quadToRelative(0.3f, 0f, 0.44f, -0.26f)
                        quadToRelative(0.14f, -0.26f, -0.01f, -0.51f)
                        lineTo(15.93f, 7.68f)
                        quadTo(15.78f, 7.43f, 15.5f, 7.43f)
                        reflectiveQuadTo(15.08f, 7.68f)
                        lineToRelative(-1.6f, 2.55f)
                        quadToRelative(-0.15f, 0.25f, -0.01f, 0.51f)
                        reflectiveQuadTo(13.9f, 11f)
                        close()
                        moveToRelative(2.03f, 5.32f)
                        lineToRelative(1.6f, -2.55f)
                        quadToRelative(0.15f, -0.25f, 0.01f, -0.51f)
                        reflectiveQuadTo(17.1f, 13f)
                        horizontalLineTo(13.9f)
                        quadToRelative(-0.3f, 0f, -0.44f, 0.26f)
                        reflectiveQuadToRelative(0.01f, 0.51f)
                        lineToRelative(1.6f, 2.55f)
                        quadToRelative(0.15f, 0.25f, 0.42f, 0.25f)
                        quadToRelative(0.28f, 0f, 0.43f, -0.25f)
                        close()
                        moveTo(5f, 21f)
                        quadTo(4.18f, 21f, 3.59f, 20.41f)
                        reflectiveQuadTo(3f, 19f)
                        verticalLineTo(5f)
                        quadTo(3f, 4.17f, 3.59f, 3.59f)
                        reflectiveQuadTo(5f, 3f)
                        horizontalLineTo(19f)
                        quadToRelative(0.83f, 0f, 1.41f, 0.59f)
                        reflectiveQuadTo(21f, 5f)
                        verticalLineTo(19f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(19f, 21f)
                        horizontalLineTo(5f)
                        close()
                    }
                }
                .build()
        return _bmr!!
    }

private var _bmr: ImageVector? = null

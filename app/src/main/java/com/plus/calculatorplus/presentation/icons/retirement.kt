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
val retirement: ImageVector
    get() {
        if (_retirement != null) {
            return _retirement!!
        }
        _retirement =
            ImageVector.Builder(
                name = "retirement",
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
                        moveTo(13.08f, 20.18f)
                        lineToRelative(0.78f, -7.45f)
                        quadTo(12.43f, 12.35f, 11.7f, 11.74f)
                        quadTo(10.98f, 11.13f, 10.25f, 10.4f)
                        lineTo(7.88f, 12.75f)
                        lineTo(9.7f, 14.58f)
                        quadToRelative(0.15f, 0.15f, 0.22f, 0.34f)
                        reflectiveQuadTo(10f, 15.3f)
                        verticalLineTo(20f)
                        quadToRelative(0f, 0.43f, -0.29f, 0.71f)
                        reflectiveQuadTo(9f, 21f)
                        quadTo(8.58f, 21f, 8.29f, 20.71f)
                        quadTo(8f, 20.43f, 8f, 20f)
                        verticalLineTo(15.75f)
                        lineTo(7.23f, 15.05f)
                        lineToRelative(0.13f, 0.93f)
                        quadToRelative(0.02f, 0.2f, -0.03f, 0.4f)
                        reflectiveQuadTo(7.15f, 16.73f)
                        lineTo(4.33f, 20.35f)
                        quadTo(4.08f, 20.68f, 3.66f, 20.73f)
                        reflectiveQuadTo(2.93f, 20.5f)
                        reflectiveQuadTo(2.55f, 19.83f)
                        quadTo(2.5f, 19.43f, 2.78f, 19.1f)
                        lineTo(5.3f, 15.85f)
                        lineTo(3.88f, 13.05f)
                        quadTo(3.68f, 12.63f, 3.65f, 11.99f)
                        reflectiveQuadTo(4.08f, 10.9f)
                        lineTo(7.43f, 7.6f)
                        quadTo(7.73f, 7.3f, 8.09f, 7.15f)
                        reflectiveQuadTo(8.83f, 7f)
                        quadToRelative(0.6f, 0f, 0.95f, 0.22f)
                        reflectiveQuadToRelative(0.47f, 0.35f)
                        lineToRelative(2f, 1.97f)
                        quadToRelative(0.68f, 0.68f, 1.65f, 1.06f)
                        reflectiveQuadTo(16f, 11f)
                        horizontalLineToRelative(1.65f)
                        quadToRelative(0.57f, 0f, 1f, 0.39f)
                        reflectiveQuadToRelative(0.47f, 0.96f)
                        lineToRelative(0.65f, 6.35f)
                        quadToRelative(0.32f, 0.2f, 0.52f, 0.54f)
                        reflectiveQuadTo(20.5f, 20f)
                        quadToRelative(0f, 0.63f, -0.44f, 1.06f)
                        reflectiveQuadTo(19f, 21.5f)
                        reflectiveQuadTo(17.93f, 21.06f)
                        reflectiveQuadTo(17.48f, 20f)
                        quadToRelative(0f, -0.43f, 0.2f, -0.76f)
                        reflectiveQuadTo(18.23f, 18.7f)
                        lineTo(18.1f, 17.5f)
                        horizontalLineTo(14.85f)
                        lineToRelative(-0.28f, 2.82f)
                        quadTo(14.53f, 20.6f, 14.31f, 20.8f)
                        reflectiveQuadTo(13.83f, 21f)
                        quadToRelative(-0.35f, 0f, -0.58f, -0.24f)
                        quadTo(13.03f, 20.53f, 13.08f, 20.18f)
                        close()
                        moveTo(12.5f, 7.5f)
                        quadToRelative(-0.82f, 0f, -1.41f, -0.59f)
                        reflectiveQuadTo(10.5f, 5.5f)
                        quadToRelative(0f, -0.83f, 0.59f, -1.41f)
                        reflectiveQuadTo(12.5f, 3.5f)
                        reflectiveQuadToRelative(1.41f, 0.59f)
                        reflectiveQuadTo(14.5f, 5.5f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(12.5f, 7.5f)
                        close()
                        moveTo(15f, 16f)
                        horizontalLineToRelative(2.95f)
                        lineTo(17.6f, 12.5f)
                        horizontalLineTo(15.38f)
                        lineTo(15f, 16f)
                        close()
                    }
                }
                .build()
        return _retirement!!
    }

private var _retirement: ImageVector? = null

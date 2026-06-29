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
val calculator: ImageVector
    get() {
        if (_calculator != null) {
            return _calculator!!
        }
        _calculator =
            ImageVector.Builder(
                name = "calculator",
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
                        moveTo(8f, 16f)
                        verticalLineToRelative(1.25f)
                        quadToRelative(0f, 0.32f, 0.21f, 0.54f)
                        reflectiveQuadTo(8.75f, 18f)
                        quadToRelative(0.33f, 0f, 0.54f, -0.21f)
                        reflectiveQuadTo(9.5f, 17.25f)
                        verticalLineTo(16f)
                        horizontalLineToRelative(1.25f)
                        quadToRelative(0.33f, 0f, 0.54f, -0.21f)
                        reflectiveQuadTo(11.5f, 15.25f)
                        reflectiveQuadTo(11.29f, 14.71f)
                        reflectiveQuadTo(10.75f, 14.5f)
                        horizontalLineTo(9.5f)
                        verticalLineTo(13.25f)
                        quadToRelative(0f, -0.33f, -0.21f, -0.54f)
                        quadTo(9.08f, 12.5f, 8.75f, 12.5f)
                        quadToRelative(-0.32f, 0f, -0.54f, 0.21f)
                        quadTo(8f, 12.93f, 8f, 13.25f)
                        verticalLineTo(14.5f)
                        horizontalLineTo(6.75f)
                        quadToRelative(-0.32f, 0f, -0.54f, 0.21f)
                        reflectiveQuadTo(6f, 15.25f)
                        reflectiveQuadToRelative(0.21f, 0.54f)
                        reflectiveQuadTo(6.75f, 16f)
                        horizontalLineTo(8f)
                        close()
                        moveToRelative(5.75f, 1.25f)
                        horizontalLineToRelative(3.5f)
                        quadToRelative(0.32f, 0f, 0.54f, -0.21f)
                        reflectiveQuadTo(18f, 16.5f)
                        reflectiveQuadTo(17.79f, 15.96f)
                        reflectiveQuadTo(17.25f, 15.75f)
                        horizontalLineToRelative(-3.5f)
                        quadToRelative(-0.32f, 0f, -0.54f, 0.21f)
                        reflectiveQuadTo(13f, 16.5f)
                        reflectiveQuadToRelative(0.21f, 0.54f)
                        reflectiveQuadToRelative(0.54f, 0.21f)
                        close()
                        moveToRelative(0f, -2.5f)
                        horizontalLineToRelative(3.5f)
                        quadToRelative(0.32f, 0f, 0.54f, -0.21f)
                        reflectiveQuadTo(18f, 14f)
                        reflectiveQuadTo(17.79f, 13.46f)
                        reflectiveQuadTo(17.25f, 13.25f)
                        horizontalLineToRelative(-3.5f)
                        quadToRelative(-0.32f, 0f, -0.54f, 0.21f)
                        quadTo(13f, 13.68f, 13f, 14f)
                        reflectiveQuadToRelative(0.21f, 0.54f)
                        reflectiveQuadToRelative(0.54f, 0.21f)
                        close()
                        moveTo(7f, 9.2f)
                        horizontalLineToRelative(3.5f)
                        quadToRelative(0.33f, 0f, 0.54f, -0.21f)
                        reflectiveQuadTo(11.25f, 8.45f)
                        reflectiveQuadTo(11.04f, 7.91f)
                        reflectiveQuadTo(10.5f, 7.7f)
                        horizontalLineTo(7f)
                        quadTo(6.68f, 7.7f, 6.46f, 7.91f)
                        quadTo(6.25f, 8.13f, 6.25f, 8.45f)
                        reflectiveQuadTo(6.46f, 8.99f)
                        reflectiveQuadTo(7f, 9.2f)
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
                        moveTo(15.5f, 9.55f)
                        lineToRelative(0.88f, 0.88f)
                        quadToRelative(0.23f, 0.22f, 0.53f, 0.22f)
                        reflectiveQuadToRelative(0.52f, -0.22f)
                        quadToRelative(0.2f, -0.2f, 0.21f, -0.51f)
                        reflectiveQuadTo(17.45f, 9.38f)
                        lineTo(16.55f, 8.45f)
                        lineTo(17.43f, 7.57f)
                        quadTo(17.65f, 7.35f, 17.65f, 7.05f)
                        reflectiveQuadTo(17.43f, 6.52f)
                        reflectiveQuadTo(16.9f, 6.3f)
                        reflectiveQuadTo(16.38f, 6.52f)
                        lineTo(15.5f, 7.4f)
                        lineTo(14.63f, 6.52f)
                        quadTo(14.4f, 6.3f, 14.1f, 6.3f)
                        reflectiveQuadTo(13.58f, 6.52f)
                        reflectiveQuadTo(13.35f, 7.05f)
                        reflectiveQuadToRelative(0.22f, 0.52f)
                        lineToRelative(0.88f, 0.88f)
                        lineToRelative(-0.9f, 0.93f)
                        quadTo(13.35f, 9.6f, 13.35f, 9.9f)
                        reflectiveQuadToRelative(0.22f, 0.53f)
                        reflectiveQuadToRelative(0.53f, 0.22f)
                        reflectiveQuadToRelative(0.52f, -0.22f)
                        lineTo(15.5f, 9.55f)
                        close()
                    }
                }
                .build()
        return _calculator!!
    }

private var _calculator: ImageVector? = null

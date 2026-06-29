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
public val savings: ImageVector
    get() {
        if (_savings != null) {
            return _savings!!
        }
        _savings =
            ImageVector.Builder(
                name = "savings",
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
                        moveTo(5.93f, 21f)
                        quadTo(5.35f, 21f, 4.81f, 20.6f)
                        quadTo(4.28f, 20.2f, 4.1f, 19.63f)
                        quadTo(3.48f, 17.52f, 3.08f, 15.99f)
                        reflectiveQuadTo(2.44f, 13.29f)
                        reflectiveQuadTo(2.1f, 11.23f)
                        reflectiveQuadTo(2f, 9.5f)
                        quadTo(2f, 7.2f, 3.6f, 5.6f)
                        reflectiveQuadTo(7.5f, 4f)
                        horizontalLineToRelative(5f)
                        quadTo(13.18f, 3.1f, 14.21f, 2.55f)
                        reflectiveQuadTo(16.5f, 2f)
                        quadToRelative(0.63f, 0f, 1.06f, 0.44f)
                        reflectiveQuadTo(18f, 3.5f)
                        quadToRelative(0f, 0.15f, -0.04f, 0.3f)
                        reflectiveQuadTo(17.88f, 4.07f)
                        quadToRelative(-0.1f, 0.27f, -0.19f, 0.55f)
                        reflectiveQuadToRelative(-0.14f, 0.6f)
                        lineTo(19.83f, 7.5f)
                        horizontalLineTo(21f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(22f, 8.5f)
                        verticalLineToRelative(5.25f)
                        quadToRelative(0f, 0.32f, -0.19f, 0.57f)
                        reflectiveQuadTo(21.3f, 14.7f)
                        lineToRelative(-2.13f, 0.7f)
                        lineToRelative(-1.25f, 4.17f)
                        quadToRelative(-0.2f, 0.65f, -0.73f, 1.04f)
                        quadTo(16.68f, 21f, 16f, 21f)
                        horizontalLineTo(14f)
                        quadToRelative(-0.82f, 0f, -1.41f, -0.59f)
                        reflectiveQuadTo(12f, 19f)
                        horizontalLineTo(10f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        quadTo(8.83f, 21f, 8f, 21f)
                        horizontalLineTo(5.93f)
                        close()
                        moveTo(16.71f, 10.71f)
                        quadTo(17f, 10.43f, 17f, 10f)
                        quadTo(17f, 9.57f, 16.71f, 9.29f)
                        reflectiveQuadTo(16f, 9f)
                        reflectiveQuadTo(15.29f, 9.29f)
                        reflectiveQuadTo(15f, 10f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        reflectiveQuadTo(16f, 11f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        close()
                        moveTo(12f, 9f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        reflectiveQuadTo(13f, 8f)
                        quadTo(13f, 7.57f, 12.71f, 7.29f)
                        reflectiveQuadTo(12f, 7f)
                        horizontalLineTo(9f)
                        quadTo(8.58f, 7f, 8.29f, 7.29f)
                        reflectiveQuadTo(8f, 8f)
                        quadTo(8f, 8.42f, 8.29f, 8.71f)
                        quadTo(8.58f, 9f, 9f, 9f)
                        horizontalLineToRelative(3f)
                        close()
                    }
                }
                .build()
        return _savings!!
    }

private var _savings: ImageVector? = null

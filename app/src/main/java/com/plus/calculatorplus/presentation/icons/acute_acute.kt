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
public val acute: ImageVector
    get() {
        if (_acute != null) {
            return _acute!!
        }
        _acute =
            ImageVector.Builder(
                name = "acute",
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
                        moveTo(15f, 20f)
                        quadTo(11.65f, 20f, 9.33f, 17.68f)
                        reflectiveQuadTo(7f, 12f)
                        quadTo(7f, 8.67f, 9.33f, 6.34f)
                        reflectiveQuadTo(15f, 4f)
                        quadToRelative(3.32f, 0f, 5.66f, 2.34f)
                        reflectiveQuadTo(23f, 12f)
                        quadToRelative(0f, 3.35f, -2.34f, 5.68f)
                        reflectiveQuadTo(15f, 20f)
                        close()
                        moveToRelative(1f, -8.4f)
                        verticalLineTo(9f)
                        quadTo(16f, 8.57f, 15.71f, 8.29f)
                        reflectiveQuadTo(15f, 8f)
                        reflectiveQuadTo(14.29f, 8.29f)
                        reflectiveQuadTo(14f, 9f)
                        verticalLineToRelative(3.02f)
                        quadToRelative(0f, 0.2f, 0.09f, 0.39f)
                        reflectiveQuadToRelative(0.21f, 0.31f)
                        lineTo(16.58f, 15f)
                        quadToRelative(0.3f, 0.3f, 0.71f, 0.3f)
                        reflectiveQuadTo(18f, 15f)
                        reflectiveQuadToRelative(0.3f, -0.71f)
                        reflectiveQuadTo(18f, 13.58f)
                        lineTo(16f, 11.6f)
                        close()
                        moveTo(3f, 9f)
                        quadTo(2.58f, 9f, 2.29f, 8.71f)
                        reflectiveQuadTo(2f, 8f)
                        quadTo(2f, 7.57f, 2.29f, 7.29f)
                        reflectiveQuadTo(3f, 7f)
                        horizontalLineTo(5f)
                        quadTo(5.43f, 7f, 5.71f, 7.29f)
                        reflectiveQuadTo(6f, 8f)
                        quadTo(6f, 8.42f, 5.71f, 8.71f)
                        reflectiveQuadTo(5f, 9f)
                        horizontalLineTo(3f)
                        close()
                        moveTo(2f, 13f)
                        quadTo(1.58f, 13f, 1.29f, 12.71f)
                        quadTo(1f, 12.43f, 1f, 12f)
                        reflectiveQuadTo(1.29f, 11.29f)
                        reflectiveQuadTo(2f, 11f)
                        horizontalLineTo(5f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(6f, 12f)
                        reflectiveQuadTo(5.71f, 12.71f)
                        reflectiveQuadTo(5f, 13f)
                        horizontalLineTo(2f)
                        close()
                        moveToRelative(1f, 4f)
                        quadTo(2.58f, 17f, 2.29f, 16.71f)
                        quadTo(2f, 16.43f, 2f, 16f)
                        reflectiveQuadTo(2.29f, 15.29f)
                        reflectiveQuadTo(3f, 15f)
                        horizontalLineTo(5f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(6f, 16f)
                        reflectiveQuadTo(5.71f, 16.71f)
                        reflectiveQuadTo(5f, 17f)
                        horizontalLineTo(3f)
                        close()
                    }
                }
                .build()
        return _acute!!
    }

private var _acute: ImageVector? = null

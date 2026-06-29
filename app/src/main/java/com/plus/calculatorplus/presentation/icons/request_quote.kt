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
public val request_quote: ImageVector
    get() {
        if (_request_quote != null) {
            return _request_quote!!
        }
        _request_quote =
            ImageVector.Builder(
                name = "request_quote",
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
                        moveTo(11f, 18f)
                        verticalLineToRelative(0.5f)
                        quadToRelative(0f, 0.2f, 0.15f, 0.35f)
                        reflectiveQuadTo(11.5f, 19f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.2f, 0f, 0.35f, -0.15f)
                        reflectiveQuadTo(13f, 18.5f)
                        verticalLineTo(18f)
                        horizontalLineToRelative(1f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(15f, 17.43f, 15f, 17f)
                        verticalLineTo(14f)
                        quadToRelative(0f, -0.43f, -0.29f, -0.71f)
                        reflectiveQuadTo(14f, 13f)
                        horizontalLineTo(11f)
                        verticalLineTo(12f)
                        horizontalLineToRelative(3f)
                        quadToRelative(0.43f, 0f, 0.71f, -0.29f)
                        quadTo(15f, 11.43f, 15f, 11f)
                        reflectiveQuadTo(14.71f, 10.29f)
                        reflectiveQuadTo(14f, 10f)
                        horizontalLineTo(13f)
                        verticalLineTo(9.5f)
                        quadTo(13f, 9.3f, 12.85f, 9.15f)
                        reflectiveQuadTo(12.5f, 9f)
                        horizontalLineToRelative(-1f)
                        quadTo(11.3f, 9f, 11.15f, 9.15f)
                        reflectiveQuadTo(11f, 9.5f)
                        verticalLineTo(10f)
                        horizontalLineTo(10f)
                        quadTo(9.58f, 10f, 9.29f, 10.29f)
                        reflectiveQuadTo(9f, 11f)
                        verticalLineToRelative(3f)
                        quadToRelative(0f, 0.42f, 0.29f, 0.71f)
                        quadTo(9.58f, 15f, 10f, 15f)
                        horizontalLineToRelative(3f)
                        verticalLineToRelative(1f)
                        horizontalLineTo(10f)
                        quadTo(9.58f, 16f, 9.29f, 16.29f)
                        reflectiveQuadTo(9f, 17f)
                        reflectiveQuadToRelative(0.29f, 0.71f)
                        quadTo(9.58f, 18f, 10f, 18f)
                        horizontalLineToRelative(1f)
                        close()
                        moveTo(6f, 22f)
                        quadTo(5.18f, 22f, 4.59f, 21.41f)
                        reflectiveQuadTo(4f, 20f)
                        verticalLineTo(4f)
                        quadTo(4f, 3.17f, 4.59f, 2.59f)
                        reflectiveQuadTo(6f, 2f)
                        horizontalLineToRelative(9f)
                        lineToRelative(5f, 5f)
                        verticalLineTo(20f)
                        quadToRelative(0f, 0.82f, -0.59f, 1.41f)
                        reflectiveQuadTo(18f, 22f)
                        horizontalLineTo(6f)
                        close()
                        moveTo(14f, 4f)
                        verticalLineTo(7f)
                        quadToRelative(0f, 0.43f, 0.29f, 0.71f)
                        reflectiveQuadTo(15f, 8f)
                        horizontalLineToRelative(3f)
                        lineTo(14f, 4f)
                        close()
                    }
                }
                .build()
        return _request_quote!!
    }

private var _request_quote: ImageVector? = null

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
public val price_check: ImageVector
    get() {
        if (_price_check != null) {
            return _price_check!!
        }
        _price_check =
            ImageVector.Builder(
                name = "price_check",
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
                        moveTo(13.95f, 18.18f)
                        lineTo(18.9f, 13.23f)
                        quadToRelative(0.27f, -0.28f, 0.7f, -0.28f)
                        reflectiveQuadToRelative(0.7f, 0.28f)
                        reflectiveQuadToRelative(0.28f, 0.7f)
                        reflectiveQuadToRelative(-0.28f, 0.7f)
                        lineToRelative(-5.65f, 5.65f)
                        quadToRelative(-0.3f, 0.3f, -0.7f, 0.3f)
                        reflectiveQuadToRelative(-0.7f, -0.3f)
                        lineTo(10.4f, 17.43f)
                        quadToRelative(-0.28f, -0.28f, -0.28f, -0.7f)
                        reflectiveQuadToRelative(0.28f, -0.7f)
                        reflectiveQuadToRelative(0.7f, -0.27f)
                        reflectiveQuadToRelative(0.7f, 0.27f)
                        lineToRelative(2.15f, 2.15f)
                        close()
                        moveTo(6.5f, 13.98f)
                        horizontalLineTo(5f)
                        quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
                        reflectiveQuadTo(4f, 12.98f)
                        reflectiveQuadTo(4.29f, 12.26f)
                        reflectiveQuadTo(5f, 11.98f)
                        horizontalLineTo(9f)
                        verticalLineToRelative(-2f)
                        horizontalLineTo(5f)
                        quadTo(4.58f, 9.98f, 4.29f, 9.69f)
                        reflectiveQuadTo(4f, 8.98f)
                        verticalLineToRelative(-4f)
                        quadTo(4f, 4.55f, 4.29f, 4.26f)
                        quadTo(4.58f, 3.97f, 5f, 3.97f)
                        horizontalLineTo(6.5f)
                        quadTo(6.5f, 3.55f, 6.79f, 3.26f)
                        quadTo(7.08f, 2.97f, 7.5f, 2.97f)
                        reflectiveQuadTo(8.21f, 3.26f)
                        reflectiveQuadTo(8.5f, 3.97f)
                        horizontalLineTo(10f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(11f, 4.97f)
                        reflectiveQuadTo(10.71f, 5.69f)
                        quadTo(10.43f, 5.97f, 10f, 5.97f)
                        horizontalLineTo(6f)
                        verticalLineToRelative(2f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.43f, 0f, 0.71f, 0.29f)
                        reflectiveQuadTo(11f, 8.98f)
                        verticalLineToRelative(4f)
                        quadToRelative(0f, 0.42f, -0.29f, 0.71f)
                        reflectiveQuadTo(10f, 13.98f)
                        horizontalLineTo(8.5f)
                        quadToRelative(0f, 0.42f, -0.29f, 0.71f)
                        reflectiveQuadTo(7.5f, 14.98f)
                        quadToRelative(-0.42f, 0f, -0.71f, -0.29f)
                        reflectiveQuadTo(6.5f, 13.98f)
                        close()
                    }
                }
                .build()
        return _price_check!!
    }

private var _price_check: ImageVector? = null

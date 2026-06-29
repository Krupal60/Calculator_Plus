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
public val monitor_weight: ImageVector
    get() {
        if (_monitor_weight != null) {
            return _monitor_weight!!
        }
        _monitor_weight =
            ImageVector.Builder(
                name = "monitor_weight",
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
                        moveTo(12f, 12f)
                        quadToRelative(1.25f, 0f, 2.13f, -0.88f)
                        reflectiveQuadTo(15f, 9f)
                        reflectiveQuadTo(14.13f, 6.88f)
                        reflectiveQuadTo(12f, 6f)
                        reflectiveQuadTo(9.88f, 6.88f)
                        reflectiveQuadTo(9f, 9f)
                        reflectiveQuadToRelative(0.88f, 2.13f)
                        reflectiveQuadTo(12f, 12f)
                        close()
                        moveTo(10.15f, 9.35f)
                        quadTo(10f, 9.2f, 10f, 9f)
                        reflectiveQuadTo(10.15f, 8.65f)
                        reflectiveQuadTo(10.5f, 8.5f)
                        reflectiveQuadToRelative(0.35f, 0.15f)
                        reflectiveQuadTo(11f, 9f)
                        reflectiveQuadTo(10.85f, 9.35f)
                        reflectiveQuadTo(10.5f, 9.5f)
                        reflectiveQuadTo(10.15f, 9.35f)
                        close()
                        moveToRelative(1.5f, 0f)
                        quadTo(11.5f, 9.2f, 11.5f, 9f)
                        reflectiveQuadTo(11.65f, 8.65f)
                        reflectiveQuadTo(12f, 8.5f)
                        reflectiveQuadToRelative(0.35f, 0.15f)
                        reflectiveQuadTo(12.5f, 9f)
                        reflectiveQuadTo(12.35f, 9.35f)
                        reflectiveQuadTo(12f, 9.5f)
                        reflectiveQuadTo(11.65f, 9.35f)
                        close()
                        moveToRelative(1.5f, 0f)
                        quadTo(13f, 9.2f, 13f, 9f)
                        reflectiveQuadTo(13.15f, 8.65f)
                        reflectiveQuadTo(13.5f, 8.5f)
                        reflectiveQuadToRelative(0.35f, 0.15f)
                        reflectiveQuadTo(14f, 9f)
                        reflectiveQuadTo(13.85f, 9.35f)
                        reflectiveQuadTo(13.5f, 9.5f)
                        reflectiveQuadTo(13.15f, 9.35f)
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
        return _monitor_weight!!
    }

private var _monitor_weight: ImageVector? = null

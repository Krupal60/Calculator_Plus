package com.plus.calculatorplus.presentation.util

import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.plus.calculatorplus.presentation.util.DpHeightSizeClasses.Compact
import com.plus.calculatorplus.presentation.util.DpHeightSizeClasses.Expanded
import com.plus.calculatorplus.presentation.util.DpHeightSizeClasses.Medium
import com.plus.calculatorplus.presentation.util.DpWidthSizeClasses.Compact
import com.plus.calculatorplus.presentation.util.DpWidthSizeClasses.Expanded
import com.plus.calculatorplus.presentation.util.DpWidthSizeClasses.ExtraLarge
import com.plus.calculatorplus.presentation.util.DpWidthSizeClasses.Large
import com.plus.calculatorplus.presentation.util.DpWidthSizeClasses.Medium


/**
 * The set of width size classes in DP. These values are the lower bounds for the corresponding size
 * classes.
 *
 * @see DpWidthSizeClasses
 */
internal val WindowSizeClass.Companion.WidthSizeClasses
    get() = DpWidthSizeClasses

/**
 * The set of height size classes in DP. These values are the lower bounds for the corresponding
 * size classes.
 *
 * @see DpHeightSizeClasses
 */
internal val WindowSizeClass.Companion.HeightSizeClasses
    get() = DpHeightSizeClasses

/**
 * The set of width size classes in DP. These values are the lower bounds for the corresponding size
 * classes.
 *
 * This object defines different width size classes, including:
 * - [Compact]: Represents the smallest width size class, starting at 0 dp.
 * - [Medium]: Represents a medium width size class, starting at 600 dp.
 * - [Expanded]: Represents an expanded width size class, starting at 840 dp.
 * - [Large]: Represents a large width size class, starting at 1200 dp.
 * - [ExtraLarge]: Represents an extremely large width size class, starting at 1600 dp.
 *
 * These values are used to define breakpoints for adaptive layouts, and are intended to align with
 * the window size class definitions.
 *
 * @see WindowSizeClass
 */
@Suppress("PrimitiveInCollection")
internal object DpWidthSizeClasses {
    /**
     * The lower bound for the Compact width size class. By default, any window width which is at
     * least this value and less than [Medium] will be considered [Compact].
     */
    val Compact = 0.dp

    /**
     * The lower bound for the Medium width size class. By default, any window width which is at
     * least this value and less than [Expanded] will be considered [Medium].
     *
     * @see WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
     */
    val Medium = WIDTH_DP_MEDIUM_LOWER_BOUND.dp

    /**
     * The lower bound for the Expanded width size class. By default, any window width which is at
     * least this value will be considered [Expanded]; or in the V2 definition of the default width
     * size classes, any window width which is at least this value and less than [Large] will be
     * considered [Expanded].
     *
     * @see WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
     */
    val Expanded = WIDTH_DP_EXPANDED_LOWER_BOUND.dp

    /**
     * The lower bound for the Large width size class. With the V2 definition of the default width
     * size, any window width which is at least this value and less than [ExtraLarge] will be
     * considered [Large].
     */
    // TODO(conradchen): Move to window-core definition when it goes to 1.5 stable
    val Large = 1200.dp

    /**
     * The lower bound for the Extra-Large width size class. With the V2 definition of the default
     * width size, any window width which is at least this value will be considered [ExtraLarge].
     */
    // TODO(conradchen): Move to window-core definition when it goes to 1.5 stable
    val ExtraLarge = 1600.dp
}

/**
 * The set of height size classes in DP. These values are the lower bounds for the corresponding
 * size classes.
 *
 * This object defines different height size classes, including:
 * - [Compact]: Represents the smallest height size class, starting at 0 dp.
 * - [Medium]: Represents a medium height size class, starting at 480 dp.
 * - [Expanded]: Represents an expanded height size class, starting at 900 dp.
 *
 * These values are used to define breakpoints for adaptive layouts, and are intended to align with
 * the window size class definitions.
 *
 * @see WindowSizeClass
 */
@Suppress("PrimitiveInCollection")
internal object DpHeightSizeClasses {
    /**
     * The lower bound for the Compact height size class. By default, any window height which is at
     * least this value and less than [Medium] will be considered [Compact].
     */
    val Compact = 0.dp

    /**
     * The lower bound for the Medium height size class. By default, any window height which is at
     * least this value and less than [Expanded] will be considered [Medium].
     *
     * @see WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
     */
    val Medium = HEIGHT_DP_MEDIUM_LOWER_BOUND.dp

    /**
     * The lower bound for the Expanded height size class. By default, any window height which is at
     * least this value will be considered [Expanded].
     *
     * @see WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND
     */
    val Expanded = HEIGHT_DP_EXPANDED_LOWER_BOUND.dp
}

/**
 * The lower bound width of the window size class in [Dp]. This is used to determine which size
 * class a given window size belongs to.
 */
internal val WindowSizeClass.minWidth
    get() = minWidthDp.dp

/**
 * The lower bound height of the window size class in [Dp]. This is used to determine which size
 * class a given window size belongs to.
 */
internal val WindowSizeClass.minHeight
    get() = minHeightDp.dp



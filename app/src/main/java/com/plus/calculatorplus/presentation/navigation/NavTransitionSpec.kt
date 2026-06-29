@file:OptIn(ExperimentalAnimationApi::class)

package com.plus.calculatorplus.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.unveilIn
import androidx.compose.animation.veilOut
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent

internal const val DEFAULT_TRANSITION_DURATION_MILLISECOND = 600

/**
 * Default transition specification using a simple fade-in / fade-out animation.
 *
 * @param T The scene type.
 * @return A [ContentTransform] with fade-in and fade-out over the default duration.
 */
fun <T : Any> defaultTransitionSpec():
          AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
    ContentTransform(
        fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
        fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
    )
}

/**
 * Default pop transition specification using a simple fade-in / fade-out animation.
 *
 * @param T The scene type.
 * @return A [ContentTransform] with fade-in and fade-out over the default duration.
 */
fun <T : Any> defaultPopTransitionSpec():
          AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
    ContentTransform(
        fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
        fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
    )
}

/**
 * Default predictive pop transition specification with fade-in and scale-out.
 *
 * @param T The scene type.
 * @return A [ContentTransform] for predictive back gestures.
 */
fun <T : Any> defaultPredictivePopTransitionSpec():
          AnimatedContentTransitionScope<Scene<T>>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform =
    {
        ContentTransform(
            fadeIn(
                spring(
                    dampingRatio = 1.0f, // reflects material3 motionScheme.defaultEffectsSpec()
                    stiffness = 1600.0f, // reflects material3 motionScheme.defaultEffectsSpec()
                )
            ),
            scaleOut(targetScale = 0.7f),
        )
    }

private const val TRANSITION_DURATION_MS = 500
private val IosTransitionEasing = CubicBezierEasing(0.2833f, 0.99f, 0.31833f, 0.99f)

private fun <T> iosTween(
    duration: Int = TRANSITION_DURATION_MS,
    easing: Easing = IosTransitionEasing
) = tween<T>(duration, easing = easing)

/**
 * iOS-style push transition specification (slide left with veil).
 *
 * @param T The scene type.
 * @return A [ContentTransform] mimicking iOS navigation push animation.
 */
fun <T : Any> iosTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform =
    {
        ContentTransform(
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = iosTween(),
            ),
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                targetOffset = { it / 4 },
                animationSpec = iosTween(),
            ) + veilOut(
                animationSpec = iosTween(),
            ),
        )
    }

/**
 * iOS-style pop transition specification (slide right with unveil).
 *
 * @param T The scene type.
 * @return A [ContentTransform] mimicking iOS navigation pop animation.
 */
fun <T : Any> iosPopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform =
    {
        ContentTransform(
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                initialOffset = { it / 4 },
                animationSpec = iosTween(),
            ) + unveilIn(
                animationSpec = iosTween(),
            ),
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = iosTween(),
            ),
        )
    }

/**
 * iOS-style predictive pop transition specification for swipe-back gestures.
 * Uses linear easing during the active gesture and a spring for settling on release.
 *
 * @param T The scene type.
 * @return A [ContentTransform] for iOS-style predictive back gesture.
 */
fun <T : Any> iosPredictivePopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform =
    { edge ->
        val towards = if (edge == NavigationEvent.EDGE_LEFT)
            AnimatedContentTransitionScope.SlideDirection.Right
        else
            AnimatedContentTransitionScope.SlideDirection.Left

        // LinearEasing during the active gesture phase (progress driven).
        // On release, spring provides a natural settle instead of a linear snap.
        val settleAnimSpec = spring<IntOffset>(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )

        ContentTransform(
            slideIntoContainer(
                towards = towards,
                initialOffset = { it / 4 },
                animationSpec = settleAnimSpec,
            ) + unveilIn(
                // veilOut/unveilIn animate a shadow overlay (~0.2–0.35 alpha range)
                // consistent with iOS depth cue — keep alpha range in sync if customizing
                animationSpec = tween(TRANSITION_DURATION_MS, easing = LinearEasing),
            ),
            slideOutOfContainer(
                towards = towards,
                animationSpec = settleAnimSpec,
            ),
        )
    }
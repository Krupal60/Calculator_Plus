package com.plus.calculatorplus.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plus.calculatorplus.presentation.ui.BmiScreenMain
import com.plus.calculatorplus.presentation.ui.CalculatorMain
import com.plus.calculatorplus.presentation.ui.ConvertersScreenMain
import com.plus.calculatorplus.presentation.ui.EmiScreenMain
import com.plus.calculatorplus.presentation.ui.MoreServices
import com.plus.calculatorplus.presentation.ui.SipScreenMain

@Composable
fun navHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, startDestination = Screen.CalculatorScreen.route
    ) {
        composable(Screen.CalculatorScreen.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }) {
            CalculatorMain(paddingValues)
        }
        composable(Screen.MoreScreen.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }) {
            MoreServices(navController, paddingValues)
        }

        composable(Screen.SipScreen.route, enterTransition = {
            slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(500)
            )
        }, exitTransition = {
            slideOutVertically(
                targetOffsetY = { it }, animationSpec = tween( 500)
            )
        }, popEnterTransition = {
            slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(500)
            )
        }, popExitTransition = {
            slideOutVertically(
                targetOffsetY = { it }, animationSpec = tween(  500)
            )
        }) {

            SipScreenMain(paddingValues)
        }

        composable(Screen.BmiScreen.route, enterTransition = {
            slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(500)
            )
        }, exitTransition = {
            slideOutVertically(
                targetOffsetY = { it }, animationSpec = tween( 500)
            )
        }, popEnterTransition = {
            slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(500)
            )
        }, popExitTransition = {
            slideOutVertically(
                targetOffsetY = { it }, animationSpec = tween(  500)
            )
        }) {
            BmiScreenMain(paddingValues)
        }

        composable(Screen.ConverterScreen.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }
        ){
            ConvertersScreenMain()
        }

        composable(Screen.EmiScreen.route
            , enterTransition = {
                slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(500)
                )
            }, exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it }, animationSpec = tween( 500)
                )
            }, popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(500)
                )
            }, popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it }, animationSpec = tween(  500)
                )
            }
        ){
            EmiScreenMain(paddingValues)
        }


    }

}
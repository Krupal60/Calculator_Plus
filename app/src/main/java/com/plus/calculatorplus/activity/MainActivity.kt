package com.plus.calculatorplus.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.navigation.Screen
import com.plus.calculatorplus.presentation.navigation.navHost
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import com.plus.calculatorplus.viewmodel.SplashScreenViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        val viewModel: SplashScreenViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.State.collect {
                    splash.setKeepOnScreenCondition { it }
                }
            }
        }

        setContent {
            CalculatorPlusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface
                ) {
                    val navController = rememberNavController()
                    val currentRoute by navController.currentBackStackEntryAsState()
                    val routeToTitleAndIcon = mapOf(
                        "calculator" to Pair(
                            "Simple Calculator", Icons.Default.Home
                        ),
                        "more" to Pair(
                            "More Calculators",
                            Icons.AutoMirrored.Filled.ArrowBack
                        ),
                        "sip" to Pair(
                            "SIP Calculator",
                            Icons.Default.KeyboardArrowDown
                        ),
                        "bmi" to Pair(
                            "BMI Calculator",
                            Icons.Default.KeyboardArrowDown
                        ),"emi" to Pair(
                            "EMI Calculator",
                            Icons.Default.KeyboardArrowDown
                        ),"converter" to Pair(
                            "Converter tools",
                            Icons.AutoMirrored.Filled.ArrowBack
                        )
                        // Add more routes as needed
                    )
                    Scaffold(topBar = {
                        MyAppBar(
                            currentRoute?.destination?.route,
                            navController,
                            routeToTitleAndIcon
                        )
                    }) {
                        navHost(navController, it)
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    currentRoute: String?,
    navController: NavController,
    routeToTitleAndIcon: Map<String, Pair<String, ImageVector>>
) {
    val (title, icon) = routeToTitleAndIcon[currentRoute] ?: return

    TopAppBar(
        title = {
            Text(
                text = title, fontSize = 18.ssp,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            if (!currentRoute.equals(Screen.CalculatorScreen.route)) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(icon, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (currentRoute.equals(Screen.CalculatorScreen.route)) {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.MoreScreen.route) {
                            launchSingleTop = true
                            popUpTo(Screen.CalculatorScreen.route)
                        }
                    }
                ) {
                    Icon(painterResource(R.drawable.more2), contentDescription = "more")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}


package com.plus.calculatorplus.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import androidx.window.core.layout.WindowSizeClass
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import com.plus.calculatorplus.R
import com.plus.calculatorplus.presentation.navigation.Screen
import com.plus.calculatorplus.presentation.navigation.navHost
import com.plus.calculatorplus.presentation.util.HeightSizeClasses
import com.plus.calculatorplus.presentation.util.WidthSizeClasses
import com.plus.calculatorplus.presentation.util.minHeight
import com.plus.calculatorplus.presentation.util.minWidth
import com.plus.calculatorplus.ui.theme.CalculatorPlusTheme
import com.plus.calculatorplus.viewmodel.SplashScreenViewModel
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var appUpdateManager: AppUpdateManager
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                makeText(
                    this,
                    "Update flow failed! Result code: ${result.resultCode}",
                    LENGTH_LONG
                ).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val splash = installSplashScreen()
        enableEdgeToEdge(
            SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        val viewModel: SplashScreenViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.State.collect {
                    splash.setKeepOnScreenCondition { it }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            val context = LocalContext.current
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
                }
            }
            CalculatorPlusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface
                ) {
                    val manager = ReviewManagerFactory.create(context)
                    val request = manager.requestReviewFlow()
                    request.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val reviewInfo = task.result
                            manager.launchReviewFlow(this, reviewInfo)
                        }
                    }
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
                        ), "emi" to Pair(
                            "EMI Calculator",
                            Icons.Default.KeyboardArrowDown
                        ), "converter" to Pair(
                            "Converter tools",
                            Icons.AutoMirrored.Filled.ArrowBack
                        )
                        // Add more routes as needed
                    )
                    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
                    val isLandscape = with(windowAdaptiveInfo) {
                        if (windowSizeClass.minWidth == WindowSizeClass.WidthSizeClasses.Compact) {
                            false
                        } else if (
                            windowSizeClass.minHeight == WindowSizeClass.HeightSizeClasses.Compact
                        ) {
                            true
                        } else if (
                            windowPosture.isTabletop
                        ) {
                            false
                        } else {
                            false
                        }
                    }
                    Scaffold(topBar = {
                        AnimatedVisibility(!isLandscape) {
                            MyAppBar(
                                currentRoute?.destination?.route,
                                navController,
                                routeToTitleAndIcon
                            )
                        }
                    }, modifier = Modifier.safeDrawingPadding()) {
                        navHost(navController, it)
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
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


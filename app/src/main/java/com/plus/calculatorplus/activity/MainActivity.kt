package com.plus.calculatorplus.activity

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import com.plus.calculatorplus.presentation.icons.calculator
import com.plus.calculatorplus.presentation.icons.more
import com.plus.calculatorplus.presentation.navigation.NavHost
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen
import com.plus.calculatorplus.presentation.navigation.rememberNavigationState
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import com.plus.calculatorplus.presentation.ui.splash.SplashViewModel
import com.plus.calculatorplus.presentation.util.HeightSizeClasses
import com.plus.calculatorplus.presentation.util.WidthSizeClasses
import com.plus.calculatorplus.presentation.util.minHeight
import com.plus.calculatorplus.presentation.util.minWidth
import kotlinx.collections.immutable.persistentSetOf
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
        val splashViewModel: SplashViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.state.collect {
                    splash.setKeepOnScreenCondition { it.isLoading }
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
                enableEdgeToEdge(
                    SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
                    SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    window.isNavigationBarContrastEnforced = false
                }
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

                    val navigationState = rememberNavigationState(
                        startRoute = Screen.CalculatorScreen,
                        topLevelRoutes = persistentSetOf(Screen.CalculatorScreen, Screen.MoreScreen)
                    )
                    val navigator = remember { Navigator(navigationState) }

                    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()

                    fun navigationSuiteType(adaptiveInfo: WindowAdaptiveInfo): NavigationSuiteType {
                        return with(adaptiveInfo) {
                            if (windowPosture.isTabletop || windowSizeClass.minHeight == WindowSizeClass.HeightSizeClasses.Compact) {
                                NavigationSuiteType.NavigationRail
                            } else if (windowSizeClass.minWidth >= WindowSizeClass.WidthSizeClasses.Expanded || windowSizeClass.minWidth == WindowSizeClass.WidthSizeClasses.Medium) {
                                NavigationSuiteType.WideNavigationRailCollapsed
                            } else {
                                NavigationSuiteType.NavigationBar
                            }
                        }
                    }

                    NavigationSuiteScaffold(
                        layoutType = navigationSuiteType(windowAdaptiveInfo),
                        navigationSuiteItems = {
                            item(
                                selected = navigationState.topLevelRoute == Screen.CalculatorScreen,
                                onClick = { navigator.navigate(Screen.CalculatorScreen) },
                                icon = { Icon(calculator, contentDescription = null) },
                                label = { Text("Calculator") }
                            )
                            item(
                                selected = navigationState.topLevelRoute == Screen.MoreScreen,
                                onClick = { navigator.navigate(Screen.MoreScreen) },
                                icon = { Icon(more, contentDescription = null) },
                                label = { Text("Tools") }
                            )
                        },
                        navigationSuiteColors = NavigationSuiteDefaults.colors(
                            navigationBarContainerColor = MaterialTheme.colorScheme.surface,
                            shortNavigationBarContainerColor = MaterialTheme.colorScheme.surface,
                            navigationRailContainerColor = MaterialTheme.colorScheme.surface,
                            wideNavigationRailColors = WideNavigationRailDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    ) {
                        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
                        Scaffold(contentWindowInsets = WindowInsets()) { _ ->
                            NavHost(navigator)
                        }
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



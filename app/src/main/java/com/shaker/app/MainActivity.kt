package com.shaker.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.shaker.app.core.ShakerBaseActivity
import com.shaker.app.presentation.ui.ShakerComposeApp
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.navigation.rememberShakerNavController
import com.shaker.app.presentation.ui.viewmodel.splash.ShakerSplashViewModel

class MainActivity : ShakerBaseActivity() {

    private val splashViewModel by viewModels<ShakerSplashViewModel>()

    private lateinit var rootNavController: ShakerNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)
        splashViewModel.displaySplashScreen()
        setContent {
            rootNavController = rememberShakerNavController()
            ShakerComposeApp(navController = rootNavController)
        }
    }
}
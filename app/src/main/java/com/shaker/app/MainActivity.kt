package com.shaker.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LiveData
import com.shaker.app.core.ShakerBaseActivity
import com.shaker.app.presentation.ui.ShakerComposeApp
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.navigation.rememberShakerNavController
import com.shaker.app.presentation.ui.viewmodel.splash.ShakerSplashViewModel
import com.shaker.domain.result.Failure

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
            ShakerComposeApp(navController = rootNavController, ::onErrorHandler)
        }
    }

    private fun onErrorHandler(errorLiveData: LiveData<List<Failure>>) {
        errorLiveData.observe(this) { errors ->
            if(errors.isNotEmpty()) {
                Toast.makeText(this, getString(R.string.error_happend_label),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}
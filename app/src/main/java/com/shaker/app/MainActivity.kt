package com.shaker.app

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LiveData
import com.shaker.app.core.ShakerBaseActivity
import com.shaker.app.presentation.ui.ShakerComposeApp
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.navigation.rememberShakerNavController
import com.shaker.app.presentation.ui.theme.SemiTransparentBlack
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
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(SemiTransparentBlack.toArgb())
        )
        setContent {
            rootNavController = rememberShakerNavController()
            ShakerComposeApp(navController = rootNavController, ::onErrorHandler)
        }
    }

    private fun onErrorHandler(errorLiveData: LiveData<List<Failure>>) {
        errorLiveData.observe(this) { errors ->
            if(errors.isNotEmpty()) {
                if(errors[0] is Failure.NoDataError) {
                    Toast.makeText(this, getString(R.string.no_data_found_label),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.error_happend_label),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
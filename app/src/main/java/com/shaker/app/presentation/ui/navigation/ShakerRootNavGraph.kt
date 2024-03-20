package com.shaker.app.presentation.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen
import com.shaker.app.presentation.ui.screen.onboarding.ShakerOnBoardingScreen
import com.shaker.app.presentation.ui.viewmodel.onboarding.OnBoardingViewModel
import com.shaker.domain.result.Failure

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    onBoardingViewModel: OnBoardingViewModel,
    navController: ShakerNavController,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    startDestination: String
) {
    NavHost(
        navController = navController.navController,
        startDestination = startDestination
    ) {
        composable(route = ShakerScreen.OnBoarding.route) {
            ShakerOnBoardingScreen(navController = navController, onBoardingViewModel = onBoardingViewModel)
        }
        composable(route = ShakerScreen.HomeRoot.route) {
            ShakerHomeScreen(navController = navController, onErrorHandler = onErrorHandler)
        }
    }
}
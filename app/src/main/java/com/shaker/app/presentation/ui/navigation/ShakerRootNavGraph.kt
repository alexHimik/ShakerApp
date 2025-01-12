package com.shaker.app.presentation.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        startDestination = startDestination,
        modifier = Modifier.navigationBarsPadding()
    ) {
        composable(route = ShakerScreen.OnBoarding.route) {
            ShakerOnBoardingScreen(navController = navController, onBoardingViewModel = onBoardingViewModel)
        }
        composable(route = ShakerScreen.HomeRoot.route) {
            ShakerHomeScreen(rootNavController = navController, onErrorHandler = onErrorHandler)
        }
    }
}
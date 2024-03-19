package com.shaker.app.presentation.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaker.app.presentation.ui.navigation.SetupNavGraph
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.onboarding.OnBoardingViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShakerComposeApp(
    onBoardingViewModel: OnBoardingViewModel = viewModel(),
    navController: ShakerNavController,
) {
    ShakerTheme {
        SetupNavGraph(
            onBoardingViewModel = onBoardingViewModel,
            navController = navController,
            startDestination = onBoardingViewModel.startDestination.value
        )
    }
}
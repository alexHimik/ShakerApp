package com.shaker.app.presentation.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaker.app.presentation.ui.navigation.SetupNavGraph
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.onboarding.OnBoardingViewModel
import com.shaker.domain.result.Failure

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShakerComposeApp(
    navController: ShakerNavController,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel(factory = OnBoardingViewModel.Factory),
) {
    ShakerTheme {
        SetupNavGraph(
            onBoardingViewModel = onBoardingViewModel,
            navController = navController,
            onErrorHandler = onErrorHandler,
            startDestination = onBoardingViewModel.startDestination.value
        )
    }
}
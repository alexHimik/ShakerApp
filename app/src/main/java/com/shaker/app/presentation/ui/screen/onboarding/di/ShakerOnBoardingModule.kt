package com.shaker.app.presentation.ui.screen.onboarding.di

import androidx.lifecycle.ViewModel
import com.shaker.app.presentation.di.android.ViewModelKey
import com.shaker.app.presentation.ui.viewmodel.onboarding.OnBoardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ShakerOnBoardingModule {

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    fun provideOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel
}
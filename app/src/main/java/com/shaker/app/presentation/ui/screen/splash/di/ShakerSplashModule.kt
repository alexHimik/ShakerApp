package com.shaker.app.presentation.ui.screen.splash.di

import androidx.lifecycle.ViewModel
import com.shaker.app.presentation.di.android.ViewModelKey
import com.shaker.app.presentation.ui.viewmodel.splash.ShakerSplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ShakerSplashModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShakerSplashViewModel::class)
    fun provideSplashViewModel(viewModel: ShakerSplashViewModel): ViewModel
}
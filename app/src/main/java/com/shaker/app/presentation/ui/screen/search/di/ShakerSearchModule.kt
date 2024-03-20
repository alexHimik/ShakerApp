package com.shaker.app.presentation.ui.screen.search.di

import androidx.lifecycle.ViewModel
import com.shaker.app.presentation.di.android.ViewModelKey
import com.shaker.app.presentation.ui.viewmodel.search.ShakerSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ShakerSearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShakerSearchViewModel::class)
    fun provideSplashViewModel(viewModel: ShakerSearchViewModel): ViewModel
}
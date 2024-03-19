package com.shaker.app.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import com.shaker.app.presentation.di.android.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
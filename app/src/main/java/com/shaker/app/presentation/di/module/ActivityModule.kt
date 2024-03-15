package com.shaker.app.presentation.di.module

import android.app.Activity
import android.content.Context
import com.shaker.app.presentation.di.scope.PresentationScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PresentationScope
    fun provideActivityContext(): Context = activity
}
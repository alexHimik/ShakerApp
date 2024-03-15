package com.shaker.app.di.module

import android.content.Context
import com.shaker.app.di.scope.ApplicationContext
import com.shaker.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule (private val context: Context) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun provideContext() = context
}
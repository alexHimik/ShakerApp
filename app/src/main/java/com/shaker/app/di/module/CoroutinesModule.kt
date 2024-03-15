package com.shaker.app.di.module

import com.shaker.app.di.scope.ApplicationCoroutineScope
import com.shaker.app.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
class CoroutinesModule {

    @Provides
    @ApplicationCoroutineScope
    @ApplicationScope
    fun applicationCoroutinesScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}

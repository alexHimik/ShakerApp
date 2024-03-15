package com.shaker.data.di.module

import com.shaker.data.di.scope.DataScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
//import com.shaker.data.BuildConfig

@Module
class InterceptorModule {

    @DataScope
    @Provides
    fun provideInterceptors(): List<Interceptor> {
        return buildList {
            //val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            add(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
    }
}
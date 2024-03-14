package com.shaker.data.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.shaker.data.api.ShakerCocktailApi
import com.shaker.data.di.scope.DataScope
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class NetworkModule(
    private val baseUrl: String
) {

    @DataScope
    @Provides
    fun provideHttpClient(appInterceptors: List<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors() += appInterceptors
        }.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @DataScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @DataScope
    @Provides
    fun provideJsonModule() = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = false
        coerceInputValues = true
    }

    @DataScope
    @Provides
    fun provideApi(retrofit: Retrofit): ShakerCocktailApi {
        return retrofit.create(ShakerCocktailApi::class.java)
    }
}
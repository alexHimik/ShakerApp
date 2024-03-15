package com.shaker.data.di.repository

import com.shaker.data.repository.cocktail.datasource.ShakerCocktailDataSource
import com.shaker.data.repository.cocktail.datasource.ShakerCocktailLocalDataSource
import com.shaker.data.repository.cocktail.datasource.ShakerCocktailRemoteDataSource
import dagger.Binds
import dagger.Module

@Module
interface ShakerCocktailDataSourceModule {

    @Binds
    fun provideCocktailRemoteDataSource(remoteSource: ShakerCocktailRemoteDataSource): ShakerCocktailDataSource.Remote

    @Binds
    fun provideCocktailLocalDataSource(localSource: ShakerCocktailLocalDataSource): ShakerCocktailDataSource.Local
}
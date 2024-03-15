package com.shaker.data.di.repository

import com.shaker.data.repository.cocktail.ShakerCocktailRepositoryImpl
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import dagger.Binds
import dagger.Module

@Module(includes = [
    ShakerCocktailDataSourceModule::class
])
interface ShakerCocktailRepositoryModule {

    @Binds
    fun provideShakerCocktailRepository(repo: ShakerCocktailRepositoryImpl): ShakerCocktailRepository
}
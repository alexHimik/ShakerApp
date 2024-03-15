package com.shaker.data.di

import com.shaker.data.di.module.AppModule
import com.shaker.data.di.module.DatabaseModule
import com.shaker.data.di.module.InterceptorModule
import com.shaker.data.di.module.NetworkModule
import com.shaker.data.di.module.StorageModule
import com.shaker.data.di.repository.ShakerCocktailRepositoryModule
import com.shaker.data.di.scope.DataScope
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import dagger.Component

@DataScope
@Component(modules = [
    AppModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    StorageModule::class,
    InterceptorModule::class,
    ShakerCocktailRepositoryModule::class
])
interface ShakerDataComponent {

    fun shakerCocktailRepository(): ShakerCocktailRepository
}
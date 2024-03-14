package com.shaker.data.di

import com.shaker.data.di.module.AppModule
import com.shaker.data.di.module.DatabaseModule
import com.shaker.data.di.module.NetworkModule
import com.shaker.data.di.scope.DataScope
import dagger.Component

@DataScope
@Component(modules = [
    AppModule::class,
    DatabaseModule::class,
    NetworkModule::class,
])
interface DataComponent {
}
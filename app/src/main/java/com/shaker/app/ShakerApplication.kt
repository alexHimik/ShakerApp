package com.shaker.app

import android.app.Application
import com.shaker.app.di.DaggerShakerAppComponent
import com.shaker.app.di.ShakerAppComponent
import com.shaker.app.di.module.ContextModule
import com.shaker.data.di.DaggerShakerDataComponent
import com.shaker.data.di.module.AppModule
import com.shaker.data.di.module.NetworkModule
import com.shaker.data.di.module.StorageModule

class ShakerApplication : Application() {

    val appComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    private fun initializeComponent(): ShakerAppComponent {
        val dataComponent = DaggerShakerDataComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(""))
            .storageModule(StorageModule(this))
            .build()

        return DaggerShakerAppComponent.builder()
            .shakerDataComponent(dataComponent)
            .contextModule(ContextModule(this))
            .build()
    }
}
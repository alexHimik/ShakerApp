package com.shaker.app.di

import com.shaker.app.ShakerApplication
import com.shaker.app.di.module.ContextModule
import com.shaker.app.di.module.CoroutinesModule
import com.shaker.app.di.scope.ApplicationScope
import com.shaker.app.presentation.di.component.PresentationComponent
import com.shaker.app.presentation.di.module.ActivityModule
import com.shaker.data.di.ShakerDataComponent
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        CoroutinesModule::class,
        ContextModule::class,
    ],
    dependencies = [
        ShakerDataComponent::class
    ]
)
interface ShakerAppComponent {

    fun inject(app: ShakerApplication)

    fun presentationComponent(activityModule: ActivityModule): PresentationComponent
}
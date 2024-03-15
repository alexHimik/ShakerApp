package com.shaker.app.presentation.di.component

import com.shaker.app.presentation.di.module.ActivityModule
import com.shaker.app.presentation.di.scope.PresentationScope
import dagger.Subcomponent

@PresentationScope
@Subcomponent(
    modules = [
        ActivityModule::class
    ]
)
interface PresentationComponent {
}